/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandhpc05076.Demo;

import com.tuandhpc05076.Dao.NhanVienDAO;
import com.tuandhpc05076.Object.O_NguoiHoc;
import com.tuandhpc05076.Object.O_NhanVien;
import com.tuandhpc05076.dao.KhoaHocDAO;
import com.tuandhpc05076.dao.NguoiHocDAO;
import com.tuandhpc05076.dao.ThongKeDAO;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author DELL E5470
 */
public class demo {

    static O_NguoiHoc getModel() {
        O_NguoiHoc list = new O_NguoiHoc();
        list.setMaNH("NH00006");
        list.setHoTen("Đặng haongf tuấn");
        list.setGioiTinh(true);
        list.setNgaySinh("2000-01-01");
        list.setDienThoai("09876443456");
        list.setEmail("HoangTuan@gmail.com");
        list.setGhiChu("Học");
        list.setMaNV("Tuan123");
        list.setNgayDK("2023-05-24");
        return list;
    }

    public static void main(String[] args) {
        //gọi thủ tục lưu
//        ThongKeDAO dao = new  ThongKeDAO();
//        List<Object[]> list=   dao.getNguoiHoc();
//        for (Object[] ob: list) {
//            System.out.println(Arrays.toString(ob));
//        }

           ThongKeDAO thongKe = new ThongKeDAO();
           ArrayList<Object[]> list= (ArrayList<Object[]>) thongKe.getBangDiem(19);
           for (Object[] ob : list) {
               System.out.println(Arrays.toString(ob));
        }

     
        NguoiHocDAO nh = new NguoiHocDAO();
        O_NguoiHoc model = getModel();
           //thêm người học
//        nh.insert(model);
        //xóa người học
//        nh.delete("NH00006");
        //truy vấn all
        ArrayList<O_NguoiHoc> listNH=(ArrayList<O_NguoiHoc>) nh.select();
        for (O_NguoiHoc nh1 : listNH) {
            System.out.println("Họ tên Người học:"+nh1.getHoTen());
        }

    }

}
