/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.quanlythuvien;

import com.btl.pojo.BoPhan;
import com.btl.pojo.DocGia;
import com.btl.pojo.DoiTuong;
import com.btl.services.BoPhanServices;
import com.btl.services.DocGiaServices;
import com.btl.services.DoiTuongServices;
import com.btl.utils.Utils;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Phan Nhat Thien
 */
public class QuanLyDocGiaController implements Initializable {

    @FXML
    private Button btnLamMoi;
    @FXML
    private Button btnTaoMaDocGia;

    @FXML
    private ComboBox<DoiTuong> cbbDoiTuong;
    @FXML
    private ComboBox<BoPhan> cbbBoPhan;

    @FXML
    private DatePicker dpNgayDangKy;
    @FXML
    private DatePicker dpNgayHetHan;
    @FXML
    private DatePicker dpNgaySinh;
    @FXML
    private RadioButton rdbNam;
    @FXML
    private RadioButton rdbNu;
    @FXML
    private TableView<DocGia> tbDocGia;
    @FXML
    private TextField txtDiaChi;
    @FXML
    private TextField txtDienThoai;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtHoTen;
    @FXML
    private TextField txtKeywords;
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtPassWord;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        
        
        try {
            this.refresh();
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyDocGiaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            this.loadTableDocGia(null);
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyDocGiaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.loadColumnsDocGia(); 
          
        this.tbDocGia.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                String maDocGia = tbDocGia.getSelectionModel().getSelectedItem().getMaDocGia();
                this.txtUserName.setText(maDocGia);
                this.txtHoTen.setText(tbDocGia.getSelectionModel().getSelectedItem().getHoTen());
                this.txtEmail.setText(tbDocGia.getSelectionModel().getSelectedItem().getEmail());
                try {
                    this.dpNgaySinh.setValue(null);
                    if(tbDocGia.getSelectionModel().getSelectedItem().getNgaySinh() != null)
                        this.dpNgaySinh.setValue(tbDocGia.getSelectionModel().getSelectedItem().getNgaySinh().toLocalDate());
                    this.cbbDoiTuong.getSelectionModel().select(DoiTuongServices.getDoiTuongById(this.tbDocGia.getSelectionModel().getSelectedItem().getMaDoiTuong()));
                    this.cbbBoPhan.getSelectionModel().select(BoPhanServices.getBoPhanById(this.tbDocGia.getSelectionModel().getSelectedItem().getMaBoPhan()));
                    this.txtDienThoai.setText("");
                    if(tbDocGia.getSelectionModel().getSelectedItem().getDienThoai() != null) {
                        this.txtDienThoai.setText(tbDocGia.getSelectionModel().getSelectedItem().getDienThoai());
                    }
                    
                    this.rdbNam.setSelected(true);
                    if(!tbDocGia.getSelectionModel().getSelectedItem().getGioiTinh().contains("Nam")) {
                        this.rdbNu.setSelected(true);
                    }
                    
                } catch (Exception ex) {
                    Logger.getLogger(QuanLyDocGiaController.class.getName()).log(Level.SEVERE, null, ex);
                }

                this.dpNgayDangKy.setValue(tbDocGia.getSelectionModel().getSelectedItem().getNgayDangKy().toLocalDate());
                this.dpNgayHetHan.setValue(tbDocGia.getSelectionModel().getSelectedItem().getNgayHetHan().toLocalDate());
                this.txtDiaChi.setText(tbDocGia.getSelectionModel().getSelectedItem().getDiaChi());
                
                this.txtPassWord.setText(tbDocGia.getSelectionModel().getSelectedItem().getMatKhau());               
            }
        });
        
        this.txtUserName.setEditable(false);
        this.btnLamMoi.setOnAction(e -> {
            try {
                refresh();
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyDocGiaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.btnTaoMaDocGia.setOnAction(e -> taoMaDocGia());
        this.txtKeywords.textProperty().addListener((evt) ->{
            try {
                this.loadTableDocGia(this.txtKeywords.getText());
            } catch (SQLException ex) {
                Logger.getLogger(QuanLyDocGiaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        this.txtDienThoai.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtDienThoai.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
    
    private void refresh() throws SQLException {
        this.loadTableDocGia(null);
        this.txtUserName.clear();
        this.txtPassWord.clear();
        this.txtHoTen.clear();
        this.cbbDoiTuong.getSelectionModel().clearSelection();
        this.cbbDoiTuong.setValue(null);
        this.cbbBoPhan.getSelectionModel().clearSelection();
        this.cbbBoPhan.setValue(null);
        this.dpNgaySinh.getEditor().clear();
        this.dpNgayDangKy.getEditor().clear();
        this.dpNgayHetHan.getEditor().clear();
        this.txtEmail.clear();
        this.txtDiaChi.clear();
        this.txtDienThoai.clear();
    }
    
    private void loadTableDocGia(String kw) throws SQLException {
        DocGiaServices s = new DocGiaServices();
        try {
            this.tbDocGia.setItems(FXCollections.observableList(s.getDSDocGia(kw)));
        } catch (SQLException ex) {
            Logger.getLogger(SachController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void taoMaDocGia() {
        this.txtUserName.setText(UUID.randomUUID().toString());
    }

    public void themHandler(ActionEvent evt) throws SQLException {
        if(this.txtPassWord.getText().isBlank() == false) {
            DocGia docGia = new DocGia();
            docGia.setMaDocGia(txtUserName.getText());
            docGia.setHoTen(txtHoTen.getText());
            docGia.setEmail(txtEmail.getText());
            docGia.setNgaySinh(parseDateOrNull(dpNgaySinh.valueProperty().get()));
            docGia.setMaDoiTuong(chonDoiTuong());
            docGia.setMaBoPhan(chonBoPhan());
            docGia.setGioiTinh(chooseGender());
            docGia.setNgayDangKy(parseDateOrNull(dpNgayDangKy.valueProperty().get()));
            docGia.setNgayHetHan(parseDateOrNull(dpNgayHetHan.valueProperty().get()));
            docGia.setDiaChi(txtDiaChi.getText());
            docGia.setDienThoai(txtDienThoai.getText());
            docGia.setMatKhau(txtPassWord.getText());

            DocGiaServices s = new DocGiaServices();       
            if(s.themDocGia(docGia) == true) {
                refresh();
                Utils.getBox("Them thanh cong!", Alert.AlertType.INFORMATION).show();
            }
            else {
                Utils.getBox("Da co loi xay ra!", Alert.AlertType.WARNING).show();
            }
        }
        else {
            Utils.getBox("Ban chua nhap mat khau!", Alert.AlertType.WARNING).show();
        }
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
    
    private void loadColumnsDocGia() {
        TableColumn col1 = new TableColumn("UserName");
        col1.setCellValueFactory(new PropertyValueFactory("maDocGia"));

        TableColumn col2 = new TableColumn("Mat khau");
        col2.setCellValueFactory(new PropertyValueFactory("matKhau"));

        TableColumn col3 = new TableColumn("Ho ten");
        col3.setCellValueFactory(new PropertyValueFactory("hoTen"));
        
        TableColumn col4 = new TableColumn("Gioi tinh");
        col4.setCellValueFactory(new PropertyValueFactory("gioiTinh"));
        
        TableColumn col5 = new TableColumn("Ngay Sinh");
        col5.setCellValueFactory(new PropertyValueFactory("ngaySinh"));
        
        TableColumn col6 = new TableColumn("Ma Doi Tuong");
        col6.setCellValueFactory(new PropertyValueFactory("maDoiTuong"));
        
        TableColumn col7 = new TableColumn("Ma Bo Phan");
        col7.setCellValueFactory(new PropertyValueFactory("maBoPhan"));
        
        TableColumn col8 = new TableColumn("Ngay Dang Ky");
        col8.setCellValueFactory(new PropertyValueFactory("ngayDangKy"));
        
        TableColumn col9 = new TableColumn("Ngay Het Han");
        col9.setCellValueFactory(new PropertyValueFactory("ngayHetHan"));
        
        TableColumn col10 = new TableColumn("Email");
        col10.setCellValueFactory(new PropertyValueFactory("email"));
        
        TableColumn col11 = new TableColumn("Dia chi");
        col11.setCellValueFactory(new PropertyValueFactory("diaChi"));
        
        TableColumn col12 = new TableColumn("Dien thoai");
        col12.setCellValueFactory(new PropertyValueFactory("dienThoai"));
        
        TableColumn col13 = new TableColumn("");
        col13.setCellFactory(param -> new TableCell<DocGia, String>() {
            final Button btnXoa = new Button("Xóa");

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btnXoa.setOnAction(event -> {
                        Alert a = Utils.getBox("Ban co chac muon xoa?", Alert.AlertType.CONFIRMATION);
                        Optional<ButtonType> result = a.showAndWait();
                        if(result.get() == ButtonType.OK) {
                            DocGia docGia = getTableView().getItems().get(getIndex());
                        
                        try {
                            DocGiaServices s = new DocGiaServices();
        
                            if(s.xoaDocGia(docGia.getMaDocGia()) == true) {
                                Utils.getBox("Xoa thanh cong!", Alert.AlertType.INFORMATION).show();
                            }
                            else {
                                Utils.getBox("Da co loi!", Alert.AlertType.WARNING).show();
                            }
                        } 
                        catch (Exception e) {
                            Utils.getBox("Da co loi!", Alert.AlertType.WARNING).show();
                        }
                        
                        getTableView().getItems().remove(docGia);
                        }
                    });
                    setGraphic(btnXoa);
                    setText(null);
                }
            }
        });
        
        this.tbDocGia.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9, col10, col11, col12, col13);
    }
    public void suaHandler(ActionEvent evt) throws SQLException {
        if(this.txtPassWord.getText().isBlank() == false) {
            String maDocGia = txtUserName.getText();
        
            DocGia docGia = new DocGia();

            docGia.setMaDocGia(maDocGia);
            docGia.setHoTen(txtHoTen.getText());
            docGia.setEmail(txtEmail.getText());
            docGia.setNgaySinh(parseDateOrNull(dpNgaySinh.valueProperty().get()));
            docGia.setMaDoiTuong(chonDoiTuong());
            docGia.setMaBoPhan(chonBoPhan());
            docGia.setGioiTinh(chooseGender());
            docGia.setNgayDangKy(parseDateOrNull(dpNgayDangKy.valueProperty().get()));
            docGia.setNgayHetHan(parseDateOrNull(dpNgayHetHan.valueProperty().get()));
            docGia.setDiaChi(txtDiaChi.getText());
            docGia.setDienThoai(txtDienThoai.getText());
            docGia.setMatKhau(txtPassWord.getText());


            DocGiaServices s = new DocGiaServices();

            if(s.suaDocGia(docGia) == true) {
                refresh();
                Utils.getBox("Sua thanh cong!", Alert.AlertType.INFORMATION).show();
            }

            else {
                Utils.getBox("Da co loi xay ra!", Alert.AlertType.WARNING).show();
            }
        }
        
        else {
            Utils.getBox("Ban chua nhap mat khau!", Alert.AlertType.WARNING).show();
        }
    }
}
