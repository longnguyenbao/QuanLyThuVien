/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.btl.quanlythuvien;

import com.btl.pojo.DocGia;
import com.btl.pojo.DoiTuong;
import com.btl.services.DocGiaServices;
import com.btl.services.DoiTuongServices;
import com.btl.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class DangNhapController implements Initializable {
    @FXML
    private ComboBox<DoiTuong> cbxDoiTuong;
    @FXML
    private TextField txtUserName;
    @FXML
    private PasswordField txtPassWord;
    @FXML
    private Button btnDangNhap;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadComboBoxDoiTuong();
        this.cbxDoiTuong.getSelectionModel().select(2);
    }    
    
    public void dangNhapHandler(ActionEvent evt) throws IOException, SQLException {
        Window owner = btnDangNhap.getScene().getWindow();

        DocGia docGia = new DocGia();
        docGia.setMaDocGia(txtUserName.getText());
        docGia.setMatKhau(txtPassWord.getText());

        if (cbxDoiTuong.getValue() == null) {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!", "Chưa chọn loại tài khoản");
            return;
        }else{
            docGia.setMaDoiTuong(cbxDoiTuong.getSelectionModel().getSelectedItem().getMaDoiTuong());
        }

        if (txtUserName.getText().isEmpty()) {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!",
                "Chưa nhập UserName");
            return;
        }

        if (txtPassWord.getText().isEmpty()) {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!",
                "Chưa nhập mật khẩu");
            return;
        }

        DocGiaServices dg = new DocGiaServices();
        DocGia newDocGia = dg.dangNhap(docGia);

        if(newDocGia != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLMenu.fxml"));
            Parent root = fxmlLoader.load();

            MenuController sceneMenu = fxmlLoader.getController();
            sceneMenu.setDocGia(newDocGia);
            sceneMenu.setMaDoiTuong(newDocGia.getMaDoiTuong());

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Menu");
            stage.show();
        }

        else {
            Utils.getBox("Thong tin dang nhap khong hop le!", Alert.AlertType.WARNING).show();
        }
    }
    
    private void loadComboBoxDoiTuong() {
        DoiTuongServices s = new DoiTuongServices();
        try {
            this.cbxDoiTuong.setItems(FXCollections.observableArrayList(s.getDSDoiTuong()));
        } catch (SQLException ex) {
            Logger.getLogger(DangNhapController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void dangKyHandler(ActionEvent evt) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLDangKy.fxml"));
        
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Đăng ký");
        stage.show();
    }
}
