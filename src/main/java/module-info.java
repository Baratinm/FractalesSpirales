module com.example123 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example123 to javafx.fxml;
    exports com.example123;
}
