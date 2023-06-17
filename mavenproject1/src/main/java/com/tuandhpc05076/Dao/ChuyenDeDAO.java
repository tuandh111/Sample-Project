package com.tuandhpc05076.dao;


import com.tuandhpc05076.helper.JdbcHelper;
import com.tuandhpc05076.Form.ChuyenDe;
import com.tuandhpc05076.Object.O_ChuyenDe;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tuan 
 */
public class ChuyenDeDAO {

    public void insert(O_ChuyenDe model) {
        String sql = "INSERT INTO ChuyenDe (MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa) VALUES (?, ?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaCD(),
                model.getTenCD(),
                model.getHocPhi(),
                model.getThoiLuong(),
                model.getHinh(),
                model.getMoTa());
    }

    public void update(O_ChuyenDe model) {
        String sql = "UPDATE ChuyenDe SET TenCD=?, HocPhi=?, ThoiLuong=?, Hinh=?, MoTa=? WHERE MaCD=?";
        JdbcHelper.executeUpdate(sql,
                model.getTenCD(),
                model.getHocPhi(),
                model.getThoiLuong(),
                model.getHinh(),
                model.getMoTa(),
                model.getMaCD());
    }

    public void delete(String MaCD) {
        String sql = "DELETE FROM ChuyenDe WHERE MaCD=?";
        JdbcHelper.executeUpdate(sql, MaCD);
    }

    public List<O_ChuyenDe> select() {
        String sql = "SELECT * FROM ChuyenDe";
        return select(sql);
    }

    public O_ChuyenDe findById(String macd) {
        String sql = "SELECT * FROM ChuyenDe WHERE MaCD=?";
        List<O_ChuyenDe> list = select(sql, macd);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<O_ChuyenDe> select(String sql, Object... args) {
        List<O_ChuyenDe> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    O_ChuyenDe model = readFromResultSet(rs);
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

    private O_ChuyenDe readFromResultSet(ResultSet rs) throws SQLException {
        O_ChuyenDe model = new O_ChuyenDe();
        model.setMaCD(rs.getString("MaCD"));
        model.setHinh(rs.getString("Hinh"));
        model.setHocPhi(rs.getFloat("HocPhi"));
        model.setMoTa(rs.getString("MoTa"));
        model.setTenCD(rs.getString("TenCD"));
        model.setThoiLuong(rs.getInt("ThoiLuong"));
        return model;
    }
}
