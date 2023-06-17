/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandhpc05076.Object;

/**
 *
 * @author DELL E5470
 */
public class O_NguoiHocTungNam {
    private String Nam;
    private int SoLuong;
    private String NgayDKsomNhat;
    private String NgayDKMuonNhat;

    public O_NguoiHocTungNam(String Nam, int SoLuong, String NgayDKsomNhat, String NgayDKMuonNhat) {
        this.Nam = Nam;
        this.SoLuong = SoLuong;
        this.NgayDKsomNhat = NgayDKsomNhat;
        this.NgayDKMuonNhat = NgayDKMuonNhat;
    }

    public O_NguoiHocTungNam() {
    }

    public String getNam() {
        return Nam;
    }

    public void setNam(String Nam) {
        this.Nam = Nam;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public String getNgayDKsomNhat() {
        return NgayDKsomNhat;
    }

    public void setNgayDKsomNhat(String NgayDKsomNhat) {
        this.NgayDKsomNhat = NgayDKsomNhat;
    }

    public String getNgayDKMuonNhat() {
        return NgayDKMuonNhat;
    }

    public void setNgayDKMuonNhat(String NgayDKMuonNhat) {
        this.NgayDKMuonNhat = NgayDKMuonNhat;
    }
    
}
