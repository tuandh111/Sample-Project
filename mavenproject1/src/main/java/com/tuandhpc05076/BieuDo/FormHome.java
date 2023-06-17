/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandhpc05076.BieuDo;

import com.tuandhpc05076.Form.ThongKeBangDiem;
import com.tuandhpc05076.Form.ThongKeDoanhThu;
import static com.tuandhpc05076.Form.ThongKeDoanhThu.listDoanhThu;
import com.tuandhpc05076.Object.O_ThongKeDoanhThu;
import java.awt.Color;
import java.sql.CallableStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL E5470
 */
public class FormHome extends javax.swing.JPanel {

    ThongKeDoanhThu tk = new ThongKeDoanhThu();
    ArrayList<O_ThongKeDoanhThu> listDoanhThu = new ArrayList<>();
    DefaultTableModel tblModel;
    String userName = "sa";
    String password = "123";
    String url = "jdbc:sqlserver://localhost:1433; databaseName= EduSys;encrypt=false";

    public void loadDataToArray() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);

            String sql = "{CALL sp_ThongKeDoanhThu(?)}";

            CallableStatement stmt = con.prepareCall(sql);
            stmt.setObject(1, 2023);
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

    public FormHome() {
        initComponents();
        loadDataToArray();
        float tong = 0;
        for (O_ThongKeDoanhThu dt : listDoanhThu) {
            tong += dt.getDoanhThu();
        }

        chart.addLegend("Doanh thu chuyên đề", new Color(12, 84, 175), new Color(0, 108, 247));
//        chart.addLegend("Thu Nhập", new Color(54, 4, 143), new Color(104, 49, 200));
//        chart.addLegend("Khoản chi", new Color(5, 125, 0), new Color(95, 209, 69));
//        chart.addLegend("Cost", new Color(186, 37, 37), new Color(241, 100, 120));
        chart.addData(new ModelChart("2021", new double[]{0}));
        chart.addData(new ModelChart("2022", new double[]{0}));
        chart.addData(new ModelChart("2023", new double[]{tong}));
        chart.start();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        chart = new com.tuandhpc05076.BieuDo.CurveChart();

        setPreferredSize(new java.awt.Dimension(800, 470));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(800, 800));

        chart.setBackground(new java.awt.Color(0, 0, 0));
        chart.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chart, javax.swing.GroupLayout.PREFERRED_SIZE, 859, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 458, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 871, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 470, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tuandhpc05076.BieuDo.CurveChart chart;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
