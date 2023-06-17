/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandhpc05076.DangNhap;

import com.tuandhpc05076.Dao.NhanVienDAO;

import com.tuandhpc05076.MaHoa.MaHoa;
import com.tuandhpc05076.Object.O_DangNhap;
import com.tuandhpc05076.Object.O_NhanVien;
import com.tuandhpc05076.helper.DialogHelper;
import com.tuandhpc05076.helper.ShareHelper;
import java.awt.Color;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author DELL E5470
 */
public class Register extends PanelCustom {

    ArrayList<O_NhanVien> listNV = new ArrayList<>();
    String userName = "sa";
    String password = "123";
    String url = "jdbc:sqlserver://localhost:1433; databaseName= EduSys;encrypt=false";
 private EventLogin event;
    public Register() {
        initComponents();
         setAlpha(1);
        loadDataToArray();

    }
  public void setEventLogin(EventLogin event) {
        this.event = event;
    }
    public boolean kiem() {
        if (txtTenDangNhap.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mã đã để trống");
            return false;
        }
        if (new String(txtMatKhau.getPassword()).equals("")) {
            JOptionPane.showMessageDialog(this, "Mật khẩu để trống");
            return false;
        }
        if (!new String(txtMatKhauMoi.getPassword()).equals(new String(txtMatKhauMoi2.getPassword()))) {
            JOptionPane.showMessageDialog(this, "Nhập lại mật khẩu không thành công");
            return false;
        }
        return true;
    }
    boolean flag = true;
    MaHoa mh = new MaHoa();

    public void btnDangKy() {
        if (kiem() == false) {
            return;
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            String sqla = "UPDATE dbo.NhanVien SET MatKhau =?  WHERE MaNV =? and MatKhau=? ";
            PreparedStatement st = con.prepareStatement(sqla);
            boolean kiemMa = false;
            String maHoaMatKhau = mh.toSHA(new String(txtMatKhau.getPassword()));
            String maHoaMatKhauMoi = mh.toSHA(new String(txtMatKhauMoi.getPassword()));
            for (O_NhanVien nv : listNV) {
                if (nv.getMaNV().trim().equals(txtTenDangNhap.getText()) && nv.getMatKhau().trim().equals(maHoaMatKhau)) {
                    st.setString(1, maHoaMatKhauMoi);
                    st.setString(2, txtTenDangNhap.getText());

                    st.setString(3, maHoaMatKhau);

                    st.executeUpdate();
                    con.close();
                    txtTenDangNhap.setText("");
                    txtMatKhau.setText("");
                    txtMatKhauMoi.setText("");
                    txtMatKhauMoi2.setText("");
                    JOptionPane.showMessageDialog(this, "Thay đổi mật khẩu thành công");
                    loadDataToArray();
                    kiemMa = true;

                    break;
                }

            }

            if (kiemMa == false) {
                JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu không chính xác");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void loadDataToArray() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            java.sql.Statement st = con.createStatement();
            String sql = "SELECT * FROM NhanVien";
            ResultSet rs = st.executeQuery(sql);
            listNV.clear();
            while (rs.next()) {

                String MaNv = rs.getString(1);
                String MatKhau = rs.getString(2);
                String HoTen = rs.getString(3);
                boolean VaiTro = rs.getBoolean(4);
                listNV.add(new O_NhanVien(MaNv, MatKhau, HoTen, VaiTro));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        textField11 = new com.tuandhpc05076.Swing.TextField1();
        jLabel1 = new javax.swing.JLabel();
        txtMatKhau = new com.tuandhpc05076.DangNhap.Password();
        txtMatKhauMoi = new com.tuandhpc05076.DangNhap.Password();
        btnXacNhan = new com.tuandhpc05076.Swing.Button();
        txtTenDangNhap = new com.tuandhpc05076.Swing.TextField1();
        txtMatKhauMoi2 = new com.tuandhpc05076.DangNhap.Password();

        textField11.setText("textField11");

        setBackground(new java.awt.Color(71, 71, 71));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(201, 201, 201));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Đổi mật khẩu");

        txtMatKhau.setForeground(new java.awt.Color(255, 255, 255));
        txtMatKhau.setHint("Mật khẩu hiện tại");

        txtMatKhauMoi.setForeground(new java.awt.Color(255, 255, 255));
        txtMatKhauMoi.setHint("Mật khẩu mới");

        btnXacNhan.setBackground(new java.awt.Color(153, 153, 255));
        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setAutoscrolls(true);
        btnXacNhan.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnXacNhan.setLabel("Xác nhận");
        btnXacNhan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnXacNhanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnXacNhanMouseExited(evt);
            }
        });
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        txtTenDangNhap.setForeground(new java.awt.Color(255, 255, 255));
        txtTenDangNhap.setHint("Nhập mã");
        txtTenDangNhap.setSelectedTextColor(new java.awt.Color(255, 255, 255));

        txtMatKhauMoi2.setForeground(new java.awt.Color(255, 255, 255));
        txtMatKhauMoi2.setHint("Nhập lại mật khẩu");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtTenDangNhap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMatKhauMoi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtMatKhauMoi2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(31, 31, 31))
            .addGroup(layout.createSequentialGroup()
                .addGap(80, 80, 80)
                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtMatKhauMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtMatKhauMoi2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(59, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        btnDangKy();
//
//        if (checkForm()) {
//            this.doiMatKhau();
//        }         // TODO add your handling code here:
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void btnXacNhanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXacNhanMouseExited
        // TODO add your handling code here:
        btnXacNhan.setBackground(new Color(153, 153, 255));
    }//GEN-LAST:event_btnXacNhanMouseExited

    private void btnXacNhanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXacNhanMouseEntered
        btnXacNhan.setBackground(Color.pink);
        // TODO add your handling code here:
    }//GEN-LAST:event_btnXacNhanMouseEntered
    NhanVienDAO dao = new NhanVienDAO();

    boolean checkForm() {

        if (txtTenDangNhap.getText().equalsIgnoreCase("")) {
            DialogHelper.alert(this, "Tên đăng nhập không được bỏ trống ô");
            return false;
        }
        if (txtMatKhau.getPassword().length < 0) {
            DialogHelper.alert(this, "Mật khẩu không được bỏ trống ô");
            return false;
        }
        if (txtMatKhauMoi.getPassword().length < 0) {
            DialogHelper.alert(this, "Mật khẩu mới không được bỏ trống ô");
            return false;
        }
        if (txtMatKhauMoi2.getPassword().length < 0) {
            DialogHelper.alert(this, "Xác nhận mật khẩu không được bỏ trống ô");
            return false;
        }
        return true;
    }


    boolean doiMatKhau() {

        String tk = txtTenDangNhap.getText();

        String matKhau = new String(txtMatKhau.getPassword());

        String matKhauMoi = new String(txtMatKhauMoi.getPassword());
        String maHoaMatKhauMoi = mh.toSHA(matKhauMoi);
        String matKhauMoi2 = new String(txtMatKhauMoi2.getPassword());
        if (!tk.equalsIgnoreCase(ShareHelper.USER.getMaNV())) {
            DialogHelper.alert(this, "Sai tên đăng nhập!");
            return false;
        }
//        if (!maHoaMatKhau.equals(ShareHelper.USER.getMatKhau())) {
//            DialogHelper.alert(this, "Sai mật khẩu!");
//            return false;
//        }
        if (!matKhauMoi.equals(matKhauMoi2)) {
            DialogHelper.alert(this, "Sai mật khẩu xác nhận!");
            return false;
        } else {
            ShareHelper.USER.setMatKhau(maHoaMatKhauMoi);
            dao.update(ShareHelper.USER);
            DialogHelper.alert(this, "Đổi mật khẩu thành công");

//        new DoiMatKhauJFrame().logoff();
            return true;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tuandhpc05076.Swing.Button btnXacNhan;
    private javax.swing.JLabel jLabel1;
    private com.tuandhpc05076.Swing.TextField1 textField11;
    private com.tuandhpc05076.DangNhap.Password txtMatKhau;
    private com.tuandhpc05076.DangNhap.Password txtMatKhauMoi;
    private com.tuandhpc05076.DangNhap.Password txtMatKhauMoi2;
    private com.tuandhpc05076.Swing.TextField1 txtTenDangNhap;
    // End of variables declaration//GEN-END:variables
}
