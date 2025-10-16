package com.example.demosudoku.controller;

import com.example.demosudoku.view.SudokuGameStage;
import com.example.demosudoku.view.SudokuWelcomeStage;
import com.example.demosudoku.view.SudokuWinStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

/**
 * Controller class for managing the victory screen of the Sudoku application.
 * <p>
 * This controller is displayed when a player successfully completes a Sudoku puzzle.
 * It provides options to return to the main menu or start a new game.
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 */
public class SudokuWinController {

    /**
     * Handles the menu button action event.
     * <p>
     * Closes the victory window and returns the player to the welcome screen,
     * allowing them to exit or start a fresh session.
     * </p>
     *
     * @param event The ActionEvent triggered by clicking the menu button
     * @throws IOException if the welcome stage FXML file cannot be loaded
     */
    @FXML
    void handleMenu(ActionEvent event) throws IOException {
        SudokuWinStage.deleteInstance();
        SudokuWelcomeStage.getInstance();
    }

    /**
     * Handles the play button action event.
     * <p>
     * Closes the victory window and immediately starts a new game with a
     * freshly generated Sudoku puzzle, maintaining the current user session.
     * </p>
     *
     * @param event The ActionEvent triggered by clicking the play again button
     * @throws IOException if the game stage FXML file cannot be loaded
     */
    @FXML
    void handlePlay(ActionEvent event) throws IOException {
        SudokuWinStage.deleteInstance();
        SudokuGameStage.getInstance();
    }
}