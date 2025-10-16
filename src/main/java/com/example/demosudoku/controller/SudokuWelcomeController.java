package com.example.demosudoku.controller;

import com.example.demosudoku.model.user.SessionManager;
import com.example.demosudoku.model.user.User;
import com.example.demosudoku.utils.AlertBox;
import com.example.demosudoku.view.SudokuGameStage;
import com.example.demosudoku.view.SudokuWelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class for managing the welcome screen of the Sudoku application.
 * <p>
 * This controller handles user authentication, navigation to the game and help screens,
 * and session management. It ensures that users provide a valid nickname before
 * starting a game.
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 */
public class SudokuWelcomeController implements Initializable {

    /**
     * Text field for entering the player's nickname.
     * This field is pre-filled with the nickname if a session exists.
     */
    @FXML
    private TextField nicknameTxt;

    /**
     * Handles the play button action event.
     * <p>
     * Validates the entered nickname and starts a new game if valid.
     * If the nickname is empty, displays an error alert. Upon successful validation,
     * creates a user session and transitions to the game screen.
     * </p>
     *
     * @param event The ActionEvent triggered by clicking the play button
     * @throws IOException if the game stage FXML file cannot be loaded
     */
    @FXML
    void handlePlay(ActionEvent event) throws IOException {
        String nickname = nicknameTxt.getText().trim();

        if (!nickname.equals("")) {
            User user = new User(nickname);
            SessionManager.getInstance().setCurrentUser(user);
            SudokuGameStage.getInstance().getController().setUser(user);
            SudokuWelcomeStage.deleteInstance();
        } else {
            new AlertBox().showAlert("Error", "Ingresa un nickname", Alert.AlertType.ERROR);
        }
    }

    /**
     * Handles the help button action event.
     * <p>
     * Closes the welcome window and opens the help screen displaying
     * game rules and instructions.
     * </p>
     *
     * @param event The ActionEvent triggered by clicking the help button
     * @throws IOException if the help stage FXML file cannot be loaded
     */
    @FXML
    void handleHelp(ActionEvent event) throws IOException {
        SudokuWelcomeStage.deleteInstance();
        com.example.demosudoku.view.SudokuHelpStage.getInstance();
    }

    /**
     * Handles the exit button action event.
     * <p>
     * Closes the welcome window and optionally clears the current user session.
     * This terminates the application.
     * </p>
     *
     * @param event The ActionEvent triggered by clicking the exit button
     */
    @FXML
    private void handleExit(ActionEvent event) {
        SudokuWelcomeStage.deleteInstance();
        SessionManager.getInstance().clearSession();
    }

    /**
     * Initializes the controller class after the FXML file has been loaded.
     * <p>
     * If a user session exists, automatically populates the nickname text field
     * with the stored nickname, providing a seamless user experience for
     * returning players.
     * </p>
     *
     * @param url            The location used to resolve relative paths for the root object,
     *                       or {@code null} if the location is not known
     * @param resourceBundle The resources used to localize the root object,
     *                       or {@code null} if the root object was not localized
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            nicknameTxt.setText(user.getNickname());
        }
    }
}