package com.example.demosudoku.model.user;

/**
 * Singleton class that manages the current player's session.
 * <p>
 * This class provides global access to the currently logged-in user,
 * allowing different parts of the application to access and modify
 * user session data without passing references explicitly.
 * </p>
 * <p>
 * The singleton pattern ensures only one session manager exists throughout
 * the application lifecycle.
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 */
public class SessionManager {

    /**
     * The single instance of SessionManager (lazy initialization).
     */

    private static SessionManager instance;

    /**
     * The currently logged-in user, or {@code null} if no session exists.
     */

    private User currentUser;

    /**
     * Private constructor to prevent direct instantiation.
     * <p>
     * Use {@link #getInstance()} to obtain the singleton instance.
     * </p>
     */

    private SessionManager() {}

    /**
     * Returns the singleton instance of SessionManager.
     * <p>
     * Creates the instance on first call (lazy initialization).
     * Thread-safe for single-threaded JavaFX applications.
     * </p>
     *
     * @return The singleton SessionManager instance
     */

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    /**
     * Returns the currently logged-in user.
     *
     * @return The current User object, or {@code null} if no user is logged in
     */

    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets the current user session.
     *
     * @param user The User object to set as the current session
     */

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Clears the current user session by setting it to {@code null}.
     * <p>
     * This method should be called when logging out or exiting the application.
     * </p>
     */

    public void clearSession() {
        currentUser = null;
    }
    /**
     * Checks if there is a current user with a valid nickname.
     *
     * @return true if a user is logged in and has a non-empty nickname, false otherwise
     */
    public boolean hasUser() {
        return currentUser != null &&
                currentUser.getNickname() != null &&
                !currentUser.getNickname().isBlank();
    }

}
