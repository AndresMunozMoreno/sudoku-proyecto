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

public class SudokuWelcomeController implements Initializable {

    @FXML
    private TextField nicknameTxt;

    @FXML
    void handlePlay(ActionEvent event) throws IOException {
        String nickname = nicknameTxt.getText().trim();

        if (!nickname.equals("")) {
            User user = new User(nickname);
            SessionManager.getInstance().setCurrentUser(user); // ✅ guarda globalmente
            SudokuGameStage.getInstance().getController().setUser(user);
            SudokuWelcomeStage.deleteInstance();
        } else {
            new AlertBox().showAlert("Error", "Ingresa un nickname", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void handleHelp(ActionEvent event) throws IOException {
        SudokuWelcomeStage.deleteInstance();
        com.example.demosudoku.view.SudokuHelpStage.getInstance();
    }

    @FXML
    private void handleExit(ActionEvent event) {
        SudokuWelcomeStage.deleteInstance();
        SessionManager.getInstance().clearSession(); // opcional si quieres cerrar sesión
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // ✅ Si ya hay un usuario, muestra su nickname automáticamente
        User user = SessionManager.getInstance().getCurrentUser();
        if (user != null) {
            nicknameTxt.setText(user.getNickname());
        }
    }
}



