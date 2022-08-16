/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.btl.pojo;

/**
 *
 * @author Phan Nhat Thien
 */
public class BoPhan {
    private int maBoPhan;
    private String tenBoPhan;

    public BoPhan(int maBoPhan, String tenBoPhan) {
        this.maBoPhan = maBoPhan;
        this.tenBoPhan = tenBoPhan;
    }

    public BoPhan() {
    }

    /**
     * @return the maBoPhan
     */
    public int getMaBoPhan() {
        return maBoPhan;
    }

    /**
     * @param maBoPhan the maBoPhan to set
     */
    public void setMaBoPhan(int maBoPhan) {
        this.maBoPhan = maBoPhan;
    }

    /**
     * @return the tenBoPhan
     */
    public String getTenBoPhan() {
        return tenBoPhan;
    }

    /**
     * @param tenBoPhan the tenBoPhan to set
     */
    public void setTenBoPhan(String tenBoPhan) {
        this.tenBoPhan = tenBoPhan;
    }
    
    @Override
    public String toString() {
        return this.tenBoPhan;
    }
}
