package com.example.demosudoku.model.user;
/**
 * Represents a player in the Sudoku application.
 * <p>
 * This class stores basic user information and can be extended
 * to include additional player data such as scores, statistics, or preferences.
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 */
public class User {

    /**
     * The player's chosen nickname.
     */

    private String nickname;

    /**
     * Constructs a new User with the specified nickname.
     *
     * @param nickname The player's nickname
     */

    public User(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns the player's nickname.
     *
     * @return The current nickname
     */

    public String getNickname() {
        return nickname;
    }

    /**
     * Sets a new nickname for the player.
     *
     * @param nickname The new nickname to set
     */

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
