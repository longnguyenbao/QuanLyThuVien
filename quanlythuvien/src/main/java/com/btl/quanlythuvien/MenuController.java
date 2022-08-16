/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.btl.quanlythuvien;

import com.btl.pojo.DocGia;
import com.btl.utils.Utils;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class MenuController implements Initializable {
    private Integer maDoiTuong;
    private DocGia docGia;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setMaDoiTuong(Integer maDoiTuong) {
        this.maDoiTuong = maDoiTuong;
    }

    public DocGia getDocGia() {
        return docGia;
    }

    public void setDocGia(DocGia docGia) {
        this.docGia = docGia;
    }
    
    public void quanLySachHandler(ActionEvent evt) throws IOException {
        if(this.maDoiTuong == 2) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLQuanLySach.fxml"));
        
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("QuanLySach");
            stage.show();
        }
        else {
            Utils.getBox("Ban khong co quyen xem muc nay!", Alert.AlertType.WARNING).show();
        }
    }
    
    public void thuVienSachHandler(ActionEvent evt) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLSach.fxml"));
        Parent root = fxmlLoader.load();
        
        SachController sceneMenu = fxmlLoader.getController();
        sceneMenu.setDocGia(docGia);
        
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("ThuVienSach");
        stage.show();
    }
    
    public void quanLyMuonHandler(ActionEvent evt) throws IOException {
        if(this.maDoiTuong == 2) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLQuanLyMuon.fxml"));
        
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("QuanLyMuon");
            stage.show();
        }
        else {
            Utils.getBox("Ban khong co quyen xem muc nay!", Alert.AlertType.WARNING).show();
        }
    }
    
    public void quanLyTraHandler(ActionEvent evt) throws IOException {
        if(this.maDoiTuong == 2) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLQuanLyTra.fxml"));
        
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("QuanLyTra");
            stage.show();
        }
        else {
            Utils.getBox("Ban khong co quyen xem muc nay!", Alert.AlertType.WARNING).show();
        }
    }
    
    public void thongKeMuonHandler(ActionEvent evt) throws IOException {
        if(this.maDoiTuong == 2) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLThongKeMuon.fxml"));
        
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("ThongKeMuon");
            stage.show();
        }
        else {
            Utils.getBox("Ban khong co quyen xem muc nay!", Alert.AlertType.WARNING).show();
        }
    }
    
    public void thongKeTraHandler(ActionEvent evt) throws IOException {
        if(this.maDoiTuong == 2) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLThongKeTra.fxml"));
        
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("ThongKeTra");
            stage.show();
        }
        else {
            Utils.getBox("Ban khong co quyen xem muc nay!", Alert.AlertType.WARNING).show();
        }
    }
    public void quanLyDocGiaHandler(ActionEvent evt) throws IOException {
        if(this.maDoiTuong == 2) {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FXMLQuanLyDocGia.fxml"));
        
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("QuanLyDocGia");
            stage.show();
        }
        else {
            Utils.getBox("Ban khong co quyen xem muc nay!", Alert.AlertType.WARNING).show();
        }
    }
}