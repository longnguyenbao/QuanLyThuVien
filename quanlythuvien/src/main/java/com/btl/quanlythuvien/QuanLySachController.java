/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.btl.quanlythuvien;

import com.btl.pojo.Sach;
import com.btl.pojo.TacGia;
import com.btl.pojo.DanhMuc;
import com.btl.services.DanhMucServices;
import com.btl.services.SachServices;
import com.btl.services.SachTacGiaServices;
import com.btl.services.TacGiaServices;
import com.btl.utils.Utils;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;
import javafx.scene.control.ButtonType;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class QuanLySachController implements Initializable {
    @FXML
    private TableView<Sach> tbSach;
    @FXML
    private TextField txtKeywords;
    @FXML
    private TextField txtMaSach;
    @FXML
    private TextField txtTenSach;
    @FXML
    private TextArea areaMoTa;
    @FXML
    private TextField txtNamXuatBan;
    @FXML
    private TextField txtNoiXuatBan;
    @FXML
    private DatePicker dateNgayNhap;
    @FXML
    private TextField txtViTri;
    @FXML
    private TableView<TacGia> tbTacGia;
    @FXML
    private ComboBox<TacGia> cbxTacGia;
    @FXML
    private Button btnThemTacGia;
    @FXML
    private Button btnLamMoi;
    @FXML
    private Button btnTaoMaSach;
    @FXML
    private ComboBox<DanhMuc> cbxDanhMuc;
    @FXML
    private ComboBox cbxLoaiTimKiem;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.refresh();
        
        this.loadColumnsSach();
        this.loadColumnsTacGia();
        this.loadComboBoxTacGia(); 
        this.loadComboBoxDanhMuc();
        
        this.tbSach.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                String maSach = tbSach.getSelectionModel().getSelectedItem().getMaSach();
                this.txtMaSach.setText(maSach);
                this.txtTenSach.setText(tbSach.getSelectionModel().getSelectedItem().getTenSach());
                this.areaMoTa.setText(tbSach.getSelectionModel().getSelectedItem().getMoTa());
                this.txtNamXuatBan.setText(Integer.toString(tbSach.getSelectionModel().getSelectedItem().getNamXuatBan()));
                this.txtNoiXuatBan.setText(tbSach.getSelectionModel().getSelectedItem().getNoiXuatBan());
                this.dateNgayNhap.setValue(tbSach.getSelectionModel().getSelectedItem().getNgayNhap().toLocalDate());
                this.txtViTri.setText(tbSach.getSelectionModel().getSelectedItem().getViTri());
                
                int maDanhMuc = tbSach.getSelectionModel().getSelectedItem().getMaDanhMuc();
                try {
                    DanhMuc danhMuc = DanhMucServices.getDanhMucById(maDanhMuc);
                    this.cbxDanhMuc.setValue(danhMuc);
                }
                catch (SQLException e){
                }

                this.loadTableTacGia(maSach);
            }
        });
        
        this.txtNamXuatBan.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtNamXuatBan.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
        
        this.txtMaSach.setEditable(false);
        this.btnThemTacGia.setOnAction(e -> addTacGia());
        this.btnLamMoi.setOnAction(e -> refresh());
        this.btnTaoMaSach.setOnAction(e -> taoMaSach());
        
        this.txtKeywords.textProperty().addListener((evt) ->{
            this.loadTableSach(this.txtKeywords.getText(), this.cbxLoaiTimKiem.getSelectionModel().getSelectedIndex());
        });
        
        this.cbxLoaiTimKiem.getItems().addAll("Theo ten sach", "Theo ten tac gia", "Theo nam xuat ban", "Theo danh muc");
        this.cbxLoaiTimKiem.getSelectionModel().select(0);
        this.cbxLoaiTimKiem.valueProperty().addListener((evt) ->{
            this.loadTableSach(this.txtKeywords.getText(), this.cbxLoaiTimKiem.getSelectionModel().getSelectedIndex());
        });
    }    
    
    private void refresh() {
        this.loadTableSach(null, null);
        this.txtMaSach.clear();
        this.txtTenSach.clear();
        this.areaMoTa.clear();
        this.txtNamXuatBan.clear();
        this.txtNoiXuatBan.clear();
        this.dateNgayNhap.getEditor().clear();
        this.txtViTri.clear();
        this.cbxDanhMuc.getSelectionModel().clearSelection();
        this.cbxDanhMuc.setValue(null);
        
        this.cbxTacGia.getSelectionModel().clearSelection();
        this.cbxTacGia.setValue(null);
        
        this.cbxLoaiTimKiem.getSelectionModel().select(0);
    }
    
    private void loadTableSach(String kw, Integer  loaiTimKiem) {
        SachServices s = new SachServices();
        try {
            this.tbSach.setItems(FXCollections.observableList(s.getDSSach(kw, loaiTimKiem)));
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySachController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadColumnsSach() {
        TableColumn col1 = new TableColumn("Ma sach");
        col1.setCellValueFactory(new PropertyValueFactory("maSach"));

        TableColumn col2 = new TableColumn("Ten sach");
        col2.setCellValueFactory(new PropertyValueFactory("tenSach"));

        TableColumn col3 = new TableColumn("Mo ta");
        col3.setCellValueFactory(new PropertyValueFactory("moTa"));
        
        TableColumn col4 = new TableColumn("Nam xuat ban");
        col4.setCellValueFactory(new PropertyValueFactory("namXuatBan"));
        
        TableColumn col5 = new TableColumn("Noi xuat ban");
        col5.setCellValueFactory(new PropertyValueFactory("noiXuatBan"));
        
        TableColumn col6 = new TableColumn("Ngay nhap");
        col6.setCellValueFactory(new PropertyValueFactory("ngayNhap"));
        
        TableColumn col7 = new TableColumn("Vi tri");
        col7.setCellValueFactory(new PropertyValueFactory("viTri"));
        
        TableColumn col8 = new TableColumn("Ma danh muc");
        col8.setCellValueFactory(new PropertyValueFactory("maDanhMuc"));
        
        TableColumn col9 = new TableColumn("");
        col9.setCellFactory(param -> new TableCell<Sach, String>() {
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
                            Sach sach = getTableView().getItems().get(getIndex());
                        
                            try {
                                SachServices s = new SachServices();

                                if(s.xoaSach(sach.getMaSach()) == true) {
                                    Utils.getBox("Xoa thanh cong!", Alert.AlertType.INFORMATION).show();
                                }
                            }
                            catch (Exception e) {
                                Utils.getBox("Da co loi xay ra!", Alert.AlertType.WARNING).show();
                            }

                            getTableView().getItems().remove(sach);
                        }
                    });
                    setGraphic(btnXoa);
                    setText(null);
                }
            }
        });
        
        this.tbSach.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9);
    }
    
    private void loadTableTacGia(String kw) {
        TacGiaServices s = new TacGiaServices();
        try {
            this.tbTacGia.setItems(FXCollections.observableList(s.getDSTacGiaTheoMaSach(kw)));
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySachController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadColumnsTacGia() {
        TableColumn col1 = new TableColumn("Ma tac gia");
        col1.setCellValueFactory(new PropertyValueFactory("maTacGia"));

        TableColumn col2 = new TableColumn("Ten tac gia");
        col2.setCellValueFactory(new PropertyValueFactory("tenTacGia"));
        
        TableColumn col3 = new TableColumn("");
        col3.setCellFactory(param -> new TableCell<TacGia, String>() {
            final Button btnXoa = new Button("Xóa");

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btnXoa.setOnAction(event -> {
                        TacGia tacGia = getTableView().getItems().get(getIndex());
                        getTableView().getItems().remove(tacGia);
                    });
                    setGraphic(btnXoa);
                    setText(null);
                }
            }
        });
        
        this.tbTacGia.getColumns().addAll(col1, col2, col3);
    }
    
    private void loadComboBoxTacGia() {
        TacGiaServices s = new TacGiaServices();
        try {
            this.cbxTacGia.setItems(FXCollections.observableArrayList(s.getDSTacGia()));
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySachController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadComboBoxDanhMuc() {
        DanhMucServices s = new DanhMucServices();
        try {
            this.cbxDanhMuc.setItems(FXCollections.observableArrayList(s.getDSDanhMuc()));
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySachController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addTacGia() {
        TacGia tacGia = new TacGia();
        tacGia.setMaTacGia(cbxTacGia.getSelectionModel().getSelectedItem().getMaTacGia());
        tacGia.setTenTacGia(cbxTacGia.getSelectionModel().getSelectedItem().getTenTacGia());
        tbTacGia.getItems().add(tacGia);
    }
    
    private Integer parseIntOrNull(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    private Date parseDateOrNull(LocalDate value) {
        try {
            return Date.valueOf(value);
        } catch (Exception e) {
            return null;
        }
    }
    
    public void suaHandler(ActionEvent evt) throws SQLException {
        if(txtTenSach.getText().isBlank() == false) {
            Alert a = Utils.getBox("Ban co chac muon sua?", Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = a.showAndWait();

            if(result.get() == ButtonType.OK) {
                String maSach = txtMaSach.getText();
                if(maSach.isEmpty()) {
                    Utils.getBox("Chua nhap ma sach!", Alert.AlertType.WARNING).show();
                }

                else if (!SachServices.checkSachExist(maSach)) {
                    Utils.getBox("Ma sach khong ton tai!", Alert.AlertType.WARNING).show();
                }

                else {
                    ArrayList<Integer> dsMaTacGia = new ArrayList();

                    for (TacGia tacGia : tbTacGia.getItems()) {
                        dsMaTacGia.add(tacGia.getMaTacGia());
                    }

                    Sach sach = new Sach();
                    sach.setMaSach(maSach);
                    sach.setTenSach(txtTenSach.getText());
                    sach.setMoTa(areaMoTa.getText());
                    sach.setNamXuatBan(0);
                    if(!txtNamXuatBan.getText().isEmpty())
                        sach.setNamXuatBan(parseIntOrNull(txtNamXuatBan.getText()));
                    sach.setNoiXuatBan(txtNoiXuatBan.getText());
                    sach.setNgayNhap(Date.valueOf(LocalDate.now()));
                    if(dateNgayNhap.getValue() != null)
                        sach.setNgayNhap(parseDateOrNull(dateNgayNhap.valueProperty().get()));
                    sach.setViTri(txtViTri.getText());
                    sach.setMaDanhMuc(1);
                    if(cbxDanhMuc.getValue() != null)
                        sach.setMaDanhMuc(cbxDanhMuc.getSelectionModel().getSelectedItem().getMaDanhMuc());

                    SachTacGiaServices s1 = new SachTacGiaServices();
                    SachServices s2 = new SachServices();

                    if(s1.xoaSachTacGia(maSach) == true && s1.themSachTacGia(maSach, dsMaTacGia) == true && s2.suaSach(sach) == true) {
                        Utils.getBox("Sua thanh cong!", Alert.AlertType.INFORMATION).show();
                        this.refresh();
                    }

                    else {
                        Utils.getBox("Da co loi xay ra!", Alert.AlertType.WARNING).show();
                    }
                }
            }
        }
        
        else {
            Utils.getBox("Ban chua nhap ten sach!", Alert.AlertType.WARNING).show();
        }
    }
    
    private void taoMaSach() {
        this.txtMaSach.setText(UUID.randomUUID().toString());
    }
    
    public void themHandler(ActionEvent evt) throws SQLException {
        if(txtTenSach.getText().isBlank() == false) {
            Alert a = Utils.getBox("Ban co chac muon them?", Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = a.showAndWait();
            if(result.get() == ButtonType.OK) {
                String maSach = txtMaSach.getText();

                if(maSach.isEmpty()) {
                    Utils.getBox("Chua nhap ma sach!", Alert.AlertType.WARNING).show();
                }

                else if (SachServices.checkSachExist(maSach)) {
                    Utils.getBox("Ma sach da ton tai!", Alert.AlertType.WARNING).show();
                }

                else {
                    ArrayList<Integer> dsMaTacGia = new ArrayList();

                    for (TacGia tacGia : tbTacGia.getItems()) {
                        dsMaTacGia.add(tacGia.getMaTacGia());
                    }

                    Sach sach = new Sach();
                    sach.setMaSach(maSach);
                    sach.setTenSach(txtTenSach.getText());
                    sach.setMoTa(areaMoTa.getText());
                    sach.setNamXuatBan(0);
                    if(!txtNamXuatBan.getText().isEmpty())
                        sach.setNamXuatBan(parseIntOrNull(txtNamXuatBan.getText()));
                    sach.setNoiXuatBan(txtNoiXuatBan.getText());
                    sach.setNgayNhap(Date.valueOf(LocalDate.now()));
                    if(dateNgayNhap.getValue() != null)
                        sach.setNgayNhap(parseDateOrNull(dateNgayNhap.valueProperty().get()));
                    sach.setViTri(txtViTri.getText());
                    sach.setMaDanhMuc(1);
                    if(cbxDanhMuc.getValue() != null)
                        sach.setMaDanhMuc(cbxDanhMuc.getSelectionModel().getSelectedItem().getMaDanhMuc());

                    SachServices s = new SachServices();
                    SachTacGiaServices s1 = new SachTacGiaServices();

                    if(s.themSach(sach) == true && s1.themSachTacGia(maSach, dsMaTacGia) == true) {
                        Utils.getBox("Them thanh cong!", Alert.AlertType.INFORMATION).show();
                        this.refresh();
                    }
                    else {
                        Utils.getBox("Da co loi xay ra!", Alert.AlertType.WARNING).show();
                    }
                }
            }
        }
        else {
            Utils.getBox("Ban chua nhap ten sach!", Alert.AlertType.WARNING).show();
        }
    }
}
