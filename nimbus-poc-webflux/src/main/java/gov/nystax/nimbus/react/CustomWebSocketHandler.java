package gov.nystax.nimbus.react;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

@Component
public class CustomWebSocketHandler implements WebSocketHandler {

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        // Echo incoming messages back to the client
        return session.send(
                session.receive()
                       .map(msg -> "Echo: " + msg.getPayloadAsText())
                       .map(session::textMessage)
        );
    }
}
