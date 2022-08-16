/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.services;

import com.btl.pojo.DocGia;
import com.btl.utils.JdbcUtils;
import com.btl.pojo.DoiTuong;
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
public class DoiTuongServices {
    public List<DoiTuong> getDSDoiTuong() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM doi_tuong");
            
            ResultSet rs = stm.executeQuery();
            List<DoiTuong> dsDoiTuong = new ArrayList<>();
            
            while (rs.next()) {                
                int maDoiTuong = rs.getInt("ma_doi_tuong");
                String tenDoiTuong = rs.getString("ten_doi_tuong");
                
                dsDoiTuong.add(new DoiTuong(maDoiTuong, tenDoiTuong));
            }
            
            return dsDoiTuong;
        }
    }
    public static DoiTuong getDoiTuongById(Integer maDoiTuong) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM `doi_tuong` WHERE `ma_doi_tuong` = ?");
            stm.setInt(1, maDoiTuong);
            ResultSet rs = stm.executeQuery();
            
            DoiTuong doiTuong = null;
            while (rs.next()) {
                doiTuong = new DoiTuong();
                
                doiTuong.setMaDoiTuong(rs.getInt("ma_doi_tuong"));
                doiTuong.setTenDoiTuong(rs.getString("ten_doi_tuong"));
                
                break;
            }
            
            return doiTuong;
        }
    }
}
