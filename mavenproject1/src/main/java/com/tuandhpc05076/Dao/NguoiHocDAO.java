/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandhpc05076.dao;

import com.tuandhpc05076.helper.JdbcHelper;
import com.tuandhpc05076.Object.O_NguoiHoc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tuan
 */
public class NguoiHocDAO  {
    
     final String SELECT_NOT_INCOURSE_SQL = "SELECT * FROM NguoiHoc where HoTen like ? and MaNH not in (select MaNH from HocVien where MaKH = ?)";
   
     public void insert(O_NguoiHoc model){
        String sql="INSERT INTO NguoiHoc (MaNH, HoTen, NgaySinh, GioiTinh, DienThoai, Email, GhiChu, MaNV) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
         JdbcHelper.executeUpdate(sql, 
                model.getMaNH(),
                model.getHoTen(), 
                model.getNgaySinh(), 
                model.isGioiTinh(), 
                model.getDienThoai(),
                model.getEmail(),
                model.getGhiChu(),
                model.getMaNV());
    }
    
  
    public void update(O_NguoiHoc model){
        String sql="UPDATE NguoiHoc SET HoTen=?, NgaySinh=?, GioiTinh=?, DienThoai=?, Email=?, GhiChu=?, MaNV=? WHERE MaNH=?";
        JdbcHelper.executeUpdate(sql, 
                model.getHoTen(), 
                model.getNgaySinh(), 
                model.isGioiTinh(), 
                model.getDienThoai(),
                model.getEmail(),
                model.getGhiChu(),
                model.getMaNV(),
                model.getMaNH());
    }
    
 
    public void delete(String id){
        String sql="DELETE FROM NguoiHoc WHERE MaNH=?";
        JdbcHelper.executeUpdate(sql, id);
    }
   
    public List<O_NguoiHoc> select(){
        String sql="SELECT * FROM NguoiHoc";
        return select(sql);
    }
    
    public List<O_NguoiHoc> selectByKeyword(String keyword){
        String sql="SELECT * FROM NguoiHoc WHERE HoTen LIKE ?";
        return select(sql, "%"+keyword+"%");
    }
    
    public List<O_NguoiHoc> selectByCourse(Integer makh){
        String sql="SELECT * FROM NguoiHoc WHERE MaNH NOT IN (SELECT MaNH FROM HocVien WHERE MaKH=?)";
        return select(sql, makh);
    }
    
 
    public O_NguoiHoc findById(String manh){
        String sql="SELECT * FROM NguoiHoc WHERE MaNH=?";
        List<O_NguoiHoc> list = select(sql, manh);
        return !list.isEmpty() ? list.get(0) : null;
    }
     private List<O_NguoiHoc> select(String sql, Object...args){
        List<O_NguoiHoc> list=new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    O_NguoiHoc model=readFromResultSet(rs);
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
    
    private O_NguoiHoc readFromResultSet(ResultSet rs) throws SQLException{
        O_NguoiHoc model=new O_NguoiHoc();
        model.setMaNH(rs.getString("MaNH"));
        model.setHoTen(rs.getString("HoTen"));
        model.setNgaySinh(rs.getString("NgaySinh"));
        model.setGioiTinh(rs.getBoolean("GioiTinh"));
        model.setDienThoai(rs.getString("DienThoai"));
        model.setEmail(rs.getString("Email"));
        model.setGhiChu(rs.getString("GhiChu"));
        model.setMaNV(rs.getString("MaNV"));
        model.setNgayDK(rs.getString("NgayDK"));
         return model;
    }
     public List<O_NguoiHoc> selectNotInCourse(int maKH, String keyword) {
      return select(SELECT_NOT_INCOURSE_SQL,"%"+keyword+"%",maKH);
    }

    
}
