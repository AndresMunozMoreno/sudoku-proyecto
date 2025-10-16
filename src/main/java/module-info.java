module com.example.demosudoku {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;


    opens com.example.demosudoku to javafx.fxml;
    opens com.example.demosudoku.controller to javafx.fxml;
    exports com.example.demosudoku;
}