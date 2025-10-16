package com.example.demosudoku.model.game;

/**
 * Interface defining the contract for game implementations.
 * <p>
 * Any class that implements this interface must provide functionality
 * to initialize and start game logic.
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 */
public interface IGame {
    /**
     * Initializes and starts the game logic.
     * <p>
     * Implementations should set up the game board, initialize UI components,
     * and prepare the game for player interaction.
     * </p>
     */
    void startGame();
}