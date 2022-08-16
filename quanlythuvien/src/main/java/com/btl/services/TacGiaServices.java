/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.services;

import com.btl.pojo.TacGia;
import com.btl.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class TacGiaServices {
    public List<TacGia> getDSTacGiaTheoMaSach(String kw) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement(
                    "SELECT * FROM tac_gia WHERE ma_tac_gia IN (SELECT ma_tac_gia FROM sach_tac_gia WHERE ma_sach = ?)"
            );
            if (kw == null)
                kw = "";
            
            stm.setString(1, kw);
            ResultSet rs = stm.executeQuery();
            
            List<TacGia> dsTacGia = new ArrayList<>();
            while (rs.next()) {                
                int maTacGia = rs.getInt("ma_tac_gia");
                String tenTacGia = rs.getString("ten_tac_gia");
                
                dsTacGia.add(new TacGia(maTacGia, tenTacGia));
            }
            
            return dsTacGia;
        }
    }
    
    public List<TacGia> getDSTacGia() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM tac_gia");
            
            ResultSet rs = stm.executeQuery();
            List<TacGia> dsTacGia = new ArrayList<>();
            
            while (rs.next()) {                
                int maTacGia = rs.getInt("ma_tac_gia");
                String tenTacGia = rs.getString("ten_tac_gia");
                
                dsTacGia.add(new TacGia(maTacGia, tenTacGia));
            }
            
            return dsTacGia;
        }
    }
}
