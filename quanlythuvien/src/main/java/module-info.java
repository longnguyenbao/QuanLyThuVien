module com.btl.quanlythuvien {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires java.base;
    requires java.sql;
    
    opens com.btl.quanlythuvien to javafx.fxml;
    exports com.btl.quanlythuvien;
    exports com.btl.pojo;
}