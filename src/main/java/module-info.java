module com.mycompany.javafx_db_example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.mycompany.javafx_db_example to javafx.fxml;
    exports com.example.javafx_db_example;
}
