/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.services;

import com.btl.pojo.DanhMuc;
import com.btl.pojo.DoiTuong;
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
public class DanhMucServices {
    public List<DanhMuc> getDSDanhMuc() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM danh_muc");
            
            ResultSet rs = stm.executeQuery();
            List<DanhMuc> dsDanhMuc = new ArrayList<>();
            
            while (rs.next()) {                
                int maDanhMuc = rs.getInt("ma_danh_muc");
                String tenDanhMuc = rs.getString("ten_danh_muc");
                
                dsDanhMuc.add(new DanhMuc(maDanhMuc, tenDanhMuc));
            }
            
            return dsDanhMuc;
        }
    }
    
    public static DanhMuc getDanhMucById(Integer maDanhMuc) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM `danh_muc` WHERE `ma_danh_muc` = ?");
            stm.setInt(1, maDanhMuc);
            ResultSet rs = stm.executeQuery();
            
            DanhMuc danhMuc = null;
            while (rs.next()) {
                danhMuc = new DanhMuc();
                
                danhMuc.setMaDanhMuc(rs.getInt("ma_danh_muc"));
                danhMuc.setTenDanhMuc(rs.getString("ten_danh_muc"));
                
                break;
            }
            
            return danhMuc;
        }
    }
}
