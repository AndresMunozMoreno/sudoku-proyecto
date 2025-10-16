package com.example.demosudoku.model.game;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents the concrete implementation of the Sudoku game logic.
 * This class is responsible for setting up the game board UI and handling user input.
 */
public class Game extends GameAbstract {

    /**
     * Constructs a new Game instance.
     *
     * @param boardGridpane The GridPane from the view where the Sudoku board will be rendered.
     */
    public Game(GridPane boardGridpane) {
        super(boardGridpane);
    }

    /**
     * Starts the game by generating a board, creating UI components (TextFields) for each cell,
     * and adding them to the GridPane. It also sets properties for each cell, such as editability.
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
     * Attaches a key released event handler to a TextField cell. When the key is released,
     * it validates the number entered by the user against the Sudoku rules.
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
     * Da una pista: elige aleatoriamente una celda vacía o incorrecta
     * y coloca el número correcto de la solución. No aplica estilos de fondo.
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
    }

    /**
     * Aplica la pista a la celda indicada, solo cambiando el número
     * y coloreando únicamente el texto de amarillo.
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
                textField.setStyle("-fx-background-color: transparent; -fx-text-fill: yellow;");
            }
        });
    }
}

