package com.example.demosudoku;

import com.example.demosudoku.view.SudokuWelcomeStage;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The main entry point class for the Sudoku JavaFX application.
 * <p>
 * This class extends {@link javafx.application.Application} and serves as the
 * launcher for the entire Sudoku game. It initializes the JavaFX runtime and
 * displays the welcome screen as the starting point of the application.
 * </p>
 * <p>
 * The application follows the MVC (Model-View-Controller) architectural pattern
 * and implements the Singleton design pattern for window management.
 * </p>
 *
 * @author Your Name
 * @version 1.0
 * @since 2025-01-15
 */
public class Main extends Application {

    /**
     * The main entry point for the Java application.
     * <p>
     * This method launches the JavaFX runtime by calling {@link Application#launch(String...)}.
     * The JavaFX platform then calls the {@link #start(Stage)} method on the JavaFX
     * Application Thread.
     * </p>
     *
     * @param args Command line arguments passed to the application.
     *             These are forwarded to the JavaFX launch method.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * The main entry point for the JavaFX application.
     * <p>
     * This method is called by the JavaFX platform after the {@link #init()} method
     * has returned and the system is ready for the application to begin running.
     * It initializes and displays the welcome screen of the Sudoku game.
     * </p>
     * <p>
     * The provided {@code primaryStage} parameter is not used in this implementation
     * because the application uses custom singleton Stage classes for window management.
     * Instead, {@link SudokuWelcomeStage#getInstance()} creates and displays the
     * initial window.
     * </p>
     *
     * @param primaryStage The primary stage provided by the JavaFX platform.
     *                     This parameter is not used in the current implementation
     *                     as custom Stage singletons are employed instead.
     * @throws IOException If the FXML file for the welcome stage cannot be loaded.
     *                     This typically indicates missing or incorrectly named
     *                     resource files in the classpath.
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        SudokuWelcomeStage.getInstance();
    }
}