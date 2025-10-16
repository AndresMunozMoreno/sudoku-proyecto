package com.example.demosudoku.view;

import com.example.demosudoku.controller.SudokuGameController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
/**
 * Singleton Stage for the main Sudoku game window.
 * <p>
 * This class ensures that only one instance of the game window exists at any time,
 * preventing multiple game sessions from running simultaneously. The stage is
 * configured without window decorations for a custom, immersive gaming experience.
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 */


public class SudokuGameStage extends Stage {

    /**
     * The controller associated with this stage's FXML view.
     */

    private SudokuGameController controller;

    /**
     * Private constructor to enforce the singleton pattern.
     * <p>
     * Loads the FXML view, sets up the scene, configures stage properties
     * including removing window decorations, and displays the stage.
     * </p>
     *
     * @throws IOException if the FXML file cannot be loaded
     */

    private SudokuGameStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/example/demosudoku/board-sudoku.fxml")
        );
        Parent root = loader.load();
        controller = loader.getController();

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
     * Returns the controller associated with this stage's view.
     * <p>
     * Provides access to the game controller for setting user data
     * and managing game state.
     * </p>
     *
     * @return The SudokuGameController instance
     */

    public SudokuGameController getController() {
        return controller;
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


        private static SudokuGameStage INSTANCE = null;
    }

    /**
     * Provides global access to the singleton SudokuGameStage instance.
     * <p>
     * Creates the instance on first call if it doesn't exist (lazy initialization).
     * Subsequent calls return the existing instance.
     * </p>
     *
     * @return The single instance of SudokuGameStage
     * @throws IOException if the FXML file cannot be loaded during first creation
     */

    public static SudokuGameStage getInstance() throws IOException {
        SudokuGameStage.Holder.INSTANCE = SudokuGameStage.Holder.INSTANCE != null ?
                SudokuGameStage.Holder.INSTANCE : new SudokuGameStage();
        return SudokuGameStage.Holder.INSTANCE;
    }

    /**
     * Closes the stage and destroys the singleton instance.
     * <p>
     * This method should be called when navigating away from the game
     * to properly clean up resources and reset the singleton state.
     * </p>
     */

    public static void deleteInstance() {
        SudokuGameStage.Holder.INSTANCE.close();
        Holder.INSTANCE = null;
    }
}
