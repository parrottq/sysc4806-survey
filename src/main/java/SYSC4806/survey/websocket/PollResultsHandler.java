package SYSC4806.survey.websocket;

import SYSC4806.survey.websocket.model.PollResultsRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PollResultsHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(PollResultsHandler.class);

    private final ConcurrentHashMap<UUID, List<WebSocketSession>> connections = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PollResultsHandler() {
        log.info("Listening for poll-results connections...");
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        PollResultsRequest request = objectMapper.readValue(message.getPayload(), PollResultsRequest.class);
        log.info("Client {} ({}) sent {} for {}", session.getId(), session.getRemoteAddress(), request.type, request.pollId);

        // TODO: Make this do something more interesting
        var response = new TextMessage("echo");
        session.sendMessage(response);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.warn("Client " + session.getId() + " error", exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        log.info("Client {} ({}) closed connection with {}", session.getId(), session.getRemoteAddress(), closeStatus);
    }
}
