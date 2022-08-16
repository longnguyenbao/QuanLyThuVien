/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.services;

import com.btl.pojo.BoPhan;
import com.btl.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Phan Nhat Thien
 */
public class BoPhanServices {
    public List<BoPhan> getDSBoPhan() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM bo_phan");
            
            ResultSet rs = stm.executeQuery();
            List<BoPhan> dsBoPhan = new ArrayList<>();
            
            while (rs.next()) {                
                int maBoPhan = rs.getInt("ma_bo_phan");
                String tenBoPhan = rs.getString("ten_bo_phan");
                
                dsBoPhan.add(new BoPhan(maBoPhan, tenBoPhan));
            }
            
            return dsBoPhan;
        }
    }
    public static BoPhan getBoPhanById(Integer maBoPhan) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM `bo_phan` WHERE `ma_bo_phan` = ?");
            stm.setInt(1, maBoPhan);
            ResultSet rs = stm.executeQuery();
            
            BoPhan boPhan = null;
            while (rs.next()) {
                boPhan = new BoPhan();
                
                boPhan.setMaBoPhan(rs.getInt("ma_bo_phan"));
                boPhan.setTenBoPhan(rs.getString("ten_bo_phan"));
                
                break;
            }
            
            return boPhan;
        }
    }
}
