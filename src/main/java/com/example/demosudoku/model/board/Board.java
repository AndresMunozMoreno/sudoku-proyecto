package com.example.demosudoku.model.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Tablero 6x6 con bloques 2x3.
 * - Genera una solución completa aleatoria (solvedBoard).
 * - Construye playerBoard revelando exactamente 2 números por bloque 2x3.
 */
public class Board implements IBoard {
    // Dimensiones
    private final int SIZE = 6;
    private final int BLOCK_ROWS = 2;
    private final int BLOCK_COLS = 3;

    // Tableros
    private final List<List<Integer>> playerBoard;
    private final List<List<Integer>> solvedBoard;

    private final Random random = new Random();

    public Board() {
        // inicializar listas (evita "might not have been initialized")
        playerBoard = createEmptyBoard();
        solvedBoard = createEmptyBoard();

        // 1) Generar solución completa
        fillSolution(0, 0);

        // 2) Inicializar playerBoard a ceros (ya lo está por createEmptyBoard),
        //    y revelar exactamente 2 números por bloque
        revealTwoPerBlock();

        // debug (opcional)
        System.out.println("=== SOLUCIÓN COMPLETA ===");
        for (var row : solvedBoard) System.out.println(row);

        System.out.println("=== PUZZLE JUGADOR ===");
        for (var row : playerBoard) System.out.println(row);
    }

    /**
     * Crea un tablero SIZE x SIZE lleno de ceros.
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
     * Genera una solución completa con backtracking.
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
     * Valida si un número puede ponerse en (row,col) sobre el tablero dado.
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
     * Revela exactamente 2 números por cada bloque 2x3 desde la solución.
     */
    private void revealTwoPerBlock() {
        // Recorremos bloques por su índice (filas de bloques y columnas de bloques)
        int totalBlockRows = SIZE / BLOCK_ROWS; // 3
        int totalBlockCols = SIZE / BLOCK_COLS; // 2

        for (int br = 0; br < totalBlockRows; br++) {
            for (int bc = 0; bc < totalBlockCols; bc++) {
                int startRow = br * BLOCK_ROWS;
                int startCol = bc * BLOCK_COLS;

                // Recolectar posiciones del bloque
                List<int[]> positions = new ArrayList<>();
                for (int i = startRow; i < startRow + BLOCK_ROWS; i++) {
                    for (int j = startCol; j < startCol + BLOCK_COLS; j++) {
                        positions.add(new int[]{i, j});
                    }
                }
                // Mezclar y elegir 2 posiciones
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
     * Copia contenido de 'from' a 'to'.
     */
    private void copyBoard(List<List<Integer>> from, List<List<Integer>> to) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                to.get(i).set(j, from.get(i).get(j));
            }
        }
    }

    /* ----------------- métodos públicos ----------------- */

    public List<List<Integer>> getBoard() {
        return playerBoard;
    }

    public List<List<Integer>> getSolvedBoard() {
        return solvedBoard;
    }


    @Override
    public boolean isValid(int row, int col, int candidate) {
        return isValidInBoard(playerBoard, row, col, candidate);
    }
}





