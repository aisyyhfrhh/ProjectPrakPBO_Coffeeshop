package Main.Controller;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Dompet {
    private static int saldo, voucher,vocher2;
    private static String idDompet;

    public static void setIdDompet(String vIdDompet){
        idDompet = vIdDompet;
    }
    public static String getIdDompet(){
        return idDompet;
    }
    public static void setSaldo(int vsaldo){
        saldo = vsaldo;
    }
    public static void kurangSaldo(int vsaldo){
        saldo -= vsaldo;
    }
    public static int getSaldo(){
        return saldo;
    }
    public static void setVoucher(int vVoucher){voucher = vVoucher; tambahSaldo(voucher);}
    public static void tambahSaldo(int vvoucher){vocher2 = saldo + voucher; setSaldo(vocher2);}
    public static int getVocher2(){return vocher2;}
    public static int getVoucher(){return voucher;}
}