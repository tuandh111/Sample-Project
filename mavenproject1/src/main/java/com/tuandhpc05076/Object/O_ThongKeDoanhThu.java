/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandhpc05076.Object;

/**
 *
 * @author DELL E5470
 */
public class O_ThongKeDoanhThu {
    private String ChuyenDe;
    private int SoKH;
    private int SoHV;
    private float DoanhThu;
    private float ThapNhat;
    private float CaoNhat;
    private float TrungBinh;

    public O_ThongKeDoanhThu(String ChuyenDe, int SoKH, int SoHV, float DoanhThu, float ThapNhat, float CaoNhat, float TrungBinh) {
        this.ChuyenDe = ChuyenDe;
        this.SoKH = SoKH;
        this.SoHV = SoHV;
        this.DoanhThu = DoanhThu;
        this.ThapNhat = ThapNhat;
        this.CaoNhat = CaoNhat;
        this.TrungBinh = TrungBinh;
    }


    public O_ThongKeDoanhThu() {
    }

    public String getChuyenDe() {
        return ChuyenDe;
    }

    public void setChuyenDe(String ChuyenDe) {
        this.ChuyenDe = ChuyenDe;
    }

    public int getSoKH() {
        return SoKH;
    }

    public void setSoKH(int SoKH) {
        this.SoKH = SoKH;
    }

    public int getSoHV() {
        return SoHV;
    }

    public void setSoHV(int SoHV) {
        this.SoHV = SoHV;
    }

    public float getDoanhThu() {
        return DoanhThu;
    }

    public void setDoanhThu(float DoanhThu) {
        this.DoanhThu = DoanhThu;
    }

    public float getThapNhat() {
        return ThapNhat;
    }

    public void setThapNhat(float ThapNhat) {
        this.ThapNhat = ThapNhat;
    }

    public float getTrungBinh() {
        return TrungBinh;
    }

    public void setTrungBinh(float TrungBinh) {
        this.TrungBinh = TrungBinh;
    }

    public float getCaoNhat() {
        return CaoNhat;
    }

    public void setCaoNhat(float CaoNhat) {
        this.CaoNhat = CaoNhat;
    }
    
    
}
