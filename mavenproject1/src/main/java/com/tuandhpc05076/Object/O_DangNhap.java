/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandhpc05076.Object;



import com.tuandhpc05076.DangNhap.Login;
import static com.tuandhpc05076.DangNhap.Login.list1;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author DELL E5470
 */
public class O_DangNhap {
        private String maNV;
    private String MatKhau;
       private String HoTen;
    private boolean VaiTro;
 

    public O_DangNhap() {
        getAlllist();
    }

    public O_DangNhap(String maNV, String MatKhau, String HoTen, boolean VaiTro) {
        this.maNV = maNV;
        this.MatKhau = MatKhau;
        this.HoTen = HoTen;
        this.VaiTro = VaiTro;
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

    public void getThongtin(JTextField txtusername, JPasswordField txtpass) {
        String userName = "sa";
        String password = "123";
        String url = "jdbc:sqlserver://localhost:1433; databaseName=EduSys;encrypt=false";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            java.sql.PreparedStatement st = con.prepareStatement("SELECT * FROM dbo.NhanVien WHERE MaNV=? AND MatKhau=?");
            st.setString(1, txtusername.getText());
            st.setString(2, (new String(txtpass.getPassword())));
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                String MaNV = rs.getString(1);
                String MatKhau = rs.getString(2);
                String HoTen = rs.getString(3);
                boolean VaiTro = rs.getBoolean(4);

                list1.add(new O_DangNhap(MaNV, MatKhau, HoTen, VaiTro));
            }
         
            con.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

    public ArrayList<O_DangNhap> getAlllist() {
        return list1;  
    
    }

}
