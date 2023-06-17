/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuandhpc05076.MaHoa;

import java.security.MessageDigest;
import org.apache.commons.codec.binary.Base64;



//import org.apache.commons.codec.binary.Base64 ;



/**
 *
 * @author DELL E5470
 */
public class MaHoa {

    public static String toSHA(String str) {
        String chuoi = "sdfsdfryreefesdfrrt";
        String tong = null;
        str+=chuoi;
        try {
            byte[] data= str.getBytes("UTF-8");
            MessageDigest md =MessageDigest.getInstance("SHA-512");// ngoài ra thì có thể bâm thành SHA-256 SHA-512
            tong = Base64.encodeBase64String(md.digest(data));
           
        } catch (Exception e) {
            e.printStackTrace();
        }
         return tong;
    }
    //512 +rP2fZv1DsymN0Wse79IWjCwsEfVnumOSmmUQDKcQYIf32gYM9D2sX5Ub9V8Rq1CHXxRAw4EuX38J3Wxblex0A==
    //256 07FdcdczOfmnaawp6Hmg2qEPK5MYiyHwNbHi61j3RSs=
    //1 djBGHtt6L2eLSG67IQ/by2emPu0=
//    public static void main(String[] args) {
//        String pass= "sdfsdfsdfsd";
//        String passvd= "123456";
//        toSHA(passvd);
//        toSHA(pass);
//        System.out.println(toSHA(pass));
//        System.out.println(toSHA(pass).equals(toSHA(passvd)));
    //}

}
