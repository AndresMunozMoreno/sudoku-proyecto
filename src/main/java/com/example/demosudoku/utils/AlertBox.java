package com.example.demosudoku.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * Utility class for creating and displaying standard JavaFX alert dialogs.
 * <p>
 * This class provides a convenient way to show alerts to users with
 * consistent styling and behavior throughout the application.
 * </p>
 *
 * @author Juan Manuel Muñoz y Andres Felipe Muñoz
 * @version 1.0
 * @since 2025-10-15
 * @see IAlertBox
 */

/**
 * The Alert instance used to display dialogs.
 */
public class AlertBox implements IAlertBox {
    private Alert alert;

    /**
     * {@inheritDoc}
     * <p>
     * Creates and displays a modal alert dialog with the specified content.
     * The dialog blocks user interaction with other windows until dismissed.
     * </p>
     */

    @Override
    public void showAlert(String headerText, String contentText, AlertType alertType) {
        alert = new Alert(alertType);
        alert.setTitle("Sudoku");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}
