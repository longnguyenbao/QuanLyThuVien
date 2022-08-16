/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.btl.quanlythuvien;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.btl.services.DocGiaServices;
import com.btl.utils.Utils;
import com.btl.pojo.DocGia;
import com.btl.pojo.DoiTuong;
import com.btl.pojo.BoPhan;
import com.btl.services.BoPhanServices;
import com.btl.services.DoiTuongServices;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;



/**
 * FXML Controller class
 *
 * @author admin
 */
public class DangKyController implements Initializable {
    @FXML
    private ComboBox<DoiTuong> cbbDoiTuong;
    @FXML
    private ComboBox<BoPhan> cbbBoPhan;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtHoTen;
    @FXML
    private TextField txtEmail;
    @FXML
    private DatePicker dpNgaySinh;
    @FXML
    private RadioButton rdbNam;
    @FXML
    private RadioButton rdbNu;
    @FXML
    private PasswordField txtPassWord;
    @FXML
    private TextField txtDiaChi;    
    @FXML
    private TextField txtDienThoai;
    @FXML
    private PasswordField txtNhapLaiMatKhau;
    @FXML
    private Button submitButton;
    private Integer maDoiTuong;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        DoiTuongServices dt = new DoiTuongServices();
        try {
            this.cbbDoiTuong.setItems(FXCollections.observableArrayList(dt.getDSDoiTuong()));
        } catch (SQLException ex) {
            Logger.getLogger(DangKyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        BoPhanServices bp = new BoPhanServices();
        try {
            this.cbbBoPhan.setItems(FXCollections.observableArrayList(bp.getDSBoPhan()));
        } catch (SQLException ex) {
            Logger.getLogger(DangKyController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.txtDienThoai.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtDienThoai.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }  
    public int getMaDoiTuong() {
        return maDoiTuong;
    }
    public void register(ActionEvent event) throws SQLException, IOException {
        Window owner = submitButton.getScene().getWindow();

        DocGia docGia = new DocGia();
        docGia.setMaDocGia(txtUserName.getText());
        docGia.setHoTen(txtHoTen.getText());
        docGia.setEmail(txtEmail.getText());
        docGia.setNgaySinh(parseDateOrNull(dpNgaySinh.valueProperty().get()));
        docGia.setGioiTinh(chooseGender());
        docGia.setNgayDangKy(parseDateNow());
        docGia.setNgayHetHan(parseDateNow());
        docGia.setDiaChi(txtDiaChi.getText());
        docGia.setDienThoai(txtDienThoai.getText());
        docGia.setMatKhau(txtPassWord.getText());
   
        DocGiaServices dg = new DocGiaServices();
        
        
        if (txtUserName.getText().isEmpty()) {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!",
                "Nhập tên UserName");
            return;
        }

        if (txtEmail.getText().isEmpty()) {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!",
                "Nhập email của bạn");
            return;
        }
        
        if (cbbDoiTuong.getValue() == null) {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!", "Nhập đối tượng");
            return;
        }else{
            docGia.setMaDoiTuong(chonDoiTuong());
        }  
        if(cbbDoiTuong.getSelectionModel().getSelectedItem().getMaDoiTuong() == 2){
             Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!","Bạn không có quyền đăng ký tài khoản Viên chức");
             return;
        }
        
        if (cbbBoPhan.getValue() == null) {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!", "Nhập bộ phận");
            return;
        }else{
            docGia.setMaBoPhan(chonBoPhan());
        } 
        if (txtPassWord.getText().isEmpty()) {
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!",
                "Nhập mật khẩu");
            return;
        }
          
        if (txtPassWord.getText().equals(txtNhapLaiMatKhau.getText()) ) {
            dg.dangKy(docGia);
            Utils.showAlert(Alert.AlertType.CONFIRMATION, owner, "Đăng ký thành công!",
            "Xin chào " + txtHoTen.getText());
            return;
        }else
            Utils.showAlert(Alert.AlertType.ERROR, owner, "Lỗi!","Mật khẩu không khớp");
           

    }
   
    private Date parseDateOrNull(LocalDate value) {
        try {
            return Date.valueOf(value);
        } catch (Exception e) {
            return Date.valueOf(LocalDate.now());
        }
    }
    private Date parseDateNow() {
        return Date.valueOf(LocalDate.now());
    }
    private String chooseGender(){
        if(rdbNam.isSelected()){
            return rdbNam.getText();
        }else
            return rdbNu.getText();
    }
    private Integer chonDoiTuong(){
        return cbbDoiTuong.getSelectionModel().getSelectedItem().getMaDoiTuong();
    }
    private Integer chonBoPhan(){
        return cbbBoPhan.getSelectionModel().getSelectedItem().getMaBoPhan();
    }
}
