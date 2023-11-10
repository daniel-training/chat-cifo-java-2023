package com.example.chat.model;

import jakarta.persistence.*;

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

// lombok annotations
// @Data // generates getters, setters, equals, hashCode, and toString methods
// @NoArgsConstructor // generates a no-args constructor
// @AllArgsConstructor // generates a constructor with all arguments
@Entity
@Table(name = "MESSAGE") // With @Table can custom the table name
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
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    /**
     * Universally Unique Identifier for the message.
     * <p>
     * This identifier guaranteed the uniqueness. It is safe to identify the entity when exchanged with other external
     * systems.
     * <p>
     * It is read only. Assigned when message created by the constructor.
     */
    @Column(name = "UUID")
    private String uuid;

    /**
     * <code>User</code> to which the message belongs.
     *
     * @see com.example.chat.model.User
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ACCOUNT_ID")
    private User user;

    /**
     * <code>Room</code> where the message belongs.
     *
     * @see com.example.chat.model.Room
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ROOM_ID")
    private Room room;

    /**
     * Content body of the message.
     */
    @Column(name = "CONTENT")
    private String content;

    /**
     * Timestamp in UTC when the message is created.
     * <p>
     * It is read only. Assigned when message created by the constructor.
     */
    @Column(name = "CREATED_AT")
    private Instant createdAt;


    // Getters & Setters

    public Long getId() {
        return id;
    }

//    public void setId(long id) {
//        this.id = id;
//    }

    public String getUuid() {
        return uuid;
    }

//    public void setUuid(String uuid) {
//        this.uuid = uuid;
//    }

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

//    public void setCreatedAt(Instant createdAt) {
//        this.createdAt = createdAt;
//    }

    // Constructors

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

    public Message(Long id, String uuid, User user, Room room, String content, Instant createdAt) {
        this.id = id;
        this.uuid = uuid;
        this.user = user;
        this.room = room;
        this.content = content;
        this.createdAt = createdAt;
    }
}
