/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandhpc05076.Object;

/**
 *
 * @author DELL E5470
 */
public class O_DiemChuyenDe {
    private String ChuyenDe;
    private int SLHV;
    private float DiemThapNhat;
    private float DiemCaoNhat;
    private float DiemTB;

    public O_DiemChuyenDe(String ChuyenDe, int SLHV, float DiemThapNhat, float DiemCaoNhat, float DiemTB) {
        this.ChuyenDe = ChuyenDe;
        this.SLHV = SLHV;
        this.DiemThapNhat = DiemThapNhat;
        this.DiemCaoNhat = DiemCaoNhat;
        this.DiemTB = DiemTB;
    }

    public int getSLHV() {
        return SLHV;
    }

    public void setSLHV(int SLHV) {
        this.SLHV = SLHV;
    }


    public O_DiemChuyenDe() {
    }

    public String getChuyenDe() {
        return ChuyenDe;
    }

    public void setChuyenDe(String ChuyenDe) {
        this.ChuyenDe = ChuyenDe;
    }


    public float getDiemThapNhat() {
        return DiemThapNhat;
    }

    public void setDiemThapNhat(float DiemThapNhat) {
        this.DiemThapNhat = DiemThapNhat;
    }

    public float getDiemCaoNhat() {
        return DiemCaoNhat;
    }

    public void setDiemCaoNhat(float DiemCaoNhat) {
        this.DiemCaoNhat = DiemCaoNhat;
    }

    public float getDiemTB() {
        return DiemTB;
    }

    public void setDiemTB(float DiemTB) {
        this.DiemTB = DiemTB;
    }
    
}
