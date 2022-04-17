package com.example.quanlithietbitruonghoc.model;

import android.database.Cursor;
import android.util.Log;

import com.example.quanlithietbitruonghoc.activity.HomeActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ThongKe2 {
    private String tenTB;
    private String tenLoai;
    private int soLuong;

    public ThongKe2(){

    }

    public ThongKe2(String tenTB, String tenLoai, int soLuong) {
        this.tenTB = tenTB;
        this.tenLoai = tenLoai;
        this.soLuong = soLuong;
    }

    public String getTenTB() {
        return tenTB;
    }

    public void setTenTB(String tenTB) {
        this.tenTB = tenTB;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public static ArrayList<ThongKe2> getDSThongKe2(int nam){
        String sql= "SELECT T.TENTB, L.TENLOAI, C.SOLUONG, C.NGAYSUDUNG " +
                    "FROM THIETBI AS T, LOAITHIETBI AS L, CHITIETSUDUNG AS C " +
                    "WHERE C.MATB = T.MATB AND T.MALOAI = L.MALOAI ";
        Cursor cursor = HomeActivity.dataBase.getData(sql);
        ArrayList<ThongKe2> thongKe2s = new ArrayList<>();
        while (cursor.moveToNext()){
            String nsd = cursor.getString(3);
            String b = nsd.substring(nsd.lastIndexOf("/"));
            Log.d("TAG", b);
            String tenTB = cursor.getString(0);
            String tenLoai = cursor.getString(1);
            int soLuong = cursor.getInt(2);
            if(nsd.substring(nsd.lastIndexOf("/")+1).equals(String.valueOf(nam))){
                Boolean exist =false;
                for(ThongKe2 k: thongKe2s){
                    if(k.getTenTB().equals(tenTB)){
                        k.setSoLuong(k.getSoLuong() + soLuong);
                        exist = true;
                        break;
                    }
                }
                if(!exist) thongKe2s.add(new ThongKe2(tenTB, tenLoai, soLuong));
            }
        }
        return thongKe2s;

    }
}
