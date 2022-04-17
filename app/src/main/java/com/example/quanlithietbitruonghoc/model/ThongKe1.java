package com.example.quanlithietbitruonghoc.model;

import android.database.Cursor;

import com.example.quanlithietbitruonghoc.activity.HomeActivity;

import java.util.ArrayList;

public class ThongKe1 {
    private int maPhong;
    private String tenTB;
    private String tenLoai;
    private int soLuong;

    public ThongKe1(){

    }

    public ThongKe1(int maPhong, String tenTB, String tenLoai, int soLuong) {
        this.maPhong = maPhong;
        this.tenTB = tenTB;
        this.tenLoai = tenLoai;
        this.soLuong = soLuong;
    }

    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
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

    public static ArrayList<ThongKe1> getDSThongKe1(String LoaiPhong){
        String sql= "SELECT C.MAPHONG, T.TENTB, L.TENLOAI, C.SOLUONG FROM" +
                    "(SELECT MAPHONG, MATB, SOLUONG FROM CHITIETSUDUNG WHERE MAPHONG IN " +
                    "(SELECT MAPHONG FROM PHONGHOC WHERE LOAIPHONG = '"+LoaiPhong+"')) AS C, " +
                    "THIETBI AS T, LOAITHIETBI AS L " +
                    "WHERE C.MATB = T.MATB AND T.MALOAI = L.MALOAI";

        Cursor cursor = HomeActivity.dataBase.getData(sql);
        ArrayList<ThongKe1> thongKe1s = new ArrayList<>();
        while (cursor.moveToNext()){
            thongKe1s.add(new ThongKe1(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3)));
        }
        return thongKe1s;
    }
}

