package com.example.quanlithietbitruonghoc.model;

import android.database.Cursor;

import com.example.quanlithietbitruonghoc.activity.HomeActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChiTietSuDung {
    private int maPhong;
    private int maTB;
    private Date ngaySuDung;
    private int soLuong;

    public ChiTietSuDung(){

    }

    public ChiTietSuDung(int maPhong, int maTB, Date ngaySuDung, int soLuong) {
        this.maPhong = maPhong;
        this.maTB = maTB;
        this.ngaySuDung = ngaySuDung;
        this.soLuong = soLuong;
    }

    public int getMaPhong() {
        return maPhong;
    }

    public void setMaPhong(int maPhong) {
        this.maPhong = maPhong;
    }

    public int getMaTB() {
        return maTB;
    }

    public void setMaTB(int maTB) {
        this.maTB = maTB;
    }

    public Date getNgaySuDung() {
        return ngaySuDung;
    }

    public void setNgaySuDung(Date ngaySuDung) {
        this.ngaySuDung = ngaySuDung;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public static ArrayList<Integer> getYear(){
        ArrayList<Integer> nams= new ArrayList<>();
        Cursor cursor = HomeActivity.dataBase.getData("SELECT DISTINCT NGAYSUDUNG FROM CHITIETSUDUNG");
        while (cursor.moveToNext()){
            String ngaySD = cursor.getString(0);
            int year = Integer.parseInt(ngaySD.substring(ngaySD.lastIndexOf("/")+1));
            if(!nams.contains(year)) nams.add(year);
        }
        return nams;
    }

}
