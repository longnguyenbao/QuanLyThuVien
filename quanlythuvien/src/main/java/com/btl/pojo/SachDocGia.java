/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.pojo;

import java.sql.Timestamp;

/**
 *
 * @author admin
 */
public class SachDocGia {
    private Sach sach;
    private DocGia docGia;
    private Integer soLuong;
    private Timestamp ngayTra;
    private Timestamp ngayMuon;
    private Timestamp ngayDat;
    private TienPhat tienPhat;

    public SachDocGia(Sach sach, DocGia docGia, Integer soLuong, Timestamp ngayTra, Timestamp ngayMuon, Timestamp ngayDat, TienPhat tienPhat) {
        this.sach = sach;
        this.docGia = docGia;
        this.soLuong = soLuong;
        this.ngayTra = ngayTra;
        this.ngayMuon = ngayMuon;
        this.ngayDat = ngayDat;
        this.tienPhat = tienPhat;
    }

    public SachDocGia() {
    }

    public Sach getSach() {
        return sach;
    }

    public void setSach(Sach sach) {
        this.sach = sach;
    }

    public DocGia getDocGia() {
        return docGia;
    }

    public void setDocGia(DocGia docGia) {
        this.docGia = docGia;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Timestamp getNgayTra() {
        return ngayTra;
    }

    public void setNgayTra(Timestamp ngayTra) {
        this.ngayTra = ngayTra;
    }

    public Timestamp getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(Timestamp ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public Timestamp getNgayDat() {
        return ngayDat;
    }

    public void setNgayDat(Timestamp ngayDat) {
        this.ngayDat = ngayDat;
    }

    public TienPhat getTienPhat() {
        return tienPhat;
    }

    public void setTienPhat(TienPhat tienPhat) {
        this.tienPhat = tienPhat;
    }

    public String getMaSach() {
        return sach.getMaSach();
    }
    
    public String getTenSach() {
        return sach.getTenSach();
    }
    
    public String getMaDocGia() {
        return docGia.getMaDocGia();
    }
    
    public String getHoTen() {
        return docGia.getHoTen();
    }
    
    public Integer getMaTienPhat() {
        return tienPhat.getMaTienPhat();
    }
    
    public Float getSoTienPhat() {
        return tienPhat.getSoTienPhat();
    }
}
