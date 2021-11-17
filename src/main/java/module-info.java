module com.check_boq {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.mail;
    requires java.desktop;

    opens com.check_boq to javafx.fxml;
    exports com.check_boq;
}