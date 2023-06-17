/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandhpc05076.Form;

import com.tuandhpc05076.Object.O_NguoiHocTungNam;
import com.tuandhpc05076.Object.O_ThongKeDoanhThu;
import com.tuandhpc05076.swing0.Form;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL E5470
 */
public class ThongKeDoanhThu extends Form {

    public static ArrayList<O_ThongKeDoanhThu> listDoanhThu = new ArrayList<>();
    DefaultTableModel tblModel;
    String userName = "sa";
    String password = "123";
    String url = "jdbc:sqlserver://localhost:1433; databaseName= EduSys;encrypt=false";

    public ThongKeDoanhThu() {
        initComponents();
        TieuDe();
        loadDataToArray();

    }

    public void TieuDe() {
        tblModel = new DefaultTableModel();
        String[] tbl = new String[]{"Chuyên Đề", "Số khóa học", "Số Học viên", "Doanh thu", "Thắp nhất", "Cao Nhất", "Trung bình"};
        tblModel.setColumnIdentifiers(tbl);
        tblUser.setModel(tblModel);
    }

    public void loadDataToArray() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);

            String sql = "{CALL sp_ThongKeDoanhThu(?)}";

            CallableStatement stmt = con.prepareCall(sql);
            stmt.setObject(1, cboNam.getSelectedItem());
            listDoanhThu.clear();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                String ChuyenDe = rs.getString(1);
                int SoKH = rs.getInt(2);
                int soHV = rs.getInt(3);
                float DoanhThu = rs.getFloat(4);
                float ThapNhat = rs.getFloat(5);
                float CaoNhat = rs.getFloat(6);
                float TrungBinh = rs.getFloat(7);

                listDoanhThu.add(new O_ThongKeDoanhThu(ChuyenDe, SoKH, soHV, DoanhThu, ThapNhat, CaoNhat, TrungBinh));

            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void duyet() {
        tblModel.setRowCount(0);
        for (O_ThongKeDoanhThu dt : listDoanhThu) {
            Object[] tbl = new Object[]{dt.getChuyenDe(), dt.getSoKH(), dt.getSoHV(),dt.getDoanhThu(), dt.getThapNhat(), dt.getCaoNhat(), dt.getTrungBinh()};
            tblModel.addRow(tbl);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        cboNam = new com.tuandhpc05076.Swing.Combobox();

        setBackground(new java.awt.Color(255, 255, 255));

        tblUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblUser);

        cboNam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "2021", "2022", "2023", "2024" }));
        cboNam.setSelectedIndex(-1);
        cboNam.setLabeText("Chọn năm");
        cboNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
                    .addComponent(cboNam, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(cboNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void textField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField4ActionPerformed

    private void cboNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNamActionPerformed
        loadDataToArray();
        duyet();        // TODO add your handling code here:
    }//GEN-LAST:event_cboNamActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tuandhpc05076.Swing.Combobox cboNam;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUser;
    // End of variables declaration//GEN-END:variables
}
