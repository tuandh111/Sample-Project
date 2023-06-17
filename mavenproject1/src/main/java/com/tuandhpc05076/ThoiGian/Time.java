package com.tuandhpc05076.ThoiGian;

import java.awt.Button;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JLabel;

//ĐẶNG HOÀNG TUẤN
//MSSV PC05076
public class Time extends Thread {

    private JLabel button;

    public Time(JLabel button) {
        this.button = button;
    }

    @Override
    public void run() {
        SimpleDateFormat SDF = new SimpleDateFormat("hh:mm:ss aa");
        while (true) {
            Date now = new Date();
            String gio = SDF.format(now);
            button.setText(gio);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }
}
