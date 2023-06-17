/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandhpc05076.dao;

import com.tuandhpc05076.helper.JdbcHelper;
import com.tuandhpc05076.Object.O_HocVien;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tuan
 */
public class HocVienDAO {
    
    final   String SELECT_BY_ID_SQL = "SELECT * FROM HocVien WHERE MaHV=?";
     public void insert(O_HocVien model){
        String sql="INSERT INTO HocVien(MaKH, MaNH, Diem) VALUES(?, ?, ?)";
         JdbcHelper.executeUpdate(sql, 
                model.getMaKH(), 
                model.getMaNH(), 
                model.getDiem());
    }
    
    public void update(O_HocVien model){
        String sql="UPDATE HocVien SET MaKH=?, MaNH=?, Diem=? WHERE MaHV=?";
        JdbcHelper.executeUpdate(sql, 
                model.getMaKH(), 
                model.getMaNH(), 
                model.getDiem(), 
                model.getMaHV());
    }
    
    public void delete(Integer MaHV){
        String sql="DELETE FROM HocVien WHERE MaHV=?";
        JdbcHelper.executeUpdate(sql, MaHV);
    }
    
    public List<O_HocVien> select(){
        String sql="SELECT * FROM HocVien";
        return select(sql);
    }
    
    public O_HocVien findById(Integer mahv){
        String sql="SELECT * FROM HocVien WHERE MaHV=?";
        List<O_HocVien> list = select(sql, mahv);
        return list.size() > 0 ? list.get(0) : null;
    }
    
    private List<O_HocVien> select(String sql, Object...args){
        List<O_HocVien> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while(rs.next()){
                    O_HocVien model=readFromResultSet(rs);
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
    
    private O_HocVien readFromResultSet(ResultSet rs) throws SQLException{
        O_HocVien model=new O_HocVien();
        model.setMaHV(rs.getInt("MaHV"));
        model.setMaKH(rs.getInt("MaKH"));
        model.setMaNH(rs.getString("MaNH"));
        model.setDiem(rs.getFloat("Diem"));
        return model;
    }
     public List<O_HocVien> selectByKhoaHoc(int makh) {
        String SQL = "SELECT * FROM HocVien WHERE MaKH = ?";
        return this.select(SQL, makh);
    }
    
}
