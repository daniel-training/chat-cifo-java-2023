package com.example.chat.model;

import java.time.Instant;

/**
 * Entity class that holds the chat message state.
 * <p>>
 * Under the WebSocket communications, it is used to map message from client side to server side and vice versa.
 * <p>
 * And it is a persistence entity that maps to the records of the message table in the DB [NOTE: Pending
 * implementation]
 * <p>
 * A message belongs to a <code>User</code> and a chat <code>Room</code>
 *
 * @see com.example.chat.model.User
 * @see com.example.chat.model.Room
 */
public class Message {


    // Fields

    /**
     * Identifies the message.
     * <p>
     * It is read only and provided by the DB at record creation time.
     * <p>
     * The id is mainly used for relationships operations in the the DB. Can be used extensively for internally
     * application operations, but be aware that uniqueness is only guaranteed within the scope of this application and
     * in relation with the single DB source. If the object is exchanged with external systems use the <code>uuid</code>
     * for identification.
     */
    private int id;

    /**
     * Universally Unique Identifier for the message.
     * <p>
     * This identifier guaranteed the uniqueness. It is safe to identify the entity when exchanged with other external
     * systems.
     * <p>
     * It is read only. Assigned when message created by the constructor.
     */
    private String uuid;

    /**
     * <code>User</code> to which the message belongs.
     *
     * @see com.example.chat.model.User
     */
    private User user;

    /**
     * <code>Room</code> where the message belongs.
     *
     * @see com.example.chat.model.Room
     */
    private Room room;

    /**
     * Content body of the message.
     */
    private String content;

    /**
     * Timestamp in UTC when the message is created.
     * <p>
     * It is read only. Assigned when message created by the constructor.
     */
    private Instant createdAt;


    // Getters & Setters

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }


    // Constructors

    /**
     * Explicit default constructor. It is need to prevent deserialize errors when mapping from client side json to
     * Message and the sprint framework converter can not deduce a specialized constructor, then it will use the default
     * constructor.
     * <p>
     * TODO: Investigate, there has to be a way to tell spring boot to explicitly use a given constructor, instead of
     *      the mapping process trying to infer one on its own.
     */
    public Message() {}

    public Message(String content, User user) {
        this.content = content;
        this.user = user;
    }

    public Message(String content, User user, Room room) {
        this.content = content;
        this.user = user;
        this.room = room;
    }


}
