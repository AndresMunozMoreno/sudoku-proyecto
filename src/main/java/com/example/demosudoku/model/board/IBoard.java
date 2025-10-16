package com.example.demosudoku.model.board;
/**
 * Interface defining the contract for a Sudoku board implementation.
 * <p>
 * Any class implementing this interface must provide functionality for
 * validating number placements according to Sudoku rules.
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 */
public interface IBoard {
    /**
     * Checks if placing a candidate number at a given position is valid.
     * <p>
     * A placement is valid if the number doesn't violate Sudoku rules:
     * it must not already exist in the same row, column, or block.
     * </p>
     *
     * @param row       The row index of the cell (0-5)
     * @param col       The column index of the cell (0-5)
     * @param candidate The number to validate (1-6)
     * @return {@code true} if the placement is valid, {@code false} otherwise
     */
    boolean isValid(int row, int col, int candidate);
}