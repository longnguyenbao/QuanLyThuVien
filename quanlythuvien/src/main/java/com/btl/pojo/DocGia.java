/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.pojo;

import java.sql.Date;

/**
 *
 * @author admin
 */
public class DocGia {
    private String maDocGia;
    private String matKhau;
    private String hoTen;
    private String gioiTinh;
    private Date ngaySinh;
    private Integer maDoiTuong;
    private Integer maBoPhan;
    private Date ngayDangKy;
    private Date ngayHetHan;
    private String email;
    private String diaChi;
    private String dienThoai;

    public DocGia(String maDocGia, String matKhau, String hoTen, String gioiTinh, Date ngaySinh, Integer maDoiTuong, Integer maBoPhan, Date ngayDangKy, Date ngayHetHan, String email, String diaChi, String dienThoai) {
        this.maDocGia = maDocGia;
        this.matKhau = matKhau;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.maDoiTuong = maDoiTuong;
        this.maBoPhan = maBoPhan;
        this.ngayDangKy = ngayDangKy;
        this.ngayHetHan = ngayHetHan;
        this.email = email;
        this.diaChi = diaChi;
        this.dienThoai = dienThoai;
    }

    public DocGia() {
    }

    public String getMaDocGia() {
        return maDocGia;
    }

    public void setMaDocGia(String maDocGia) {
        this.maDocGia = maDocGia;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public Integer getMaDoiTuong() {
        return maDoiTuong;
    }

    public void setMaDoiTuong(Integer maDoiTuong) {
        this.maDoiTuong = maDoiTuong;
    }

    public Integer getMaBoPhan() {
        return maBoPhan;
    }

    public void setMaBoPhan(Integer maBoPhan) {
        this.maBoPhan = maBoPhan;
    }

    public Date getNgayDangKy() {
        return ngayDangKy;
    }

    public void setNgayDangKy(Date ngayDangKy) {
        this.ngayDangKy = ngayDangKy;
    }

    public Date getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(Date ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getDienThoai() {
        return dienThoai;
    }

    public void setDienThoai(String dienThoai) {
        this.dienThoai = dienThoai;
    }

    
}
