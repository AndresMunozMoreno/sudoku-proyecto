package com.example.demosudoku.controller;

import com.example.demosudoku.view.SudokuHelpStage;
import com.example.demosudoku.model.user.SessionManager;
import com.example.demosudoku.view.SudokuWelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

/**
 * Controller class for managing the help screen of the Sudoku application.
 * <p>
 * This controller handles navigation from the help screen to other screens,
 * allowing users to return to the main menu or start playing directly
 * from the help window.
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 */
public class SudokuHelpController {

    /**
     * Handles the menu button action event.
     * <p>
     * Closes the help window and returns to the welcome screen.
     * The user's nickname is preserved if a session exists.
     * </p>
     *
     * @param event The ActionEvent triggered by clicking the menu button
     * @throws IOException if the welcome stage FXML file cannot be loaded
     */
    @FXML
    void handleMenu(ActionEvent event) throws IOException {
        SudokuHelpStage.deleteInstance();
        SudokuWelcomeStage.getInstance();
    }

    /**
     * Handles the play button action event.
     * <p>
     * Closes the help window and starts a new game. If the player already
     * has a stored user session, the game starts without requesting
     * credentials again.
     * </p>
     *
     * @param event The ActionEvent triggered by clicking the play button
     * @throws IOException if the game stage FXML file cannot be loaded
     */
    @FXML
    void handlePlay(ActionEvent event) {
        try {

            if (!SessionManager.getInstance().hasUser()) {
                new com.example.demosudoku.utils.AlertBox().showAlert(
                        "Error",
                        "Debes ingresar un nickname antes de jugar",
                        javafx.scene.control.Alert.AlertType.ERROR
                );
                return;
            }


            com.example.demosudoku.view.SudokuHelpStage.deleteInstance();
            com.example.demosudoku.view.SudokuGameStage.getInstance();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}