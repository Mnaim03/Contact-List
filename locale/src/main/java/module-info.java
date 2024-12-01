module com.mycompany.locale {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.locale to javafx.fxml;
    exports com.mycompany.locale;
}
