package com.example.demosudoku.model.game;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Concrete implementation of the Sudoku game logic and user interface.
 * <p>
 * This class is responsible for:
 * <ul>
 *   <li>Setting up the game board UI with TextFields</li>
 *   <li>Handling user input and validation</li>
 *   <li>Providing visual feedback for valid/invalid moves</li>
 *   <li>Managing the hint system</li>
 *   <li>Detecting win conditions</li>
 * </ul>
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 * @see GameAbstract
 */

public class Game extends GameAbstract {

    /**
     * Constructs a new Game instance.
     *
     * @param boardGridpane The GridPane from the view where the Sudoku board will be rendered
     */
    public Game(GridPane boardGridpane) {
        super(boardGridpane);
    }

    /**
     * Starts the game by generating the board and creating UI components.
     * <p>
     * This method:
     * <ol>
     *   <li>Creates a TextField for each cell in the 6x6 grid</li>
     *   <li>Sets styling and font properties</li>
     *   <li>Marks pre-filled cells as non-editable</li>
     *   <li>Attaches input validation handlers</li>
     *   <li>Adds all components to the GridPane</li>
     * </ol>
     * </p>
     */

    @Override
    public void startGame() {
        int height = 65;
        int width = 60;
        String fontFamily = "Acme";
        int fontSize = 35;

        for (int i = 0; i < board.getBoard().size(); i++) {
            for (int j = 0; j < board.getBoard().get(i).size(); j++) {
                int number = board.getBoard().get(i).get(j);
                System.out.print(number + " ");

                TextField textField = new TextField();
                textField.setAlignment(Pos.CENTER);
                textField.setText(number == 0 ? "" : String.valueOf(number));
                textField.setPrefSize(width, height);
                textField.setFont(Font.font(fontFamily, fontSize));
                textField.setStyle("-fx-background-color: transparent;");

                if (number != 0) {
                    textField.setEditable(false);
                }

                handleNumberField(textField, i, j);
                boardGridpane.add(textField, j, i);
            }
            System.out.println();
        }
    }

    /**
     * Attaches event handlers to a TextField cell for input validation.
     * <p>
     * Implements two key features:
     * <ul>
     *   <li><b>TextFormatter:</b> Restricts input to single digits 1-6 or empty</li>
     *   <li><b>KeyReleased handler:</b> Validates moves and provides visual feedback</li>
     * </ul>
     * Valid moves are displayed in black, invalid moves in red.
     * </p>
     *
     * @param txt The TextField to attach handlers to
     * @param row The row index of this cell (0-5)
     * @param col The column index of this cell (0-5)
     */

    private void handleNumberField(TextField txt, int row, int col) {
        // ✅ Restringir: solo 1 dígito 1-6 o vacío
        txt.setTextFormatter(new javafx.scene.control.TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[1-6]?")) { // vacío o 1-6
                return change;
            }
            return null;
        }));

        txt.setOnKeyReleased(event -> {
            String input = txt.getText().trim();

            if (input.isEmpty()) {
                board.getBoard().get(row).set(col, 0);
                txt.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");
                return;
            }

            try {
                int number = Integer.parseInt(input);
                if (number >= 1 && number <= 6) {
                    boolean valid = board.isValid(row, col, number);
                    if (valid) {
                        board.getBoard().get(row).set(col, number);
                        txt.setStyle("-fx-background-color: transparent; -fx-text-fill: black;");

                        // ✅ Verificar si el sudoku está completo y correcto
                        checkWinCondition();
                    } else {
                        txt.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
                    }
                } else {
                    txt.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
                }
            } catch (NumberFormatException e) {
                txt.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
            }
        });
    }

    /**
     * Checks if the player has successfully completed the puzzle.
     * <p>
     * The win condition is satisfied when:
     * <ol>
     *   <li>All cells are filled (no zeros remain)</li>
     *   <li>The player board exactly matches the solution board</li>
     * </ol>
     * If both conditions are met, the victory screen is displayed.
     * </p>
     */

    private void checkWinCondition() {
        var playerBoard = board.getBoard();
        var solvedBoard = board.getSolvedBoard();

        // Verificar que todas las celdas estén llenas
        for (int row = 0; row < playerBoard.size(); row++) {
            for (int col = 0; col < playerBoard.get(row).size(); col++) {
                if (playerBoard.get(row).get(col) == 0) {
                    return; // Aún hay celdas vacías
                }
            }
        }

        // Verificar que el tablero coincida con la solución
        for (int row = 0; row < playerBoard.size(); row++) {
            for (int col = 0; col < playerBoard.get(row).size(); col++) {
                if (playerBoard.get(row).get(col) != solvedBoard.get(row).get(col)) {
                    return; // Hay errores en el tablero
                }
            }
        }

        // ✅ El jugador ganó - mostrar ventana de victoria
        showWinStage();
    }

    /**
     * Displays the victory screen and closes the game window.
     * <p>
     * Called automatically when the player completes the puzzle correctly.
     * Transitions from the game view to the win view.
     * </p>
     */

    private void showWinStage() {
        try {
            com.example.demosudoku.view.SudokuGameStage.deleteInstance();
            com.example.demosudoku.view.SudokuWinStage.getInstance();
            System.out.println("🎉 ¡Felicidades! Has completado el Sudoku correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Provides a hint to the player by revealing one correct number.
     * <p>
     * The hint system:
     * <ol>
     *   <li>Identifies all empty or incorrect cells</li>
     *   <li>Randomly selects one cell from the candidates</li>
     *   <li>Fills it with the correct number from the solution</li>
     *   <li>Displays the hint in a distinct color (#A3B3CF)</li>
     *   <li>Checks if the puzzle is now complete</li>
     * </ol>
     * </p>
     */

    public void provideHelp() {
        var playerBoard = board.getBoard();
        var solvedBoard = board.getSolvedBoard();
        List<int[]> hintCandidates = new ArrayList<>();

        // 1️⃣ Reunir celdas vacías o incorrectas
        for (int row = 0; row < playerBoard.size(); row++) {
            for (int col = 0; col < playerBoard.get(row).size(); col++) {
                int current = playerBoard.get(row).get(col);
                int correct = solvedBoard.get(row).get(col);
                if (current == 0 || current != correct) {
                    hintCandidates.add(new int[]{row, col});
                }
            }
        }

        // 2️⃣ Si no hay candidatos → salir
        if (hintCandidates.isEmpty()) {
            System.out.println("✅ No hay más celdas vacías ni incorrectas para sugerir.");
            return;
        }

        // 3️⃣ Elegir una celda aleatoria
        Random random = new Random();
        int[] chosen = hintCandidates.get(random.nextInt(hintCandidates.size()));
        int row = chosen[0];
        int col = chosen[1];
        int correctValue = solvedBoard.get(row).get(col);

        // 4️⃣ Aplicar la pista
        applyHintToCell(row, col, correctValue);
        System.out.println("💡 Pista en (" + row + "," + col + "): " + correctValue);

        // 5️⃣ Verificar si con esta pista se completó el juego
        checkWinCondition();
    }

    /**
     * Applies a hint to a specific cell by updating both model and view.
     * <p>
     * Updates the player board data structure and finds the corresponding
     * TextField in the GridPane to display the hint visually.
     * </p>
     *
     * @param row          The row index of the cell receiving the hint (0-5)
     * @param col          The column index of the cell receiving the hint (0-5)
     * @param correctValue The correct number from the solution (1-6)
     */

    private void applyHintToCell(int row, int col, int correctValue) {
        // Actualizar el modelo
        board.getBoard().get(row).set(col, correctValue);

        // Actualizar el TextField correspondiente
        boardGridpane.getChildren().forEach(node -> {
            Integer r = GridPane.getRowIndex(node);
            Integer c = GridPane.getColumnIndex(node);
            if (r != null && c != null && r == row && c == col && node instanceof TextField) {
                TextField textField = (TextField) node;
                textField.setText(String.valueOf(correctValue));
                textField.setEditable(true);
                textField.setStyle("-fx-background-color: transparent; -fx-text-fill: #A3B3CF;");
            }
        });
    }
}

