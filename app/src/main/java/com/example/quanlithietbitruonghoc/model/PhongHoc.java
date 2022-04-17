package com.example.quanlithietbitruonghoc.model;

import android.database.Cursor;

import com.example.quanlithietbitruonghoc.activity.HomeActivity;

import java.util.ArrayList;

public class PhongHoc {
    private String maPhong;
    private String loaiPhong;
    private int tang;

    public PhongHoc(){

    }

    public PhongHoc(String maPhong, String loaiPhong, int tang) {
        this.maPhong = maPhong;
        this.loaiPhong = loaiPhong;
        this.tang = tang;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public String getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(String loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public int getTang() {
        return tang;
    }

    public void setTang(int tang) {
        this.tang = tang;
    }

    public static ArrayList<String> getAllLoaiPhong(){
        ArrayList<String> loaiPhongs= new ArrayList<>();
        Cursor cursor= HomeActivity.dataBase.getData("SELECT DISTINCT LOAIPHONG FROM PHONGHOC");
        while (cursor.moveToNext()){
            loaiPhongs.add(cursor.getString(0));
        }
        return loaiPhongs;
    }

    public static Boolean checkTonTaiMaPhong(int maPhong){
        Cursor data=HomeActivity.dataBase.getData("SELECT * FROM PHONGHOC WHERE MAPHONG = "+ maPhong);
        if(data.moveToNext()){
            return true;
        }
        return false;
    }
}
