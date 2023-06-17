/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandhpc05076.Dao;

import com.tuandhpc05076.Form.NhanVien;
import com.tuandhpc05076.helper.JdbcHelper;
import com.tuandhpc05076.Object.O_NhanVien;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tuan
 */
public class NhanVienDAO {
     public void insert(O_NhanVien model){
        String sql="INSERT INTO NhanVien (MaNV, MatKhau, HoTen, VaiTro) VALUES (?, ?, ?, ?)";
         JdbcHelper.executeUpdate(sql, 
                model.getMaNV(), 
                model.getMatKhau(), 
                model.getHoTen(), 
                model.isVaiTro());
    }
    
    public void update(O_NhanVien model){
        String sql="UPDATE NhanVien SET MatKhau=?, HoTen=?, VaiTro=? WHERE MaNV=?";
        JdbcHelper.executeUpdate(sql, 
                model.getMatKhau(), 
                model.getHoTen(), 
                model.isVaiTro(),
                model.getMaNV());
    }
    
    public void delete(String MaNV){
        String sql="DELETE FROM NhanVien WHERE MaNV=?";
        JdbcHelper.executeUpdate(sql, MaNV);
    }
    
    public List<O_NhanVien> select(){
        String sql="SELECT * FROM NhanVien";
        return select(sql);
    }
    
    public O_NhanVien findById(String manv){
        String sql="SELECT * FROM NhanVien WHERE MaNV=?";
        List<O_NhanVien> list = select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    private List<O_NhanVien> select(String sql, Object...args){
        List<O_NhanVien> list=new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    O_NhanVien model=readFromResultSet(rs);
                    list.add(model);
                }
            } 
            finally{
                rs.getStatement().getConnection().close();
            }
        } 
        catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }
    
    private O_NhanVien readFromResultSet(ResultSet rs) throws SQLException{
       O_NhanVien model=new O_NhanVien();
        model.setMaNV(rs.getString("MaNV"));
        model.setMatKhau(rs.getString("MatKhau"));
        model.setHoTen(rs.getString("HoTen"));
        model.setVaiTro(rs.getBoolean("VaiTro"));
        return model;
    }

}
