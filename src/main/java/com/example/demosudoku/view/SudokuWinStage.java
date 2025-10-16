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
 * Singleton Stage for the victory/win window.
 * <p>
 * This class displays a congratulatory message when the player successfully
 * completes a Sudoku puzzle. It appears as a modal dialog, blocking interaction
 * with the game window until the player chooses to start a new game or return
 * to the menu. The stage is configured without window decorations for a
 * streamlined celebration screen.
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 */

public class SudokuWinStage extends Stage {

    /**
     * Private constructor to enforce the singleton pattern.
     * <p>
     * Loads the victory screen FXML, sets up the scene, configures stage properties
     * including modal behavior and removal of window decorations, and prepares
     * the stage for display.
     * </p>
     *
     * @throws IOException if the FXML file cannot be loaded
     */

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

    /**
     * Inner static holder class for lazy singleton initialization.
     * <p>
     * This pattern ensures thread-safe lazy loading without requiring
     * explicit synchronization.
     * </p>
     */

    private static class Holder {

        /**
         * The singleton instance, initially {@code null}.
         */

        private static SudokuWinStage INSTANCE = null;
    }

    /**
     * Provides global access to the singleton SudokuWinStage instance.
     * <p>
     * Creates the instance on first call if it doesn't exist. If the instance
     * already exists, brings it to the front and shows it. This prevents multiple
     * victory windows from appearing simultaneously.
     * </p>
     *
     * @return The single instance of SudokuWinStage
     * @throws IOException if the FXML file cannot be loaded during first creation
     */

    public static SudokuWinStage getInstance() throws IOException {
        if (Holder.INSTANCE == null) {
            Holder.INSTANCE = new SudokuWinStage();
        }
        Holder.INSTANCE.show();
        Holder.INSTANCE.toFront();
        return Holder.INSTANCE;
    }

    /**
     * Closes the stage and destroys the singleton instance.
     * <p>
     * This method safely closes the victory window if it exists and resets
     * the singleton state to allow future instantiation when another game
     * is completed.
     * </p>
     */

    public static void deleteInstance() {
    if(Holder.INSTANCE != null) {
        Holder.INSTANCE.close();
        Holder.INSTANCE = null;
    }
    }
}
