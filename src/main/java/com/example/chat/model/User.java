package com.example.chat.model;

import java.time.Instant;

/**
 * Entity class that holds the User state.
 * <p>
 * There are four types of users defined by their role:
 * <li>System user ("SYSTEM" role)
 * <li>Admin User ("ADMIN" role)
 * <li>Registered user ("USER" role)
 * <li>Guest User ("GUEST" role)
 * <p>
 * The system user is a special user who owns entities and performs operations from the point of view of the application
 * system. Admin users are authorized to manage the Chat Admin Website. The chat application is used by registered users
 * and guests who create their own messages in the chat rooms. Registered users are the users who have registered to use
 * the application, this gives the user some advantages like a reserved nickname and can create and own permanent chat
 * rooms. Guest users are temporary users who use the chat application without registering and are destroyed when the
 * user closes the application or connection or when a chat inactivity policy is reached. Guest users do not have any
 * reserved nicknames and cannot create persistent chat rooms, but temporary ones that are available tied to the guest's
 * existence, that is, until the guest user closes the application or connection or when a chat inactivity policy is
 * reached.
 *
 * @see Role
 * @see com.example.chat.model.Room
 * @see com.example.chat.model.Message
 */
public class User {


// Constants & Enums


    /**
     * A user can have one of the following roles:
     *
     * <li>SYSTEM: role for the special "system" user.
     * <li>ADMIN: role for Admin users authorized to manage Application Admin Website.
     * <li>USER: role for registered users that uses the application.
     * <li>GUEST: role for temporal guest users that uses the application without registering.
     * <p>
     * System is the role for the special user system, who owns entities and performs operations from the system
     * application point of view. The admin role is assigned to admin users that are authorized to manage the Chat Admin
     * Website. The user role are for users who have registered to use the application, this gives the user some
     * advantages for the application use. Guest users are temporary users who use the chat application without
     * registering and are destroyed when the user closes the application or connection or when a chat inactivity policy
     * is reached.
     */
    public enum Role {
        SYSTEM,
        ADMIN,
        USER,
        GUEST
    }


// Fields

    /**
     * Identifies the user.
     * <p>
     * It is read only and provided by the DB at record creation time.
     * <p>
     * The id is mainly used for relationships operations in the DB. Can be used extensively for internally
     * application operations, but be aware that uniqueness is only guaranteed within the scope of this application and
     * in relation with the single DB source. If the object is exchanged with external systems use the <code>uuid</code>
     * for identification.
     */
    private int id;

    /**
     * Universally Unique Identifier for the user.
     * <p>
     * This identifier guaranteed the uniqueness. It is safe to identify the entity when exchanged with other external
     * systems.
     * <p>
     * It is read only. Assigned when the user is going to be created in the DB.
     */
    private String uuid;

    /**
     * Specifies the role of the user. There are four roles: SYSTEM, ADMIN, USER and GUEST
     *
     * @see Role
     */
    //TODO: @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * The name of the user
     */
    private String name;

    /**
     * The surname of the user
     */
    private String surname;

    /**
     * The nickname of the user, used as reference during chat session, is used to set the message's sender.
     */
    private String nickname;

    /**
     * The email of the user, is used in the sign-in process.
     * TODO: authentication & authorization are pending to implement
     */
    private String email;

    /**
     * Allows to establish whether a user is operational or not. Allows to activate or deactivate the account without
     * deleting it, for example to ban sign-in a conflicting user or forbid an administrator from accessing the Admin
     * Website.
     */
    private boolean active;

    /**
     * Timestamp in UTC when the user is created.
     * <p>
     * It is read only. Assigned when the user is going to be created in the DB.
     */
    private Instant createdAt;

    /**
     * Timestamp in UTC when the user is updated.
     * <p>
     * It is read only. Assigned when the user is going to be updated in the DB.
     */
    private Instant updatedAt;


    // Getters & Setters

    public int getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }


    // Constructors

    /**
     * Class constructor specifying only the nickname. Therefor is a GUEST user.
     *
     * @param nickname provide the nickname of the user
     */
    public User(String nickname) {
        this.nickname = nickname;
        this.role = Role.GUEST;
    }

    public User(Role role, String name, String surname, String nickname, String email) {
        this.role = role;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.email = email;
    }

}
