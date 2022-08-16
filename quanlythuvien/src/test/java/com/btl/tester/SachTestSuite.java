/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.tester;

import com.btl.pojo.Sach;
import com.btl.services.SachServices;
import com.btl.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
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
public class SachTestSuite {
    private static Connection conn;
    private static SachServices s;
    
    @BeforeAll
    public static void beforeAll() {
        s = new SachServices();
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
    public void testThemSach() throws SQLException {
        Sach sach = new Sach();
        sach.setMaSach("9657e827-8436-4e11-8fbf-952775272616");
        sach.setTenSach("Sach 1");
        sach.setMoTa("Mo ta 1");
        sach.setNamXuatBan(2022);
        sach.setNoiXuatBan("Noi xuat ban 1");
        sach.setNgayNhap(Date.valueOf(LocalDate.now()));
        sach.setViTri("Vi tri 1");
        sach.setMaDanhMuc(1);
        
        Assertions.assertTrue(s.themSach(sach));
        
        Sach newSach = SachServices.getSachById(sach.getMaSach());
        Assertions.assertNotNull(newSach);
        Assertions.assertEquals(sach.getMaSach(), newSach.getMaSach());
        Assertions.assertEquals(sach.getMaDanhMuc(), newSach.getMaDanhMuc());
    }
    
    @Test
    @Order(2)
    public void testThemSachErr() throws SQLException {
        Sach sach = new Sach();
        sach.setMaSach(null);
        sach.setTenSach("Sach 1");
        sach.setMoTa("Mo ta 1");
        sach.setNamXuatBan(2022);
        sach.setNoiXuatBan("Noi xuat ban 1");
        sach.setNgayNhap(Date.valueOf(LocalDate.now()));
        sach.setViTri("Vi tri 1");
        sach.setMaDanhMuc(1);
        
        Assertions.assertFalse(s.themSach(sach));
        
        Sach newSach = SachServices.getSachById(sach.getMaSach());
        Assertions.assertNull(newSach);
    }
    
    @Test
    @Order(3)
    public void testSuaSach() throws SQLException {
        Sach sach = new Sach();
        sach.setMaSach("9657e827-8436-4e11-8fbf-952775272616");
        sach.setTenSach("Ten sach 2");
        sach.setMoTa("Mo ta 2");
        sach.setNamXuatBan(2020);
        sach.setNoiXuatBan("Moi xuat ban 2");
        sach.setNgayNhap(Date.valueOf(LocalDate.now()));
        sach.setViTri("Vi tri 2");
        sach.setMaDanhMuc(2);
        
        Assertions.assertTrue(s.suaSach(sach));
        
        Sach newSach = SachServices.getSachById(sach.getMaSach());
        Assertions.assertNotNull(newSach);
        Assertions.assertEquals(sach.getMaSach(), newSach.getMaSach());
        Assertions.assertEquals(sach.getMaDanhMuc(), newSach.getMaDanhMuc());
    }
    
    @Test
    @Order(4)
    public void testSuaSachErr() throws SQLException {
        Sach sach = new Sach();
        sach.setMaSach("abcd");
        sach.setTenSach("Ten sach 2");
        sach.setMoTa("Mo ta 2");
        sach.setNamXuatBan(2020);
        sach.setNoiXuatBan("Moi xuat ban 2");
        sach.setNgayNhap(Date.valueOf(LocalDate.now()));
        sach.setViTri("Vi tri 2");
        sach.setMaDanhMuc(2);
        
        Assertions.assertFalse(s.suaSach(sach));
        
        Sach newSach = SachServices.getSachById(sach.getMaSach());
        Assertions.assertNull(newSach);
    }
    
    @Test
    @Order(5)
    public void testXoaSach() throws SQLException {
        String maSach = "9657e827-8436-4e11-8fbf-952775272616";
        
        Assertions.assertTrue(s.xoaSach(maSach));
    }
    
    @Test
    @Order(6)
    public void testXoaSachErr() throws SQLException {
        String maSach = "abcd";
        
        Assertions.assertFalse(s.xoaSach(maSach));
    }
}
