module apirequests.proj_u1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;


    requires javafx.web;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires com.fasterxml.jackson.dataformat.xml;
    requires java.sql;


    opens apirequests.proj_u1 to javafx.fxml;
    exports apirequests.proj_u1;
    exports apirequests.proj_u1.view;
    opens apirequests.proj_u1.view to javafx.fxml;
    exports apirequests.proj_u1.model;
    opens apirequests.proj_u1.model;
    exports apirequests.proj_u1.mgmt;
    opens apirequests.proj_u1.mgmt to javafx.fxml;


}