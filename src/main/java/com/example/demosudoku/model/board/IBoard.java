package com.example.demosudoku.model.board;

/**
 * Defines the contract for a Sudoku board. Implementations must provide
 * methods for filling blocks and validating number placements.
 */
public interface IBoard {
    /**
     * Checks if placing a candidate number at a given position is valid.
     *
     * @param row       The row index of the cell.
     * @param col       The column index of the cell.
     * @param candidate The number to validate.
     * @return {@code true} if the move is valid, {@code false} otherwise.
     */
    boolean isValid(int row, int col, int candidate);

}
