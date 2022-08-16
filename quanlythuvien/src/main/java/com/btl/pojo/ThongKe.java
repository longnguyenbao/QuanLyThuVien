/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.pojo;

import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class ThongKe {
    private Integer nam;
    private Integer quy;
    private Integer tongSoLuong;

    public ThongKe(Integer nam, Integer quy, Integer tongSoLuong) {
        this.nam = nam;
        this.quy = quy;
        this.tongSoLuong = tongSoLuong;
    }

    public ThongKe() {
    }

    public Integer getNam() {
        return nam;
    }

    public void setNam(Integer nam) {
        this.nam = nam;
    }

    public Integer getQuy() {
        return quy;
    }

    public void setQuy(Integer quy) {
        this.quy = quy;
    }

    public Integer getTongSoLuong() {
        return tongSoLuong;
    }

    public void setTongSoLuong(Integer tongSoLuong) {
        this.tongSoLuong = tongSoLuong;
    }
    
    
}
