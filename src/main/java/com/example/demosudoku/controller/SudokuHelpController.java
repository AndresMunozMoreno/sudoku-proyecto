package com.example.demosudoku.controller;

import com.example.demosudoku.view.SudokuHelpStage;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;

import java.io.IOException;

public class SudokuHelpController {

    @FXML
    void handleClose(ActionEvent event) {
        SudokuHelpStage.deleteInstance();
    }
}
