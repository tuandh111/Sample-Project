/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandhpc05076.ChuongTrinh;



import com.tuandhpc05076.swing0.Form;
import com.tuandhpc05076.swing0.ScrollBarCustom;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JScrollPane;

public class MainBody extends javax.swing.JPanel {


    public MainBody() {
        initComponents();
        setOpaque(false);
        scroll.setBorder(null);
        scroll.setViewportBorder(null);
        scroll.getViewport().setOpaque(false);
        scroll.setVerticalScrollBar(new ScrollBarCustom());
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    }

    public void displayForm(Component form) {
        displayForm(form, "");
    }

    public void displayForm(Component form, String title) {
        lbTitle.setText(title);
        panelBody.removeAll();
        panelBody.add(form);
        panelBody.repaint();
        panelBody.revalidate();
    }

    public void changeColor(Color color) {
        lbTitle.setForeground(color);
        if (panelBody.getComponentCount() != 0) {
            Form com = (Form) panelBody.getComponent(0);
            com.changeColor(color);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTitle = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        scroll = new javax.swing.JScrollPane();
        panelBody = new javax.swing.JPanel();

        panelTitle.setOpaque(false);

        lbTitle.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelTitleLayout = new javax.swing.GroupLayout(panelTitle);
        panelTitle.setLayout(panelTitleLayout);
        panelTitleLayout.setHorizontalGroup(
            panelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTitleLayout.createSequentialGroup()
                .addContainerGap(858, Short.MAX_VALUE)
                .addComponent(lbTitle)
                .addContainerGap())
        );
        panelTitleLayout.setVerticalGroup(
            panelTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle)
                .addContainerGap())
        );

        panelBody.setOpaque(false);
        panelBody.setLayout(new java.awt.BorderLayout());
        scroll.setViewportView(panelBody);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(scroll, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scroll, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbTitle;
    private javax.swing.JPanel panelBody;
    private javax.swing.JPanel panelTitle;
    private javax.swing.JScrollPane scroll;
    // End of variables declaration//GEN-END:variables
}
