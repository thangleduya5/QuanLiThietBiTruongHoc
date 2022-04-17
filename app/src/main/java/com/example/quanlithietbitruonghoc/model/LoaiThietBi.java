package com.example.quanlithietbitruonghoc.model;

import android.database.Cursor;

import com.example.quanlithietbitruonghoc.activity.HomeActivity;

import java.util.ArrayList;

public class LoaiThietBi {
    private int maLoai;
    private String tenLoai;

    public LoaiThietBi(){

    }

    public LoaiThietBi(int maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }

    public static ArrayList<LoaiThietBi> getAll(){
        ArrayList<LoaiThietBi> loaiThietBis = new ArrayList<>();
        Cursor cursor = HomeActivity.dataBase.getData("SELECT * FROM LOAITHIETBI");
        while (cursor.moveToNext()){
            loaiThietBis.add(new LoaiThietBi(cursor.getInt(0), cursor.getString(1)));
        }
        return  loaiThietBis;
    }

    public static String getTenLoai(int maLoai){
        Cursor cursor = HomeActivity.dataBase.getData("SELECT TENLOAI FROM LOAITHIETBI WHERE MALOAI = "+  maLoai);
        while (cursor.moveToNext()){
            return cursor.getString(0);
        }
        return  "";
    }
}
