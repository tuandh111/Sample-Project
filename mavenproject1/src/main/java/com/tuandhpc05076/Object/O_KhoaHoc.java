/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandhpc05076.Object;

/**
 *
 * @author DELL E5470
 */
public class O_KhoaHoc {

    private int MaKH;
    private String MaCD;
    private float HocPhi;
    private int ThoiLuong;

    private String NgayKhaiGiang;
    private String GhiChu;
    private String ThemBoi;
    private String NgayTao;

    public O_KhoaHoc() {
    }

    public String getMaCD() {
        return MaCD;
    }

    public void setMaCD(String MaCD) {
        this.MaCD = MaCD;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    public O_KhoaHoc(int MaKH, String MaCD, float HocPhi, int ThoiLuong, String NgayKhaiGiang, String GhiChu, String ThemBoi, String NgayTao) {
        this.MaKH = MaKH;
        this.MaCD = MaCD;
        this.HocPhi = HocPhi;
        this.ThoiLuong = ThoiLuong;
        this.NgayKhaiGiang = NgayKhaiGiang;
        this.GhiChu = GhiChu;
        this.ThemBoi = ThemBoi;
        this.NgayTao = NgayTao;
    }

    public int getMaKH() {
        return MaKH;
    }

    public void setMaKH(int MaKH) {
        this.MaKH = MaKH;
    }

    public int getThoiLuong() {
        return ThoiLuong;
    }

    public void setThoiLuong(int ThoiLuong) {
        this.ThoiLuong = ThoiLuong;
    }

    public float getHocPhi() {
        return HocPhi;
    }

    public void setHocPhi(float HocPhi) {
        this.HocPhi = HocPhi;
    }

    public String getNgayKhaiGiang() {
        return NgayKhaiGiang;
    }

    public void setNgayKhaiGiang(String NgayKhaiGiang) {
        this.NgayKhaiGiang = NgayKhaiGiang;
    }

    public String getThemBoi() {
        return ThemBoi;
    }

    public void setThemBoi(String ThemBoi) {
        this.ThemBoi = ThemBoi;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String NgayTao) {
        this.NgayTao = NgayTao;
    }

}
