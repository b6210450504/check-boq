module com.check_boq {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.check_boq to javafx.fxml;
    exports com.check_boq;
}