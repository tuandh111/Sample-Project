/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandhpc05076.ChuongTrinh;

import com.tuandhpc05076.swing0.EventMenu;
import com.tuandhpc05076.swing0.MenuButton;
import com.tuandhpc05076.swing0.SystemTheme;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
import org.jdesktop.animation.timing.interpolation.PropertySetter;

public class Menu extends javax.swing.JPanel {

    public int getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(int selectedLocation) {
        this.selectedLocation = selectedLocation;
        repaint();
    }

    public void addEventMenu(EventMenu event) {
        this.events.add(event);
    }

    private int selectedIndex = 0;
    private Animator animator;
    private TimingTarget target;
    private int selectedLocation = 151;
    private int targetLocation;
    private List<EventMenu> events = new ArrayList<>();

    public Menu() {
        initComponents();
        setOpaque(false);
        setBackground(Color.WHITE);
        menu.setLayout(new MigLayout("fillx, wrap, inset 0", "[fill]", "[fill, 36!]0[fill, 36!]"));

        initMenu();
    }

    private void initMenu() {
        addMenu("Trang chủ", "1", 0);
        addMenu("Chuyên đề", "2", 1);
        addMenu("Khóa học", "3", 2);
        addMenu("Người học", "4", 3);
        addMenu("Học viên", "5", 4);
        addMenu("Nhân viên", "6", 5);

        addMenu("Doanh thu chuyên đề", "11", 6);
        addMenu("Biểu đồ doanh thu năm", "8", 7);
        addMenu("Điểm từng khóa học", "9", 8);
        addMenu("Người học từng năm", "10", 9);
        addMenu("Điểm chuyên đề", "4", 10);
        addMenu("Cài đặt", "7", 11);

        menu.repaint();
        menu.revalidate();
        setSelectedMenu(0);
        animator = new Animator(300);
        animator.addTarget(new TimingTargetAdapter() {
            @Override
            public void begin() {
                clearSelected();
            }

            @Override
            public void end() {
                setSelectedMenu(selectedIndex);
                runEvent();
            }
        });
        animator.setDeceleration(.5f);
        animator.setAcceleration(.5f);
        animator.setResolution(0);
    }

    private void addMenu(String menuName, String icon, int index) {
        MenuButton m = new MenuButton();
        m.setIcoName(icon);
        m.setIcon(new ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/" + icon + ".png")));
        m.setFont(m.getFont().deriveFont(Font.BOLD, 12));
        m.setForeground(new Color(127, 127, 127));
        m.setHorizontalAlignment(JButton.LEFT);
        m.setText("  " + menuName);
        m.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (index != selectedIndex) {
                    if (animator.isRunning()) {
                        animator.stop();
                    }
                    int y = m.getY() + menu.getY();
                    targetLocation = y;
                    selectedIndex = index;
                    animator.removeTarget(target);
                    target = new PropertySetter(Menu.this, "selectedLocation", selectedLocation, targetLocation);
                    animator.addTarget(target);
                    animator.start();
                }
            }
        });
        menu.add(m);
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int y = selectedLocation;
        g2.setColor(SystemTheme.mainColor);
        g2.fill(createShape(y));
        g2.dispose();
        super.paintComponent(grphcs);
    }

    private Shape createShape(int y) {
        int width = getWidth() - 12;
        int r = 20;
        Area area = new Area(new RoundRectangle2D.Float(6, y, width, 35, r, r));
        area.add(new Area(new RoundRectangle2D.Float(width - r + 6, y, r, r, 5, 5)));
        area.add(new Area(new RoundRectangle2D.Float(6, y + 35 - r, r, r, 5, 5)));
        return area;
    }

    private void clearSelected() {
        for (Component com : menu.getComponents()) {
            if (com instanceof MenuButton) {
                MenuButton c = (MenuButton) com;
                c.setForeground(new Color(127, 127, 127));
                c.setEffectColor(new Color(173, 173, 173));
                if (!c.getIcoName().contains("_s")) {
                    c.setIcon(new ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/" + c.getIcoName() + ".png")));
                }
            }
        }
    }

    public void setSelectedMenu(int index) {
        MenuButton cmd = (MenuButton) menu.getComponent(index);
        cmd.setForeground(Color.WHITE);
        cmd.setEffectColor(Color.WHITE);
        cmd.setIcon(new ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/" + cmd.getIcoName() + "_s.png")));
    }

    private void runEvent() {
        for (EventMenu event : events) {
            event.selectedMenu(selectedIndex);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menu = new javax.swing.JPanel();
        imageAvatar2 = new com.tuandhpc05076.swing0.ImageAvatar();
        txtTenDangNhap = new javax.swing.JLabel();

        menu.setOpaque(false);

        javax.swing.GroupLayout menuLayout = new javax.swing.GroupLayout(menu);
        menu.setLayout(menuLayout);
        menuLayout.setHorizontalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        menuLayout.setVerticalGroup(
            menuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 549, Short.MAX_VALUE)
        );

        imageAvatar2.setBorderSize(1);
        imageAvatar2.setBorderSpace(0);
        imageAvatar2.setImage(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/logoTruong.jpg"))); // NOI18N
        imageAvatar2.setInheritsPopupMenu(true);

        txtTenDangNhap.setBackground(new java.awt.Color(173, 173, 173));
        txtTenDangNhap.setFont(new java.awt.Font("sansserif", 1, 14)); // NOI18N
        txtTenDangNhap.setForeground(new java.awt.Color(117, 117, 117));
        txtTenDangNhap.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtTenDangNhap.setText("FPT FOLYTECHNIC");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imageAvatar2, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
                    .addComponent(txtTenDangNhap, javax.swing.GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imageAvatar2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtTenDangNhap)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(menu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(42, 42, 42))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tuandhpc05076.swing0.ImageAvatar imageAvatar2;
    private javax.swing.JPanel menu;
    private javax.swing.JLabel txtTenDangNhap;
    // End of variables declaration//GEN-END:variables
}
