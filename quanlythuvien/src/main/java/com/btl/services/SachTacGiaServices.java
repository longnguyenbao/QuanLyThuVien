/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.services;

import com.btl.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;;
import java.util.ArrayList;
import java.util.LinkedHashSet;

/**
 *
 * @author admin
 */
public class SachTacGiaServices {
    public boolean xoaSachTacGia(String maSach) throws SQLException {
        if (SachServices.checkSachExist(maSach) == true) {
            try (Connection conn = JdbcUtils.getConn()) {
                conn.setAutoCommit(false);
                
                PreparedStatement stm = conn.prepareStatement("DELETE FROM `sach_tac_gia` WHERE ma_sach = ?");

                stm.setString(1, maSach);
                
                stm.executeUpdate();
                conn.commit();

                return true;
            }
        }
        return false;
    }
    
    public boolean themSachTacGia(String maSach, ArrayList<Integer> dsMaTacGia) throws SQLException {
        if (SachServices.checkSachExist(maSach) == true) {
            try (Connection conn = JdbcUtils.getConn()) {
                if(!dsMaTacGia.isEmpty()) {
                    LinkedHashSet<Integer> hashSet = new LinkedHashSet<>(dsMaTacGia);
                    ArrayList<Integer> dsMaTacGiaMoi = new ArrayList<>(hashSet);
                    
                    conn.setAutoCommit(false);
                    PreparedStatement stm = conn.prepareStatement("INSERT INTO `sach_tac_gia` VALUES (?, ?)");
                
                    for (int maTacGia: dsMaTacGiaMoi) {
                        stm.setString(1, maSach);
                        stm.setInt(2, maTacGia);

                        stm.executeUpdate();

                    }

                    conn.commit();
                    
                    return true;
                }
                return true;
            }
        }
        return false;
    }
}
