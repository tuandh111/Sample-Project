/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandhpc05076.Form;

import static com.tuandhpc05076.Form.NhanVien.list;
import com.tuandhpc05076.Object.O_ChuyenDe;
import com.tuandhpc05076.Object.O_DangNhap;
import com.tuandhpc05076.Object.O_KhoaHoc;
import com.tuandhpc05076.swing0.Form;
import java.awt.Color;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class KhoaHoc extends javax.swing.JPanel {

    public static ArrayList<O_KhoaHoc> listKH = new ArrayList<>();
    String userName = "sa";
    String password = "123";
    String url = "jdbc:sqlserver://localhost:1433; databaseName= EduSys;encrypt=false";
    ChuyenDe cd = new ChuyenDe();
    ArrayList<O_ChuyenDe> listLayCD = cd.listCD;
    DefaultTableModel tblModel = new DefaultTableModel();
    String Macd = "";
//
    O_DangNhap tk = new O_DangNhap();
    ArrayList<O_DangNhap> listDangNhap = tk.getAlllist();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public KhoaHoc() {
        initComponents();
        jTabbedPane1.setBackground(KhoaHoc.this.getBackground());
        jPanel1.setBackground(KhoaHoc.this.getBackground());
        jPanel2.setBackground(KhoaHoc.this.getBackground());

        Date now = new Date();
        String Ngay = sdf.format(now);
        txtNgayTao.setText(Ngay);

        loadChuyenDe();
        tieuDe();
    }

    public void loadChuyenDe() {
        DefaultComboBoxModel tbl = new DefaultComboBoxModel();
        for (O_ChuyenDe layCD : listLayCD) {
            tbl.addElement(layCD.getTenCD().trim());

        }
        cboChuyenDe.setModel(tbl);
        cboChuyenDe.setSelectedIndex(-1);

    }

    public void tieuDe() {
        String[] tbl = new String[]{"Mã KH", "Thời lượng", "Học phí", "Ngày Khai giảng", "Tạo bởi", "Ngày tạo"};
        tblModel.setColumnIdentifiers(tbl);
        tblUser.setModel(tblModel);
    }

    public void btnTim() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            String sql = "SELECT MaKH,ChuyenDe.MaCD,ChuyenDe.HocPhi,ChuyenDe.ThoiLuong,NgayKG,GhiChu,MaNV,NgayTao FROM dbo.KhoaHoc "
                    + "INNER JOIN dbo.ChuyenDe ON ChuyenDe.MaCD = KhoaHoc.MaCD WHERE TenCD like ?";
            PreparedStatement st = con.prepareStatement(sql);
            String name = (String) cboChuyenDe.getSelectedItem();
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            listKH.clear();
            while (rs.next()) {

                int MaKH = rs.getInt(1);
                String MaCd = rs.getString(2);
                float HocPhi = rs.getFloat(3);
                int SoLuong = rs.getInt(4);

                String NgayKhaiGiang = rs.getString(5);
                String GhiChu = rs.getString(6);
                String ThemBoi = rs.getString(7);
                String NgayTao = rs.getString(8);

                listKH.add(new O_KhoaHoc(MaKH, MaCd, HocPhi, SoLuong, NgayKhaiGiang, GhiChu, ThemBoi, NgayTao));
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Duyet() {
        tblModel.setRowCount(0);
        for (O_KhoaHoc nv : listKH) {
            Object[] tbl = new Object[]{nv.getMaKH(), nv.getThoiLuong(), nv.getHocPhi(), nv.getNgayKhaiGiang(), nv.getThemBoi(), nv.getNgayTao()};
            tblModel.addRow(tbl);
        }
    }

    public boolean checkForm() {
        if (cboChuyenDe.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn chuyên đề");
            return false;
        }
        if (txtNgayKhaiGiang.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ngày khai giảng của bạn chưa nhập");
            return false;
        }
        try {
            Date Ngay = sdf.parse(txtNgayKhaiGiang.getText());
            Date now = new Date();
            if (Ngay.compareTo(now) < 0) {
                JOptionPane.showMessageDialog(this, "Ngày khai giảng phải sau ngày hiện tại");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày khai giảng không đúng định dạng\nyyyy-MM-dd VD:2022-01-22");
            return false;
        }
        if (txtLopHoc.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Lớp học của bạn đã để trống");
            return false;
        }
             boolean kiemMoTa= false;
        for (O_KhoaHoc kh : listKH) {
            if(kh.getGhiChu().trim().equalsIgnoreCase(txtLopHoc.getText())){
                kiemMoTa=true;
            }
        }
        if(kiemMoTa==true){
            JOptionPane.showMessageDialog(this, "Lớp học bạn đã tồn tại trong danh sách");
            return false;
        }
        return true;
    }

    public void btnNew() {
        txtNgayKhaiGiang.setText("");
        txtLopHoc.setText("");
    }

    public void btnInDanhSach() {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();
            String name = (String) cboChuyenDe.getSelectedItem();
            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet(name);

                Row rowCol = sheet.createRow(0);
                for (int i = 0; i < tblUser.getColumnCount(); i++) {
                    org.apache.poi.ss.usermodel.Cell cell = rowCol.createCell(i);
                    cell.setCellValue(tblUser.getColumnName(i));
                }

                for (int j = 0; j < tblUser.getRowCount(); j++) {
                    Row row = sheet.createRow(j + 1);
                    for (int k = 0; k < tblUser.getColumnCount(); k++) {
                        org.apache.poi.ss.usermodel.Cell cell = row.createCell(k);
                        if (tblUser.getValueAt(j, k) != null) {
                            cell.setCellValue(tblUser.getValueAt(j, k).toString());
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

    public void btnSave() {
        if (checkForm() == false) {
            return;
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            String sqla = "insert into dbo.KhoaHoc values(?,?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sqla);
            String name = (String) cboChuyenDe.getSelectedItem();
            String ma = "";
            System.out.println(listLayCD.size());
            for (O_ChuyenDe cd : listLayCD) {

                if (cd.getTenCD().trim().equals(name.trim())) {

                    ma = cd.getMaCD();
                    break;
                }

            }
            st.setString(1, ma);
            st.setFloat(2, Float.parseFloat(txtHocPhi.getText()));
            st.setInt(3, Integer.parseInt(txtThoiLuongGio.getText()));
            st.setString(4, txtNgayKhaiGiang.getText());
            st.setString(5, txtLopHoc.getText());
            st.setString(6, txtThemBoi.getText());
            Date now = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String Ngay = sdf.format(now);
            st.setString(7, Ngay);
            st.executeUpdate();

            con.close();
            st.close();

            btnTim();
            Duyet();
            JOptionPane.showMessageDialog(this, "Thêm khóa học thành công");
            btnNew();
        } catch (Exception e) {
            e.printStackTrace();
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
        int chon = tblUser.getSelectedRow();

        if (chon < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chuyên đề trong danh sách để có thể xóa");
            return;
        }
        int name = (int) tblModel.getValueAt(chon, 0);

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            String sqla = "DELETE FROM dbo.KhoaHoc WHERE MaKH=?";
            PreparedStatement st = con.prepareStatement(sqla);

            st.setInt(1, name);
            System.out.println(name);
            st.executeUpdate();
            con.close();
            btnTim();
            Duyet();
            JOptionPane.showMessageDialog(this, "Xóa khóa học thành công");
            btnNew();
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnUpdate() {
        int chon = tblUser.getSelectedRow();

        if (chon < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chuyên đề trong danh sách để có thể cập nhật");
            return;
        }
        int name = (int) tblModel.getValueAt(chon, 0);
        System.out.println(name);

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            String sqla = "UPDATE dbo.KhoaHoc SET NgayKG=? , NgayTao=?,GhiChu=? WHERE MaKH=?";
            PreparedStatement st = con.prepareStatement(sqla);
            st.setString(1, txtNgayKhaiGiang.getText());
            st.setString(2, txtNgayTao.getText());
            st.setString(3, txtLopHoc.getText());
            st.setInt(4, name);

            st.executeUpdate();
            con.close();
            btnTim();
            Duyet();
            JOptionPane.showMessageDialog(this, "Cập nhật khóa học thành công");
            
            btnNew();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtTenChuyenDe = new com.tuandhpc05076.Swing.TextField();
        txtHocPhi = new com.tuandhpc05076.Swing.TextField();
        txtThemBoi = new com.tuandhpc05076.Swing.TextField();
        button1 = new com.tuandhpc05076.Swing.Button();
        button2 = new com.tuandhpc05076.Swing.Button();
        button3 = new com.tuandhpc05076.Swing.Button();
        button4 = new com.tuandhpc05076.Swing.Button();
        button5 = new com.tuandhpc05076.Swing.Button();
        button6 = new com.tuandhpc05076.Swing.Button();
        button7 = new com.tuandhpc05076.Swing.Button();
        button8 = new com.tuandhpc05076.Swing.Button();
        txtNgayKhaiGiang = new com.tuandhpc05076.Swing.TextField();
        txtThoiLuongGio = new com.tuandhpc05076.Swing.TextField();
        txtNgayTao = new com.tuandhpc05076.Swing.TextField();
        btnMoi = new com.tuandhpc05076.Swing.Button();
        btnXoa = new com.tuandhpc05076.Swing.Button();
        btnSua = new com.tuandhpc05076.Swing.Button();
        btnThem = new com.tuandhpc05076.Swing.Button();
        txtLopHoc = new com.tuandhpc05076.Swing.TextField();
        txtGhiChu1 = new com.tuandhpc05076.Swing.TextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        txtTimKiem = new com.tuandhpc05076.Swing.TextField1();
        btnTimKiem = new com.tuandhpc05076.Swing.Button();
        btnInThanhExel = new com.tuandhpc05076.Swing.Button();
        cboDieuKienLoc = new com.tuandhpc05076.Swing.Combobox();
        cboChuyenDe = new com.tuandhpc05076.Swing.Combobox();

        setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtTenChuyenDe.setBackground(new java.awt.Color(255, 255, 255));
        txtTenChuyenDe.setLabelText("Chuyên đề");

        txtHocPhi.setBackground(new java.awt.Color(255, 255, 255));
        txtHocPhi.setLabelText("Học phí");

        txtThemBoi.setBackground(new java.awt.Color(255, 255, 255));
        txtThemBoi.setLabelText("Người tạo");

        button1.setText("button1");

        button2.setText("button1");

        button3.setText("button1");

        button4.setText("button1");

        button5.setText("button1");

        button6.setText("button1");

        button7.setText("button1");

        button8.setText("button1");

        txtNgayKhaiGiang.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayKhaiGiang.setLabelText("Khai giảng");

        txtThoiLuongGio.setBackground(new java.awt.Color(255, 255, 255));
        txtThoiLuongGio.setLabelText("Thời lương (Giờ)");

        txtNgayTao.setBackground(new java.awt.Color(255, 255, 255));
        txtNgayTao.setLabelText("Ngày tạo");

        btnMoi.setBackground(new java.awt.Color(153, 153, 255));
        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/edit.png"))); // NOI18N
        btnMoi.setText("Mới");
        btnMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMoiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMoiMouseExited(evt);
            }
        });
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });

        btnXoa.setBackground(new java.awt.Color(153, 153, 255));
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/delete.png"))); // NOI18N
        btnXoa.setText("Xóa");
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnXoaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnXoaMouseExited(evt);
            }
        });
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnSua.setBackground(new java.awt.Color(153, 153, 255));
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/update.png"))); // NOI18N
        btnSua.setText("Sửa");
        btnSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSuaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSuaMouseExited(evt);
            }
        });
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(153, 153, 255));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/plus.png"))); // NOI18N
        btnThem.setText("Thêm");
        btnThem.setAutoscrolls(true);
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnThemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThemMouseExited(evt);
            }
        });
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        txtLopHoc.setBackground(new java.awt.Color(255, 255, 255));
        txtLopHoc.setLabelText("Lớp học");

        txtGhiChu1.setBackground(new java.awt.Color(255, 255, 255));
        txtGhiChu1.setLabelText("Ghi chú");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 315, Short.MAX_VALUE)
                                .addComponent(button8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(button7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtThemBoi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
                            .addComponent(txtHocPhi, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtTenChuyenDe, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtLopHoc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtGhiChu1, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayKhaiGiang, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtThoiLuongGio, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(90, 90, 90)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayKhaiGiang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtThoiLuongGio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThemBoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNgayTao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLopHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtGhiChu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(80, 80, 80)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(495, 495, 495)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(button1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(button6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(button8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Thông tin", jPanel1);

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
        tblUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUserMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblUserMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblUser);

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

        cboDieuKienLoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tất cả", "Spring", "Summer", "Fall" }));
        cboDieuKienLoc.setSelectedIndex(-1);
        cboDieuKienLoc.setLabeText("Chọn điều kiện lọc");
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
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnInThanhExel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(cboDieuKienLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboDieuKienLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnInThanhExel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh sách", jPanel2);

        cboChuyenDe.setLabeText("Chuyên đề");
        cboChuyenDe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cboChuyenDeMouseClicked(evt);
            }
        });
        cboChuyenDe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboChuyenDeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(cboChuyenDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        btnSave();        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemActionPerformed

    private void cboChuyenDeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboChuyenDeActionPerformed
        O_DangNhap dn = listDangNhap.get(0);
        String name = (String) cboChuyenDe.getSelectedItem();
        if (name == null) {
            return;
        }
        btnTim();
        Duyet();
        for (O_ChuyenDe cd : listLayCD) {
            if (cd.getTenCD().trim().equalsIgnoreCase(name.trim())) {

                txtTenChuyenDe.setText(cd.getTenCD());
                txtTenChuyenDe.setEditable(false);
//                    txtNgayKhaiGiang.setText(kh.getNgayKhaiGiang());
                txtHocPhi.setText(String.valueOf(cd.getHocPhi()));
                txtHocPhi.setEditable(false);
                txtThoiLuongGio.setText(String.valueOf(cd.getThoiLuong()));
                txtThoiLuongGio.setEditable(false);
                Macd = cd.getMaCD();

                txtThemBoi.setText(dn.getMaNV());
                txtThemBoi.setEditable(false);
//                    txtNgayTao.setText(kh.getNgayTao());
//                    txtNgayTao.setEditable(false);
                txtGhiChu1.setText(cd.getMoTa());
                txtGhiChu1.setEditable(false);
                txtNgayTao.setEditable(false);
                break;

            }

        }

        // TODO add your handling code here:
    }//GEN-LAST:event_cboChuyenDeActionPerformed

    private void cboChuyenDeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cboChuyenDeMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_cboChuyenDeMouseClicked

    private void tblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblUserMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        btnDelete();        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        btnUpdate();        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        btnNew();        // TODO add your handling code here:
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnThemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseExited
        // TODO add your handling code here:
        btnThem.setBackground(new Color(153, 153, 255));
    }//GEN-LAST:event_btnThemMouseExited

    private void btnThemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseEntered
        btnThem.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btnThemMouseEntered

    private void btnSuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseEntered
        btnSua.setBackground(Color.pink);         // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaMouseEntered

    private void btnSuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseExited
        btnSua.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaMouseExited

    private void btnXoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseEntered
        // TODO add your handling code here:
        btnXoa.setBackground(Color.pink);
    }//GEN-LAST:event_btnXoaMouseEntered

    private void btnXoaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseExited
        btnXoa.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaMouseExited

    private void btnMoiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseEntered
        btnMoi.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btnMoiMouseEntered

    private void btnMoiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseExited
        btnMoi.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnMoiMouseExited

    private void btnTimKiemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemMouseEntered
        btnTimKiem.setBackground(Color.pink);        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemMouseEntered

    private void btnTimKiemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemMouseExited
        btnTimKiem.setBackground(new Color(153, 153, 255));       // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemMouseExited

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        // TODO add your handling code here:
        if (cboChuyenDe.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Chưa chọn chuyên đề để có thể tìm kiếm");
            return;
        }
        if (txtTimKiem.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã cần tìm kiếm");
            return;
        }
        int i=0;
        boolean kiem = false;
        for (O_KhoaHoc kh : listKH) {
            if (Integer.parseInt(txtTimKiem.getText()) == kh.getMaKH()) {
                txtNgayKhaiGiang.setText(kh.getNgayKhaiGiang());
                txtNgayTao.setText(kh.getNgayTao());
                txtLopHoc.setText(kh.getGhiChu());
                JOptionPane.showMessageDialog(this, "Đã tìm thấy");
                kiem = true;
                jTabbedPane1.setSelectedIndex(0);
                tblUser.setRowSelectionInterval(i, i);
            }
            i++;
        }
        if (kiem == false) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khóa học trong mã chuyên này");
        }

    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnInThanhExelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInThanhExelActionPerformed
        btnInDanhSach();        // TODO add your handling code here:
    }//GEN-LAST:event_btnInThanhExelActionPerformed

    private void btnInThanhExelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInThanhExelMouseExited
        btnInThanhExel.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnInThanhExelMouseExited

    private void btnInThanhExelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInThanhExelMouseEntered
        btnInThanhExel.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btnInThanhExelMouseEntered

    private void tblUserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMousePressed
        int chon = tblUser.getSelectedRow();
        int name = (int) tblModel.getValueAt(chon, 0);
        if (evt.getClickCount() == 2) {
            for (O_KhoaHoc kh : listKH) {
                if (kh.getMaKH() == name) {
                    txtNgayKhaiGiang.setText(kh.getNgayKhaiGiang());
                    txtNgayTao.setText(kh.getNgayTao());
                    txtLopHoc.setText(kh.getGhiChu());
                }
            }
            jTabbedPane1.setSelectedIndex(0);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tblUserMousePressed

    private void cboDieuKienLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDieuKienLocActionPerformed
        // TODO add your handling code here:
        O_DangNhap dn = listDangNhap.get(0);
        String name = (String) cboDieuKienLoc.getSelectedItem();
        if (name == null) {
            return;
        }
        btnTim();
        tblModel.setRowCount(0);
        if (name.equalsIgnoreCase("Spring")) {
            for (O_KhoaHoc nv : listKH) {
                String ngay = nv.getNgayKhaiGiang();
                String[] ngay1 = ngay.split("-");
                if (Integer.parseInt(ngay1[1]) < 5) {
                    Object[] tbl = new Object[]{nv.getMaKH(), nv.getThoiLuong(), nv.getHocPhi(), nv.getNgayKhaiGiang(), nv.getThemBoi(), nv.getNgayTao()};
                    tblModel.addRow(tbl);
                }
            }
        } else if (name.equalsIgnoreCase("Summer")) {
            for (O_KhoaHoc nv : listKH) {
                String ngay = nv.getNgayKhaiGiang();
                String[] ngay1 = ngay.split("-");
                if (Integer.parseInt(ngay1[1]) < 9&&Integer.parseInt(ngay1[1]) >4) {
                    Object[] tbl = new Object[]{nv.getMaKH(), nv.getThoiLuong(), nv.getHocPhi(), nv.getNgayKhaiGiang(), nv.getThemBoi(), nv.getNgayTao()};
                    tblModel.addRow(tbl);
                }
            }
        } else if(name.equalsIgnoreCase("Fall")) {
            for (O_KhoaHoc nv : listKH) {
                String ngay = nv.getNgayKhaiGiang();
                String[] ngay1 = ngay.split("-");
                if (Integer.parseInt(ngay1[1]) < 13&&Integer.parseInt(ngay1[1]) >=9) {
                    Object[] tbl = new Object[]{nv.getMaKH(), nv.getThoiLuong(), nv.getHocPhi(), nv.getNgayKhaiGiang(), nv.getThemBoi(), nv.getNgayTao()};
                    tblModel.addRow(tbl);
                }
            }
        } else if (name.equalsIgnoreCase("Tất cả")) {
            Duyet();
        }

    }//GEN-LAST:event_cboDieuKienLocActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tuandhpc05076.Swing.Button btnInThanhExel;
    private com.tuandhpc05076.Swing.Button btnMoi;
    private com.tuandhpc05076.Swing.Button btnSua;
    private com.tuandhpc05076.Swing.Button btnThem;
    private com.tuandhpc05076.Swing.Button btnTimKiem;
    private com.tuandhpc05076.Swing.Button btnXoa;
    private com.tuandhpc05076.Swing.Button button1;
    private com.tuandhpc05076.Swing.Button button2;
    private com.tuandhpc05076.Swing.Button button3;
    private com.tuandhpc05076.Swing.Button button4;
    private com.tuandhpc05076.Swing.Button button5;
    private com.tuandhpc05076.Swing.Button button6;
    private com.tuandhpc05076.Swing.Button button7;
    private com.tuandhpc05076.Swing.Button button8;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.tuandhpc05076.Swing.Combobox cboChuyenDe;
    private com.tuandhpc05076.Swing.Combobox cboDieuKienLoc;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblUser;
    private com.tuandhpc05076.Swing.TextField txtGhiChu1;
    private com.tuandhpc05076.Swing.TextField txtHocPhi;
    private com.tuandhpc05076.Swing.TextField txtLopHoc;
    private com.tuandhpc05076.Swing.TextField txtNgayKhaiGiang;
    private com.tuandhpc05076.Swing.TextField txtNgayTao;
    private com.tuandhpc05076.Swing.TextField txtTenChuyenDe;
    private com.tuandhpc05076.Swing.TextField txtThemBoi;
    private com.tuandhpc05076.Swing.TextField txtThoiLuongGio;
    private com.tuandhpc05076.Swing.TextField1 txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
