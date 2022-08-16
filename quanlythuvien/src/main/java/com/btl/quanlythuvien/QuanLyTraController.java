/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.btl.quanlythuvien;

import com.btl.services.SachDocGiaServices;
import com.btl.pojo.SachDocGia;
import com.btl.pojo.TienPhat;
import com.btl.utils.Utils;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
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
public class QuanLyTraController implements Initializable {
    @FXML
    private TableView<SachDocGia> tbSachChuaTra;
    @FXML
    private TableView<SachDocGia> tbSachDaTra;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.loadTableSachChuaTra();
        this.loadColumnsSachChuaTra();
        this.loadColumnsSachDaTra();
    }    
    
    private void loadTableSachChuaTra() {
        SachDocGiaServices s = new SachDocGiaServices();
        try {
            this.tbSachChuaTra.setItems(FXCollections.observableList(s.getDSSachMuon()));
        } catch (SQLException ex) {
            Logger.getLogger(QuanLyTraController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Long tinhKhoangCachNgay(Long dateStart, Long dateEnd) throws ParseException {
        Long diff = dateEnd - dateStart;
        Long diffDays = diff / (24 * 60 * 60 * 1000);
        
        return diffDays;
    }
    
    private void loadColumnsSachChuaTra() {
        TableColumn col1 = new TableColumn("Ma sach");
        col1.setCellValueFactory(new PropertyValueFactory("maSach"));
        
        TableColumn col2 = new TableColumn("Ten sach");
        col2.setCellValueFactory(new PropertyValueFactory("tenSach"));
        
        TableColumn col3 = new TableColumn("Ma doc gia");
        col3.setCellValueFactory(new PropertyValueFactory("maDocGia"));
        
        TableColumn col4 = new TableColumn("Ten doc gia");
        col4.setCellValueFactory(new PropertyValueFactory("hoTen"));
        
        TableColumn col5 = new TableColumn("Ngay muon");
        col5.setCellValueFactory(new PropertyValueFactory("ngayMuon"));
        
        TableColumn col6 = new TableColumn("So luong");
        col6.setCellValueFactory(new PropertyValueFactory("soLuong"));
        
        TableColumn col7 = new TableColumn("");
        col7.setCellFactory(param -> new TableCell<SachDocGia, String>() {
            final Button btnTra = new Button("Tra sach");
            
            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                    setText(null);
                } 
                else {
                    btnTra.setOnAction(event -> {
                        SachDocGia sachDocGia = getTableView().getItems().get(getIndex());
                        SachDocGiaServices s = new SachDocGiaServices();
                        
                        try {
                            LocalDateTime ngayTra = LocalDateTime.now();
                            sachDocGia.setNgayTra(Timestamp.valueOf(ngayTra));
                            
                            TienPhat tienPhat = new TienPhat();
                            tienPhat.setMaTienPhat(1);
                            tienPhat.setSoTienPhat(sachDocGia.getSoLuong() * (float)0.0);
                            if(tinhKhoangCachNgay(sachDocGia.getNgayMuon().getTime(), sachDocGia.getNgayTra().getTime()) > 30) {
                                tienPhat.setMaTienPhat(2);
                                tienPhat.setSoTienPhat(sachDocGia.getSoLuong() * (float)5000.0);
                            }
                            sachDocGia.setTienPhat(tienPhat);
                            
                            if(s.traSach(sachDocGia) == true) {
                                Utils.getBox("Tra sach thanh cong!", Alert.AlertType.INFORMATION).show();
                                tbSachDaTra.getItems().add(sachDocGia);
                                getTableView().getItems().remove(sachDocGia);
                            }
                        }
                        catch(Exception e) {
                            Utils.getBox("Da co loi xay ra!", Alert.AlertType.WARNING).show();
                        }
                    });
                    setGraphic(btnTra);
                    setText(null);
                }
            }
        });

        this.tbSachChuaTra.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
    }
    
    private void loadColumnsSachDaTra() {
        TableColumn col1 = new TableColumn("Ma sach");
        col1.setCellValueFactory(new PropertyValueFactory("maSach"));
        
        TableColumn col2 = new TableColumn("Ten sach");
        col2.setCellValueFactory(new PropertyValueFactory("tenSach"));
        
        TableColumn col3 = new TableColumn("Ma doc gia");
        col3.setCellValueFactory(new PropertyValueFactory("maDocGia"));
        
        TableColumn col4 = new TableColumn("Ten doc gia");
        col4.setCellValueFactory(new PropertyValueFactory("hoTen"));
        
        TableColumn col5 = new TableColumn("Ngay muon");
        col5.setCellValueFactory(new PropertyValueFactory("ngayMuon"));
        
        TableColumn col6 = new TableColumn("Ngay tra");
        col6.setCellValueFactory(new PropertyValueFactory("ngayTra"));
        
        TableColumn col7 = new TableColumn("So luong");
        col7.setCellValueFactory(new PropertyValueFactory("soLuong"));
        
        TableColumn col8 = new TableColumn("Tien phat");
        col8.setCellValueFactory(new PropertyValueFactory("soTienPhat"));
        
        TableColumn col9 = new TableColumn("");
        col9.setCellFactory(param -> new TableCell<SachDocGia, String>() {
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
                            if(s.huyTraSach(sachDocGia) == true) {
                                Utils.getBox("Huy tra sach thanh cong!", Alert.AlertType.INFORMATION).show();
                                tbSachChuaTra.getItems().add(sachDocGia);
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

        this.tbSachDaTra.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8, col9);
    }
}
