package com.example.demosudoku.controller;

import com.example.demosudoku.model.game.Game;
import com.example.demosudoku.model.user.SessionManager;
import com.example.demosudoku.model.user.User;
import com.example.demosudoku.view.SudokuWelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for managing the main Sudoku game view.
 * <p>
 * This controller is responsible for initializing and managing the game board's user interface,
 * handling user interactions such as menu navigation and hint requests, and maintaining
 * the connection between the view and the game model.
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 */
public class SudokuGameController implements Initializable {

    /**
     * The GridPane element from the FXML file that holds the Sudoku board cells.
     * This component displays the 6x6 grid where players interact with the game.
     */
    @FXML
    private GridPane boardGridPane;

    /**
     * The game instance that manages the Sudoku logic and board state.
     */
    private Game game;

    /**
     * The user object representing the current player.
     */
    private User user;

    /**
     * Initializes the controller class after the FXML file has been loaded.
     * <p>
     * This method is automatically called by the JavaFX framework. It creates a new
     * game instance and starts the game by rendering the board on the GridPane.
     * </p>
     *
     * @param url            The location used to resolve relative paths for the root object,
     *                       or {@code null} if the location is not known
     * @param resourceBundle The resources used to localize the root object,
     *                       or {@code null} if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        game = new Game(boardGridPane);
        game.startGame();
    }

    /**
     * Sets the user for the current game session.
     * <p>
     * This method is called by the welcome controller to pass the authenticated
     * user's data to the game controller, allowing for personalized gameplay.
     * </p>
     *
     * @param user The user object containing player information such as nickname
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Handles the menu button action event.
     * <p>
     * Closes the current game window and returns the player to the welcome screen.
     * Any unsaved game progress will be lost.
     * </p>
     *
     * @param event The ActionEvent triggered by clicking the menu button
     */
    @FXML
    private void handleMenu(ActionEvent event) {
        com.example.demosudoku.view.SudokuGameStage.deleteInstance();

        try {
            com.example.demosudoku.view.SudokuWelcomeStage.getInstance();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the hint button action event.
     * <p>
     * Requests the game to provide a hint by revealing one correct number
     * in an empty or incorrect cell. The hint is displayed in a different color
     * to distinguish it from user inputs.
     * </p>
     *
     * @param event The ActionEvent triggered by clicking the hint button
     */
    @FXML
    private void handleHint(ActionEvent event) {
        game.provideHelp();
    }
}