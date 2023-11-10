package com.example.chat.webSocketController;

import com.example.chat.model.Message;
import com.example.chat.model.Room;
import com.example.chat.model.User;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;


/**
 * Controller Class to WebSocket message handling.
 * <p>
 * Messages are routed to the methods of the controller that maps with <code>@MessageMapping</code> annotation the
 * endpoint of destination for incoming messages. Then applies any necessary logic for each case and returns the result
 * message that is broadcast to all subscribers to the endpoint mapped with the <code>@SendTo</code> annotation
 */
@Controller
public class RoomWebSocketController {

    /**
     * Handles the incoming message sent from the chat room, formats it including the sender and broadcasts the
     * message to the subscribers of the corresponding chat room.
     *
     * @param room    chat room where the message belongs. Is part of the endpoint path.
     * @param message incoming message from client sender
     * @return the message to broadcast formatted with including the sender
     * @throws Exception TODO ...
     */
    @MessageMapping("/chat/{room}")
    @SendTo("/topic/{room}")
    public Message inboundMessage(@DestinationVariable String room, Message message) throws Exception {

        // NOTE: In this version all users are GUEST. Sets as sender and broadcast the message.
        //       Following versions will add logic to users, chat rooms and persistence for the messages.
        User user = message.getUser();

        return new Message(String.format("[%s] %s", user.getNickname(), HtmlUtils.htmlEscape(message.getContent())),
                user,
                new Room(room, user));

    }

}
