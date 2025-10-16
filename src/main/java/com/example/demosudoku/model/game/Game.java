package com.example.demosudoku.model.game;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

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
                textField.setText(String.valueOf(number));
                textField.setPrefSize(width, height);
                textField.setFont(Font.font(fontFamily, fontSize));
                textField.setStyle("-fx-background-color: transparent;");

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
        // ✅ 1) Restringir lo que se puede escribir: solo 1 dígito entre 1 y 6 o vacío
        txt.setTextFormatter(new javafx.scene.control.TextFormatter<String>(change -> {
            String newText = change.getControlNewText();
            if (newText.matches("[1-6]?")) { // acepta vacío o un solo dígito 1-6
                return change;
            }
            return null; // cualquier otra cosa se ignora
        }));

        // ✅ 2) Validar cuando el usuario termina de escribir
        txt.setOnKeyReleased(event -> {
            String input = txt.getText().trim();

            if (input.isEmpty()) {
                // se borró el contenido
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
}
