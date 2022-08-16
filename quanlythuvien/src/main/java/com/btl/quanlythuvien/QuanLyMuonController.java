/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.btl.quanlythuvien;

import com.btl.services.SachDocGiaServices;
import com.btl.pojo.SachDocGia;
import com.btl.utils.Utils;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class QuanLyMuonController implements Initializable {
    @FXML
    private TableView<SachDocGia> tbSachChuaMuon;
    @FXML
    private TableView<SachDocGia> tbSachDaMuon;
    @FXML
    private Button btnXoa;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.xoaSachDatQua48Gio();
            this.btnXoa.setOnAction(e -> this.xoaSachDatQua48Gio());
        }
        catch(Exception e){
        }
        
        this.loadTableSachChuaMuon();
        this.loadColumnsSachChuaMuon();
        this.loadColumnsSachDaMuon();
    }    
    
    private void loadTableSachChuaMuon() {
        SachDocGiaServices s = new SachDocGiaServices();
        try {
            this.tbSachChuaMuon.setItems(FXCollections.observableList(s.getDSSachDat()));
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyMuonController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void loadColumnsSachChuaMuon() {
        TableColumn col1 = new TableColumn("Ma sach");
        col1.setCellValueFactory(new PropertyValueFactory("maSach"));
        
        TableColumn col2 = new TableColumn("Ten sach");
        col2.setCellValueFactory(new PropertyValueFactory("tenSach"));
        
        TableColumn col3 = new TableColumn("Ma doc gia");
        col3.setCellValueFactory(new PropertyValueFactory("maDocGia"));
        
        TableColumn col4 = new TableColumn("Ten doc gia");
        col4.setCellValueFactory(new PropertyValueFactory("hoTen"));
        
        TableColumn col5 = new TableColumn("Ngay dat sach");
        col5.setCellValueFactory(new PropertyValueFactory("ngayDat"));
        
        TableColumn col6 = new TableColumn("So luong");
        col6.setCellValueFactory(new PropertyValueFactory("soLuong"));
        
        TableColumn col7 = new TableColumn("");
        col7.setCellFactory(param -> new TableCell<SachDocGia, String>() {
            final Button btnChoMuon = new Button("Cho muon");
            
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } 
                else {
                    btnChoMuon.setOnAction(event -> {
                        SachDocGia sachDocGia = getTableView().getItems().get(getIndex());
                        SachDocGiaServices s = new SachDocGiaServices();
                        
                        try {
                            sachDocGia.setNgayMuon(Timestamp.valueOf(LocalDateTime.now()));
                            if(s.muonSach(sachDocGia) == true) {
                                Utils.getBox("Cho muon thanh cong!", Alert.AlertType.INFORMATION).show();
                                tbSachDaMuon.getItems().add(sachDocGia);
                                getTableView().getItems().remove(sachDocGia);
                            }
                            else {
                                Utils.getBox("Sach da dat truoc qua thoi han cho phep!", Alert.AlertType.WARNING).show();
                            }
                        }
                        catch(Exception e) {
                            Utils.getBox("Da co loi xay ra!", Alert.AlertType.WARNING).show();
                        }
                    });
                    setGraphic(btnChoMuon);
                    setText(null);
                }
            }
        });

        this.tbSachChuaMuon.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
    }
    
    private void loadColumnsSachDaMuon() {
        TableColumn col1 = new TableColumn("Ma sach");
        col1.setCellValueFactory(new PropertyValueFactory("maSach"));
        
        TableColumn col2 = new TableColumn("Ten sach");
        col2.setCellValueFactory(new PropertyValueFactory("tenSach"));
        
        TableColumn col3 = new TableColumn("Ma doc gia");
        col3.setCellValueFactory(new PropertyValueFactory("maDocGia"));
        
        TableColumn col4 = new TableColumn("Ten doc gia");
        col4.setCellValueFactory(new PropertyValueFactory("hoTen"));
        
        TableColumn col5 = new TableColumn("Ngay dat sach");
        col5.setCellValueFactory(new PropertyValueFactory("ngayDat"));
        
        TableColumn col6 = new TableColumn("So luong");
        col6.setCellValueFactory(new PropertyValueFactory("soLuong"));
        
        TableColumn col7 = new TableColumn("");
        col7.setCellFactory(param -> new TableCell<SachDocGia, String>() {
            final Button btnHuy = new Button("Huy");
            
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } 
                else {
                    btnHuy.setOnAction(event -> {
                        SachDocGia sachDocGia = getTableView().getItems().get(getIndex());

                        SachDocGiaServices s = new SachDocGiaServices();
                        
                        try {
                            if(s.huyMuonSach(sachDocGia) == true) {
                                Utils.getBox("Huy muon thanh cong!", Alert.AlertType.INFORMATION).show();
                                tbSachChuaMuon.getItems().add(sachDocGia);
                                getTableView().getItems().remove(sachDocGia);
                            }
                        }
                        catch(Exception e) {
                            Utils.getBox("Da co loi xay ra!", Alert.AlertType.WARNING).show();
                        }
                    });
                    setGraphic(btnHuy);
                    setText(null);
                }
            }
        });

        this.tbSachDaMuon.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
    }
    
    private void xoaSachDatQua48Gio() {
        try {
            SachDocGiaServices.xoaSachDatQua48Gio();
        }
        catch (SQLException e) {
        }
    }
}