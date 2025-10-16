package com.example.demosudoku.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * A singleton Stage for the Help window.
 * Opens as a separate modal window to show help information.
 */
public class SudokuHelpStage extends Stage {

    private SudokuHelpStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/demosudoku/rules-sudoku.fxml")
        );
        Parent root = loader.load();

        Scene scene = new Scene(root);
        setScene(scene);
        initStyle(StageStyle.UNDECORATED);
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL); // Bloquea la ventana principal hasta cerrar ayuda
        getIcons().add(
                new Image(String.valueOf(getClass().getResource("/com/example/demosudoku/favicon.png")))
        );
    }

    private static class Holder {
        private static SudokuHelpStage INSTANCE = null;
    }

    public static SudokuHelpStage getInstance() throws IOException {
        if (Holder.INSTANCE == null) {
            Holder.INSTANCE = new SudokuHelpStage();
        }
        Holder.INSTANCE.show();
        Holder.INSTANCE.toFront();
        return Holder.INSTANCE;
    }

    public static void deleteInstance() {
        if (Holder.INSTANCE != null) {
            Holder.INSTANCE.close();
            Holder.INSTANCE = null;
        }
    }
}

