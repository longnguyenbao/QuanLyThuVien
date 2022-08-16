/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.pojo;

/**
 *
 * @author admin
 */
public class DanhMuc {
    private Integer maDanhMuc;
    private String tenDanhMuc;

    public DanhMuc(Integer maDanhMuc, String tenDanhMuc) {
        this.maDanhMuc = maDanhMuc;
        this.tenDanhMuc = tenDanhMuc;
    }
    

    public DanhMuc() {
    }

    public Integer getMaDanhMuc() {
        return maDanhMuc;
    }
    
    public void setMaDanhMuc(Integer maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }
    
    @Override
    public String toString() {
        return this.tenDanhMuc;
    }
}
