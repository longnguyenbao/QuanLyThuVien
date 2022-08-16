/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.pojo;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

/**
 *
 * @author admin
 */
public class Sach {
    private String maSach;
    private String tenSach;
    private String moTa;
    private Integer namXuatBan;
    private String noiXuatBan;
    private Date ngayNhap;
    private String viTri;
    private Integer maDanhMuc;
    
    public Sach(String maSach, String tenSach, String moTa, Integer namXuatBan, String noiXuatBan, Date ngayNhap, String viTri, Integer maDanhMuc) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.moTa = moTa;
        this.namXuatBan = namXuatBan;
        this.noiXuatBan = noiXuatBan;
        this.ngayNhap = ngayNhap;
        this.viTri = viTri;
        this.maDanhMuc = maDanhMuc;
    }

    public Sach() {
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getNamXuatBan() {
        return namXuatBan;
    }

    public void setNamXuatBan(Integer namXuatBan) {
        this.namXuatBan = namXuatBan;
    }

    public String getNoiXuatBan() {
        return noiXuatBan;
    }

    public void setNoiXuatBan(String noiXuatBan) {
        this.noiXuatBan = noiXuatBan;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getViTri() {
        return viTri;
    }

    public void setViTri(String viTri) {
        this.viTri = viTri;
    }

    public Integer getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(Integer maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }
}
