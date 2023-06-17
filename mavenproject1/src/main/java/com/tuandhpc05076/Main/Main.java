/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.tuandhpc05076.Main;

import com.tuandhpc05076.BieuDo.FormHome;
import com.tuandhpc05076.ChuongTrinh.ChaoJDialog;
import com.tuandhpc05076.ChuongTrinh.GioiThieuJDialog;
import com.tuandhpc05076.DangNhap.quenMatKhau;
import com.tuandhpc05076.Form.ChuyenDe;

import com.tuandhpc05076.Form.HocVien;
import com.tuandhpc05076.Form.Home_Form;
import com.tuandhpc05076.Form.KhoaHoc;
import com.tuandhpc05076.Form.NguoiHoc;
import com.tuandhpc05076.Form.NhanVien;
import com.tuandhpc05076.Form.Setting_Form;
import com.tuandhpc05076.Form.ThongKeChuyenDe;
import com.tuandhpc05076.Form.ThongKeDiemTungKhoaHoc;
import com.tuandhpc05076.Form.ThongKeDoanhThu;
import com.tuandhpc05076.Form.ThongKeKhoaHoc;
import com.tuandhpc05076.Form.ThongKeNguoiHocTungNam;
import com.tuandhpc05076.Object.O_DangNhap;
import com.tuandhpc05076.swing0.EventColorChange;
import com.tuandhpc05076.swing0.EventMenu;
import com.tuandhpc05076.swing0.SystemProperties;
import com.tuandhpc05076.swing0.SystemTheme;
import com.tuandhpc05076.swing0.ThemeColor;
import com.tuandhpc05076.swing0.ThemeColorChange;
import com.tuandhpc05076.ThoiGian.Time;
import com.tuandhpc05076.helper.ShareHelper;

import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.time.Clock;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author DELL E5470
 */
public class Main extends javax.swing.JFrame {

    private Setting_Form settingForm;
    //dùng để set tiêu đề
    private ChuyenDe ChuyenDe = new ChuyenDe();
    private KhoaHoc khoaHoc = new KhoaHoc();
    private NguoiHoc nguoiHoc = new NguoiHoc();
    HocVien hocVien = new HocVien();
    NhanVien nhanVien = new NhanVien();
    ThongKeChuyenDe thongKeChuyenDe = new ThongKeChuyenDe();
    ThongKeKhoaHoc thongKeKhoaHoc = new ThongKeKhoaHoc();
    ThongKeDiemTungKhoaHoc thongKeDiemTungKhoaHoc = new ThongKeDiemTungKhoaHoc();
    ThongKeDoanhThu thongKeDoanhThu = new ThongKeDoanhThu();
    ThongKeNguoiHocTungNam thongKeNguoiHocTungNam = new ThongKeNguoiHocTungNam();
    //trả về một danh sách đăng nhập
    O_DangNhap tk = new O_DangNhap();
    ArrayList<O_DangNhap> listDangNhap = tk.getAlllist();

    FormHome banDo = new FormHome();
    boolean kiem = false;

    public Main() {
        setUndecorated(true);
        initComponents();
        setBackground(new Color(0, 0, 0));
        Time tg = new Time(txtThoiGian);
        tg.start();
        for (O_DangNhap dn : listDangNhap) {
            if (dn.isVaiTro() == false) {
                kiem = true;
            }
        }
        init();
    }

    int i = 0;

    private void init() {
        header.initMoving(this);
        header.initEvent(this, panelBackground1);

        menu.addEventMenu(new EventMenu() {
            @Override
            public void selectedMenu(int index) {
                if (index == 0) {
                    mainBody.displayForm(new Home_Form());
                }
                if (index == 1) {
                    mainBody.displayForm(new ChuyenDe(), "Chuyên đề");
                }
                if (index == 2) {
                    mainBody.displayForm(new KhoaHoc(), "Khóa học");
                }
                if (index == 3) {
                    mainBody.displayForm(new NguoiHoc(), "Người học");
                }
                if (index == 4) {
                    mainBody.displayForm(new HocVien(), "Học viên");
                }
                if (index == 5) {
                    mainBody.displayForm(new NhanVien(), "Nhân viên");
                }
                if (index == 6) {
                    if (kiem == false) {
                        mainBody.displayForm(new ThongKeDoanhThu(), "Thống kê doang thu");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Bạn không có quyền xem doanh thu");
                    }
                }
                if (index == 7) {

                    if (kiem == false) {
                        mainBody.displayForm(new FormHome(), "Biểu đồ");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Bạn không có quyền xem biểu đồ doanh thu");
                    }
                }
                if (index == 8) {
                    mainBody.displayForm(new ThongKeDiemTungKhoaHoc(), "Thống kê từng khóa học");
                }
                if (index == 9) {
                    mainBody.displayForm(new ThongKeNguoiHocTungNam(), "Thống kế người học từng năm");
                }
                if (index == 10) {
                    mainBody.displayForm(new ThongKeChuyenDe(), "Thống kê chuyên đề");
                } else if (index == 11) {
                    mainBody.displayForm(settingForm, "Cài đặt");
                }
            }
        });
        ThemeColorChange.getInstance().addThemes(new ThemeColor(new Color(34, 34, 34), Color.WHITE) {
            @Override
            public void onColorChange(Color color) {
                panelBackground1.setBackground(color);

            }
        });
        ThemeColorChange.getInstance().addThemes(new ThemeColor(Color.WHITE, new Color(80, 80, 80)) {
            @Override
            public void onColorChange(Color color) {
                mainBody.changeColor(color);

            }
        });

        ThemeColorChange.getInstance().initBackground(panelBackground1);
//        SystemProperties pro = new SystemProperties();
//        pro.loadFromFile();
//       
//        if (!pro.isDarkMode()) {
        ThemeColorChange.getInstance().setMode(ThemeColorChange.Mode.LIGHT);
        panelBackground1.setBackground(Color.WHITE);
        mainBody.changeColor(new Color(80, 80, 80));
//        }
//        if (pro.getBackgroundImage() != null) {
//            ThemeColorChange.getInstance().changeBackgroundImage(pro.getBackgroundImage());
//        }

//        SystemTheme.mainColor = pro.getColor();
        settingForm = new Setting_Form();
        settingForm.setEventColorChange(new EventColorChange() {
            @Override
            public void colorChange(Color color) {
                SystemTheme.mainColor = color;
                ThemeColorChange.getInstance().ruenEventColorChange(color);

                repaint();
//                pro.save("theme_color", color.getRGB() + "");
            }
        });
//        settingForm.setSelectedThemeColor(pro.getColor());
//        settingForm.setDarkMode(pro.isDarkMode());
//        settingForm.initBackgroundImage(pro.getBackgroundImage());
        settingForm.setDarkMode(false);
        mainBody.changeColor(new Color(80, 80, 80));

        mainBody.displayForm(new Home_Form());

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem2 = new javax.swing.JMenuItem();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        panelBackground1 = new com.tuandhpc05076.swing0.PanelBackground();
        header = new com.tuandhpc05076.swing0.Header();
        mainBody = new com.tuandhpc05076.ChuongTrinh.MainBody();
        menu = new com.tuandhpc05076.ChuongTrinh.Menu();
        txtThoiGian = new javax.swing.JLabel();
        textImage1 = new com.tuandhpc05076.Swing.TextImage();
        txtAP = new javax.swing.JLabel();
        txtTuyenSinh = new javax.swing.JLabel();
        txtCaoDang = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        setForeground(new java.awt.Color(153, 153, 153));
        setPreferredSize(new java.awt.Dimension(1200, 700));

        panelBackground1.setBackground(new java.awt.Color(34, 34, 34));
        panelBackground1.setPreferredSize(new java.awt.Dimension(1200, 700));

        header.setPreferredSize(new java.awt.Dimension(100, 22));

        mainBody.setPreferredSize(new java.awt.Dimension(873, 479));

        menu.setBackground(new java.awt.Color(34, 34, 34));

        txtThoiGian.setForeground(new java.awt.Color(255, 153, 255));
        txtThoiGian.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/alarm-clock.png"))); // NOI18N

        textImage1.setText("Thực học - Thực nghiệp ");
        textImage1.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        textImage1.setTextImage(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/bg_5_small.jpg"))); // NOI18N

        txtAP.setForeground(new java.awt.Color(255, 153, 255));
        txtAP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/10.png"))); // NOI18N
        txtAP.setText("ap.poly.edu.vn");
        txtAP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAPMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtAPMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtAPMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                txtAPMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtAPMouseReleased(evt);
            }
        });

        txtTuyenSinh.setForeground(new java.awt.Color(255, 153, 255));
        txtTuyenSinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/teamwork.png"))); // NOI18N
        txtTuyenSinh.setText("Tuyển sinh");
        txtTuyenSinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTuyenSinhMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtTuyenSinhMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtTuyenSinhMouseExited(evt);
            }
        });

        txtCaoDang.setForeground(new java.awt.Color(255, 153, 255));
        txtCaoDang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/book-stack.png"))); // NOI18N
        txtCaoDang.setText("caodang@fpt.edu.vn");
        txtCaoDang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtCaoDangMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtCaoDangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txtCaoDangMouseExited(evt);
            }
        });

        javax.swing.GroupLayout panelBackground1Layout = new javax.swing.GroupLayout(panelBackground1);
        panelBackground1.setLayout(panelBackground1Layout);
        panelBackground1Layout.setHorizontalGroup(
            panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBackground1Layout.createSequentialGroup()
                .addGroup(panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelBackground1Layout.createSequentialGroup()
                        .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBackground1Layout.createSequentialGroup()
                                .addGroup(panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCaoDang)
                                    .addComponent(txtTuyenSinh))
                                .addGap(48, 48, 48)
                                .addComponent(textImage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtThoiGian, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtAP))
                                .addGap(14, 14, 14))
                            .addComponent(mainBody, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(panelBackground1Layout.createSequentialGroup()
                        .addContainerGap(63, Short.MAX_VALUE)
                        .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, 1137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        panelBackground1Layout.setVerticalGroup(
            panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBackground1Layout.createSequentialGroup()
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBackground1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textImage1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBackground1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(txtAP)
                        .addGroup(panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelBackground1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtThoiGian, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panelBackground1Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtCaoDang)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(mainBody, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(84, 84, 84))
            .addGroup(panelBackground1Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(panelBackground1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTuyenSinh)
                    .addComponent(menu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/settings.png"))); // NOI18N
        jMenu1.setText("Hệ Thống");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/key.png"))); // NOI18N
        jMenuItem1.setText("Đăng nhập");
        jMenu1.add(jMenuItem1);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/turn-off.png"))); // NOI18N
        jMenuItem3.setText("Đăng xuất ");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/password.png"))); // NOI18N
        jMenuItem4.setText("Đổi mật khẩu");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F10, 0));
        jMenuItem5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/stop.png"))); // NOI18N
        jMenuItem5.setText("Kết thúc");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuBar1.add(jMenu1);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/management.png"))); // NOI18N
        jMenu2.setText("Quản lý");

        jMenuItem6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/transcript.png"))); // NOI18N
        jMenuItem6.setText("Chuyên đề");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuItem7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/book-stack.png"))); // NOI18N
        jMenuItem7.setText("Khóa học ");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem7);

        jMenuItem8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/teamwork.png"))); // NOI18N
        jMenuItem8.setText("Người học");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem8);

        jMenuItem9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/conductor.png"))); // NOI18N
        jMenuItem9.setText("Học viên");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem9);
        jMenu2.add(jSeparator1);

        jMenuItem10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/undergraduate.png"))); // NOI18N
        jMenuItem10.setText("Nhân viên");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem10);

        jMenuBar1.add(jMenu2);

        jMenu4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/analytics.png"))); // NOI18N
        jMenu4.setText("Thống kê");

        jMenuItem11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/online-learning.png"))); // NOI18N
        jMenuItem11.setText("Người học từng năm");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);
        jMenu4.add(jSeparator2);

        jMenuItem12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/table.png"))); // NOI18N
        jMenuItem12.setText("Người học từng năm");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem12);

        jMenuItem13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/book-stack.png"))); // NOI18N
        jMenuItem13.setText("Điểm từng khóa học");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem13);
        jMenu4.add(jSeparator3);

        jMenuItem14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/profits.png"))); // NOI18N
        jMenuItem14.setText("Doanh thu chuyên đề");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem14);

        jMenuBar1.add(jMenu4);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/help-desk.png"))); // NOI18N
        jMenu5.setText("Trợ giúp");
        jMenu5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenu5ActionPerformed(evt);
            }
        });

        jMenuItem15.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/planet-earth.png"))); // NOI18N
        jMenuItem15.setText("Hướng dẫn sử dụng");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem15);

        jMenuItem16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/Image/training.png"))); // NOI18N
        jMenuItem16.setText("Giới thiệu sản phẩm");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem16);

        jMenuItem17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/2.png"))); // NOI18N
        jMenuItem17.setText("Thông tin ");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem17);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBackground1, javax.swing.GroupLayout.DEFAULT_SIZE, 757, Short.MAX_VALUE)
        );

        panelBackground1.getAccessibleContext().setAccessibleParent(panelBackground1);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenu5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenu5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenu5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO add your handling code here:
        mainBody.displayForm(ChuyenDe, "Chuyên đề");

    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        mainBody.displayForm(nguoiHoc, "Người học");        // TODO add your handling code here:

    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        mainBody.displayForm(nguoiHoc, "Người học");
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        mainBody.displayForm(hocVien, "Học viên");
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        mainBody.displayForm(nhanVien, "Nhân viên");//        mainBody.displayForm(new NhanVien());        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        mainBody.displayForm(new ThongKeNguoiHocTungNam(), "Thống kê khóa học");        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        mainBody.displayForm(thongKeDiemTungKhoaHoc, "Thống kê từng khóa học");        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        if (kiem == false) {
            mainBody.displayForm(thongKeDoanhThu, "Thống kê doang thu");
        } else {
            JOptionPane.showMessageDialog(rootPane, "Bạn không có quyền xem doanh thu");
        }   // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        // TODO add your handling code here:
        mainBody.displayForm(thongKeNguoiHocTungNam, "Thống kế người học từng năm");
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:

        this.dispose();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        GioiThieuJDialog gt = new GioiThieuJDialog(this, true);
        gt.setVisible(true);
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        listDangNhap.clear();
        this.dispose();

    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        int chon = JOptionPane.showConfirmDialog(this, "Bạn có muốn kết thúc không?", "Kết thúc", JOptionPane.YES_NO_OPTION);
        System.out.println(chon);
        if (chon == 0) {
            System.exit(0);
        }
// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        try {
            Desktop.getDesktop().browse(new File("Help/index.html").toURI());
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không tìm thấy file trong máy");
        }
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void txtAPMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAPMouseClicked
        // TODO add your handling code here:
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://ap.poly.edu.vn"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }//GEN-LAST:event_txtAPMouseClicked

    private void txtTuyenSinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTuyenSinhMouseClicked
        // TODO add your handling code here:
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://caodang.fpt.edu.vn/tuyen-sinh-cao-dang-xet-tuyen?utm_source=Google&utm_medium=CPC&utm_campaign=CD_FPT&gad=1&gclid=CjwKCAjw04yjBhApEiw"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }//GEN-LAST:event_txtTuyenSinhMouseClicked

    private void txtCaoDangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCaoDangMouseClicked
        // TODO add your handling code here:
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://mail.google.com/mail/u/0/#drafts?compose=DmwnWrRvwMBZsWmBwWVzXSfVGwLPhgtBgSWhTKgPDdJbKJhgScKBXQJRCHcMCBnhxpSBqNhXkvwG"));
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }//GEN-LAST:event_txtCaoDangMouseClicked

    private void txtAPMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAPMouseReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtAPMouseReleased

    private void txtAPMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAPMouseExited
        txtAP.setForeground(new Color(255, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_txtAPMouseExited

    private void txtAPMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAPMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtAPMousePressed

    private void txtAPMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAPMouseEntered
        txtAP.setForeground(Color.yellow);        // TODO add your handling code here:
    }//GEN-LAST:event_txtAPMouseEntered

    private void txtCaoDangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCaoDangMouseExited
        // TODO add your handling code here:
        txtCaoDang.setForeground(new Color(255, 153, 255));
    }//GEN-LAST:event_txtCaoDangMouseExited

    private void txtCaoDangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtCaoDangMouseEntered
        txtCaoDang.setForeground(Color.yellow);        // TODO add your handling code here:
    }//GEN-LAST:event_txtCaoDangMouseEntered

    private void txtTuyenSinhMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTuyenSinhMouseExited
        // TODO add your handling code here:
        txtTuyenSinh.setForeground(new Color(255, 153, 255));
    }//GEN-LAST:event_txtTuyenSinhMouseExited

    private void txtTuyenSinhMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTuyenSinhMouseEntered
        txtTuyenSinh.setForeground(Color.yellow);         // TODO add your handling code here:
    }//GEN-LAST:event_txtTuyenSinhMouseEntered

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        try {
            Desktop.getDesktop().browse(new File("Help/index.html").toURI());
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Không tìm thấy file trong máy");
        }      // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tuandhpc05076.swing0.Header header;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private com.tuandhpc05076.ChuongTrinh.MainBody mainBody;
    private com.tuandhpc05076.ChuongTrinh.Menu menu;
    private com.tuandhpc05076.swing0.PanelBackground panelBackground1;
    private com.tuandhpc05076.Swing.TextImage textImage1;
    private javax.swing.JLabel txtAP;
    private javax.swing.JLabel txtCaoDang;
    private javax.swing.JLabel txtThoiGian;
    private javax.swing.JLabel txtTuyenSinh;
    // End of variables declaration//GEN-END:variables
}
