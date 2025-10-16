package com.example.demosudoku.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Singleton Stage for the help/rules window.
 * <p>
 * This class displays game instructions and Sudoku rules in a modal window,
 * blocking interaction with other windows until dismissed. It ensures only
 * one help window can be open at any time. The stage is configured without
 * window decorations for a clean, modern appearance.
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 */
public class SudokuWelcomeStage extends Stage {

    /**
     * Private constructor to enforce the singleton pattern.
     * <p>
     * Loads the help screen FXML, sets up the scene, configures stage properties
     * including modal behavior and removal of window decorations, and prepares
     * the stage for display.
     * </p>
     *
     * @throws IOException if the FXML file cannot be loaded
     */

    private SudokuWelcomeStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/demosudoku/menu.sudoku.fxml")
        );
        Parent root = loader.load();

        Scene scene = new Scene(root);
        setScene(scene);
        initStyle(StageStyle.UNDECORATED);
        setResizable(false);
        getIcons().add(
                new Image(String.valueOf(getClass().getResource("/com/example/demosudoku/favicon.png")))
        );
        show();
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

        private static SudokuWelcomeStage INSTANCE = null;
    }

    /**
     * Provides global access to the singleton SudokuHelpStage instance.
     * <p>
     * Creates the instance on first call if it doesn't exist. If the instance
     * already exists, brings it to the front and shows it. This ensures only
     * one help window is open at any time.
     * </p>
     *
     * @return The single instance of SudokuHelpStage
     * @throws IOException if the FXML file cannot be loaded during first creation
     */
    public static SudokuWelcomeStage getInstance() throws IOException {
        Holder.INSTANCE = Holder.INSTANCE != null ?
                Holder.INSTANCE : new SudokuWelcomeStage();
        return Holder.INSTANCE;
    }

    /**
     * Closes the stage and destroys the singleton instance.
     * <p>
     * This method safely closes the help window if it exists and resets
     * the singleton state to allow future instantiation.
     * </p>
     */
    public static void deleteInstance() {
        Holder.INSTANCE.close();
        Holder.INSTANCE = null;
    }
}
