package SYSC4806.survey.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final PollResultsHandler pollResultsHandler;

    public WebSocketConfig(PollResultsHandler pollResultsHandler) {
        this.pollResultsHandler = pollResultsHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Register the url route to the websocket
        registry.addHandler(this.pollResultsHandler, "/poll/results-stream");
    }
}
