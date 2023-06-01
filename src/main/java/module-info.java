module com.example.proectlaba {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.proectlaba to javafx.fxml;
    exports com.example.proectlaba;
}