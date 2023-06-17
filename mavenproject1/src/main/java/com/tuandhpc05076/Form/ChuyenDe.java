/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.tuandhpc05076.Form;

import static com.tuandhpc05076.Form.NhanVien.list;
import com.tuandhpc05076.Object.O_ChuyenDe;
import com.tuandhpc05076.Object.O_DangNhap;
import com.tuandhpc05076.Object.O_NhanVien;
import com.tuandhpc05076.helper.ShareHelper;
import com.tuandhpc05076.swing0.Form;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author DELL E5470
 */
public class ChuyenDe extends javax.swing.JPanel {

    public static ArrayList<O_ChuyenDe> listCD = new ArrayList<>();
    DefaultTableModel tblModel;
    String userName = "sa";
    String password = "123";
    String url = "jdbc:sqlserver://localhost:1433; databaseName= EduSys;encrypt=false";
    O_DangNhap tk = new O_DangNhap();
    ArrayList<O_DangNhap> listDangNhap = tk.getAlllist();
    int i = -1;
    String strHinh = "";

    public ChuyenDe() {
        initComponents();
        jTabbedPane1.setBackground(ChuyenDe.this.getBackground());
        jPanel1.setBackground(ChuyenDe.this.getBackground());
        jPanel2.setBackground(ChuyenDe.this.getBackground());

        TieuDe();
        loadDataToArray();
        Duyet();
        txtMaChuyenDe.setEditable(false);
        tuDongTangMa();
        tblUser.getColumnModel().getColumn(0).setPreferredWidth(5);//Mã
        tblUser.getColumnModel().getColumn(1).setPreferredWidth(150);//tên chuyên đề
        tblUser.getColumnModel().getColumn(2).setPreferredWidth(3);
        tblUser.getColumnModel().getColumn(3).setPreferredWidth(3);
        tblUser.getColumnModel().getColumn(4).setPreferredWidth(150);
        tblUser.getColumnModel().getColumn(5).setPreferredWidth(150);

    }

    public void tuDongTangMa() {
        String name = "";
        int i = 0;
        O_ChuyenDe nh = listCD.get(listCD.size() - 1);

        name = nh.getMaCD().trim();
        String[] tbl = name.split("D");
        String so = String.valueOf(Integer.parseInt(tbl[1]) + 1);
        String ten = "CD";
        for (int j = 0; j <= 2 - so.length(); j++) {
            ten += "0";
        }
        ten = ten + so;
        txtMaChuyenDe.setText(ten);

    }

    public void TieuDe() {
        tblModel = new DefaultTableModel();
        String[] tbl = new String[]{"Mã CĐ", "Tên CĐ", "Học phí", "Thời lượng", "Hình", "Mô tả"};
        tblModel.setColumnIdentifiers(tbl);
        tblUser.setModel(tblModel);
    }

    public void loadDataToArray() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            java.sql.Statement st = con.createStatement();
            String sql = "SELECT * FROM ChuyenDe";
            ResultSet rs = st.executeQuery(sql);
            listCD.clear();
            while (rs.next()) {

                String MaCD = rs.getString(1);
                String TenCD = rs.getString(2);
                float HocPhi = rs.getFloat(3);
                int ThoiLuong = rs.getInt(4);
                String Hinh = rs.getString(5);
                String VaiTro = rs.getString(6);
                listCD.add(new O_ChuyenDe(MaCD, TenCD, HocPhi, ThoiLuong, Hinh, VaiTro));
            }
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Duyet() {
        tblModel.setRowCount(0);
        for (O_ChuyenDe cd : listCD) {
            Object[] tbl = new Object[]{cd.getMaCD(), cd.getTenCD(), cd.getHocPhi(), cd.getThoiLuong(), cd.getHinh(), cd.getMoTa()};
            tblModel.addRow(tbl);
        }
    }

    public void btnNew() {
        txtMaChuyenDe.setText("");
        txtMaChuyenDe.setEditable(false);
        txtTenChuyenDe.setText("");

        txtAnh.setIcon(null);
        txtHocPhi.setText("");
        txtThoiLuongGio.setText("");
        txtMoTa.setText("");
        tuDongTangMa();
    }

    public void showThongTin() {
        O_ChuyenDe cd = listCD.get(i);
        txtMaChuyenDe.setText(cd.getMaCD());
        txtMaChuyenDe.setEditable(false);
        txtTenChuyenDe.setText(cd.getTenCD());
        txtHocPhi.setText(String.valueOf(cd.getHocPhi()));
        txtThoiLuongGio.setText(String.valueOf(cd.getThoiLuong()));
        if (cd.getHinh().equals("No Avatar")) {
            txtAnh.setText("No Avatar");
            txtAnh.setIcon(null);
        } else {
            txtAnh.setText("");
            File logosFolder = new File("Logos");
            String absolutePath = logosFolder.getPath();
            ImageIcon imgic = new ImageIcon(absolutePath + "/" + cd.getHinh().trim());
            Image img = imgic.getImage();
            int Width = txtAnh.getWidth();
            int Height = txtAnh.getHeight();
            txtAnh.setIcon(new ImageIcon(img.getScaledInstance(Width, Height, 0)));

        }
        tblUser.setRowSelectionInterval(i, i);
        txtMoTa.setText(cd.getMoTa());
    }
    String ma = "(CD)\\d{3}";

    public boolean kiem() {
        if (txtAnh.getIcon() == null) {
            JOptionPane.showMessageDialog(this, "Bạn phải chọn hình");
            return false;
        }
        if (txtMaChuyenDe.getText().equalsIgnoreCase("")) {
            JOptionPane.showMessageDialog(this, "Mã chuyên đề đã để trống");
            return false;
        }
        boolean kiem = false;
        for (O_ChuyenDe cd : listCD) {
            if (cd.getMaCD().trim().equals(txtMaChuyenDe.getText())) {
                kiem = true;
            }
        }
        if (kiem == true) {
            JOptionPane.showMessageDialog(this, "Mã của bạn đã tồn tại trong danh sách");
            return false;

        }
        if (!txtMaChuyenDe.getText().matches(ma)) {
            JOptionPane.showMessageDialog(this, "Mã của bạn nhập không đúng định dạng\nVD:CD001");
            return false;
        }
        if (txtTenChuyenDe.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Tên chuyên đề của bạn đã để trống");
            return false;
        }
        if (txtThoiLuongGio.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Thời lượng của bạn đã để trống");
            return false;
        }
        try {
            Integer.parseInt(txtThoiLuongGio.getText());
            if (Integer.parseInt(txtThoiLuongGio.getText()) < 0) {
                JOptionPane.showMessageDialog(this, "Thời lượng giờ của bạn phải lớn hơn 0");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Thời lượng giờ của bạn không phải là số");
            return false;
        }
        if (txtHocPhi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Học phí của bạn đã để trống");
            return false;
        }
        try {
            Float.parseFloat(txtHocPhi.getText());
            if (Float.parseFloat(txtHocPhi.getText()) < 0) {
                JOptionPane.showMessageDialog(this, "Học phí của bạn không được nhỏ hơn 0");
                return false;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Học phí của bạn không phải là số");
            return false;
        }
        if (txtMoTa.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Mô tả của bạn đã để trống");
            return false;
        }

        return true;
    }

    public void btnInDanhSach() {
        try {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showSaveDialog(this);
            File saveFile = jFileChooser.getSelectedFile();

            if (saveFile != null) {
                saveFile = new File(saveFile.toString() + ".xlsx");
                Workbook wb = new XSSFWorkbook();
                Sheet sheet = wb.createSheet("Chuyên đề");

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
        if (kiem() == false) {
            return;
        }
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            String sqla = "insert into dbo.ChuyenDe values(?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sqla);
            st.setString(1, txtMaChuyenDe.getText());
            st.setString(2, txtTenChuyenDe.getText());
            st.setFloat(3, Float.parseFloat(txtHocPhi.getText()));
            st.setInt(4, Integer.parseInt(txtThoiLuongGio.getText()));
            if (strHinh.equals("")) {
                st.setString(5, "No Avatar");
            } else {
                st.setString(5, strHinh);
            }
            st.setString(6, txtMoTa.getText());

            st.executeUpdate();
            con.close();
            loadDataToArray();
            Duyet();
            JOptionPane.showMessageDialog(this, "Thêm chuyên đề thành công");
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

        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            String sqla = "DELETE FROM dbo.ChuyenDe WHERE MaCD=?";
            PreparedStatement st = con.prepareStatement(sqla);

            st.setString(1, txtMaChuyenDe.getText());

            st.executeUpdate();
            con.close();
            loadDataToArray();
            Duyet();
            JOptionPane.showMessageDialog(this, "Xóa chuyên đề thành công");
            btnNew();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Chuyên đề này đang tổ chức học không được xóa");
        }
    }

    public void btnUpdate() {
        int chon = tblUser.getSelectedRow();
        if (chon < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn chuyên đề trong danh sách để có thể cập nhật");
            return;
        }
        String name = (String) tblModel.getValueAt(chon, 4);
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            java.sql.Connection con = DriverManager.getConnection(url, userName, password);
            String sqla = "UPDATE dbo.ChuyenDe SET TenCD=?,HocPhi=?,ThoiLuong=?,Hinh=?,MoTa=? WHERE MaCD=?";
            PreparedStatement st = con.prepareStatement(sqla);
            st.setString(6, txtMaChuyenDe.getText());
            st.setString(1, txtTenChuyenDe.getText());
            st.setFloat(2, Float.parseFloat(txtHocPhi.getText()));
            st.setInt(3, Integer.parseInt(txtThoiLuongGio.getText()));
            if (strHinh.equals("")) {
                st.setString(4, name);
            } else {
                st.setString(4, strHinh);
            }
            st.setString(5, txtMoTa.getText());
            st.executeUpdate();
            con.close();
            loadDataToArray();
            Duyet();
            JOptionPane.showMessageDialog(this, "Cập nhật chuyên đề thành công");
            btnNew();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnTim() {
        if (txtTimKiem.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Chưa nhập mã cần tìm kiếm");
            return;
        }
        int i = 0;
        boolean kiem = false;
        for (O_ChuyenDe cd : listCD) {
            if (cd.getMaCD().trim().equals(txtTimKiem.getText())) {
                txtMaChuyenDe.setText(cd.getMaCD());
                txtMaChuyenDe.setEditable(false);
                txtTenChuyenDe.setText(cd.getTenCD());
                txtHocPhi.setText(String.valueOf(cd.getHocPhi()));
                txtThoiLuongGio.setText(String.valueOf(cd.getThoiLuong()));
                if (cd.getHinh().equals("No Avatar")) {
                    txtAnh.setText("No Avatar");
                    txtAnh.setIcon(null);
                } else {
                    txtAnh.setText("");
                    File logosFolder = new File("Logos");
                    String absolutePath = logosFolder.getPath();
                    ImageIcon imgic = new ImageIcon(absolutePath + "/" + cd.getHinh().trim());
                    Image img = imgic.getImage();
                    int Width = txtAnh.getWidth();
                    int Height = txtAnh.getHeight();
                    txtAnh.setIcon(new ImageIcon(img.getScaledInstance(Width, Height, 0)));

                }
                tblUser.setRowSelectionInterval(i, i);
                txtMoTa.setText(cd.getMoTa());
                jTabbedPane1.setSelectedIndex(0);
                JOptionPane.showMessageDialog(this, "Tìm kiếm thành công");
                kiem = true;
            }
            i++;
        }
        if (kiem == false) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fileChooser = new javax.swing.JFileChooser();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtAnh = new javax.swing.JLabel();
        txtMaChuyenDe = new com.tuandhpc05076.Swing.TextField();
        txtTenChuyenDe = new com.tuandhpc05076.Swing.TextField();
        txtHocPhi = new com.tuandhpc05076.Swing.TextField();
        txtThoiLuongGio = new com.tuandhpc05076.Swing.TextField();
        textAreaScroll1 = new textarea.TextAreaScroll();
        txtMoTa = new com.tuandhpc05076.Swing.TextArea();
        btnThem = new com.tuandhpc05076.Swing.Button();
        btnSua = new com.tuandhpc05076.Swing.Button();
        btnXoa = new com.tuandhpc05076.Swing.Button();
        btnMoi = new com.tuandhpc05076.Swing.Button();
        btnToi = new com.tuandhpc05076.Swing.Button();
        btnCuoi = new com.tuandhpc05076.Swing.Button();
        btnLui = new com.tuandhpc05076.Swing.Button();
        btnDau = new com.tuandhpc05076.Swing.Button();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUser = new javax.swing.JTable();
        txtTimKiem = new com.tuandhpc05076.Swing.TextField1();
        btnTimKiem = new com.tuandhpc05076.Swing.Button();
        btnInThanhExel = new com.tuandhpc05076.Swing.Button();
        cboDieuKienLoc = new com.tuandhpc05076.Swing.Combobox();

        fileChooser.setCurrentDirectory(new java.io.File("C:\\Users\\DELL E5470\\Pictures\\Screenshots"));
        fileChooser.setMinimumSize(new java.awt.Dimension(1000, 500));
        fileChooser.setPreferredSize(new java.awt.Dimension(1000, 500));

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(800, 550));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(800, 550));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hình ảnh", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP, new java.awt.Font("Segoe UI", 3, 12), new java.awt.Color(0, 0, 0))); // NOI18N
        jPanel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel3MouseClicked(evt);
            }
        });

        txtAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtAnhMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtAnh, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                .addContainerGap())
        );

        txtMaChuyenDe.setBackground(new java.awt.Color(255, 255, 255));
        txtMaChuyenDe.setLabelText("Mã chuyên đề");

        txtTenChuyenDe.setBackground(new java.awt.Color(255, 255, 255));
        txtTenChuyenDe.setLabelText("Tên chuyên đề");

        txtHocPhi.setBackground(new java.awt.Color(255, 255, 255));
        txtHocPhi.setLabelText("Học phí (Nghìn)");

        txtThoiLuongGio.setBackground(new java.awt.Color(255, 255, 255));
        txtThoiLuongGio.setLabelText("Thời lượng (Giờ)");

        textAreaScroll1.setLabelText("Mô tả chuyên đề");

        txtMoTa.setColumns(20);
        txtMoTa.setRows(5);
        textAreaScroll1.setViewportView(txtMoTa);

        btnThem.setBackground(new java.awt.Color(153, 153, 255));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/tuandhpc05076/icon1/plus.png"))); // NOI18N
        btnThem.setText("Thêm ");
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

        jButton1.setText("Tải hình ảnh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(34, 34, 34)
                                        .addComponent(jButton1)))
                                .addGap(40, 40, 40)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaChuyenDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTenChuyenDe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtThoiLuongGio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtHocPhi, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(textAreaScroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 853, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDau, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLui, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnToi, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(66, 66, 66)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(txtMaChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtTenChuyenDe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txtThoiLuongGio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHocPhi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(textAreaScroll1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnToi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLui, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(49, Short.MAX_VALUE))
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

        cboDieuKienLoc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mặc định", "Tăng", "Giảm" }));
        cboDieuKienLoc.setSelectedIndex(-1);
        cboDieuKienLoc.setLabeText("Sắp xếp học phí theo");
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
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnInThanhExel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(cboDieuKienLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKiem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)))
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 865, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cboDieuKienLoc, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(btnInThanhExel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );

        jTabbedPane1.addTab("Danh sách", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 865, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void textField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textField4ActionPerformed

    private void jPanel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel3MouseClicked
//        JFileChooser jdc = new JFileChooser("D:\\DuAnMau\\mavenproject1\\src\\main\\resources\\com\\tuandhpc05076\\Image");
//        FileNameExtensionFilter filename = new FileNameExtensionFilter("Hinh anh", "png", "jpg");
//        jdc.setFileFilter(filename);
//        jdc.setMultiSelectionEnabled(false);// nếu lá true thì cho chọn nhiều
//        jdc.showOpenDialog(null);
//
//        try {
//            File file = jdc.getSelectedFile();
//
//            if (file == null) {
//                return;
//            }
//
//            Image img = ImageIO.read(file);
//
//            strHinh = file.getName();
//            txtAnh.setText("");
//            int Width = txtAnh.getWidth();
//            int Height = txtAnh.getHeight();
//            txtAnh.setIcon(new ImageIcon(img.getScaledInstance(Width, Height, 0)));
//
//            FileUtils.copyFile(file, new File("/src/main/resources/com/tuandhpc05076/Image"));
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//        }         // TODO add your handling code here:


    }//GEN-LAST:event_jPanel3MouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        btnSave();        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMouseClicked
        // TODO add your handling code here:


    }//GEN-LAST:event_tblUserMouseClicked

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        btnUpdate();                // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        btnNew();        // TODO add your handling code here:
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        btnDelete();        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnDauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDauActionPerformed
        if (listCD.size() > 0) {
            i = 0;
            showThongTin();
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_btnDauActionPerformed

    private void btnCuoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuoiActionPerformed
        // TODO add your handling code here:
        if (listCD.size() > 0) {
            i = listCD.size() - 1;
            showThongTin();
        }
    }//GEN-LAST:event_btnCuoiActionPerformed

    private void btnLuiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLuiActionPerformed
        if (listCD.size() > 0) {
            i--;
            if (i < 0) {
                i = 0;
            }
            showThongTin();
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnLuiActionPerformed

    private void btnToiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToiActionPerformed
        // TODO add your handling code here:
        if (list.size() > 0) {
            i++;
            if (i == listCD.size()) {
                i = listCD.size() - 1;
            }
            showThongTin();
        }
    }//GEN-LAST:event_btnToiActionPerformed

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        btnTim();        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnTimKiemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemMouseExited
        btnTimKiem.setBackground(new Color(153, 153, 255));       // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemMouseExited

    private void btnTimKiemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTimKiemMouseEntered
        btnTimKiem.setBackground(Color.pink);        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimKiemMouseEntered

    private void btnThemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseEntered
        btnThem.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btnThemMouseEntered

    private void btnThemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseExited
        btnThem.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemMouseExited

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

    private void btnMoiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseEntered
        btnMoi.setBackground(Color.pink);          // TODO add your handling code here:
    }//GEN-LAST:event_btnMoiMouseEntered

    private void btnMoiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseExited
        btnMoi.setBackground(new Color(153, 153, 255));        // TODO add your handling code here:
    }//GEN-LAST:event_btnMoiMouseExited

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

    private void txtAnhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtAnhMouseClicked
        JFileChooser jdc = new JFileChooser();
        jdc.showOpenDialog(null);

        try {
            File file = jdc.getSelectedFile();
            if (file == null) {
                return;
            }
            ShareHelper.saveLogo(file);
            Image img = ImageIO.read(file);
            strHinh = file.getName();
            txtAnh.setText("");
//            int Width = txtanh.getWidth();
//            int Height = txtanh.getHeight();
            txtAnh.setIcon(new ImageIcon(img.getScaledInstance(140, 135, 0)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }       // TODO add your handling code here:
    }//GEN-LAST:event_txtAnhMouseClicked

    private void tblUserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUserMousePressed

        int chon = tblUser.getSelectedRow();
        String name = (String) tblModel.getValueAt(chon, 0);
        if (evt.getClickCount() == 2) {
            for (O_ChuyenDe cd : listCD) {
                if (cd.getMaCD().trim().equals(name)) {
                    txtMaChuyenDe.setText(cd.getMaCD());
                    txtMaChuyenDe.setEditable(false);
                    txtTenChuyenDe.setText(cd.getTenCD());
                    txtHocPhi.setText(String.valueOf(cd.getHocPhi()));
                    txtThoiLuongGio.setText(String.valueOf(cd.getThoiLuong()));
                    if (cd.getHinh().equals("No Avatar")) {
                        txtAnh.setText("No Avatar");
                        txtAnh.setIcon(null);
                    } else {
                        txtAnh.setText("");
                        File logosFolder = new File("Logos");
                        String absolutePath = logosFolder.getPath();
                        ImageIcon imgic = new ImageIcon(absolutePath + "/" + cd.getHinh().trim());

                        Image img = imgic.getImage();
                        int Width = txtAnh.getWidth();
                        int Height = txtAnh.getHeight();
                        txtAnh.setIcon(new ImageIcon(img.getScaledInstance(Width, Height, 0)));
                    }
                    txtMoTa.setText(cd.getMoTa());
                }
            }
            jTabbedPane1.setSelectedIndex(0);
        }
// TODO add your handling code here:
    }//GEN-LAST:event_tblUserMousePressed

    private void cboDieuKienLocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboDieuKienLocActionPerformed
        // TODO add your handling code here:
        O_DangNhap dn = listDangNhap.get(0);
        String name = (String) cboDieuKienLoc.getSelectedItem();
        if (name == null) {
            return;
        }
        if (name.equalsIgnoreCase("Tăng")) {
            Collections.sort(listCD, (sv1, sv2) -> (int) (sv1.getHocPhi() - sv2.getHocPhi()));
//        Collections.reverse(list);
            Duyet();
        } else if (name.equalsIgnoreCase("Giảm")) {
            Collections.sort(listCD, (sv1, sv2) -> (int) (sv1.getHocPhi() - sv2.getHocPhi()));
            Collections.reverse(listCD);
            Duyet();
        } else {
            Collections.sort(listCD, (sv1, sv2) -> (sv1.getMaCD().compareTo(sv2.getMaCD())));
            Duyet();
        }

    }//GEN-LAST:event_cboDieuKienLocActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
      JFileChooser jdc = new JFileChooser();
        jdc.showOpenDialog(null);

        try {
            File file = jdc.getSelectedFile();
            if (file == null) {
                return;
            }
            ShareHelper.saveLogo(file);
            Image img = ImageIO.read(file);
            strHinh = file.getName();
            txtAnh.setText("");
//            int Width = txtanh.getWidth();
//            int Height = txtanh.getHeight();
            txtAnh.setIcon(new ImageIcon(img.getScaledInstance(140, 135, 0)));
        } catch (IOException ex) {
            ex.printStackTrace();
        }        // TODO add your handling code here:

    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.tuandhpc05076.Swing.Button btnCuoi;
    private com.tuandhpc05076.Swing.Button btnDau;
    private com.tuandhpc05076.Swing.Button btnInThanhExel;
    private com.tuandhpc05076.Swing.Button btnLui;
    private com.tuandhpc05076.Swing.Button btnMoi;
    private com.tuandhpc05076.Swing.Button btnSua;
    private com.tuandhpc05076.Swing.Button btnThem;
    private com.tuandhpc05076.Swing.Button btnTimKiem;
    private com.tuandhpc05076.Swing.Button btnToi;
    private com.tuandhpc05076.Swing.Button btnXoa;
    private com.tuandhpc05076.Swing.Combobox cboDieuKienLoc;
    private javax.swing.JFileChooser fileChooser;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tblUser;
    private textarea.TextAreaScroll textAreaScroll1;
    private javax.swing.JLabel txtAnh;
    private com.tuandhpc05076.Swing.TextField txtHocPhi;
    private com.tuandhpc05076.Swing.TextField txtMaChuyenDe;
    private com.tuandhpc05076.Swing.TextArea txtMoTa;
    private com.tuandhpc05076.Swing.TextField txtTenChuyenDe;
    private com.tuandhpc05076.Swing.TextField txtThoiLuongGio;
    private com.tuandhpc05076.Swing.TextField1 txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
