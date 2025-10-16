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
 * Singleton Stage for the welcome/main menu window.
 * <p>
 * This class manages the application's entry point, providing options to
 * start a game, view help, or exit. It ensures only one welcome window
 * exists at any time and maintains session state across navigation.
 * The stage is configured without window decorations for a modern appearance.
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 */

public class SudokuHelpStage extends Stage {

    /**
     * Private constructor to enforce the singleton pattern.
     * <p>
     * Loads the welcome screen FXML, sets up the scene, configures stage properties
     * including removing window decorations, and displays the stage.
     * </p>
     *
     * @throws IOException if the FXML file cannot be loaded
     */

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

        private static SudokuHelpStage INSTANCE = null;
    }

    /**
     * Provides global access to the singleton SudokuWelcomeStage instance.
     * <p>
     * Creates the instance on first call if it doesn't exist (lazy initialization).
     * Subsequent calls return the existing instance without creating duplicates.
     * </p>
     *
     * @return The single instance of SudokuWelcomeStage
     * @throws IOException if the FXML file cannot be loaded during first creation
     */

    public static SudokuHelpStage getInstance() throws IOException {
        if (Holder.INSTANCE == null) {
            Holder.INSTANCE = new SudokuHelpStage();
        }
        Holder.INSTANCE.show();
        Holder.INSTANCE.toFront();
        return Holder.INSTANCE;
    }

    /**
     * Closes the stage and destroys the singleton instance.
     * <p>
     * This method should be called when navigating to other screens
     * to properly clean up resources and reset the singleton state.
     * </p>
     */

    public static void deleteInstance() {
        if (Holder.INSTANCE != null) {
            Holder.INSTANCE.close();
            Holder.INSTANCE = null;
        }
    }
}

