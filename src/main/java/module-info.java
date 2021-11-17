module com.check_boq {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;
    requires java.desktop;
    requires java.mail;

    opens com.check_boq;
    exports com.check_boq;
}