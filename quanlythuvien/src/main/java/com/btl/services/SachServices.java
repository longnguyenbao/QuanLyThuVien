/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.services;

import com.btl.pojo.Sach;
import com.btl.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

/**
 *
 * @author admin
 */
public class SachServices {
    public List<Sach> getDSSach(String kw, Integer loaiTimKiem) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm;
            String sql = "SELECT * FROM sach ";
            // OR  OR 
            if (kw == null)
                kw = "";

            if(loaiTimKiem != null) {
                switch(loaiTimKiem) {
                    case 0:
                        sql += "WHERE ten_sach like concat('%',?,'%')";
                        stm = conn.prepareStatement(sql);
                        stm.setString(1, kw);
                        return addDSSach(stm);
                    case 1:
                        sql += "WHERE ma_sach IN "
                                + "(SELECT ma_sach FROM sach_tac_gia WHERE ma_tac_gia IN "
                                + "(SELECT ma_tac_gia FROM tac_gia WHERE ten_tac_gia LIKE concat('%',?,'%')))";
                        stm = conn.prepareStatement(sql);
                        stm.setString(1, kw);
                        return addDSSach(stm);
                    case 2:
                        sql += "WHERE nam_xuat_ban like concat('%',?,'%')";
                        stm = conn.prepareStatement(sql);
                        stm.setString(1, kw);
                        return addDSSach(stm);
                    case 3:
                        sql += "WHERE ma_danh_muc like concat('%',?,'%')";
                        stm = conn.prepareStatement(sql);
                        stm.setString(1, kw);
                        return addDSSach(stm);
                    default:
                        break;
                }
            }

            stm = conn.prepareStatement(sql);
            
            return addDSSach(stm);
        }
    }
    
    private List<Sach> addDSSach(PreparedStatement stm) throws SQLException {
        List<Sach> dsSach = new ArrayList<>();
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {                
            String maSach = rs.getString("ma_sach");
            String tenSach = rs.getString("ten_sach");
            String moTa = rs.getString("mo_ta");
            Integer namXuatBan = rs.getInt("nam_xuat_ban");
            String noiXuatBan = rs.getString("noi_xuat_ban");
            Date ngayNhap = rs.getDate("ngay_nhap");
            String viTri = rs.getString("vi_tri");
            Integer maDanhMuc = rs.getInt("ma_danh_muc");

            dsSach.add(new Sach(maSach, tenSach, moTa, namXuatBan, noiXuatBan, ngayNhap, viTri, maDanhMuc));
        }
        
        return dsSach;
    }
    
    public static boolean checkSachExist(String maSach) throws SQLException {
        if (maSach.isBlank() == false) {
            try (Connection conn = JdbcUtils.getConn()) {
                PreparedStatement stm = conn.prepareStatement("SELECT ma_sach FROM sach WHERE ma_sach = ?");

                stm.setString(1, maSach);
                ResultSet rs = stm.executeQuery();

                if (rs.isBeforeFirst()) {
                    return true;
                }

                return false;
            }
        }
        return false;
    }
    
    public boolean suaSach(Sach sach) throws SQLException {
        if(checkSachExist(sach.getMaSach()) == true) {
            try (Connection conn = JdbcUtils.getConn()) {
                conn.setAutoCommit(false);
                
                PreparedStatement stm = //
                        conn.prepareStatement("UPDATE `sach` "
                                + "SET `ten_sach` = ?, "
                                + "`mo_ta` = ?, "
                                + "`nam_xuat_ban` = ?, "
                                + "`noi_xuat_ban` = ?, "
                                + "`ngay_nhap` = ?, "
                                + "`vi_tri` = ?, "
                                + "`ma_danh_muc` = ? "
                                + "WHERE `ma_sach` IN (?)");

                stm.setString(1, sach.getTenSach());
                stm.setString(2, sach.getMoTa());
                stm.setInt(3, sach.getNamXuatBan());
                stm.setString(4, sach.getNoiXuatBan());
                stm.setDate(5, sach.getNgayNhap());
                stm.setString(6, sach.getViTri());
                stm.setInt(7, sach.getMaDanhMuc());
                stm.setString(8, sach.getMaSach());
                
                stm.executeUpdate();
                conn.commit();

                return true;
            }
        }
        return false;
    }
    
    public boolean themSach(Sach sach) throws SQLException {
        if(sach.getMaSach() != null && sach.getMaSach().isBlank() == false && checkSachExist(sach.getMaSach()) == false){
            try (Connection conn = JdbcUtils.getConn()) {
                conn.setAutoCommit(false);

                PreparedStatement stm = conn.prepareStatement("INSERT INTO `sach` "
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)");

                stm.setString(1, sach.getMaSach());
                stm.setString(2, sach.getTenSach());
                stm.setString(3, sach.getMoTa());
                stm.setInt(4, sach.getNamXuatBan());
                stm.setString(5, sach.getNoiXuatBan());
                stm.setDate(6, sach.getNgayNhap());
                stm.setString(7, sach.getViTri());
                stm.setInt(8, sach.getMaDanhMuc());
                

                stm.executeUpdate();
                conn.commit();

                return true;
            }
        }
        return false;
    }
    
    public boolean xoaSach(String maSach) throws SQLException {
        if(maSach != null && checkSachExist(maSach) == true) {
            try (Connection conn = JdbcUtils.getConn()) {
                conn.setAutoCommit(false);
                
                PreparedStatement stm = conn.prepareStatement("DELETE FROM `sach` WHERE ma_sach = ?");

                stm.setString(1, maSach);
                
                stm.executeUpdate();
                conn.commit();

                return true;
            }
        }
        else {
            return false;
        }
    }
    
    public static Sach getSachById(String maSach) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM `sach` WHERE ma_sach = ?");
            stm.setString(1, maSach);
            ResultSet rs = stm.executeQuery();

            Sach sach = null;
            while (rs.next()) {
                sach = new Sach();

                sach.setMaSach(rs.getString("ma_sach"));
                sach.setTenSach(rs.getString("ten_sach"));
                sach.setMoTa(rs.getString("mo_ta"));
                sach.setNamXuatBan(rs.getInt("nam_xuat_ban"));
                sach.setNoiXuatBan(rs.getString("noi_xuat_ban"));
                sach.setNgayNhap(rs.getDate("ngay_nhap"));
                sach.setViTri(rs.getString("vi_tri"));
                sach.setMaDanhMuc(rs.getInt("ma_danh_muc"));
                break;
            }

            return sach;
        }
    }
}
