/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.services;

import com.btl.pojo.DocGia;
import static com.btl.services.DocGiaServices.checkDocGiaExist;
import com.btl.utils.JdbcUtils;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class DocGiaServices {
    public List<DocGia> getDSDocGia(String kw) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM `doc_gia` WHERE (ho_ten like concat('%',?,'%')) OR (ma_doc_gia like concat('%',?,'%'))");
            if (kw == null)
                kw = "";
            
            stm.setString(1, kw);
            stm.setString(2, kw);
            ResultSet rs = stm.executeQuery();
            
            List<DocGia> dsDocgia = new ArrayList<>();
            while (rs.next()) {                
                
                String maDocGia = rs.getString("ma_doc_gia");
                String matKhau = rs.getString("mat_khau") ;
                String hoTen = rs.getString("ho_ten");
                String gioiTinh = rs.getString("gioi_tinh");
                Date ngaySinh = rs.getDate("ngay_sinh");
                Integer maDoiTuong = rs.getInt("ma_doi_tuong");
                Integer maBoPhan = rs.getInt("ma_bo_phan") ;
                Date ngayDangKy = rs.getDate("ngay_dang_ky");
                Date ngayHetHan = rs.getDate("ngay_het_han");
                String email = rs.getString("email");
                String diaChi = rs.getString("dia_chi");
                String dienThoai = rs.getString("dien_thoai");
                
                
                
                dsDocgia.add(new DocGia(maDocGia, matKhau, hoTen, gioiTinh, ngaySinh, maDoiTuong, maBoPhan, ngayDangKy, ngayHetHan, email, diaChi, dienThoai));
            }
            
            return dsDocgia;
        }
    }
    public static boolean checkDocGiaExist(String maDocGia) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT `ma_doc_gia` FROM `doc_gia` WHERE `ma_doc_gia` = ?");
            
            stm.setString(1, maDocGia);
            ResultSet rs = stm.executeQuery();
            
            if (rs.isBeforeFirst()) {
                return true;
            }

            return false;
        }
    }
    
    public static DocGia getDocGiaById(String maDocGia) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM `doc_gia` WHERE `ma_doc_gia` = ?");
            stm.setString(1, maDocGia);
            ResultSet rs = stm.executeQuery();
            
            DocGia docGia = null;
            while (rs.next()) {
                docGia = new DocGia();
                
                docGia.setMaDocGia(rs.getString("ma_doc_gia"));
                docGia.setMatKhau(rs.getString("mat_khau"));
                docGia.setHoTen(rs.getString("ho_ten"));
                docGia.setGioiTinh(rs.getString("gioi_tinh"));
                docGia.setNgaySinh(rs.getDate("ngay_sinh"));
                docGia.setMaDoiTuong(rs.getInt("ma_doi_tuong"));
                docGia.setMaBoPhan(rs.getInt("ma_bo_phan"));
                docGia.setNgayDangKy(rs.getDate("ngay_dang_ky"));
                docGia.setNgayHetHan(rs.getDate("ngay_het_han"));
                docGia.setEmail(rs.getString("email"));
                docGia.setDiaChi(rs.getString("dia_chi"));
                docGia.setDienThoai(rs.getString("dien_thoai"));
                break;
            }
            
            return docGia;
        }
    }
    
    public void dangKy(DocGia docGia) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()){
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO `doc_gia` "
                    + "(ma_doc_gia, ho_ten, email, ngay_sinh,gioi_tinh, ma_doi_tuong, ma_bo_phan, ngay_dang_ky, ngay_het_han, dia_chi, dien_thoai, mat_khau)"
                      + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            
            preparedStatement.setString(1, docGia.getMaDocGia());
            preparedStatement.setString(2, docGia.getHoTen());
            preparedStatement.setString(3, docGia.getEmail());
            preparedStatement.setDate(4, docGia.getNgaySinh());
            preparedStatement.setString(5, docGia.getGioiTinh());
            preparedStatement.setInt(6, docGia.getMaDoiTuong());
            preparedStatement.setInt(7, docGia.getMaBoPhan());
            preparedStatement.setDate(8, docGia.getNgayDangKy());
            preparedStatement.setDate(9, docGia.getNgayHetHan());
            preparedStatement.setString(10, docGia.getDiaChi());
            preparedStatement.setString(11, docGia.getDienThoai());
            preparedStatement.setString(12, getMd5(docGia.getMatKhau()));
            
            
            preparedStatement.executeUpdate();
        }
    }
    public static String getMd5(String input)
    {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
  
            byte[] messageDigest = md.digest(input.getBytes());
 
            BigInteger no = new BigInteger(1, messageDigest);

            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } 
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    public DocGia dangNhap(DocGia docGia) throws SQLException, IOException{
        try (Connection conn = JdbcUtils.getConn()) {
                PreparedStatement stm = conn.prepareStatement("SELECT * FROM `doc_gia`"
                        + " WHERE `ma_doc_gia` = ? and `ma_doi_tuong` = ? and `mat_khau` = ? ");

                stm.setString(1, docGia.getMaDocGia());
                stm.setInt(2, docGia.getMaDoiTuong());
                stm.setString(3, getMd5(docGia.getMatKhau()));
                ResultSet rs = stm.executeQuery();

                DocGia newDocGia = null;
                while (rs.next()) {
                    newDocGia = new DocGia();
                     
                    newDocGia.setMaDocGia(rs.getString("ma_doc_gia"));
                    newDocGia.setHoTen(rs.getString("ho_ten"));
                    newDocGia.setGioiTinh(rs.getString("gioi_tinh"));
                    newDocGia.setNgaySinh(rs.getDate("ngay_sinh"));
                    newDocGia.setMaDoiTuong(rs.getInt("ma_doi_tuong"));
                    newDocGia.setMaBoPhan(rs.getInt("ma_bo_phan"));
                    newDocGia.setNgayDangKy(rs.getDate("ngay_dang_ky"));
                    newDocGia.setNgayHetHan(rs.getDate("ngay_het_han"));
                    newDocGia.setEmail(rs.getString("email"));
                    newDocGia.setDiaChi(rs.getString("dia_chi"));
                    newDocGia.setDienThoai(rs.getString("dien_thoai"));
                    break;
                }
                
                return newDocGia;
        }
    }
    public boolean themDocGia(DocGia docGia) throws SQLException {
        if(docGia.getMaDocGia() != null && docGia.getMaDocGia().isBlank() == false && docGia.getMatKhau().isBlank() == false  && checkDocGiaExist(docGia.getMaDocGia()) == false){
            try (Connection conn = JdbcUtils.getConn()) {
                conn.setAutoCommit(false);

                PreparedStatement stm = conn.prepareStatement("INSERT INTO `doc_gia`"
                        + "(ma_doc_gia, ho_ten, email, ngay_sinh,gioi_tinh, ma_doi_tuong, ma_bo_phan, ngay_dang_ky, ngay_het_han, dia_chi, dien_thoai, mat_khau)"
                        + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                stm.setString(1, docGia.getMaDocGia());
                stm.setString(2, docGia.getHoTen());
                stm.setString(3, docGia.getEmail());
                stm.setDate(4, docGia.getNgaySinh());
                stm.setString(5, docGia.getGioiTinh());
                stm.setInt(6, docGia.getMaDoiTuong());
                stm.setInt(7, docGia.getMaBoPhan());
                stm.setDate(8, docGia.getNgayDangKy());
                stm.setDate(9, docGia.getNgayHetHan());
                stm.setString(10, docGia.getDiaChi());
                stm.setString(11, docGia.getDienThoai());
                stm.setString(12, getMd5(docGia.getMatKhau()));

                stm.executeUpdate();
                conn.commit();

                return true;
            }
        }
        return false;
    }
    public boolean xoaDocGia(String maDocGia) throws SQLException {
        if(maDocGia != null && checkDocGiaExist(maDocGia) == true) {
            try (Connection conn = JdbcUtils.getConn()) {
                conn.setAutoCommit(false);
                
                PreparedStatement stm = conn.prepareStatement("DELETE FROM `doc_gia` WHERE ma_doc_gia = ?");

                stm.setString(1, maDocGia);
                
                stm.executeUpdate();
                conn.commit();

                return true;
            }
        }
        else {
            return false;
        }
    }
    public boolean suaDocGia(DocGia docGia) throws SQLException {
        if(docGia.getMatKhau().isBlank() == false && checkDocGiaExist(docGia.getMaDocGia()) == true) {
            try (Connection conn = JdbcUtils.getConn()) {
                conn.setAutoCommit(false);
                
                PreparedStatement stm = //
                        conn.prepareStatement("UPDATE `doc_gia` "
                                + "SET `ho_ten` = ?, "
                                + "`email` = ?, "
                                + "`ngay_sinh` = ?, "
                                + "`gioi_tinh` = ?, "
                                + "`ma_doi_tuong` = ?, "
                                + "`ma_bo_phan` = ?, "
                                + "`ngay_dang_ky` = ?, "
                                + "`ngay_het_han` = ?, "
                                + "`dia_chi` = ?, "
                                + "`dien_thoai` = ?, "
                                + "`mat_khau` = ? "
                                + "WHERE `ma_doc_gia` IN (?)");

                stm.setString(1, docGia.getHoTen());
                stm.setString(2, docGia.getEmail());
                stm.setDate(3, docGia.getNgaySinh());
                stm.setString(4, docGia.getGioiTinh());
                stm.setInt(5, docGia.getMaDoiTuong());
                stm.setInt(6, docGia.getMaBoPhan());
                stm.setDate(7, docGia.getNgayDangKy());
                stm.setDate(8, docGia.getNgayHetHan());
                stm.setString(9, docGia.getDiaChi());
                stm.setString(10, docGia.getDienThoai());
                stm.setString(11, getMd5(docGia.getMatKhau()));
                stm.setString(12, docGia.getMaDocGia());
                
                
                stm.executeUpdate();
                conn.commit();

                return true;
             }
        }
        return false;
    }
}
