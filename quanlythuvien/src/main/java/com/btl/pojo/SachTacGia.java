/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.pojo;

/**
 *
 * @author admin
 */
public class SachTacGia {
    private String maSach;
    private int maTacGia;

    public SachTacGia(String maSach, int maTacGia) {
        this.maSach = maSach;
        this.maTacGia = maTacGia;
    }

    public SachTacGia() {
    }

    public String getMaSach() {
        return maSach;
    }

    public void setMaSach(String maSach) {
        this.maSach = maSach;
    }

    public int getMaTacGia() {
        return maTacGia;
    }

    public void setMaTacGia(int maTacGia) {
        this.maTacGia = maTacGia;
    }
    
    
}
