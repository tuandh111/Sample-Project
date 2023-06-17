/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandhpc05076.Object;

/**
 *
 * @author DELL E5470
 */
public class O_BangDiem {
    private String MaNH;
    private String HoTen;
    private  float Diem;
    private String TrangThai;

    public O_BangDiem() {
    }

    public String getTrangThai() {
     
        if(Diem<5){
            TrangThai="Kém";
        }else if(Diem<6.5){
            TrangThai="Trung bình";
        }else if(Diem<8){
            TrangThai="Khá";
        }else if(Diem<9){
            TrangThai="Giỏi";
        }else{
            TrangThai="Xuất sắc";
        }
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    public O_BangDiem(String MaNH, String HoTen, float Diem) {
        this.MaNH = MaNH;
        this.HoTen = HoTen;
        this.Diem = Diem;
    }

    public O_BangDiem(String MaNH, String HoTen, float Diem, String TrangThai) {
        this.MaNH = MaNH;
        this.HoTen = HoTen;
        this.Diem = Diem;
        this.TrangThai = TrangThai;
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

    public float getDiem() {
        return Diem;
    }

    public void setDiem(float Diem) {
        this.Diem = Diem;
    }
  
    
}
