module net.pastegram.automationofvacationscheduleformation {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;


    opens net.pastegram.avsf to javafx.fxml;
    exports net.pastegram.avsf;
    opens net.pastegram.avsf.controller to javafx.fxml;
    exports net.pastegram.avsf.controller;
    opens net.pastegram.avsf.entity to javafx.fxml;
    exports net.pastegram.avsf.entity;
}