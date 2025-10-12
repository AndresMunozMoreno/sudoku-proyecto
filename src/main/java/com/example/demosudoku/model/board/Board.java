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
        removeCellsForPuzzle(24); // dejamos 12 números vacíos como ejemplo, puedes ajustar
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
            if (removed >= cellsToRemove) break;

            int r = pos[0];
            int c = pos[1];
            if (playerBoard.get(r).get(c) != 0) {
                playerBoard.get(r).set(c, 0);
                removed++;
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
    public boolean fillBlocks(int blockIndex) {
        // ya no se usa, pero lo dejamos para cumplir la interfaz
        return true;
    }

    /**
     * Valida si la jugada es correcta comparando con la solución completa.
     */
    @Override
    public boolean isValid(int row, int col, int candidate) {
        return solvedBoard.get(row).get(col) == candidate;
    }
}





