module com.relief {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;

    opens com.library to javafx.fxml;
    exports com.library;

    opens com.relief to javafx.fxml;
    exports com.relief;
}
