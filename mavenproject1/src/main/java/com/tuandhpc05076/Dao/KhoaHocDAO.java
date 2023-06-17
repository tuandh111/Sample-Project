package com.tuandhpc05076.dao;

import com.tuandhpc05076.helper.JdbcHelper;
import com.tuandhpc05076.Object.O_KhoaHoc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tuan
 */
public class KhoaHocDAO {

    final String SELECT_BY_MA_CD_SQL = "SELECT * FROM KhoaHoc where MaCD = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM KhoaHoc";
   

    public void insert(O_KhoaHoc model) {
        String sql = "INSERT INTO KhoaHoc (MaCD, HocPhi, ThoiLuong, NgayKG, GhiChu, MaNV) VALUES (?, ?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaCD(),
                model.getHocPhi(),
                model.getThoiLuong(),
                model.getNgayKhaiGiang(),
                model.getGhiChu(),
                model.getThemBoi());
    }

    public void update(O_KhoaHoc model) {
        String sql = "UPDATE KhoaHoc SET MaCD=?, HocPhi=?, ThoiLuong=?, NgayKG=?, GhiChu=?, MaNV=? WHERE MaKH=?";
        JdbcHelper.executeUpdate(sql,
                model.getMaCD(),
                model.getHocPhi(),
                model.getThoiLuong(),
                model.getNgayKhaiGiang(),
                model.getGhiChu(),
                model.getThemBoi(),
                model.getMaKH());
    }

    public void delete(Integer MaKH) {
        String sql = "DELETE FROM KhoaHoc WHERE MaKH=?";
        JdbcHelper.executeUpdate(sql, MaKH);
    }

    public List<O_KhoaHoc> select() {
        String sql = "SELECT * FROM KhoaHoc";
        return select(sql);
    }

    public O_KhoaHoc findById(Integer makh) {
        String sql = "SELECT * FROM KhoaHoc WHERE MaKH=?";
        List<O_KhoaHoc> list = select(sql, makh);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<O_KhoaHoc> select(String sql, Object... args) {
        List<O_KhoaHoc> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    O_KhoaHoc model = readFromResultSet(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private O_KhoaHoc readFromResultSet(ResultSet rs) throws SQLException {
        O_KhoaHoc model = new O_KhoaHoc();
        model.setMaKH(rs.getInt("MaKH"));
        model.setHocPhi((float) rs.getDouble("HocPhi"));
        model.setThoiLuong(rs.getInt("ThoiLuong"));
        model.setNgayKhaiGiang(rs.getString("NgayKG"));
        model.setGhiChu(rs.getString("GhiChu"));
        model.setThemBoi(rs.getString("MaNV"));
        model.setNgayTao(rs.getString("NgayTao"));
        model.setMaCD(rs.getString("MaCD"));

        return model;
    }

    public List<O_KhoaHoc> selectKhoaHocByChuyenDe(String MaCD) {
        return select(SELECT_BY_MA_CD_SQL,MaCD);
    }
    
}
