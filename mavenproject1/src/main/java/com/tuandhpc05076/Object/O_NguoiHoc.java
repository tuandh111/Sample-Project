/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandhpc05076.Object;

import java.util.Date;
import javax.xml.crypto.Data;

/**
 *
 * @author DELL E5470
 */
public class O_NguoiHoc {
    private String MaNH;
    private String HoTen;
    private boolean  GioiTinh;
    private String NgaySinh;
    private String DienThoai;
    private String Email;
    private String GhiChu;
    private String MaNV;
    private String NgayDK;

    public O_NguoiHoc() {
    }

    public String getMaNH() {
        return MaNH;
    }

    public void setMaNH(String MaNH) {
        this.MaNH = MaNH;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public boolean isGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String DienThoai) {
        this.DienThoai = DienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getNgayDK() {
        return NgayDK;
    }

    public void setNgayDK(String NgayDK) {
        this.NgayDK = NgayDK;
    }

    public O_NguoiHoc(String MaNH, String HoTen, boolean GioiTinh, String NgaySinh, String DienThoai, String Email, String GhiChu, String MaNV, String NgayDK) {
        this.MaNH = MaNH;
        this.HoTen = HoTen;
        this.GioiTinh = GioiTinh;
        this.NgaySinh = NgaySinh;
        this.DienThoai = DienThoai;
        this.Email = Email;
        this.GhiChu = GhiChu;
        this.MaNV = MaNV;
        this.NgayDK = NgayDK;
    }


    
}
