package com.example.demosudoku.model.game;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;

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
        for (int i = 0; i < board.getBoard().size(); i++) {
            for (int j = 0; j < board.getBoard().get(i).size(); j++) {
                int number = board.getBoard().get(i).get(j);
                System.out.print(number + " ");

                TextField textField = new TextField();
                textField.setAlignment(Pos.CENTER);
                textField.setText(String.valueOf(number));
                if (number != 0) {
                    textField.setEditable(false);
                } else{
                    textField.setText("");
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
     *
     * @param txt The TextField to which the handler will be attached.
     * @param row The row index of the cell in the board.
     * @param col The column index of the cell in the board.
     */
    private void handleNumberField(TextField txt, int row, int col) {
        txt.setOnKeyReleased(event -> {
            String input = txt.getText().trim();

            // Si el campo está vacío → poner 0 en el tablero y limpiar estilo
            if (input.isEmpty()) {
                board.getBoard().get(row).set(col, 0);
                txt.setStyle("");
                return;
            }

            try {
                int number = Integer.parseInt(input);

                if (number >= 1 && number <= 6) {
                    // ⚡ Validar antes de guardar
                    boolean valid = board.isValid(row, col, number);

                    // 👇 👉 AQUI va el println 👇
                    System.out.println("Validación (" + row + "," + col + ") con " + number + ": " + valid);

                    if (valid) {
                        board.getBoard().get(row).set(col, number);
                        txt.setStyle("-fx-border-color: green; -fx-border-width: 2;");
                    } else {
                        txt.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                    }

                } else {
                    txt.setStyle("-fx-border-color: red; -fx-border-width: 2;");
                }

            } catch (NumberFormatException e) {
                txt.setStyle("-fx-border-color: red; -fx-border-width: 2;");
            }
        });
    }


}
