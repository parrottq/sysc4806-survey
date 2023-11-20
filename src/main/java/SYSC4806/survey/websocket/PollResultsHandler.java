package SYSC4806.survey.websocket;

import SYSC4806.survey.model.Poll;
import SYSC4806.survey.repository.PollRepository;
import SYSC4806.survey.websocket.model.PollResultsRequest;
import SYSC4806.survey.websocket.model.PollResultsRequestType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PollResultsHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(PollResultsHandler.class);

    private static final String WEBSOCKET_POLL_SUBSCRIPTION_LIST = "sysc4806-survey.survey.websocket.PollResultsHandler.SUBSCRIPTION_LIST";

    private final ConcurrentHashMap<UUID, Set<WebSocketSession>> connections = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PollRepository pollRepository;

    public PollResultsHandler(PollRepository pollRepository) {
        log.info("Listening for poll-results connections...");
        this.pollRepository = pollRepository;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        PollResultsRequest request = objectMapper.readValue(message.getPayload(), PollResultsRequest.class);
        log.info("Client {} ({}) sent {} for {}", session.getId(), session.getRemoteAddress(), request.type, request.pollId);

        if (request.type.equals(PollResultsRequestType.Subscribe)) {
            // Check that the poll exists before adding to the subscriber list
            var pollOpt = this.pollRepository.findById(request.pollId);

            if (pollOpt.isEmpty()) {
                return;
            }
            var poll = pollOpt.get();

            var socketSubscriptions = (HashSet<UUID>) session.getAttributes().computeIfAbsent(WEBSOCKET_POLL_SUBSCRIPTION_LIST, (_key) -> new HashSet<UUID>());
            socketSubscriptions.add(request.pollId);

            // Add the web socket to the subscriber list
            var pollSubscribers = this.connections.computeIfAbsent(request.pollId, (_key) -> Collections.synchronizedSet(new HashSet<>(1)));
            pollSubscribers.add(session);

            // Send the current poll status
            var pollJson = objectMapper.writeValueAsString(poll);
            session.sendMessage(new TextMessage(pollJson));
        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.warn("Client " + session.getId() + " error", exception);
        this.removeSessionFromConnections(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.warn("Object: {}", objectMapper.writeValueAsString(new PollResultsRequest()));
        log.info("Client {} ({}) closed connection with {}", session.getId(), session.getRemoteAddress(), closeStatus);
        this.removeSessionFromConnections(session);
    }

    private void removeSessionFromConnections(WebSocketSession session) {
        var socketSubscriptions = Optional.ofNullable((HashSet<UUID>) session.getAttributes().get(WEBSOCKET_POLL_SUBSCRIPTION_LIST));

        if (socketSubscriptions.isEmpty()) {
            // Client hasn't subscribed to anything
            return;
        }

        // Unsubscribe from all the polls
        for (var subscriptionPollId : socketSubscriptions.get()) {
            var pollSubscribersOpt = Optional.ofNullable(this.connections.get(subscriptionPollId));
            pollSubscribersOpt.ifPresent((pollSubscribers) -> {
                pollSubscribers.remove(session);
            });
        }
    }

    public Optional<Poll> pushPollUpdate(UUID pollId) throws JsonProcessingException {
        log.info("Pushing Poll Updates to poll: {}", pollId);
        var pollOpt = this.pollRepository.findById(pollId);

        var subscribedSessions = this.connections.getOrDefault(pollId, Set.of());
        if (!subscribedSessions.isEmpty() && pollOpt.isPresent()) {
            var pollJson = objectMapper.writeValueAsString(pollOpt.get());
            var msg = new TextMessage(pollJson);

            // Send the current poll status to all subscribers
            subscribedSessions.parallelStream().forEach((session) -> {
                try {
                    session.sendMessage(msg);
                } catch (IOException e) {
                    log.warn("Failed to send poll update message", e);
                    removeSessionFromConnections(session);
                }
            });
        }

        return pollOpt;
    }
}
