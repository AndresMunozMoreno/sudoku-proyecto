package com.example.demosudoku.model.game;

import com.example.demosudoku.model.board.Board;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;

/**
 * Abstract base class providing common properties for Sudoku game implementations.
 * <p>
 * This class establishes the foundation for game logic by maintaining references
 * to the board model, UI components, and game interface contract.
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 * @see IGame
 */

public class GameAbstract implements IGame {

    /**
     * The UI grid pane where the Sudoku board is displayed.
     */

    protected GridPane boardGridpane;

    /**
     * The underlying data structure and logic for the Sudoku board.
     */

    protected Board board;

    /**
     * A list of TextFields representing the cells on the board.
     */

    protected ArrayList<TextField> numberFields;

    /**
     * Constructs a GameAbstract instance, initializing core game components.
     *
     * @param boardGridpane The GridPane that will contain the Sudoku cells
     */

    public GameAbstract(GridPane boardGridpane) {
        this.boardGridpane = boardGridpane;
        this.board = new Board();
        this.numberFields = new ArrayList<TextField>();
    }

    /**
     * {@inheritDoc}
     * <p>
     * Default implementation does nothing. Subclasses should override
     * to implement actual game start logic.
     * </p>
     */

    @Override
    public void startGame() {
    }

    /**
     * Returns an empty string for border styling.
     * <p>
     * This method can be overridden by subclasses to provide custom
     * CSS styling for board borders.
     * </p>
     *
     * @return An empty string by default
     */

    public String sudokuBorderStyle() {
        return "";
    }
}
