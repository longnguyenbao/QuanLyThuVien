/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.pojo;

/**
 *
 * @author admin
 */
public class DoiTuong {
    private int maDoiTuong;
    private String tenDoiTuong;

    public DoiTuong(int maDoiTuong, String tenDoiTuong) {
        this.maDoiTuong = maDoiTuong;
        this.tenDoiTuong = tenDoiTuong;
    }

    public DoiTuong() {
    }

    public int getMaDoiTuong() {
        return maDoiTuong;
    }

    public void setMaDoiTuong(int maDoiTuong) {
        this.maDoiTuong = maDoiTuong;
        
    }

    public String getTenDoiTuong() {
        return tenDoiTuong;
    }

    public void setTenDoiTuong(String tenDoiTuong) {
        this.tenDoiTuong = tenDoiTuong;
    }
    
    @Override
    public String toString() {
        return this.tenDoiTuong;
    }
}
