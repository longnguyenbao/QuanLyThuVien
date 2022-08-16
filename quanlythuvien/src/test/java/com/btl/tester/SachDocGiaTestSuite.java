/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.tester;

import com.btl.pojo.DocGia;
import com.btl.pojo.Sach;
import com.btl.pojo.SachDocGia;
import com.btl.pojo.ThongKe;
import com.btl.pojo.TienPhat;
import com.btl.services.SachDocGiaServices;
import com.btl.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;

/**
 *
 * @author admin
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SachDocGiaTestSuite {
    private static Connection conn;
    private static SachDocGiaServices s;
    
    @BeforeAll
    public static void beforeAll() {
        s = new SachDocGiaServices();
        try {
            conn = JdbcUtils.getConn();
        } catch (SQLException ex) {
            Logger.getLogger(SachDocGiaTestSuite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void afterAll() {
        if (conn != null)
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(SachDocGiaTestSuite.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    @Test
    @Order(1)
    public void testDatSach() throws SQLException {
        Sach sach = new Sach();
        sach.setMaSach("115dd543-06f0-417e-941a-1ec75fde5f64");
        
        DocGia docGia = new DocGia();
        docGia.setMaDocGia("user");
        
        SachDocGia sachDocGia = new SachDocGia();
        sachDocGia.setSach(sach);
        sachDocGia.setDocGia(docGia);
        sachDocGia.setNgayDat(Timestamp.valueOf(LocalDate.now() + " 00:00:00.0"));
        sachDocGia.setSoLuong(1);
              
        ArrayList<SachDocGia> dsSachDocGia = new ArrayList();
        dsSachDocGia.add(sachDocGia);
        
        Assertions.assertEquals(1, s.datSach(dsSachDocGia));
        
        ArrayList<SachDocGia> newDSSachDocGia = s.getDSSachDatByIds(dsSachDocGia);
        Assertions.assertNotNull(newDSSachDocGia);
        
        for (int i = 0; i < dsSachDocGia.size(); i++) {
            Assertions.assertEquals(dsSachDocGia.get(i).getMaSach(), newDSSachDocGia.get(i).getMaSach());
            Assertions.assertEquals(dsSachDocGia.get(i).getMaDocGia(), newDSSachDocGia.get(i).getMaDocGia());
            Assertions.assertEquals(dsSachDocGia.get(i).getNgayDat(), newDSSachDocGia.get(i).getNgayDat());
        }
    }
    
    @Test
    @Order(2)
    public void testDatSachErr1() throws SQLException {    
        ArrayList<SachDocGia> dsSachDocGia = new ArrayList();
        
        Assertions.assertEquals(-2, s.datSach(dsSachDocGia));
        
        ArrayList<SachDocGia> newDSSachDocGia = s.getDSSachDatByIds(dsSachDocGia);
        Assertions.assertTrue(newDSSachDocGia.isEmpty());
    }
    
    @Test
    @Order(3)
    public void testDatSachErr2() throws SQLException {
        Sach sach = new Sach();
        sach.setMaSach("115dd543-06f0-417e-941a-1ec75fde5f64");
        
        DocGia docGia = new DocGia();
        docGia.setMaDocGia("user");
        
        SachDocGia sachDocGia1 = new SachDocGia();
        sachDocGia1.setSach(sach);
        sachDocGia1.setDocGia(docGia);
        sachDocGia1.setNgayDat(Timestamp.valueOf(LocalDate.now() + " 01:00:00.0"));
        sachDocGia1.setSoLuong(1);
        
        SachDocGia sachDocGia2 = new SachDocGia();
        sachDocGia2.setSach(sach);
        sachDocGia2.setDocGia(docGia);
        sachDocGia2.setNgayDat(Timestamp.valueOf(LocalDate.now() + " 02:00:00.0"));
        sachDocGia2.setSoLuong(1);
        
        SachDocGia sachDocGia3 = new SachDocGia();
        sachDocGia3.setSach(sach);
        sachDocGia3.setDocGia(docGia);
        sachDocGia3.setNgayDat(Timestamp.valueOf(LocalDate.now() + " 03:00:00.0"));
        sachDocGia3.setSoLuong(1);
        
        SachDocGia sachDocGia4 = new SachDocGia();
        sachDocGia4.setSach(sach);
        sachDocGia4.setDocGia(docGia);
        sachDocGia4.setNgayDat(Timestamp.valueOf(LocalDate.now() + " 04:00:00.0"));
        sachDocGia4.setSoLuong(1);
        
        SachDocGia sachDocGia5 = new SachDocGia();
        sachDocGia5.setSach(sach);
        sachDocGia5.setDocGia(docGia);
        sachDocGia5.setNgayDat(Timestamp.valueOf(LocalDate.now() + " 05:00:00.0"));
        sachDocGia5.setSoLuong(1);
        
        SachDocGia sachDocGia6 = new SachDocGia();
        sachDocGia6.setSach(sach);
        sachDocGia6.setDocGia(docGia);
        sachDocGia6.setNgayDat(Timestamp.valueOf(LocalDate.now() + " 06:00:00.0"));
        sachDocGia6.setSoLuong(1);
              
        ArrayList<SachDocGia> dsSachDocGia = new ArrayList();
        dsSachDocGia.add(sachDocGia1);
        dsSachDocGia.add(sachDocGia2);
        dsSachDocGia.add(sachDocGia3);
        dsSachDocGia.add(sachDocGia4);
        dsSachDocGia.add(sachDocGia5);
        dsSachDocGia.add(sachDocGia6);
        
        Assertions.assertEquals(-1, s.datSach(dsSachDocGia));
        
        ArrayList<SachDocGia> newDSSachDocGia = s.getDSSachDatByIds(dsSachDocGia);
        Assertions.assertTrue(newDSSachDocGia.isEmpty());
    }
    
    @Test
    @Order(4)
    public void testDatSachErr3() throws SQLException {
        Sach sach = new Sach();
        sach.setMaSach("115dd543-06f0-417e-941a-1ec75fde5f64");
        
        DocGia docGia = new DocGia();
        docGia.setMaDocGia("user");
        
        SachDocGia sachDocGia = new SachDocGia();
        sachDocGia.setSach(sach);
        sachDocGia.setDocGia(docGia);
        sachDocGia.setNgayDat(Timestamp.valueOf(LocalDate.now() + " 07:00:00.0"));
        sachDocGia.setSoLuong(6);

              
        ArrayList<SachDocGia> dsSachDocGia = new ArrayList();
        dsSachDocGia.add(sachDocGia);
        
        Assertions.assertEquals(-1, s.datSach(dsSachDocGia));
        
        ArrayList<SachDocGia> newDSSachDocGia = s.getDSSachDatByIds(dsSachDocGia);
        Assertions.assertTrue(newDSSachDocGia.isEmpty());
    }
    
    @Test
    @Order(5)
    public void testDatSachErr4() throws SQLException {
        Sach sach = new Sach();
        sach.setMaSach("115dd543-06f0-417e-941a-1ec75fde5f64");
        
        DocGia docGia = new DocGia();
        docGia.setMaDocGia("user");
        
        SachDocGia sachDocGia = new SachDocGia();
        sachDocGia.setSach(sach);
        sachDocGia.setDocGia(docGia);
        sachDocGia.setNgayDat(Timestamp.valueOf(LocalDate.now() + " 08:00:00.0"));
        sachDocGia.setSoLuong(5);

              
        ArrayList<SachDocGia> dsSachDocGia = new ArrayList();
        dsSachDocGia.add(sachDocGia);
        
        Assertions.assertEquals(-4, s.datSach(dsSachDocGia));
        
        ArrayList<SachDocGia> newDSSachDocGia = s.getDSSachDatByIds(dsSachDocGia);
        Assertions.assertTrue(newDSSachDocGia.isEmpty());
    }
    
    @Test
    @Order(6)
    public void testDatSachErr5() throws SQLException {
        Sach sach = new Sach();
        sach.setMaSach("115dd543-06f0-417e-941a-1ec75fde5f64");
        
        DocGia docGia = new DocGia();
        docGia.setMaDocGia("user123");
        
        SachDocGia sachDocGia = new SachDocGia();
        sachDocGia.setSach(sach);
        sachDocGia.setDocGia(docGia);
        sachDocGia.setNgayDat(Timestamp.valueOf(LocalDate.now() + " 09:00:00.0"));
        sachDocGia.setSoLuong(1);
     
        ArrayList<SachDocGia> dsSachDocGia = new ArrayList();
        dsSachDocGia.add(sachDocGia);
        
        Assertions.assertEquals(-3, s.datSach(dsSachDocGia));
        
        ArrayList<SachDocGia> newDSSachDocGia = s.getDSSachDatByIds(dsSachDocGia);
        Assertions.assertTrue(newDSSachDocGia.isEmpty());
    }
    
    @Test
    @Order(7)
    public void testMuonSach() throws SQLException {
        Sach sach = new Sach();
        sach.setMaSach("115dd543-06f0-417e-941a-1ec75fde5f64");
        
        DocGia docGia = new DocGia();
        docGia.setMaDocGia("user");
        
        SachDocGia sachDocGia = new SachDocGia();
        sachDocGia.setSach(sach);
        sachDocGia.setDocGia(docGia);
        sachDocGia.setNgayDat(Timestamp.valueOf(LocalDate.now() + " 00:00:00.0"));
        sachDocGia.setNgayMuon(Timestamp.valueOf(LocalDate.now() + " 01:00:00.0"));
        
        Assertions.assertTrue(s.muonSach(sachDocGia));
        
        SachDocGia newSachDocGia = s.getSachDocGiaByIds(sachDocGia);
        Assertions.assertNotNull(newSachDocGia);
        Assertions.assertEquals(sachDocGia.getMaSach(), newSachDocGia.getMaSach());
        Assertions.assertEquals(sachDocGia.getMaDocGia(), newSachDocGia.getMaDocGia());
        Assertions.assertEquals(sachDocGia.getNgayDat(), newSachDocGia.getNgayDat());
    }
    
    @Test
    @Order(8)
    public void testMuonSachErr1() throws SQLException {
        Sach sach = new Sach();
        sach.setMaSach("115dd543-06f0-417e-941a-1ec75fde5f64");
        
        DocGia docGia = new DocGia();
        docGia.setMaDocGia("user");
        
        SachDocGia sachDocGia = new SachDocGia();
        sachDocGia.setSach(sach);
        sachDocGia.setDocGia(docGia);
        sachDocGia.setNgayDat(Timestamp.valueOf("2000-01-01 00:00:00.0"));
        sachDocGia.setNgayMuon(Timestamp.valueOf(LocalDate.now() + " 00:00:00.0"));
        
        Assertions.assertFalse(s.muonSach(sachDocGia));
        
        SachDocGia newSachDocGia = s.getSachDocGiaByIds(sachDocGia);
        Assertions.assertNull(newSachDocGia);
    }
    
    @Test
    @Order(9)
    public void testTraSach() throws SQLException {
        Sach sach = new Sach();
        sach.setMaSach("115dd543-06f0-417e-941a-1ec75fde5f64");
        
        DocGia docGia = new DocGia();
        docGia.setMaDocGia("user");
        
        TienPhat tienPhat = new TienPhat();
        tienPhat.setMaTienPhat(1);
        
        SachDocGia sachDocGia = new SachDocGia();
        sachDocGia.setSach(sach);
        sachDocGia.setDocGia(docGia);
        sachDocGia.setNgayDat(Timestamp.valueOf(LocalDate.now() + " 00:00:00.0"));
        sachDocGia.setNgayTra(Timestamp.valueOf(LocalDate.now() + " 02:00:00.0"));
        sachDocGia.setTienPhat(tienPhat);
        
        Assertions.assertTrue(s.traSach(sachDocGia));
        
        SachDocGia newSachDocGia = s.getSachDocGiaByIds(sachDocGia);
        Assertions.assertNotNull(newSachDocGia);
        Assertions.assertEquals(sachDocGia.getMaSach(), newSachDocGia.getMaSach());
        Assertions.assertEquals(sachDocGia.getMaDocGia(), newSachDocGia.getMaDocGia());
        Assertions.assertEquals(sachDocGia.getNgayDat(), newSachDocGia.getNgayDat());
    }
    
    @Test
    @Order(10)
    public void thongKeMuonTheoNam() throws SQLException {
        ThongKe thongKe = s.thongKeMuonTheoNam(LocalDate.now().getYear());
        Assertions.assertNotNull(thongKe);
        Assertions.assertTrue(thongKe.getTongSoLuong() == 1);
    }
    
    @Test
    @Order(11)
    public void thongKeMuonTheoQuy() throws SQLException {
        ArrayList<ThongKe> dsThongKe = s.thongKeMuonTheoQuy(LocalDate.now().getYear());
        Assertions.assertNotNull(dsThongKe);
        
        for(ThongKe thongKe: dsThongKe) {
            Assertions.assertTrue(thongKe.getTongSoLuong() == 1);
        }
    }
    
    @Test
    @Order(12)
    public void thongKeTraTheoNam() throws SQLException {
        ThongKe thongKe = s.thongKeTraTheoNam(LocalDate.now().getYear());
        Assertions.assertNotNull(thongKe);
        Assertions.assertTrue(thongKe.getTongSoLuong() == 1);
    }
    
    @Test
    @Order(13)
    public void thongKeTraTheoQuy() throws SQLException {
        ArrayList<ThongKe> dsThongKe = s.thongKeTraTheoQuy(LocalDate.now().getYear());
        Assertions.assertNotNull(dsThongKe);
        
        for(ThongKe thongKe: dsThongKe) {
            Assertions.assertTrue(thongKe.getTongSoLuong() == 1);
        }
    }
    
    @Test
    @Order(14)
    public void testHuyMuonSach() throws SQLException {
        Sach sach = new Sach();
        sach.setMaSach("115dd543-06f0-417e-941a-1ec75fde5f64");
        
        DocGia docGia = new DocGia();
        docGia.setMaDocGia("user");
        
        SachDocGia sachDocGia = new SachDocGia();
        sachDocGia.setSach(sach);
        sachDocGia.setDocGia(docGia);
        sachDocGia.setNgayDat(Timestamp.valueOf(LocalDate.now() + " 00:00:00.0"));
        
        Assertions.assertTrue(s.huyMuonSach(sachDocGia));

        SachDocGia newSachDocGia = s.getSachDocGiaByIds(sachDocGia);
        Assertions.assertNull(newSachDocGia.getNgayMuon());
    }
    
    @Test
    @Order(15)
    public void testHuyTraSach() throws SQLException {
        Sach sach = new Sach();
        sach.setMaSach("115dd543-06f0-417e-941a-1ec75fde5f64");
        
        DocGia docGia = new DocGia();
        docGia.setMaDocGia("user");
        
        SachDocGia sachDocGia = new SachDocGia();
        sachDocGia.setSach(sach);
        sachDocGia.setDocGia(docGia);
        sachDocGia.setNgayDat(Timestamp.valueOf(LocalDate.now() + " 00:00:00.0"));
        
        Assertions.assertTrue(s.huyTraSach(sachDocGia));
        
        SachDocGia newSachDocGia = s.getSachDocGiaByIds(sachDocGia);
        Assertions.assertNull(newSachDocGia.getNgayTra());
    }
}