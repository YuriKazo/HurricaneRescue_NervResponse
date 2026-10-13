module com.relief {
    requires javafx.controls;
    requires javafx.fxml;
    requires json.simple;
    requires junit;

    opens com.relief to javafx.fxml;
    exports com.relief;
}
