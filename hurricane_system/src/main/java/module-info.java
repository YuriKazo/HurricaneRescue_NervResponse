module com.relief {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens com.relief to javafx.fxml;
    exports com.relief;
}
