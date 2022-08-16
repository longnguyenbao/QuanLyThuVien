/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.pojo;

/**
 *
 * @author admin
 */
public class TienPhat {
    private Integer maTienPhat;
    private Float soTienPhat;

    public TienPhat(Integer maTienPhat, Float soTienPhat) {
        this.maTienPhat = maTienPhat;
        this.soTienPhat = soTienPhat;
    }

    public TienPhat() {
    }

    public Integer getMaTienPhat() {
        return maTienPhat;
    }

    public void setMaTienPhat(Integer maTienPhat) {
        this.maTienPhat = maTienPhat;
    }

    public Float getSoTienPhat() {
        return soTienPhat;
    }

    public void setSoTienPhat(Float soTienPhat) {
        this.soTienPhat = soTienPhat;
    }
    
    
}
