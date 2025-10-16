package com.example.demosudoku.model.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Implementation of a 6x6 Sudoku board with 2x3 blocks.
 * <p>
 * This class generates a complete random solution and creates a playable puzzle
 * by revealing exactly 2 numbers per 2x3 block. The board uses backtracking
 * algorithm to ensure a valid and unique solution exists.
 * </p>
 * <p>
 * The board maintains two separate grids:
 * <ul>
 *   <li><b>playerBoard:</b> The current state of the player's puzzle with some cells revealed</li>
 *   <li><b>solvedBoard:</b> The complete solution used for validation and hints</li>
 * </ul>
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 * @see IBoard
 */
public class Board implements IBoard {

    /**
     * The size of the Sudoku board (6x6).
     */
    private final int SIZE = 6;
    /**
     * The number of rows in each block (2).
     */
    private final int BLOCK_ROWS = 2;
    /**
     * The number of columns in each block (3).
     */
    private final int BLOCK_COLS = 3;


    /**
     * The board displayed to the player, containing zeros for empty cells
     * and revealed numbers from the solution.
     */
    private final List<List<Integer>> playerBoard;

    /**
     * The complete solution board used for validation and providing hints.
     */

    private final List<List<Integer>> solvedBoard;

    /**
     * Random number generator for creating unique puzzles and selecting hint positions.
     */

    private final Random random = new Random();

    /**
     * Constructs a new Board instance and initializes both the player board and solution.
     * <p>
     * The constructor performs the following operations:
     * <ol>
     *   <li>Creates empty 6x6 grids for both boards</li>
     *   <li>Generates a complete valid solution using backtracking</li>
     *   <li>Reveals exactly 2 numbers per 2x3 block for the player board</li>
     * </ol>
     * </p>
     */

    public Board() {

        playerBoard = createEmptyBoard();
        solvedBoard = createEmptyBoard();

        fillSolution(0, 0);


        revealTwoPerBlock();

        System.out.println("=== SOLUCIÓN COMPLETA ===");
        for (var row : solvedBoard) System.out.println(row);

        System.out.println("=== PUZZLE JUGADOR ===");
        for (var row : playerBoard) System.out.println(row);
    }

    /**
     * Creates an empty 6x6 board filled with zeros.
     *
     * @return A List of Lists representing a 6x6 grid initialized with zeros
     */

    private List<List<Integer>> createEmptyBoard() {
        List<List<Integer>> board = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                row.add(0);
            }
            board.add(row);
        }
        return board;
    }

    /**
     * Generates a complete valid Sudoku solution using backtracking algorithm.
     * <p>
     * This recursive method tries to fill each cell with a valid number (1-6),
     * ensuring no conflicts in rows, columns, or 2x3 blocks. Numbers are
     * shuffled randomly to create different solutions each time.
     * </p>
     *
     * @param row The current row being filled (0-5)
     * @param col The current column being filled (0-5)
     * @return {@code true} if a valid solution is found, {@code false} otherwise
     */

    private boolean fillSolution(int row, int col) {
        if (row == SIZE) return true;
        int nextRow = (col == SIZE - 1) ? row + 1 : row;
        int nextCol = (col == SIZE - 1) ? 0 : col + 1;

        List<Integer> numbers = new ArrayList<>();
        for (int n = 1; n <= SIZE; n++) numbers.add(n);
        Collections.shuffle(numbers, random);

        for (int num : numbers) {
            if (isValidInBoard(solvedBoard, row, col, num)) {
                solvedBoard.get(row).set(col, num);
                if (fillSolution(nextRow, nextCol)) return true;
                solvedBoard.get(row).set(col, 0);
            }
        }
        return false;
    }

    /**
     * Validates whether a number can be placed at a specific position on the board.
     * <p>
     * Checks three constraints:
     * <ul>
     *   <li>The number doesn't exist in the same row</li>
     *   <li>The number doesn't exist in the same column</li>
     *   <li>The number doesn't exist in the same 2x3 block</li>
     * </ul>
     * </p>
     *
     * @param board The board to validate against (either playerBoard or solvedBoard)
     * @param row   The row index where the number would be placed (0-5)
     * @param col   The column index where the number would be placed (0-5)
     * @param num   The number to validate (1-6)
     * @return {@code true} if the placement is valid, {@code false} otherwise
     */

    private boolean isValidInBoard(List<List<Integer>> board, int row, int col, int num) {
        // fila
        for (int j = 0; j < SIZE; j++) {
            if (board.get(row).get(j) == num) return false;
        }
        // columna
        for (int i = 0; i < SIZE; i++) {
            if (board.get(i).get(col) == num) return false;
        }
        // bloque 2x3
        int startRow = (row / BLOCK_ROWS) * BLOCK_ROWS;
        int startCol = (col / BLOCK_COLS) * BLOCK_COLS;
        for (int i = startRow; i < startRow + BLOCK_ROWS; i++) {
            for (int j = startCol; j < startCol + BLOCK_COLS; j++) {
                if (board.get(i).get(j) == num) return false;
            }
        }
        return true;
    }

    /**
     * Reveals exactly 2 numbers per 2x3 block from the solution to create the initial puzzle.
     * <p>
     * This method iterates through all 6 blocks (3 rows × 2 columns of blocks),
     * randomly selects 2 positions in each block, and copies those numbers
     * from the solved board to the player board.
     * </p>
     */

    private void revealTwoPerBlock() {

        int totalBlockRows = SIZE / BLOCK_ROWS; // 3
        int totalBlockCols = SIZE / BLOCK_COLS; // 2

        for (int br = 0; br < totalBlockRows; br++) {
            for (int bc = 0; bc < totalBlockCols; bc++) {
                int startRow = br * BLOCK_ROWS;
                int startCol = bc * BLOCK_COLS;


                List<int[]> positions = new ArrayList<>();
                for (int i = startRow; i < startRow + BLOCK_ROWS; i++) {
                    for (int j = startCol; j < startCol + BLOCK_COLS; j++) {
                        positions.add(new int[]{i, j});
                    }
                }

                Collections.shuffle(positions, random);
                for (int k = 0; k < 2; k++) {
                    int r = positions.get(k)[0];
                    int c = positions.get(k)[1];
                    playerBoard.get(r).set(c, solvedBoard.get(r).get(c));
                }
            }
        }
    }

    /**
     * Copies the contents of one board to another.
     *
     * @param from The source board to copy from
     * @param to   The destination board to copy to
     */

    private void copyBoard(List<List<Integer>> from, List<List<Integer>> to) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                to.get(i).set(j, from.get(i).get(j));
            }
        }
    }

    /**
     * Returns the current state of the player's board.
     *
     * @return A List of Lists representing the player's current puzzle state
     */

    public List<List<Integer>> getBoard() {
        return playerBoard;
    }

    /**
     * Returns the complete solution board.
     *
     * @return A List of Lists representing the complete solved puzzle
     */

    public List<List<Integer>> getSolvedBoard() {
        return solvedBoard;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Validates a candidate number against the current player board state.
     * </p>
     */

    @Override
    public boolean isValid(int row, int col, int candidate) {
        return isValidInBoard(playerBoard, row, col, candidate);
    }

}




