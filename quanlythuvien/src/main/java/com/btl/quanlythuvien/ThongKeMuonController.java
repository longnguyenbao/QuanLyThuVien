/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.btl.quanlythuvien;

import com.btl.services.SachDocGiaServices;
import com.btl.pojo.ThongKe;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author admin
 */
public class ThongKeMuonController implements Initializable {
    @FXML
    private BorderPane bpThongKeTheoNam;
    @FXML
    private BorderPane bpThongKeTheoQuy;
    @FXML
    private ComboBox<Integer> cbNam;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {
            Integer currYear = LocalDate.now().getYear();
            for(int i = currYear; i >= currYear-10; i--) {
                cbNam.getItems().add(i);
            }
            cbNam.setValue(currYear);
            
            this.thongKeTheoNam(cbNam.getValue());
            this.thongKeTheoQuy(cbNam.getValue());
            
            cbNam.valueProperty().addListener(evt -> {
                try {
                    this.thongKeTheoNam(cbNam.getValue());
                    this.thongKeTheoQuy(cbNam.getValue());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
    private void thongKeTheoNam(Integer nam) throws SQLException {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Nam");
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("So luong muon");
        
        BarChart barChart = new BarChart(xAxis, yAxis);
        
        SachDocGiaServices s = new SachDocGiaServices();
        ThongKe thongKe = s.thongKeMuonTheoNam(nam);
        
        XYChart.Series data = new XYChart.Series();
        data.setName("Thong ke theo nam");
        
        data.getData().add(new XYChart.Data(thongKe.getNam().toString(), thongKe.getTongSoLuong()));
        
        barChart.getData().add(data);
        barChart.setLegendVisible(false);
        
        bpThongKeTheoNam.setCenter(barChart);
    }
    
    private void thongKeTheoQuy(Integer nam) throws SQLException {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Quy");
        
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("So luong muon");
        
        BarChart barChart = new BarChart(xAxis, yAxis);
        
        SachDocGiaServices s = new SachDocGiaServices();
        ArrayList<ThongKe> dsThongKe = s.thongKeMuonTheoQuy(nam);
        
        XYChart.Series data = new XYChart.Series();
        data.setName("Thong ke theo quy");

        for(ThongKe thongKe: dsThongKe){
            data.getData().add(new XYChart.Data(thongKe.getQuy().toString(), thongKe.getTongSoLuong()));
        }
        
        barChart.getData().add(data);
        barChart.setLegendVisible(false);
        
        bpThongKeTheoQuy.setCenter(barChart);
    }
}
