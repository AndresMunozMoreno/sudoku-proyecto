package com.example.demosudoku.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class SudokuWinStage extends Stage {

    private SudokuWinStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/demosudoku/win-sudoku.fxml")
        );
        Parent root = loader.load();

        Scene scene = new Scene(root);
        setScene(scene);
        initStyle(StageStyle.UNDECORATED);
        setResizable(false);
        initModality(Modality.APPLICATION_MODAL);
        getIcons().add(
                new Image(String.valueOf(getClass().getResource("/com/example/demosudoku/favicon.png")))
        );
    }

    private static class Holder {
        private static SudokuWinStage INSTANCE = null;
    }

    public static SudokuWinStage getInstance() throws IOException {
        if (Holder.INSTANCE == null) {
            Holder.INSTANCE = new SudokuWinStage();
        }
        Holder.INSTANCE.show();
        Holder.INSTANCE.toFront();
        return Holder.INSTANCE;
    }

    public static void deleteInstance() {
    if(Holder.INSTANCE != null) {
        Holder.INSTANCE.close();
        Holder.INSTANCE = null;
    }
    }
}
