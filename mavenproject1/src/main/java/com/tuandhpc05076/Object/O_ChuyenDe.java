/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandhpc05076.Object;

/**
 *
 * @author DELL E5470
 */
public class O_ChuyenDe {
    private String MaCD;
    private String TenCD;
    private float HocPhi;
    private int ThoiLuong;
    private String Hinh;
    private String MoTa;

    public O_ChuyenDe(String MaCD, String TenCD, float HocPhi, int ThoiLuong, String Hinh, String MoTa) {
        this.MaCD = MaCD;
        this.TenCD = TenCD;
        this.HocPhi = HocPhi;
        this.ThoiLuong = ThoiLuong;
        this.Hinh = Hinh;
        this.MoTa = MoTa;
    }

    public String getMaCD() {
        return MaCD;
    }

    public void setMaCD(String MaCD) {
        this.MaCD = MaCD;
    }

    public O_ChuyenDe() {
    }

    public String getTenCD() {
        return TenCD;
    }

    public void setTenCD(String TenCD) {
        this.TenCD = TenCD;
    }

    public float getHocPhi() {
        return HocPhi;
    }

    public void setHocPhi(float HocPhi) {
        this.HocPhi = HocPhi;
    }

    public int getThoiLuong() {
        return ThoiLuong;
    }

    public void setThoiLuong(int ThoiLuong) {
        this.ThoiLuong = ThoiLuong;
    }

    public String getHinh() {
        return Hinh;
    }
    

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }
    
}
