/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.services;

import com.btl.pojo.TienPhat;
import com.btl.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author admin
 */
public class TienPhatServices {
    public static TienPhat getTienPhatById(Integer maTienPhat) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM `tien_phat` WHERE ma_tien_phat = ?");
            stm.setInt(1, maTienPhat);
            ResultSet rs = stm.executeQuery();
            
            TienPhat tienPhat = null;
            while (rs.next()) {
                tienPhat = new TienPhat();
                tienPhat.setMaTienPhat(rs.getInt("ma_tien_phat"));
                tienPhat.setSoTienPhat(rs.getFloat("so_tien_phat"));
            }
            
            return tienPhat;
        }
    }
}
