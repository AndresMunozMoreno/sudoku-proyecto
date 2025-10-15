package com.example.demosudoku.controller;

import com.example.demosudoku.view.SudokuGameStage;
import com.example.demosudoku.view.SudokuHelpStage;
import com.example.demosudoku.view.SudokuWelcomeStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class SudokuHelpController {

    // 🟡 Botón "MENÚ"
    @FXML
    void handleMenu(ActionEvent event) throws IOException {
        SudokuHelpStage.deleteInstance();      // cerrar ventana de reglas
        SudokuWelcomeStage.getInstance();      // volver al menú principal
    }

    // 🟢 Botón "JUGAR AHORA"
    @FXML
    void handlePlay(ActionEvent event) throws IOException {
        SudokuHelpStage.deleteInstance();      // cerrar ventana de reglas
        SudokuGameStage.getInstance();         // abrir el tablero de juego
    }
}
