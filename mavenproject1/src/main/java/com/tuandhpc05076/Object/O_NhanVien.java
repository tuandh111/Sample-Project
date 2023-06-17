/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandhpc05076.Object;

import com.tuandhpc05076.Form.NhanVien;
import static com.tuandhpc05076.Form.NhanVien.list;

import static java.nio.file.Files.list;
import static java.rmi.Naming.list;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import static java.util.Collections.list;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author DELL E5470
 */
public class O_NhanVien {

    private String maNV;
    private String MatKhau;
    private boolean VaiTro;
    private String HoTen;

    public O_NhanVien(String maNV, String MatKhau, String HoTen, boolean VaiTro) {
        this.maNV = maNV;
        this.MatKhau = MatKhau;
        this.VaiTro = VaiTro;
        this.HoTen = HoTen;
    }

    public O_NhanVien() {
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public boolean isVaiTro() {
        return VaiTro;
    }

    public void setVaiTro(boolean VaiTro) {
        this.VaiTro = VaiTro;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public boolean getVaiTro() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
