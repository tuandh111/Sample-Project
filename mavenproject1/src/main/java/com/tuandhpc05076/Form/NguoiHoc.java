/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandhpc05076.Form;

import static com.tuandhpc05076.Form.NhanVien.list;
import com.tuandhpc05076.Object.O_DangNhap;
import com.tuandhpc05076.Object.O_NguoiHoc;
import com.tuandhpc05076.Object.O_NhanVien;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author DELL E5470
 */
public class NguoiHoc extends javax.swing.JPanel {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static ArrayList<O_NguoiHoc> listNH = new ArrayList<>();
    DefaultTableModel tblmodel;
    String userName = "sa";
    String password = "123";
    String url = "jdbc:sqlserver://localhost:1433; databaseName= EduSys;encrypt=false";
    //đăng nhập
    O_DangNhap tk = new O_DangNhap();
    ArrayList<O_DangNhap> listDangNhap = tk.getAlllist();
    int i = -1;

    public NguoiHoc() {
        initComponents();
        jTabbedPane1.setBackground(NguoiHoc.this.getBackground());
        jPanel1.setBackground(NguoiHoc.this.getBackground());
        jPanel2.setBackground(NguoiHoc.this.getBackground());
        TieuDe();
        loadDataToArray();
        Duyet();
        txtMaNguoiHoc.setEditable(false);
        tblUser.getColumnModel().getColumn(0).setPreferredWidth(10);
        tblUser.getColumnModel().getColumn(1).setPreferredWidth(100);
        tblUser.getColumnModel().getColumn(2).setPreferredWidth(5);
        tblUser.getColumnModel().getColumn(3).setPreferredWidth(20);
        tblUser.getColumnModel().getColumn(5).setPreferredWidth(100);
        tblUser.getColumnModel().getColumn(7).setPreferredWidth(10);
        tblUser.getColumnModel().getColumn(8).setPreferredWidth(10);
    }

    public void tuDongTangMa() {
        String name = "";
        int i = 0;
        O_NguoiHoc nh = listNH.get(listNH.size() - 1);

        name = nh.getMaNH().trim();
        String[] tbl = name.split("H");
        String so = String.valueOf(Integer.parseInt(tbl[1]) + 1);
        String ten = "NH";
        for (int j = 0; j <= 4 - so.length(); j++) {
            ten += "0";
        }
        ten = ten + so;
        txtMaNguoiHoc.setText(ten);

    }

    public void TieuDe() {
        tblmodel = new DefaultTableModel();
        String[] tbl = new String[]{"Mã NH", "Họ và tên", "Giới tính", "Ngày sinh", "Điện thoại", "Email", "Ghi chú", "Ma NV", "Ngày ĐK"};
        tblmodel.setColumnIdentifiers(tbl);
        tblUser.setModel(tblmodel);
    }

    public void loadDataToArray() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            java.sql.Statement st = con.createStatement();
            String sql = "SELECT * FROM NguoiHoc";
            ResultSet rs = st.executeQuery(sql);
            listNH.clear();
            while (rs.next()) {

                String MaNH = rs.getString(1);
                String HoVaTen = rs.getString(2);
                boolean GioiTinh = rs.getBoolean(3);
                String NgaySinh = rs.getString(4);
                String DienThoai = rs.getString(5);
                String Email = rs.getString(6);

                String GhiChu = rs.getString(7);
                String MaNV = rs.getString(8);
                String NgayDK = rs.getString(9);
                listNH.add(new O_NguoiHoc(MaNH, HoVaTen, GioiTinh, NgaySinh, DienThoai, Email, GhiChu, MaNV, NgayDK));
            }
            tuDongTangMa();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Duyet() {
        tblmodel.setRowCount(0);
        for (O_NguoiHoc nh : listNH) {
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

    public void btnNew() {
        txtMaNguoiHoc.setText("");
        txtHoVaTen.setText("");
        txtNgaySinh.setText("");
        txtDienThoai.setText("");
        txtEmail.setText("");
        txtGhiChu.setText("");
        tuDongTangMa();
    }

    public void showNguoiHoc() {
        O_NguoiHoc nh = listNH.get(i);
        txtMaNguoiHoc.setText(nh.getMaNH());
        txtHoVaTen.setText(nh.getHoTen());
        if (nh.isGioiTinh()) {
            rdoNam.setSelected(true);
        } else {
            rdoNu.setSelected(true);
        }
        txtNgaySinh.setText(nh.getNgaySinh());
        txtDienThoai.setText(nh.getDienThoai());
        txtEmail.setText(nh.getEmail());
        txtGhiChu.setText(nh.getGhiChu());
        tblUser.setRowSelectionInterval(i, i);
    }

    String emailvd = "\\w+@\\w+(\\.\\w+){1,2}";
    String manv = "(NH)\\d{5}";
    String so = "0\\d{9,10}";
    String regex = "\\d{2}/\\d{2}/\\d{4}";
    String hoTen = "^[a-zA-Z ]+$";

    public boolean checkForm() {
        if (txtMaNguoiHoc.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mã người học của bạn đã để trống");
            return false;
        }
        boolean kiem = false;
        for (O_NguoiHoc nh : listNH) {
            if (nh.getMaNH().trim().equals(txtMaNguoiHoc.getText())) {
                kiem = true;
            }
        }
        if (kiem == true) {
            JOptionPane.showMessageDialog(this, "Mã người hocj bạn đã tồn tại trong danh sách");
            return false;
        }
        if (!txtMaNguoiHoc.getText().matches(manv)) {
            JOptionPane.showMessageDialog(this, "Mã người học không đúng định dạng\nVD:NH00001");
            return false;
        }
        if (txtHoVaTen.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Họ tên của bạn đã để trống");
            return false;
        }
//        if (!txtHoVaTen.getText().matches(hoTen)) {
//            JOptionPane.showMessageDialog(this, "Họ tên không đúng định dạng");
//            return false;
//        }
        if (buttonGroup1.getSelection() == null) {
            JOptionPane.showMessageDialog(this, "Giới tính của bạn chưa chọn");
            return false;
        }
        if (txtNgaySinh.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ngày sinh của bạn đã để trống");
            return false;
        }
        try {
            Date Ngay = sdf.parse(txtNgaySinh.getText());

            int year = Ngay.getYear() + 1900; // lấy năm dưới dạng số nguyên
            System.out.println(year); // in ra năm

            Date now = new Date();
            int yearHienTai = now.getYear() + 1900;
            if (yearHienTai - year < 16) {
                JOptionPane.showMessageDialog(this, "Bạn phải nhập tuổi lớn hơn 16 tuổi");
                return false;
            }
//            if (Ngay.compareTo(now) > 0) {
//                JOptionPane.showMessageDialog(this, "Ngày sinh phải trước ngày hiện tại");
//                return false;
//            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không đúng định dạng\nyyyy-MM-dd VD:2022-01-22");
            return false;
        }
        if (txtDienThoai.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Số điện thoại của bạn đã để trống");
            return false;
        }
        if (!txtDienThoai.getText().matches(so)) {
            JOptionPane.showMessageDialog(this, "Số điện thoại của bạn không đúng định dạng");
            return false;
        }
        if (txtEmail.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Email của bạn đã bỏ trống");
            return false;
        }
        if (!txtEmail.getText().matches(emailvd)) {
            JOptionPane.showMessageDialog(this, "Email của bạn không đúng định dạng");
            return false;
        }
        if (txtGhiChu.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Ghi chú của bạn đã để trống");
            return false;
        }
        return true;
    }

    public void btnSave() {
        if (checkForm() == false) {
            return;
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            String sqla = "insert into dbo.NguoiHoc values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sqla);
            st.setString(1, txtMaNguoiHoc.getText());
            st.setString(2, txtHoVaTen.getText());
            if (rdoNam.isSelected()) {
                st.setBoolean(3, true);
            } else {
                st.setBoolean(3, false);
            }

            st.setString(4, txtNgaySinh.getText());
            st.setString(5, txtDienThoai.getText());
            st.setString(6, txtEmail.getText());
            st.setString(7, txtGhiChu.getText());
            O_DangNhap dn = listDangNhap.get(0);
            st.setString(8, dn.getMaNV());
            Date now = new Date();
            String ngayHienTai = sdf.format(now);
            st.setString(9, ngayHienTai);

            st.executeUpdate();
            con.close();
            loadDataToArray();
            Duyet();
            JOptionPane.showMessageDialog(this, "Thêm người học thành công");
            btnNew();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnTimKiem() {
        if (txtTimKiem.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nhập mã cần tìm kiếm");
            return;
        }
        int i = 0;
        boolean kiem = false;
        for (O_NguoiHoc nh : listNH) {
            if (nh.getMaNH().trim().equals(txtTimKiem.getText())) {
                txtMaNguoiHoc.setText(nh.getMaNH());
                txtHoVaTen.setText(nh.getHoTen());
                if (nh.isGioiTinh()) {
                    rdoNam.setSelected(true);
                } else {
                    rdoNu.setSelected(true);
                }
                txtNgaySinh.setText(nh.getNgaySinh());
                txtDienThoai.setText(nh.getDienThoai());
                txtEmail.setText(nh.getEmail());
                txtGhiChu.setText(nh.getGhiChu());
                tblUser.setRowSelectionInterval(i, i);
                JOptionPane.showMessageDialog(this, "Đã tìm thấy");
                kiem = true;
                jTabbedPane1.setSelectedIndex(0);
            }
            i++;
        }
        if (kiem == false) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy người học trong mã này");
        }
    }

    public void btnUpdate() {
        int chon = tblUser.getSelectedRow();
        if (chon < 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn người học để có thể cập nhật");
            return;
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            String sqla = "UPDATE dbo.NguoiHoc SET HoTen=?, GioiTinh=?,NgaySinh=?,DienThoai=?,Email=?,GhiChu=?,MaNV=?,NgayDK=? WHERE MaNH=?";
            PreparedStatement st = con.prepareStatement(sqla);
            st.setString(9, txtMaNguoiHoc.getText());
            st.setString(1, txtHoVaTen.getText());
            if (rdoNam.isSelected()) {
                st.setBoolean(2, true);
            } else {
                st.setBoolean(2, false);
            }

            st.setString(3, txtNgaySinh.getText());
            st.setString(4, txtDienThoai.getText());
            st.setString(5, txtEmail.getText());
            st.setString(6, txtGhiChu.getText());
            O_DangNhap dn = listDangNhap.get(0);
            st.setString(7, dn.getMaNV());
            Date now = new Date();
            String ngayHienTai = sdf.format(now);
            st.setString(8, ngayHienTai);

            st.executeUpdate();
            con.close();
            loadDataToArray();
            Duyet();
            JOptionPane.showMessageDialog(this, "Cập nhật người học thành công");
            btnNew();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnDelete() {
        int chon = tblUser.getSelectedRow();
        if (chon < 0) {
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn người học để có thể xóa");
            return;
        }
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
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            String sqla = "DELETE FROM dbo.NguoiHoc WHERE MaNH =?";
            PreparedStatement st = con.prepareStatement(sqla);
            st.setString(1, txtMaNguoiHoc.getText());

            st.executeUpdate();
            con.close();
            loadDataToArray();
            Duyet();
            JOptionPane.showMessageDialog(this, "Xóa người học thành công");
            btnNew();
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
                Sheet sheet = wb.createSheet("Người học");

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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        txtMaNguoiHoc = new com.tuandhpc05076.Swing.TextField();
        txtHoVaTen = new com.tuandhpc05076.Swing.TextField();
        txtDienThoai = new com.tuandhpc05076.Swing.TextField();
        txtNgaySinh = new com.tuandhpc05076.Swing.TextField();
        textAreaScroll1 = new textarea.TextAreaScroll();
        txtGhiChu = new com.tuandhpc05076.Swing.TextArea();
        btnMoi = new com.tuandhpc05076.Swing.Button();
        btntThem = new com.tuandhpc05076.Swing.Button();
        btnSua = new com.tuandhpc05076.Swing.Button();
        btnXoa = new com.tuandhpc05076.Swing.Button();
        btnToi = new com.tuandhpc05076.Swing.Button();
        btnCuoi = new com.tuandhpc05076.Swing.Button();
        btnLui = new com.tuandhpc05076.Swing.Button();
        btnDau = new com.tuandhpc05076.Swing.Button();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        txtEmail = new com.tuandhpc05076.Swing.TextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        textArea2 = new com.tuandhpc05076.Swing.TextArea();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        txtTimKiem = new com.tuandhpc05076.Swing.TextField1();
        btnTimKiem = new com.tuandhpc05076.Swing.Button();
        btnInThanhExel = new com.tuandhpc05076.Swing.Button();
        cboVaiTro = new com.tuandhpc05076.Swing.Combobox();

        setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtMaNguoiHoc.setBackground(new java.awt.Color(255, 255, 255));
        txtMaNguoiHoc.setLabelText("Mã người học");

        txtHoVaTen.setBackground(new java.awt.Color(255, 255, 255));
        txtHoVaTen.setLabelText("Họ và tên");

        txtDienThoai.setBackground(new java.awt.Color(255, 255, 255));
        txtDienThoai.setLabelText("Điện thoại");

        txtNgaySinh.setBackground(new java.awt.Color(255, 255, 255));
        txtNgaySinh.setLabelText("Ngày sinh");

        textAreaScroll1.setLabelText("Ghi chú");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        textAreaScroll1.setViewportView(txtGhiChu);

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

        btntThem.setBackground(new java.awt.Color(153, 153, 255));
        btntThem.setForeground(new java.awt.Color(255, 255, 255));
        btntThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/plus.png"))); // NOI18N
        btntThem.setText("Thêm");
        btntThem.setMaximumSize(new java.awt.Dimension(72, 36));
        btntThem.setMinimumSize(new java.awt.Dimension(72, 36));
        btntThem.setPreferredSize(new java.awt.Dimension(72, 36));
        btntThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btntThemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btntThemMouseExited(evt);
            }
        });
        btntThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntThemActionPerformed(evt);
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

        btnToi.setBackground(new java.awt.Color(153, 153, 255));
        btnToi.setForeground(new java.awt.Color(255, 255, 255));
        btnToi.setText(">>");
        btnToi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnToiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnToiMouseExited(evt);
            }
        });
        btnToi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToiActionPerformed(evt);
            }
        });

        btnCuoi.setBackground(new java.awt.Color(153, 153, 255));
        btnCuoi.setForeground(new java.awt.Color(255, 255, 255));
        btnCuoi.setText(">|");
        btnCuoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCuoiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCuoiMouseExited(evt);
            }
        });
        btnCuoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCuoiActionPerformed(evt);
            }
        });

        btnLui.setBackground(new java.awt.Color(153, 153, 255));
        btnLui.setForeground(new java.awt.Color(255, 255, 255));
        btnLui.setText("<<");
        btnLui.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLuiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLuiMouseExited(evt);
            }
        });
        btnLui.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLuiActionPerformed(evt);
            }
        });

        btnDau.setBackground(new java.awt.Color(153, 153, 255));
        btnDau.setForeground(new java.awt.Color(255, 255, 255));
        btnDau.setText("|<");
        btnDau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDauMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDauMouseExited(evt);
            }
        });
        btnDau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDauActionPerformed(evt);
            }
        });

        buttonGroup1.add(rdoNam);
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setLabel("Nữ");

        txtEmail.setBackground(new java.awt.Color(255, 255, 255));
        txtEmail.setLabelText("Địa chỉ email");

        textArea2.setColumns(20);
        textArea2.setRows(5);
        jScrollPane2.setViewportView(textArea2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(textAreaScroll1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btntThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnDau, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnLui, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnToi, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(10, 10, 10)
                                .addComponent(btnCuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))
                            .addComponent(txtHoVaTen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
                                .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(rdoNam)
                                .addGap(24, 24, 24)
                                .addComponent(rdoNu)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtMaNguoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(txtMaNguoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtHoVaTen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(txtNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoNu)
                            .addComponent(rdoNam))
                        .addGap(18, 18, 18)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDienThoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addComponent(textAreaScroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btntThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnToi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(34, Short.MAX_VALUE))
        );

        rdoNam.getAccessibleContext().setAccessibleName("rdoNam");

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

        txtTimKiem.setHint("Tìm kiếm");
        txtTimKiem.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtTimKiemCaretUpdate(evt);
            }
        });
        txtTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKiemActionPerformed(evt);
            }
        });

        btnTimKiem.setBackground(new java.awt.Color(153, 153, 255));
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/search.png"))); // NOI18N
        btnTimKiem.setText("Tìm kiếm theo mã");
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

        cboVaiTro.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Tất cả", "Nam", "Nữ" }));
        cboVaiTro.setSelectedIndex(-1);
        cboVaiTro.setLabeText("Chọn điều kiện lọc");
        cboVaiTro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboVaiTroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 905, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(cboVaiTro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnInThanhExel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(15, 15, 15))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cboVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 354, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnInThanhExel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Danh sách", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btntThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntThemActionPerformed
        btnSave();        // TODO add your handling code here:
    }//GEN-LAST:event_btntThemActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        btnTimKiem();        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void tblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblUserMouseClicked

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        btnUpdate();        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        btnDelete();        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        btnNew();        // TODO add your handling code here:
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnDauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDauActionPerformed
        if (listNH.size() > 0) {
            i = 0;
            showNguoiHoc();
        }
// TODO add your handling code here:
    }//GEN-LAST:event_btnDauActionPerformed

    private void btnCuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuoiActionPerformed

        if (listNH.size() > 0) {
            i = listNH.size() - 1;
            showNguoiHoc();
        }// TODO add your handling code here:
    }//GEN-LAST:event_btnCuoiActionPerformed

    private void btnToiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToiActionPerformed
        // TODO add your handling code here:
        if (listNH.size() > 0) {
            i++;
            if (i == listNH.size()) {
                i = listNH.size() - 1;
            }
            showNguoiHoc();
        }
    }//GEN-LAST:event_btnToiActionPerformed

    private void btnLuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuiActionPerformed
        // TODO add your handling code here:
        if (listNH.size() > 0) {
            i--;
            if (i < 0) {
                i = 0;
            }
            showNguoiHoc();
        }

    }//GEN-LAST:event_btnLuiActionPerformed

    private void btnTimKiemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemMouseExited
        btnTimKiem.setBackground(new Color(153, 153, 255));      // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemMouseExited

    private void btnTimKiemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemMouseEntered
        btnTimKiem.setBackground(Color.pink);        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemMouseEntered

    private void btnMoiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseEntered
        btnMoi.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btnMoiMouseEntered

    private void btnMoiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseExited
        btnMoi.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnMoiMouseExited

    private void btntThemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btntThemMouseEntered
        btntThem.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btntThemMouseEntered

    private void btntThemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btntThemMouseExited
        btntThem.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btntThemMouseExited

    private void btnSuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseEntered
        btnSua.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaMouseEntered

    private void btnSuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseExited
        btnSua.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaMouseExited

    private void btnXoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseEntered
        btnXoa.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaMouseEntered

    private void btnXoaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseExited
        btnXoa.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaMouseExited

    private void btnDauMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDauMouseEntered
        btnDau.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btnDauMouseEntered

    private void btnDauMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDauMouseExited
        btnDau.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnDauMouseExited

    private void btnLuiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLuiMouseEntered
        btnLui.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btnLuiMouseEntered

    private void btnLuiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLuiMouseExited
        btnLui.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnLuiMouseExited

    private void btnToiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnToiMouseEntered
        btnToi.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btnToiMouseEntered

    private void btnToiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnToiMouseExited
        btnToi.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnToiMouseExited

    private void btnCuoiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCuoiMouseEntered
        btnCuoi.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btnCuoiMouseEntered

    private void btnCuoiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCuoiMouseExited
        btnCuoi.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnCuoiMouseExited

    private void btnInThanhExelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInThanhExelMouseEntered
        btnInThanhExel.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btnInThanhExelMouseEntered

    private void btnInThanhExelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnInThanhExelMouseExited
        btnInThanhExel.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnInThanhExelMouseExited

    private void btnInThanhExelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInThanhExelActionPerformed
        btnInDanhSach();        // TODO add your handling code here:
    }//GEN-LAST:event_btnInThanhExelActionPerformed

    private void tblUserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMousePressed
        int chon = tblUser.getSelectedRow();

        String name = (String) tblmodel.getValueAt(chon, 0);
        if (evt.getClickCount() == 2) {
            for (O_NguoiHoc nh : listNH) {
                if (nh.getMaNH().trim().equals(name.trim())) {

                    txtMaNguoiHoc.setText(nh.getMaNH());
                    txtHoVaTen.setText(nh.getHoTen());
                    if (nh.isGioiTinh()) {
                        rdoNam.setSelected(true);
                    } else {
                        rdoNu.setSelected(true);
                    }
                    txtNgaySinh.setText(nh.getNgaySinh());
                    txtDienThoai.setText(nh.getDienThoai());
                    txtEmail.setText(nh.getEmail());
                    txtGhiChu.setText(nh.getGhiChu());

                }

            }
            jTabbedPane1.setSelectedIndex(0);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_tblUserMousePressed

    private void cboVaiTroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboVaiTroActionPerformed
        // TODO add your handling code here:
        loadDataToArray();

        tblmodel.setRowCount(0);
        String name = (String) cboVaiTro.getSelectedItem();
        if (name.equals("Nam")) {
            for (O_NguoiHoc nh : listNH) {
                if (nh.isGioiTinh()) {
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
        } else if (name.equals("Nữ")) {
            for (O_NguoiHoc nh : listNH) {
                if (nh.isGioiTinh() == false) {
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
        } else {
            Duyet();
        }
    }//GEN-LAST:event_cboVaiTroActionPerformed

    private void txtTimKiemCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtTimKiemCaretUpdate
        // TODO add your handling code here:
        String timKiem = txtTimKiem.getText();
        if (timKiem == null) {
            return;
        }
        if (timKiem.equals("")) {
            Duyet();
        }
        if (timKiem.matches(manv)) {
            Duyet();
            return;
        }
        tblmodel.setRowCount(0);
        for (O_NguoiHoc nh : listNH) {
            if (nh.getHoTen().startsWith(timKiem)) {

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

    }//GEN-LAST:event_txtTimKiemCaretUpdate

    private void txtTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKiemActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimKiemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tuandhpc05076.Swing.Button btnCuoi;
    private com.tuandhpc05076.Swing.Button btnDau;
    private com.tuandhpc05076.Swing.Button btnInThanhExel;
    private com.tuandhpc05076.Swing.Button btnLui;
    private com.tuandhpc05076.Swing.Button btnMoi;
    private com.tuandhpc05076.Swing.Button btnSua;
    private com.tuandhpc05076.Swing.Button btnTimKiem;
    private com.tuandhpc05076.Swing.Button btnToi;
    private com.tuandhpc05076.Swing.Button btnXoa;
    private com.tuandhpc05076.Swing.Button btntThem;
    private javax.swing.ButtonGroup buttonGroup1;
    private com.tuandhpc05076.Swing.Combobox cboVaiTro;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblUser;
    private com.tuandhpc05076.Swing.TextArea textArea2;
    private textarea.TextAreaScroll textAreaScroll1;
    private com.tuandhpc05076.Swing.TextField txtDienThoai;
    private com.tuandhpc05076.Swing.TextField txtEmail;
    private com.tuandhpc05076.Swing.TextArea txtGhiChu;
    private com.tuandhpc05076.Swing.TextField txtHoVaTen;
    private com.tuandhpc05076.Swing.TextField txtMaNguoiHoc;
    private com.tuandhpc05076.Swing.TextField txtNgaySinh;
    private com.tuandhpc05076.Swing.TextField1 txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
