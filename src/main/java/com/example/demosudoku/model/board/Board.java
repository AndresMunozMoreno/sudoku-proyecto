package com.example.demosudoku.model.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Represents a 6x6 Sudoku board divided into 2x3 blocks.
 * The board is stored as a List of Lists of Integers, and Set is used to validate
 * uniqueness of numbers in rows, columns, and blocks efficiently.
 *
 * Data structures:
 * - List<List<Integer>> → Dynamic storage of the board.
 * - Set<Integer> → Used for duplicate detection in validation.
 */
public class Board implements IBoard {
    private final int SIZE = 6;
    private final int BLOCK_ROWS = 2;
    private final int BLOCK_COLS = 3;

    private final int TOTAL_BLOCK_ROWS = SIZE / BLOCK_ROWS; // 3
    private final int TOTAL_BLOCK_COLS = SIZE / BLOCK_COLS; // 2
    private final int TOTAL_BLOCKS = TOTAL_BLOCK_ROWS * TOTAL_BLOCK_COLS; // 6

    private final List<List<Integer>> board;
    private final Random random = new Random();

    public Board() {
        board = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            List<Integer> row = new ArrayList<>();
            for (int j = 0; j < SIZE; j++) {
                row.add(0);
            }
            board.add(row);
        }
        if (!fillBlocks(0)) {
            System.out.println("Failed to generate the Sudoku board.");
        }
    }

    @Override
    public boolean fillBlocks(int blockIndex) {
        if (blockIndex == TOTAL_BLOCKS) return true;

        int blockRow = blockIndex / TOTAL_BLOCK_COLS;
        int blockCol = blockIndex % TOTAL_BLOCK_COLS;
        int startRow = blockRow * BLOCK_ROWS;
        int startCol = blockCol * BLOCK_COLS;

        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= SIZE; i++) numbers.add(i);
        Collections.shuffle(numbers, random);

        for (int i = startRow; i < startRow + BLOCK_ROWS; i++) {
            for (int j = startCol; j < startCol + BLOCK_COLS; j++) {
                for (Integer number : numbers) {
                    if (isValid(i, j, number)) {
                        board.get(i).set(j, number);
                        if (fillBlocks(blockIndex + 1)) {
                            return true;
                        }
                        board.get(i).set(j, 0);
                    }
                }
            }
        }
        return false;
    }

    /**
     * Validates whether placing the candidate number in (row, col)
     * respects Sudoku rules for row, column, and 2x3 block.
     *
     * @param row       Row index
     * @param col       Column index
     * @param candidate Number to validate
     * @return true if placement is valid, false otherwise
     */
    @Override
    public boolean isValid(int row, int col, int candidate) {
        // ✅ Validate row using a Set
        Set<Integer> rowSet = new HashSet<>();
        for (int j = 0; j < SIZE; j++) {
            int value = board.get(row).get(j);
            if (value != 0 && !rowSet.add(value)) {
                return false; // duplicate found in row
            }
        }

        // ✅ Validate column using a Set
        Set<Integer> colSet = new HashSet<>();
        for (int i = 0; i < SIZE; i++) {
            int value = board.get(i).get(col);
            if (value != 0 && !colSet.add(value)) {
                return false; // duplicate found in column
            }
        }

        // ✅ Validate 2x3 block using a Set
        int startRow = (row / BLOCK_ROWS) * BLOCK_ROWS;
        int startCol = (col / BLOCK_COLS) * BLOCK_COLS;

        Set<Integer> blockSet = new HashSet<>();
        for (int i = startRow; i < startRow + BLOCK_ROWS; i++) {
            for (int j = startCol; j < startCol + BLOCK_COLS; j++) {
                int value = board.get(i).get(j);
                if (value != 0 && !blockSet.add(value)) {
                    return false; // duplicate found in block
                }
            }
        }

        // Finally, ensure candidate doesn't already exist
        return !rowSet.contains(candidate)
                && !colSet.contains(candidate)
                && !blockSet.contains(candidate);
    }

    public List<List<Integer>> getBoard() {
        return board;
    }
}
