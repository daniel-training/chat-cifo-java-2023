package com.example.chat.model;

import java.time.Instant;

/**
 * Entity class that holds the chat room state.
 * <p>
 * There are three types of chat rooms:
 * <li>System predefined Chat rooms
 * <li>Chat rooms created by registered users.
 * <li>Temporary chat rooms, created by guest users.
 * System predefined and user registered chat rooms are persisted on DB until the owner decides to eliminate them. And
 * Temporary chat rooms are ephemeral ones that are destroyed when the guest user closes the application or connection
 * or when a chat idle policy reached.
 * <p>
 * The system predefined chat rooms are owned by the "System" special role user. The registered user chat rooms are
 * owned by the user that created it. And the temporary chat room are owned by the user "guest" special role.
 *
 * @see com.example.chat.model.User
 */
public class Room {

    // Fields

    /**
     * Identifies the chat room.
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
     * Universally Unique Identifier for the chat room.
     * <p>
     * This identifier guaranteed the uniqueness. It is safe to identify the entity when exchanged with other external
     * systems.
     * <p>
     * It is read only. Assigned when the chat room is going to be created in the DB.
     */
    private String uuid;

    /**
     * Holds the name of the chat room. It is the reference used for the WebSocket subscription endpoint.
     */
    private String title;

    /**
     * Summary about what is discussed in the chat room.
     */
    private String description;

    /**
     * <code>User</code> to which the chat room belongs.
     * <li>The system predefined chat rooms are owned by the "System" special role user.
     * <li>The registered user chat rooms ar owned by the user that created it.
     * <li>The temporary chat room are owned by the user "guest" special role. *
     *
     * @see com.example.chat.model.User
     */
    private User owner;

    /**
     * Timestamp in UTC when the chat room is created.
     * <p>
     * It is read only. Assigned when the chat room is going to be created in the DB.
     */
    private Instant createdAt;

    /**
     * Timestamp in UTC when the chat room is updated.
     * <p>
     * It is read only. Assigned when the chat room is going to be updated in the DB.
     */
    private Instant updatedAt;


    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }


    // Constructors

    public Room(String title, String description, User owner) {
        this.title = title;
        this.description = description;
        this.owner = owner;
    }

    public Room(String title, User owner) {
        this.title = title;
        this.owner = owner;
    }


}
