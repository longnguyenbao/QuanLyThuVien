/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.tester;

import com.btl.services.SachTacGiaServices;
import com.btl.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
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
public class SachTacGiaTestSuite {
    private static Connection conn;
    private static SachTacGiaServices s;
    
    @BeforeAll
    public static void beforeAll() {
        s = new SachTacGiaServices();
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
    public void testXoaSachTacGia() throws SQLException {
        String maSach = "267f046d-defc-4621-8672-3cf62d56eee1";
        Assertions.assertTrue(s.xoaSachTacGia(maSach));
    }
    
    @Test
    @Order(2)
    public void testXoaSachTacGiaErr() throws SQLException {
        String maSach = "abcd";
        Assertions.assertFalse(s.xoaSachTacGia(maSach));
    }
    
    @Test
    @Order(3)
    public void testThemSachTacGia() throws SQLException {
        String maSach = "267f046d-defc-4621-8672-3cf62d56eee1";
        ArrayList<Integer> dsMaTacGia = new ArrayList<>();
        dsMaTacGia.add(1);
        dsMaTacGia.add(2);
        dsMaTacGia.add(3);
        
        Assertions.assertTrue(s.themSachTacGia(maSach, dsMaTacGia));
    }
    
    @Test
    @Order(4)
    public void testThemSachTacGiaErr() throws SQLException {
        String maSach = "abcd";
        ArrayList<Integer> dsMaTacGia = new ArrayList<>();
        dsMaTacGia.add(1);
        dsMaTacGia.add(2);
        dsMaTacGia.add(3);
        
        Assertions.assertFalse(s.themSachTacGia(maSach, dsMaTacGia));
    }
}
