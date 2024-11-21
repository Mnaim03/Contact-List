/* doesn't work with source level 1.8:
module com.mycompany.contactlist.swe {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.contactlist.swe to javafx.fxml;
    exports com.mycompany.contactlist.swe;
}
*/
