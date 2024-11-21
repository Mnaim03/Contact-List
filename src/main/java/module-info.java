module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.main.it.university.group27 to javafx.fxml;
    exports org.main.it.university.group27;
}