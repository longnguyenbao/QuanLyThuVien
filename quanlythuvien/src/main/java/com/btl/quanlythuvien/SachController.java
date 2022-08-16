/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.btl.quanlythuvien;

import com.btl.pojo.DanhMuc;
import com.btl.pojo.DocGia;
import com.btl.pojo.Sach;
import com.btl.pojo.TacGia;
import com.btl.pojo.SachDocGia;
import com.btl.services.DanhMucServices;
import com.btl.services.SachServices;
import com.btl.services.SachDocGiaServices;
import com.btl.services.TacGiaServices;
import com.btl.utils.Utils;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


/**
 * FXML Controller class
 *
 * @author admin
 */
public class SachController implements Initializable {
    private DocGia docGia;
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
    private Button btnLamMoi;
    @FXML
    private TableView<SachDocGia> tbSachMuon;
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
        
        this.txtMaSach.setEditable(false);
        this.txtTenSach.setEditable(false);
        this.areaMoTa.setEditable(false);
        this.txtNamXuatBan.setEditable(false);
        this.txtNoiXuatBan.setEditable(false);
        this.dateNgayNhap.setEditable(false);
        this.txtViTri.setEditable(false);
        this.cbxDanhMuc.setEditable(false);
        
        this.btnLamMoi.setOnAction(e -> refresh());
        
        this.loadColumnsSachMuon();
        
        this.txtKeywords.textProperty().addListener((evt) ->{
            this.loadTableSach(this.txtKeywords.getText(), this.cbxLoaiTimKiem.getSelectionModel().getSelectedIndex());
        });
        
        this.cbxLoaiTimKiem.getItems().addAll("Theo ten sach", "Theo ten tac gia", "Theo nam xuat ban", "Theo danh muc");
        this.cbxLoaiTimKiem.getSelectionModel().select(0);
        this.cbxLoaiTimKiem.valueProperty().addListener((evt) ->{
            this.loadTableSach(this.txtKeywords.getText(), this.cbxLoaiTimKiem.getSelectionModel().getSelectedIndex());
        });
    }

    public DocGia getDocGia() {
        return docGia;
    }

    public void setDocGia(DocGia docGia) {
        this.docGia = docGia;
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
        this.cbxLoaiTimKiem.getSelectionModel().select(0);
    }
    
    private void loadTableSach(String kw, Integer loaiTimKiem) {
        SachServices s = new SachServices();
        try {
            this.tbSach.setItems(FXCollections.observableList(s.getDSSach(kw, loaiTimKiem)));
        } catch (SQLException ex) {
            Logger.getLogger(SachController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadColumnsSach() {
        TableColumn col1 = new TableColumn("Masach");
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
            final Button btnMuon = new Button("Muon");

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btnMuon.setOnAction(event -> {
                        Sach sach = getTableView().getItems().get(getIndex());
                        
                        SachDocGia sachDocGia = new SachDocGia();
                        sachDocGia.setSach(sach);
                        sachDocGia.setDocGia(docGia);
                        sachDocGia.setSoLuong(1);

                        int rowIndex = getRowIndex(tbSachMuon, sachDocGia);
                        
                        if (rowIndex == -1){
                            tbSachMuon.getItems().add(sachDocGia);
                        }
                        else {
                            SachDocGia currentRow = tbSachMuon.getItems().get(rowIndex);
                            currentRow.setSoLuong(currentRow.getSoLuong()+1);
                            tbSachMuon.refresh();
                        }
                    });
                    setGraphic(btnMuon);
                    setText(null);
                }
            }
        });
        
        this.tbSach.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9);
    }
    
    private int getRowIndex(TableView<SachDocGia> table, SachDocGia sachDocGia) {
        for(int i = 0; i < table.getItems().size(); i++) {
            if (sachDocGia.getSach().getMaSach().equals( table.getItems().get(i).getSach().getMaSach())) {
                return i;
            }
        }
        return -1;
    }
    
    private void loadTableTacGia(String kw) {
        TacGiaServices s = new TacGiaServices();
        try {
            this.tbTacGia.setItems(FXCollections.observableList(s.getDSTacGiaTheoMaSach(kw)));
        } catch (SQLException ex) {
            Logger.getLogger(SachController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadColumnsTacGia() {
        TableColumn col1 = new TableColumn("Ma tac gia");
        col1.setCellValueFactory(new PropertyValueFactory("maTacGia"));

        TableColumn col2 = new TableColumn("Ten tac gia");
        col2.setCellValueFactory(new PropertyValueFactory("tenTacGia"));
        
        this.tbTacGia.getColumns().addAll(col1, col2);
    }
    
    private void loadColumnsSachMuon() { 
        TableColumn col1 = new TableColumn("Ma Sach");
        col1.setCellValueFactory(new PropertyValueFactory("maSach"));

        TableColumn col2 = new TableColumn("Ten sach");
        col2.setCellValueFactory(new PropertyValueFactory("tenSach"));
        
        TableColumn col3 = new TableColumn("So luong");
        col3.setCellValueFactory(new PropertyValueFactory("soLuong"));
        
        TableColumn col4 = new TableColumn("");
        col4.setCellFactory(param -> new TableCell<SachDocGia, String>() {
            final Button btnXoa = new Button("Xóa");

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    btnXoa.setOnAction(event -> {
                        SachDocGia sachDocGia = getTableView().getItems().get(getIndex());
                        getTableView().getItems().remove(sachDocGia);
                    });
                    setGraphic(btnXoa);
                    setText(null);
                }
            }
        });
        
        this.tbSachMuon.getColumns().addAll(col1, col2, col3, col4);
    }
    
    private void loadComboBoxDanhMuc() {
        DanhMucServices s = new DanhMucServices();
        try {
            this.cbxDanhMuc.setItems(FXCollections.observableArrayList(s.getDSDanhMuc()));
        } catch (SQLException ex) {
            Logger.getLogger(QuanLySachController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void datSachHander(ActionEvent evt) throws SQLException {
        Alert a = Utils.getBox("Ban co chac muon dat sach?", Alert.AlertType.CONFIRMATION);
        Optional<ButtonType> result = a.showAndWait();
        
        if(result.get() == ButtonType.OK) {
            ArrayList<SachDocGia> dsSachDocGia = new ArrayList(tbSachMuon.getItems());
        
            SachDocGiaServices s = new SachDocGiaServices();

            switch(s.datSach(dsSachDocGia)) {
                case -4:
                    Utils.getBox("Ban da muon qua so luot muon sach con lai!", Alert.AlertType.WARNING).show();
                    break;
                case -3:
                    Utils.getBox("Khong tim thay user!", Alert.AlertType.WARNING).show();
                    break;
                case -2:
                    Utils.getBox("Ban chua chon sach nao!", Alert.AlertType.WARNING).show();
                    break;
                case -1:
                    Utils.getBox("So luong sach da vuot qua quy dinh!", Alert.AlertType.WARNING).show();
                    break;
                case 1:
                    Utils.getBox("Dat sach thanh cong!", Alert.AlertType.INFORMATION).show();
                    tbSachMuon.getItems().clear();
                    break;
                default:
                    Utils.getBox("Da co loi xay ra!", Alert.AlertType.WARNING).show();
                    break;
            }
        }
    }
}