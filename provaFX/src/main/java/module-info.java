module org.example.provafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.provafx to javafx.fxml;
    exports org.example.provafx;
}