/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandhpc05076.Form;

import com.tuandhpc05076.Object.O_NguoiHoc;
import com.tuandhpc05076.Object.O_NguoiHocTungNam;
import com.tuandhpc05076.swing0.Form;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Stack;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class ThongKeNguoiHocTungNam extends Form {

    ArrayList<O_NguoiHocTungNam> list = new ArrayList<>();
    DefaultTableModel tblModel;
    String userName = "sa";
    String password = "123";
    String url = "jdbc:sqlserver://localhost:1433; databaseName= EduSys;encrypt=false";
    Vector vt = new Vector();
    Vector vtTieuDe = new Vector();

    public ThongKeNguoiHocTungNam() {
        initComponents();
        TieuDe();
        loadDataToArray();
        Duyet();
    }

    public void TieuDe() {
        tblModel = new DefaultTableModel();
        String[] tbl = new String[]{"Năm", "Lượng NH", "Ngày ĐK sớm nhất", "Ngày ĐK muộn nhất"};
        tblModel.setColumnIdentifiers(tbl);
        tblUser.setModel(tblModel);
    }

    public void loadDataToArray() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);

            String sql = "{CALL Sp_LuongNguoiHoc}";

            CallableStatement stmt = con.prepareCall(sql);
            list.clear();
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                String Nam = rs.getString(1);
                int SoLuong = rs.getInt(2);
                String NgayDKSomNhat = rs.getString(3);
                String NgayDKMuonNhat = rs.getString(4);

                list.add(new O_NguoiHocTungNam(Nam, SoLuong, NgayDKSomNhat, NgayDKMuonNhat));

            }
           
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Duyet() {
        tblModel.setRowCount(0);
        for (O_NguoiHocTungNam nh : list) {
            Object[] tbl = new Object[]{nh.getNam(), nh.getSoLuong(), nh.getNgayDKsomNhat(), nh.getNgayDKMuonNhat()};
            
            tblModel.addRow(tbl);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 904, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(63, 63, 63)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void textField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUser;
    // End of variables declaration//GEN-END:variables
}
