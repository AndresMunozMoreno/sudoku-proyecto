package com.example.demosudoku.model.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Board implements IBoard {

    private final int SIZE = 6;
    private final int BLOCK_ROWS = 2;
    private final int BLOCK_COLS = 3;

    private final List<List<Integer>> playerBoard;   // tablero mostrado al jugador (con huecos)
    private final List<List<Integer>> solvedBoard;   // tablero resuelto completamente
    private final Random random = new Random();

    public Board() {
        // Inicializar estructuras vacías
        playerBoard = createEmptyBoard();
        solvedBoard = createEmptyBoard();

        // 1️⃣ Generar solución completa
        fillSolution(0, 0);

        // 2️⃣ Copiar solución al tablero del jugador
        copyBoard(solvedBoard, playerBoard);

        // 3️⃣ Quitar números para crear el puzzle (dejar pistas)
        removeCellsForPuzzle(24); // por ejemplo quitar 24 celdas, puedes ajustar yo deje 12 numero por que son los que el profe tiene
        System.out.println("=== SOLUCIÓN COMPLETA ===");
        for (var row : solvedBoard) System.out.println(row);

        System.out.println("=== PUZZLE JUGADOR ===");
        for (var row : playerBoard) System.out.println(row);
    }

    /**
     * Crea un tablero vacío SIZE×SIZE lleno de ceros.
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
     * Genera una solución completa de Sudoku 6×6 usando backtracking.
     */
    private boolean fillSolution(int row, int col) {
        if (row == SIZE) {
            return true; // se llenó todo
        }

        int nextRow = (col == SIZE - 1) ? row + 1 : row;
        int nextCol = (col == SIZE - 1) ? 0 : col + 1;

        // Candidatos en orden aleatorio
        List<Integer> numbers = new ArrayList<>();
        for (int n = 1; n <= SIZE; n++) numbers.add(n);
        Collections.shuffle(numbers, random);

        for (int num : numbers) {
            if (isValidInBoard(solvedBoard, row, col, num)) {
                solvedBoard.get(row).set(col, num);
                if (fillSolution(nextRow, nextCol)) {
                    return true;
                }
                solvedBoard.get(row).set(col, 0); // backtrack
            }
        }
        return false;
    }

    /**
     * Valida si se puede colocar un número en la posición dada en un tablero.
     */
    private boolean isValidInBoard(List<List<Integer>> board, int row, int col, int num) {
        // Fila
        for (int j = 0; j < SIZE; j++) {
            if (board.get(row).get(j) == num) return false;
        }
        // Columna
        for (int i = 0; i < SIZE; i++) {
            if (board.get(i).get(col) == num) return false;
        }
        // Bloque
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
     * Quita celdas de playerBoard para crear el puzzle inicial.
     */
    private void removeCellsForPuzzle(int cellsToRemove) {
        List<int[]> positions = new ArrayList<>();
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                positions.add(new int[]{i, j});
            }
        }

        Collections.shuffle(positions, random);

        int removed = 0;
        for (int[] pos : positions) {
            int r = pos[0];
            int c = pos[1];

            int backup = playerBoard.get(r).get(c);
            playerBoard.get(r).set(c, 0);

            // Creamos un tablero temporal basado en la SOLUCIÓN COMPLETA
            List<List<Integer>> tempBoard = copyBoardForCount(solvedBoard);
            tempBoard.get(r).set(c, 0); // quitamos la celda temporal

            if (countSolutions(tempBoard) == 1) {
                removed++;
                if (removed >= cellsToRemove) break;
            } else {
                playerBoard.get(r).set(c, backup); // restauramos porque no es único
            }
        }
    }

    /**
     * Copia el contenido de un tablero a otro.
     */
    private void copyBoard(List<List<Integer>> from, List<List<Integer>> to) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                to.get(i).set(j, from.get(i).get(j));
            }
        }
    }

    /**
     * Devuelve el tablero del jugador (con huecos).
     */
    public List<List<Integer>> getBoard() {
        return playerBoard;
    }

    /**
     * Devuelve la solución completa (para pistas o verificación).
     */
    public List<List<Integer>> getSolvedBoard() {
        return solvedBoard;
    }

    @Override
    public boolean isValid(int row, int col, int candidate) {
        return isValidInBoard(playerBoard, row, col, candidate);
    }

    /**
     * Cuenta cuántas soluciones tiene un tablero usando backtracking.
     */
    private int countSolutions(List<List<Integer>> board) {
        return countSolutionsHelper(board, 0, 0);
    }

    private int countSolutionsHelper(List<List<Integer>> board, int row, int col) {
        if (row == SIZE) return 1;

        int nextRow = (col == SIZE - 1) ? row + 1 : row;
        int nextCol = (col == SIZE - 1) ? 0 : col + 1;

        if (board.get(row).get(col) != 0) {
            return countSolutionsHelper(board, nextRow, nextCol);
        }

        int solutions = 0;
        for (int num = 1; num <= SIZE; num++) {
            if (isValidInBoard(board, row, col, num)) {
                board.get(row).set(col, num);
                solutions += countSolutionsHelper(board, nextRow, nextCol);
                board.get(row).set(col, 0);
                if (solutions > 1) break; // no necesitamos más de 1
            }
        }
        return solutions;
    }

    /**
     * Devuelve una copia profunda de un tablero.
     */
    private List<List<Integer>> copyBoardForCount(List<List<Integer>> board) {
        List<List<Integer>> copy = new ArrayList<>();
        for (List<Integer> row : board) {
            List<Integer> newRow = new ArrayList<>(row);
            copy.add(newRow);
        }
        return copy;
    }
}




