/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandhpc05076.Form;

import com.raven.table.cell.Cell;
import static com.tuandhpc05076.Form.NhanVien.list;
import com.tuandhpc05076.Object.O_ChuyenDe;
import com.tuandhpc05076.Object.O_DangNhap;
import com.tuandhpc05076.Object.O_HocVien;
import com.tuandhpc05076.Object.O_KhoaHoc;
import com.tuandhpc05076.Object.O_NguoiHoc;
import com.tuandhpc05076.swing0.Form;
import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author DELL E5470
 */
public class HocVien extends javax.swing.JPanel {

    public static ArrayList<O_HocVien> listHV = new ArrayList<>();
    private ArrayList<O_ChuyenDe> listCD = new ArrayList<>();
    DefaultTableModel tblmodel;
    String userName = "sa";
    String password = "123";
    String url = "jdbc:sqlserver://localhost:1433; databaseName= EduSys;encrypt=false";
    // lấy danh sách người học
    NguoiHoc nh = new NguoiHoc();
    ArrayList<O_NguoiHoc> listLayDanhSachNH = nh.listNH;
    //lấy danh sách chuyên đề
    ChuyenDe cd = new ChuyenDe();
    ArrayList<O_ChuyenDe> listLayCD = cd.listCD;
//lấy thông tin đăng nhập
    O_DangNhap tk = new O_DangNhap();
    ArrayList<O_DangNhap> listDangNhap = tk.getAlllist();
    //

    public HocVien() {
        initComponents();
        jTabbedPane1.setBackground(HocVien.this.getBackground());
        jPanel1.setBackground(HocVien.this.getBackground());
        jPanel2.setBackground(HocVien.this.getBackground());
        TieuDeNguoiHoc();
        DuyetNguoiHoc();
        TieuDeHocVien();

        DefaultComboBoxModel tbl = new DefaultComboBoxModel();
        for (O_ChuyenDe layCD : listLayCD) {
            tbl.addElement(layCD.getTenCD().trim());

        }
        cboChuyenDe.setModel(tbl);
        cboChuyenDe.setSelectedIndex(-1);
        tblNguoiHoc.getColumnModel().getColumn(0).setPreferredWidth(10);
        tblNguoiHoc.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblNguoiHoc.getColumnModel().getColumn(2).setPreferredWidth(5);
        tblNguoiHoc.getColumnModel().getColumn(5).setPreferredWidth(100);
        tblNguoiHoc.getColumnModel().getColumn(6).setPreferredWidth(100);
        tblNguoiHoc.getColumnModel().getColumn(7).setPreferredWidth(10);
        tblNguoiHoc.getColumnModel().getColumn(8).setPreferredWidth(10);
    }

    public void TieuDeNguoiHoc() {
        tblmodel = new DefaultTableModel();
        String[] tbl = new String[]{"Mã NH", "Họ và tên", "Giới tính", "Ngày sinh", "Điện thoại", "Email", "Ghi chú", "Mã NV", "Ngày ĐK"};
        tblmodel.setColumnIdentifiers(tbl);
        tblNguoiHoc.setModel(tblmodel);
    }

    public void TieuDeHocVien() {
        tblmodel = new DefaultTableModel();
        String[] tbl = new String[]{"Mã HV", "Mã NH", "Họ và tên", "Điểm"};
        tblmodel.setColumnIdentifiers(tbl);
        tblHocVien.setModel(tblmodel);
    }

    public void DuyetHocVien() {
        tblmodel.setRowCount(0);
        for (O_HocVien hv : listHV) {

            Object[] tbl = new Object[]{hv.getMaHV(), hv.getMaNH(), hv.getHoVaTen(), hv.getDiem()};
            tblmodel.addRow(tbl);
        }
    }

    public void DuyetNguoiHoc() {
        tblmodel.setRowCount(0);
        for (O_NguoiHoc nh : NguoiHoc.listNH) {
            String gt = "";
            if (nh.isGioiTinh()) {
                gt = "Nam";
            } else {
                gt = "Nữ";
            }
            Object[] tbl = new Object[]{nh.getMaNH(), nh.getHoTen(), gt, nh.getNgaySinh(), nh.getDienThoai(), nh.getEmail(), nh.getGhiChu(), nh.getMaNV(), nh.getNgayDK()};
            tblmodel.addRow(tbl);
        }
    }
    //SELECT MaHV,HocVien.MaNH,HoTen,KhoaHoc.MaKH,Diem  FROM dbo.HocVien INNER JOIN dbo.KhoaHoc ON KhoaHoc.MaKH = HocVien.MaKH INNER JOIN dbo.NguoiHoc ON NguoiHoc.MaNH = HocVien.MaNH WHERE KhoaHoc.GhiChu= N'Khóa học'

    public void loadDataToArrayBangHocVien() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);

            String sql = "SELECT MaHV,KhoaHoc.MaKH,HocVien.MaNH,HoTen,Diem  FROM dbo.HocVien INNER JOIN dbo.KhoaHoc ON KhoaHoc.MaKH = HocVien.MaKH INNER JOIN dbo.NguoiHoc ON NguoiHoc.MaNH = HocVien.MaNH WHERE KhoaHoc.GhiChu= ?";
            PreparedStatement st = con.prepareStatement(sql);

            String name = (String) cboKhoaHoc.getSelectedItem();
            if (name == null) {
                return;
            }
            String[] ten = name.split("-");

            st.setString(1, ten[1]);

            ResultSet rs = st.executeQuery();
            listHV.clear();
            while (rs.next()) {

                int MaHV = rs.getInt(1);
                int MaKH = rs.getInt(2);
                String MaNH = rs.getString(3);
                String HoTen = rs.getString(4);
                float Diem = rs.getFloat(5);
                listHV.add(new O_HocVien(MaHV, MaKH, MaNH, HoTen, Diem));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void LayDuLieuBangKhoaHoc() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            String sql = "SELECT ChuyenDe.MaCD,TenCD,KhoaHoc.MaKH,ChuyenDe.ThoiLuong,NgayKG,GhiChu FROM dbo.ChuyenDe INNER JOIN dbo.KhoaHoc ON KhoaHoc.MaCD = ChuyenDe.MaCD WHERE TenCD =?";
            PreparedStatement st = con.prepareStatement(sql);
            String name = (String) cboChuyenDe.getSelectedItem();
            st.setString(1, name);

            ResultSet rs = st.executeQuery();
            listCD.clear();
            while (rs.next()) {

                String MaCD = rs.getString(1);
                String TenCD = rs.getString(2);
                float HocPhi = rs.getFloat(3);
                int ThoiLuong = rs.getInt(4);
                String Hinh = rs.getString(5);

                String MoTa = rs.getString(6);
                listCD.add(new O_ChuyenDe(MaCD, TenCD, HocPhi, ThoiLuong, Hinh, MoTa));
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnTim() {
        if (txtTimKiem.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Bạn chưa nhập mã cần tìm kiếm");
            return;
        }
        int i = 0;
        boolean kiem = false;
        for (O_NguoiHoc nh : NguoiHoc.listNH) {
            if (nh.getMaNH().trim().equalsIgnoreCase(txtTimKiem.getText())) {
                tblNguoiHoc.setRowSelectionInterval(i, i);
                JOptionPane.showMessageDialog(this, "Đã tìm thấy");
                kiem = true;
                break;
            }
            i++;
        }
        if (kiem == false) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy người học trong mã này");
        }
    }

    public void btnDelete() {
        boolean kiem = false;
        for (O_DangNhap dn : listDangNhap) {
            if (dn.isVaiTro() == false) {
                kiem = true;
            }
        }
        if (kiem == true) {
            JOptionPane.showMessageDialog(this, "Bạn là nhân viên không có quyền xóa");
            return;
        }
        int chon = tblHocVien.getSelectedRow();
        if (chon < 0) {
            JOptionPane.showMessageDialog(this, "Bạn cần chọn học viên để có thể xóa");
            return;
        }
        String name = tblmodel.getValueAt(chon, 0).toString();
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            String sqla = "DELETE FROM dbo.HocVien WHERE MaHV =?";
            PreparedStatement st = con.prepareStatement(sqla);
            st.setString(1, name);

            st.executeUpdate();
            con.close();
            loadDataToArrayBangHocVien();
            DuyetHocVien();
            JOptionPane.showMessageDialog(this, "Xóa học viên thành công");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    String diem = "";

    public void btnUpdate() {
        int chon = tblHocVien.getSelectedRow();
        if (chon < 0) {
            JOptionPane.showMessageDialog(this, "Bạn cần chọn học viên để có thể cập nhật");
            return;
        }
        diem = JOptionPane.showInputDialog(this, "Nhập điểm cần cập nhật");
        if (diem == null) {
            return;
        }
        try {
            float diem0 = Float.parseFloat(diem);
            if (diem0 < 0) {
                JOptionPane.showMessageDialog(this, "Điểm không được nhỏ hơn 0");
                return;
            }
            if (diem0 > 10) {
                JOptionPane.showMessageDialog(this, "Điểm không được lớn hơn 10");
                return;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Điểm không phải là số");
            return;
        }
        
        String name = tblmodel.getValueAt(chon, 0).toString();

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            String sqla = "UPDATE dbo.HocVien SET Diem=? WHERE MaHV=?";
            PreparedStatement st = con.prepareStatement(sqla);

            st.setFloat(1, Float.parseFloat(diem));
            st.setInt(2, Integer.parseInt(name));

            st.executeUpdate();
            con.close();
            loadDataToArrayBangHocVien();
            DuyetHocVien();
            JOptionPane.showMessageDialog(this, "Cập nhật điểm thành công");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnInDanhSach() {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();

            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("Học viên");

                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < tblHocVien.getColumnCount(); i++) {
                    org.apache.poi.ss.usermodel.Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tblHocVien.getColumnName(i));
                }

                for (int j = 0; j < tblHocVien.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tblHocVien.getColumnCount(); k++) {
                        org.apache.poi.ss.usermodel.Cell cell = row.createCell(k);
                        if (tblHocVien.getValueAt(j, k) != null) {
                            cell.setCellValue(tblHocVien.getValueAt(j, k).toString());
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(new File(saveFile.toString()));
                wb.write(out);
                wb.close();
                out.close();
                OpenFile(saveFile.toString());
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi");
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException io) {
            System.out.println(io);
        }
    }

    public void OpenFile(String file) {
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (Exception e) {
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtTimKiem = new com.tuandhpc05076.Swing.TextField1();
        btnTimKiem = new com.tuandhpc05076.Swing.Button();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNguoiHoc = new javax.swing.JTable();
        button10 = new com.tuandhpc05076.Swing.Button();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHocVien = new javax.swing.JTable();
        btnInThanhExel = new com.tuandhpc05076.Swing.Button();
        btnCapNhatDiem = new com.tuandhpc05076.Swing.Button();
        btnXoaKhoiKhoaHoc1 = new com.tuandhpc05076.Swing.Button();
        txtTimKiemHV = new com.tuandhpc05076.Swing.TextField1();
        btnTimKiemHV = new com.tuandhpc05076.Swing.Button();
        cboDieuKienLoc = new com.tuandhpc05076.Swing.Combobox();
        cboChuyenDe = new com.tuandhpc05076.Swing.Combobox();
        cboKhoaHoc = new com.tuandhpc05076.Swing.Combobox();

        setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtTimKiem.setHint("Nhập mã cần tìm kiếm");

        btnTimKiem.setBackground(new java.awt.Color(153, 153, 255));
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/search.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTimKiemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTimKiemMouseExited(evt);
            }
        });
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });

        tblNguoiHoc.setModel(new javax.swing.table.DefaultTableModel(
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
        tblNguoiHoc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNguoiHocMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblNguoiHoc);

        button10.setBackground(new java.awt.Color(153, 153, 255));
        button10.setForeground(new java.awt.Color(255, 255, 255));
        button10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/plus.png"))); // NOI18N
        button10.setText("Thêm vào khóa học");
        button10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button10MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button10MouseExited(evt);
            }
        });
        button10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                button10ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(39, 39, 39)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 749, Short.MAX_VALUE)
                        .addComponent(button10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 893, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 314, Short.MAX_VALUE)
                .addComponent(button10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(81, 81, 81)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(46, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Người học", jPanel1);

        tblHocVien.setModel(new javax.swing.table.DefaultTableModel(
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
        tblHocVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHocVienMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblHocVien);

        btnInThanhExel.setBackground(new java.awt.Color(153, 153, 255));
        btnInThanhExel.setForeground(new java.awt.Color(255, 255, 255));
        btnInThanhExel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/add-folder.png"))); // NOI18N
        btnInThanhExel.setText("In thành file Excel");
        btnInThanhExel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnInThanhExelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnInThanhExelMouseExited(evt);
            }
        });
        btnInThanhExel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInThanhExelActionPerformed(evt);
            }
        });

        btnCapNhatDiem.setBackground(new java.awt.Color(153, 153, 255));
        btnCapNhatDiem.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatDiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/update.png"))); // NOI18N
        btnCapNhatDiem.setText("Cập nhật điểm");
        btnCapNhatDiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCapNhatDiemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCapNhatDiemMouseExited(evt);
            }
        });
        btnCapNhatDiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatDiemActionPerformed(evt);
            }
        });

        btnXoaKhoiKhoaHoc1.setBackground(new java.awt.Color(153, 153, 255));
        btnXoaKhoiKhoaHoc1.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaKhoiKhoaHoc1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/delete.png"))); // NOI18N
        btnXoaKhoiKhoaHoc1.setText("Xóa khỏi khóa học");
        btnXoaKhoiKhoaHoc1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnXoaKhoiKhoaHoc1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnXoaKhoiKhoaHoc1MouseExited(evt);
            }
        });
        btnXoaKhoiKhoaHoc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKhoiKhoaHoc1ActionPerformed(evt);
            }
        });

        txtTimKiemHV.setHint("Nhập mã người học cần tìm kiếm");
        txtTimKiemHV.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemHVCaretUpdate(evt);
            }
        });

        btnTimKiemHV.setBackground(new java.awt.Color(153, 153, 255));
        btnTimKiemHV.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiemHV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/search.png"))); // NOI18N
        btnTimKiemHV.setText("Tìm kiếm theo mã HV");
        btnTimKiemHV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTimKiemHVMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTimKiemHVMouseExited(evt);
            }
        });
        btnTimKiemHV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemHVActionPerformed(evt);
            }
        });

        cboDieuKienLoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tăng", "Giảm" }));
        cboDieuKienLoc.setSelectedIndex(-1);
        cboDieuKienLoc.setLabeText("Sắp xếp điểm theo");
        cboDieuKienLoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboDieuKienLocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 893, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnInThanhExel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaKhoiKhoaHoc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCapNhatDiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(cboDieuKienLoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiemHV, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimKiemHV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiemHV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiemHV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboDieuKienLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnInThanhExel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhatDiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaKhoiKhoaHoc1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Học viên", jPanel2);

        cboChuyenDe.setLabeText("Chuyên đề");
        cboChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChuyenDeActionPerformed(evt);
            }
        });

        cboKhoaHoc.setLabeText("Khóa học");
        cboKhoaHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboKhoaHocActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addComponent(cboKhoaHoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboKhoaHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void textField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField4ActionPerformed

    private void cboChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChuyenDeActionPerformed
        // TODO add your handling code here:
        O_DangNhap dn = listDangNhap.get(0);
        String name = (String) cboChuyenDe.getSelectedItem();
        if (name == null) {
            return;
        }
        LayDuLieuBangKhoaHoc();
        DefaultComboBoxModel tbl = new DefaultComboBoxModel();
        for (O_ChuyenDe cd : listCD) {
            tbl.addElement(cd.getMaCD() + "-" + cd.getMoTa() + "-" + cd.getHinh());
        }
        cboKhoaHoc.setModel(tbl);
        cboKhoaHoc.setSelectedIndex(-1);

    }//GEN-LAST:event_cboChuyenDeActionPerformed

    private void button10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_button10ActionPerformed
        int[] rows = tblNguoiHoc.getSelectedRows();

        if (rows.length == 0) {
            JOptionPane.showMessageDialog(this, "Chưa chọn học viên để thêm vào");
            return;
        }
        if (cboChuyenDe.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chưa chọn chuyên đề");
            return;
        }
        if (cboKhoaHoc.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chưa chọn Khóa học");
            return;
        }
        for (int i = 0; i < rows.length; i++) {

            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                java.sql.Connection con = DriverManager.getConnection(url, userName, password);
                String sqla = "insert into dbo.HocVien values(?,?,?)";
                PreparedStatement st = con.prepareStatement(sqla);
                String name = (String) cboKhoaHoc.getSelectedItem();
                String ten[] = name.split("-");
                float ten1 = 0;
                for (O_ChuyenDe cd : listCD) {
                    if (cd.getMoTa().trim().equalsIgnoreCase(ten[1])) {
                        st.setInt(1, (int) cd.getHocPhi());
                        ten1 = cd.getHocPhi();

                    }
                }
                st.setString(2, tblNguoiHoc.getValueAt(rows[i], 0).toString());
                for (O_HocVien hv : listHV) {
                    if (hv.getMaKH() == ten1 && hv.getMaNH().trim().equals(tblNguoiHoc.getValueAt(rows[i], 0).toString())) {
                        JOptionPane.showMessageDialog(this, "Người học này đã học trong khóa học này rồi");
                        return;
                    }
                }

                st.setFloat(3, 0);

                st.executeUpdate();
                con.close();
                loadDataToArrayBangHocVien();
                DuyetHocVien();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        JOptionPane.showMessageDialog(this, "Thêm người học thành công");
        jTabbedPane1.setSelectedIndex(1);

    }//GEN-LAST:event_button10ActionPerformed

    private void tblNguoiHocMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNguoiHocMouseClicked
        // TODO add your handling code here:
//        tblNguoiHoc.setRowSelectionAllowed(true);
//        tblNguoiHoc.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

// Lấy các chỉ số của các dòng được chọn
//        setSelectedRows(tblNguoiHoc, chon);

    }//GEN-LAST:event_tblNguoiHocMouseClicked

    private void cboKhoaHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboKhoaHocActionPerformed
        loadDataToArrayBangHocVien();
        DuyetHocVien();
// TODO add your handling code here:

    }//GEN-LAST:event_cboKhoaHocActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        btnTim();        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tblHocVienMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHocVienMouseClicked

// TODO add your handling code here:
    }//GEN-LAST:event_tblHocVienMouseClicked

    private void btnCapNhatDiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatDiemActionPerformed
        btnUpdate();        // TODO add your handling code here:
    }//GEN-LAST:event_btnCapNhatDiemActionPerformed

    private void btnInThanhExelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInThanhExelActionPerformed
        btnInDanhSach();        // TODO add your handling code here:
    }//GEN-LAST:event_btnInThanhExelActionPerformed

    private void btnTimKiemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemMouseExited
        btnTimKiem.setBackground(new Color(153, 153, 255));       // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemMouseExited

    private void btnTimKiemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemMouseEntered
        btnTimKiem.setBackground(Color.pink);        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemMouseEntered

    private void button10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button10MouseEntered
        button10.setBackground(Color.pink);         // TODO add your handling code here:
    }//GEN-LAST:event_button10MouseEntered

    private void button10MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_button10MouseExited
        button10.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_button10MouseExited

    private void btnInThanhExelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInThanhExelMouseEntered
        btnInThanhExel.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btnInThanhExelMouseEntered

    private void btnInThanhExelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInThanhExelMouseExited
        btnInThanhExel.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnInThanhExelMouseExited

    private void btnCapNhatDiemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatDiemMouseEntered
        btnCapNhatDiem.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btnCapNhatDiemMouseEntered

    private void btnCapNhatDiemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatDiemMouseExited
        btnCapNhatDiem.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnCapNhatDiemMouseExited

    private void btnXoaKhoiKhoaHoc1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaKhoiKhoaHoc1MouseEntered
        // TODO add your handling code here:
        btnXoaKhoiKhoaHoc1.setBackground(Color.pink);
    }//GEN-LAST:event_btnXoaKhoiKhoaHoc1MouseEntered

    private void btnXoaKhoiKhoaHoc1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaKhoiKhoaHoc1MouseExited
        // TODO add your handling code here:
        btnXoaKhoiKhoaHoc1.setBackground(new Color(153, 153, 255));
    }//GEN-LAST:event_btnXoaKhoiKhoaHoc1MouseExited

    private void btnXoaKhoiKhoaHoc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKhoiKhoaHoc1ActionPerformed
        btnDelete();        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaKhoiKhoaHoc1ActionPerformed

    private void btnTimKiemHVMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemHVMouseEntered
        // TODO add your handling code here:
        btnTimKiemHV.setBackground(Color.pink);
    }//GEN-LAST:event_btnTimKiemHVMouseEntered

    private void btnTimKiemHVMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemHVMouseExited
        // TODO add your handling code here:
        btnTimKiemHV.setBackground(new Color(153, 153, 255));
    }//GEN-LAST:event_btnTimKiemHVMouseExited

    private void btnTimKiemHVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemHVActionPerformed
        // TODO add your handling code here:
        if(txtTimKiemHV.getText().equals("")){
            JOptionPane.showMessageDialog(this,"Chưa nhập mã học viên cần tìm kiếm");
            return;
        }
        int i = 0;
        boolean kiem1 = false;
        for (O_HocVien hv : listHV) {
            if (hv.getMaHV() == Integer.parseInt(txtTimKiemHV.getText())) {
                tblHocVien.setRowSelectionInterval(i, i);
                JOptionPane.showMessageDialog(this, "Đã tìm thấy");
                kiem1 = true;
            }
            i++;
        }
        if (kiem1 == false) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy học viên trong mã này");
        }
    }//GEN-LAST:event_btnTimKiemHVActionPerformed

    private void cboDieuKienLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDieuKienLocActionPerformed
        // TODO add your handling code here:
        O_DangNhap dn = listDangNhap.get(0);
        if (cboKhoaHoc.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Khóa học chưa chọn");
            return;
        }
        String name = (String) cboDieuKienLoc.getSelectedItem();
        if (name == null) {
            return;
        }
        if (name.equalsIgnoreCase("Tăng")) {
            Collections.sort(listHV, (sv1, sv2) -> (int) (sv1.getDiem() - sv2.getDiem()));
            //        Collections.reverse(list);
            DuyetHocVien();
        } else {
            Collections.sort(listHV, (sv1, sv2) -> (int) (sv1.getDiem() - sv2.getDiem()));
            Collections.reverse(listHV);
            DuyetHocVien();
        }
    }//GEN-LAST:event_cboDieuKienLocActionPerformed

    private void txtTimKiemHVCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemHVCaretUpdate
        // TODO add your handling code here:
        try {
            Integer.parseInt(txtTimKiemHV.getText());
            loadDataToArrayBangHocVien();
            DuyetHocVien();
            return;
        } catch (Exception e) {
            
        }
        String name = txtTimKiemHV.getText();
        if (name == null) {
            return;
        }
        tblmodel.setRowCount(0);
        for (O_HocVien hv : listHV) {
            if (hv.getMaNH().startsWith(name)) {
                Object[] tbl = new Object[]{hv.getMaHV(), hv.getMaNH(), hv.getHoVaTen(), hv.getDiem()};
                tblmodel.addRow(tbl);
            }
        }
    }//GEN-LAST:event_txtTimKiemHVCaretUpdate


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tuandhpc05076.Swing.Button btnCapNhatDiem;
    private com.tuandhpc05076.Swing.Button btnInThanhExel;
    private com.tuandhpc05076.Swing.Button btnTimKiem;
    private com.tuandhpc05076.Swing.Button btnTimKiemHV;
    private com.tuandhpc05076.Swing.Button btnXoaKhoiKhoaHoc1;
    private com.tuandhpc05076.Swing.Button button10;
    private com.tuandhpc05076.Swing.Combobox cboChuyenDe;
    private com.tuandhpc05076.Swing.Combobox cboDieuKienLoc;
    private com.tuandhpc05076.Swing.Combobox cboKhoaHoc;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblHocVien;
    private javax.swing.JTable tblNguoiHoc;
    private com.tuandhpc05076.Swing.TextField1 txtTimKiem;
    private com.tuandhpc05076.Swing.TextField1 txtTimKiemHV;
    // End of variables declaration//GEN-END:variables
}
