package com.example.demosudoku.controller;

import com.example.demosudoku.view.SudokuGameStage;
import com.example.demosudoku.view.SudokuHelpStage;
import com.example.demosudoku.view.SudokuWelcomeStage;
import com.example.demosudoku.view.SudokuWinStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;

public class SudokuWinController {
    @FXML
    void handleMenu(ActionEvent event) throws IOException {
        SudokuWinStage.deleteInstance();
        SudokuWelcomeStage.getInstance();
    }
    @FXML
    void handlePlay(ActionEvent event) throws IOException {
        SudokuWinStage.deleteInstance();
        SudokuGameStage.getInstance();
    }
}
