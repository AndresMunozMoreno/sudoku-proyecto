package com.example.demosudoku.controller;

import com.example.demosudoku.view.SudokuHelpStage;
import com.example.demosudoku.view.SudokuWelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class SudokuHelpController {

    @FXML
    void handleMenu(ActionEvent event) throws IOException {
        SudokuHelpStage.deleteInstance();
        SudokuWelcomeStage.getInstance(); // 👉 se reabre el menú con el nickname conservado
    }

    @FXML
    void handlePlay(ActionEvent event) throws IOException {
        SudokuHelpStage.deleteInstance();
        // Si el jugador ya tiene user guardado, no hace falta pedirlo otra vez
        com.example.demosudoku.view.SudokuGameStage.getInstance();
    }
}

