module edu.badpals.db_magictg {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires javafx.graphics;
    requires java.xml;


    opens edu.badpals.db_magictg to javafx.fxml;
    exports edu.badpals.db_magictg;
    exports edu.badpals.db_magictg.controller;
    opens edu.badpals.db_magictg.controller to javafx.fxml;
}