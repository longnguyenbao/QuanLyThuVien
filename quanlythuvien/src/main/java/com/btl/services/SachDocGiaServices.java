/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.services;

import com.btl.pojo.SachDocGia;
import com.btl.pojo.DocGia;
import com.btl.pojo.Sach;
import com.btl.pojo.TienPhat;
import com.btl.pojo.ThongKe;
import com.btl.utils.JdbcUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author admin
 */
public class SachDocGiaServices {
    public int datSach(ArrayList<SachDocGia> dsSachDocGia) throws SQLException {
        int soLuongQuyDinh = 5;
        if(dsSachDocGia.isEmpty()) {
            return -2;
        }
        else if (dsSachDocGia.size() > soLuongQuyDinh) {
            return -1;
        }
        else {
            int tongSoLuong = 0;
            
            for(SachDocGia sachDocGia: dsSachDocGia) {
                tongSoLuong += sachDocGia.getSoLuong();
                
                if(tongSoLuong > soLuongQuyDinh) {
                    return -1;
                }
            }
            
            String maDocGia = dsSachDocGia.get(0).getDocGia().getMaDocGia();
            
            if(DocGiaServices.checkDocGiaExist(maDocGia)) {
                if(getSoLuongSachDaMuon(maDocGia) + tongSoLuong <= soLuongQuyDinh) {
                    try (Connection conn = JdbcUtils.getConn()) {
                        conn.setAutoCommit(false);
                        PreparedStatement stm;
                        
                        for(SachDocGia sachDocGia: dsSachDocGia) {
                            stm = conn.prepareStatement(
                                "INSERT INTO `sach_doc_gia`(`ma_sach`, `ma_doc_gia`, `so_luong`, `ngay_dat`) "
                                + "VALUES (?, ?, ?, ?)");
                            
                            stm.setString(1, sachDocGia.getMaSach());
                            stm.setString(2, sachDocGia.getDocGia().getMaDocGia());
                            stm.setInt(3, sachDocGia.getSoLuong());
                            if(sachDocGia.getNgayDat() != null)
                                stm.setTimestamp(4, sachDocGia.getNgayDat());
                            else 
                                stm.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                            
                            stm.executeUpdate();
                        }
                        
                        conn.commit();
                        
                        return 1;
                    }
                }
                return -4;
            }
            return -3;
        }
    }
    
    public static int getSoLuongSachDaMuon(String maDocGia) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement(
                    "SELECT SUM(`so_luong`) AS `so_luong_da_muon`"
                            + "FROM `sach_doc_gia` "
                            + "WHERE `ngay_tra` IS NULL "
                            + "AND `ma_doc_gia` = ?"
                            + "AND TIMESTAMPDIFF(HOUR, `ngay_dat`, CURRENT_TIMESTAMP()) <= 48");
            
            stm.setString(1, maDocGia);
            ResultSet rs = stm.executeQuery();
            
            int soLuongDaMuon = 0;
            
            while (rs.next()) {
                soLuongDaMuon = rs.getInt("so_luong_da_muon");
            }
            
            return soLuongDaMuon;
        }
    }
    
    public List<SachDocGia> getDSSachDat() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * "
                    + "FROM `sach_doc_gia` "
                    + "WHERE `ngay_muon` IS NULL "
                    + "AND TIMESTAMPDIFF(HOUR, `ngay_dat`, CURRENT_TIMESTAMP()) <= 48");
            
            ResultSet rs = stm.executeQuery();
            
            List<SachDocGia> dsSachDocGia = new ArrayList<>();
            while (rs.next()) {  
                Sach sach = SachServices.getSachById(rs.getString("ma_sach"));
                DocGia docGia = DocGiaServices.getDocGiaById(rs.getString("ma_doc_gia"));
                Integer soLuong = rs.getInt("so_luong");
                Timestamp ngayTra = rs.getTimestamp("ngay_tra");
                Timestamp ngayMuon = rs.getTimestamp("ngay_muon");
                Timestamp ngayDat = rs.getTimestamp("ngay_dat");
                TienPhat tienPhat = TienPhatServices.getTienPhatById(rs.getInt("ma_tien_phat"));
                
                dsSachDocGia.add(new SachDocGia(sach, docGia, soLuong, ngayTra, ngayMuon, ngayDat, tienPhat));
            }
            
            return dsSachDocGia;
        }
    }
    
    public ArrayList<SachDocGia> getDSSachDatByIds(ArrayList<SachDocGia> dsSachDocGia) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            ArrayList<SachDocGia> newDSSachDocGia = new ArrayList();
            for(int i = 0 ; i < dsSachDocGia.size(); i++) {
                PreparedStatement stm = conn.prepareStatement("SELECT * "
                + "FROM `sach_doc_gia` "
                + "WHERE `ma_sach` = ? "
                + "AND `ma_doc_gia` = ? "
                + "AND `ngay_dat` = ? ");

                stm.setString(1, dsSachDocGia.get(i).getMaSach());
                stm.setString(2, dsSachDocGia.get(i).getMaDocGia());
                stm.setTimestamp(3, dsSachDocGia.get(i).getNgayDat());

                ResultSet rs = stm.executeQuery();

                while (rs.next()) {  
                    Sach sach = SachServices.getSachById(rs.getString("ma_sach"));
                    DocGia docGia = DocGiaServices.getDocGiaById(rs.getString("ma_doc_gia"));
                    Integer soLuong = rs.getInt("so_luong");
                    Timestamp ngayTra = rs.getTimestamp("ngay_tra");
                    Timestamp ngayMuon = rs.getTimestamp("ngay_muon");
                    Timestamp ngayDat = rs.getTimestamp("ngay_dat");
                    TienPhat tienPhat = TienPhatServices.getTienPhatById(rs.getInt("ma_tien_phat"));
                    
                    newDSSachDocGia.add(new SachDocGia(sach, docGia, soLuong, ngayTra, ngayMuon, ngayDat, tienPhat));
                }
            }
            
            return newDSSachDocGia;
        }
    }
    
    public static boolean checkNgayDatHopLe(SachDocGia sachDocGia) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT `ma_sach` "
                    + "FROM `sach_doc_gia`"
                    + "WHERE `ma_sach` = ?"
                    + "AND `ma_doc_gia` = ?"
                    + "AND `ngay_dat` = ?"
                    + "AND TIMESTAMPDIFF(HOUR, `ngay_dat`, CURRENT_TIMESTAMP()) <= 48");
            
            
            stm.setString(1, sachDocGia.getMaSach());
            stm.setString(2, sachDocGia.getMaDocGia());
            stm.setTimestamp(3, sachDocGia.getNgayDat());
            
            ResultSet rs = stm.executeQuery();
            
            if (rs.isBeforeFirst()) {
                return true;
            }
            return false;
        }
    }
    
    public boolean muonSach(SachDocGia sachDocGia) throws SQLException {
        if(checkNgayDatHopLe(sachDocGia) == true) {
            try (Connection conn = JdbcUtils.getConn()) {
                conn.setAutoCommit(false);

                PreparedStatement stm = conn.prepareStatement(
                        "UPDATE `sach_doc_gia` "
                                + "SET `ngay_muon`= ? "
                                + "WHERE `ma_sach` = ?"
                                + "AND `ma_doc_gia` = ?"
                                + "AND `ngay_dat` = ?"
                                + "AND TIMESTAMPDIFF(HOUR, `ngay_dat`, CURRENT_TIMESTAMP()) <= 48");


                stm.setTimestamp(1, sachDocGia.getNgayMuon());
                stm.setString(2, sachDocGia.getMaSach());
                stm.setString(3, sachDocGia.getMaDocGia());
                stm.setTimestamp(4, sachDocGia.getNgayDat());

                stm.executeUpdate();
                conn.commit();

                return true;
            }
        }
        return false;
    }
    
    public boolean huyMuonSach(SachDocGia sachDocGia) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            PreparedStatement stm = conn.prepareStatement(
                        "UPDATE `sach_doc_gia` "
                                + "SET `ngay_muon`= ? "
                                + "WHERE `ma_sach` = ? "
                                + "AND `ma_doc_gia` = ? "
                                + "AND `ngay_dat` = ? ");

            stm.setTimestamp(1, null);
            stm.setString(2, sachDocGia.getMaSach());
            stm.setString(3, sachDocGia.getMaDocGia());
            stm.setTimestamp(4, sachDocGia.getNgayDat());

            stm.executeUpdate();
            conn.commit();
            
            return true;
        }
    }
    
    public List<SachDocGia> getDSSachMuon() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * "
                    + "FROM `sach_doc_gia` "
                    + "WHERE `ngay_muon` IS NOT NULL "
                    + "AND `ngay_tra` IS NULL");
            
            ResultSet rs = stm.executeQuery();
            
            List<SachDocGia> dsSachDocGia = new ArrayList<>();
            while (rs.next()) {  
                Sach sach = SachServices.getSachById(rs.getString("ma_sach"));
                DocGia docGia = DocGiaServices.getDocGiaById(rs.getString("ma_doc_gia"));
                Integer soLuong = rs.getInt("so_luong");
                Timestamp ngayTra = rs.getTimestamp("ngay_tra");
                Timestamp ngayMuon = rs.getTimestamp("ngay_muon");
                Timestamp ngayDat = rs.getTimestamp("ngay_dat");
                TienPhat tienPhat = TienPhatServices.getTienPhatById(rs.getInt("ma_tien_phat"));
                
                dsSachDocGia.add(new SachDocGia(sach, docGia, soLuong, ngayTra, ngayMuon, ngayDat, tienPhat));
            }
            
            return dsSachDocGia;
        }
    }
    
    public boolean traSach(SachDocGia sachDocGia) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);

            PreparedStatement stm = conn.prepareStatement(
                    "UPDATE `sach_doc_gia` "
                                + "SET `ngay_tra`= ? , `ma_tien_phat` = ? "
                                + "WHERE `ma_sach` = ? "
                                + "AND `ma_doc_gia` = ? "
                                + "AND `ngay_dat` = ? ");


            stm.setTimestamp(1, sachDocGia.getNgayTra());
            stm.setInt(2, sachDocGia.getMaTienPhat());
            stm.setString(3, sachDocGia.getMaSach());
            stm.setString(4, sachDocGia.getMaDocGia());
            stm.setTimestamp(5, sachDocGia.getNgayDat());

            stm.executeUpdate();
            conn.commit();

            return true;
        }
    }
    
    public boolean huyTraSach(SachDocGia sachDocGia) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);
            
            PreparedStatement stm = conn.prepareStatement(
                        "UPDATE `sach_doc_gia` "
                                + "SET `ngay_tra`= ?, `ma_tien_phat` = ? "
                                + "WHERE `ma_sach` = ? "
                                + "AND `ma_doc_gia` = ? "
                                + "AND `ngay_dat` = ? ");


            stm.setTimestamp(1, null);
            stm.setObject(2, null);
            stm.setString(3, sachDocGia.getMaSach());
            stm.setString(4, sachDocGia.getMaDocGia());
            stm.setTimestamp(5, sachDocGia.getNgayDat());

            stm.executeUpdate();
            conn.commit();
            
            return true;
        }
    }
    
    public SachDocGia getSachDocGiaByIds(SachDocGia sachDocGia) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            PreparedStatement stm = conn.prepareStatement("SELECT * "
            + "FROM `sach_doc_gia` "
            + "WHERE `ma_sach` = ? "
            + "AND `ma_doc_gia` = ? "
            + "AND `ngay_dat` = ? ");

            stm.setString(1, sachDocGia.getMaSach());
            stm.setString(2, sachDocGia.getMaDocGia());
            stm.setTimestamp(3, sachDocGia.getNgayDat());

            ResultSet rs = stm.executeQuery();

            while (rs.next()) {  
                Sach sach = SachServices.getSachById(rs.getString("ma_sach"));
                DocGia docGia = DocGiaServices.getDocGiaById(rs.getString("ma_doc_gia"));
                Integer soLuong = rs.getInt("so_luong");
                Timestamp ngayTra = rs.getTimestamp("ngay_tra");
                Timestamp ngayMuon = rs.getTimestamp("ngay_muon");
                Timestamp ngayDat = rs.getTimestamp("ngay_dat");
                TienPhat tienPhat = TienPhatServices.getTienPhatById(rs.getInt("ma_tien_phat"));

                return new SachDocGia(sach, docGia, soLuong, ngayTra, ngayMuon, ngayDat, tienPhat);
            }
        }
        return null;
    }
    
    public ThongKe thongKeMuonTheoNam(Integer nam) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            ThongKe thongKe = null;
            
            PreparedStatement stm = conn.prepareStatement(
                    "SELECT SUM(`so_luong`) AS 'tong_so_luong', "
                            + "EXTRACT(YEAR FROM `ngay_muon`) AS 'year' "
                            + "FROM `sach_doc_gia` "
                            + "WHERE EXTRACT(YEAR FROM `ngay_dat`) = ? "
                            + "AND `ngay_muon` IS NOT NULL");
            
            stm.setInt(1, nam);
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                thongKe = new ThongKe();
                thongKe.setTongSoLuong(rs.getInt("tong_so_luong"));
                thongKe.setNam(rs.getInt("year"));
            }
            return thongKe;
        }
    }
    
    public ArrayList<ThongKe> thongKeMuonTheoQuy(Integer nam) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            ArrayList<ThongKe> dsThongKe = new ArrayList();
            
            PreparedStatement stm = conn.prepareStatement(
                    "SELECT SUM(`so_luong`) AS 'tong_so_luong', "
                            + "EXTRACT(QUARTER FROM `ngay_muon`) AS 'quarter' "
                            + "FROM `sach_doc_gia` "
                            + "WHERE EXTRACT(YEAR FROM `ngay_dat`) = ? "
                            + "AND `ngay_muon` IS NOT NULL "
                            + "GROUP BY EXTRACT(QUARTER FROM `ngay_muon`) ");
            
            stm.setInt(1, nam);
                    
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Integer tongSoLuong = rs.getInt("tong_so_luong");
                Integer quy = rs.getInt("quarter");
                
                dsThongKe.add(new ThongKe(nam, quy, tongSoLuong));
            }
            return dsThongKe;
        }
    }
    
    public ThongKe thongKeTraTheoNam(Integer nam) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            ThongKe thongKe = null;
            
            PreparedStatement stm = conn.prepareStatement(
                    "SELECT SUM(`so_luong`) AS 'tong_so_luong', "
                            + "EXTRACT(YEAR FROM `ngay_muon`) AS 'year' "
                            + "FROM `sach_doc_gia` "
                            + "WHERE EXTRACT(YEAR FROM `ngay_dat`) = ? "
                            + "AND `ngay_muon` IS NOT NULL "
                            + "AND `ngay_tra` IS NOT NULL ");
            
            stm.setInt(1, nam);
            
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                thongKe = new ThongKe();
                thongKe.setTongSoLuong(rs.getInt("tong_so_luong"));
                thongKe.setNam(rs.getInt("year"));
            }
            return thongKe;
        }
    }
    
    public ArrayList<ThongKe> thongKeTraTheoQuy(Integer nam) throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            ArrayList<ThongKe> dsThongKe = new ArrayList();
            
            PreparedStatement stm = conn.prepareStatement(
                    "SELECT SUM(`so_luong`) AS 'tong_so_luong', "
                            + "EXTRACT(QUARTER FROM `ngay_muon`) AS 'quarter' "
                            + "FROM `sach_doc_gia` "
                            + "WHERE EXTRACT(YEAR FROM `ngay_dat`) = ? "
                            + "AND `ngay_muon` IS NOT NULL "
                            + "AND `ngay_tra` IS NOT NULL "
                            + "GROUP BY EXTRACT(QUARTER FROM `ngay_muon`) ");
            
            stm.setInt(1, nam);
                    
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Integer tongSoLuong = rs.getInt("tong_so_luong");
                Integer quy = rs.getInt("quarter");
                
                dsThongKe.add(new ThongKe(nam, quy, tongSoLuong));
            }
            return dsThongKe;
        }
    }
    
    public static boolean xoaSachDatQua48Gio() throws SQLException {
        try (Connection conn = JdbcUtils.getConn()) {
            conn.setAutoCommit(false);

            PreparedStatement stm = conn.prepareStatement("DELETE FROM `sach_doc_gia` "
                    + "WHERE TIMESTAMPDIFF(HOUR, `ngay_dat`, CURRENT_TIMESTAMP()) > 48 "
                    + "AND `ngay_tra` IS NULL "
                    + "AND `ngay_muon` IS NULL ");
            
            stm.executeUpdate();
            conn.commit();
            
            return true;
        }
    }
}