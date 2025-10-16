package com.example.demosudoku.utils;

import javafx.scene.control.Alert;

/**
 * Interface defining the contract for alert box utilities.
 * <p>
 * Implementations must provide functionality to display alert dialogs
 * to users with customizable content and alert types.
 * </p>
 *
 * @author Your Name
 * @version 1.0
 * @since 2025-01-15
 */
public interface IAlertBox {
    /**
     * Displays an alert dialog to the user.
     *
     * @param headerText  The text to display in the header area of the dialog
     * @param contentText The main content message of the dialog
     * @param alertType   The type of alert (ERROR, INFORMATION, WARNING, CONFIRMATION)
     */
    void showAlert(String headerText, String contentText, Alert.AlertType alertType);
}