package com.example.quanlithietbitruonghoc.model;

import android.database.Cursor;

import com.example.quanlithietbitruonghoc.activity.HomeActivity;

public class ThietBi {
    private int maTB;
    private String tenTB;
    private String xuatXu;
    private int maLoai;

    public ThietBi(){

    }

    public ThietBi(int maTB, String tenTB, String xuatXu, int maLoai) {
        this.maTB = maTB;
        this.tenTB = tenTB;
        this.xuatXu = xuatXu;
        this.maLoai = maLoai;
    }

    public int getMaTB() {
        return maTB;
    }

    public void setMaTB(int maTB) {
        this.maTB = maTB;
    }

    public String getTenTB() {
        return tenTB;
    }

    public void setTenTB(String tenTB) {
        this.tenTB = tenTB;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public static Boolean checkTonTaiMaThietBi(int maTB){
        Cursor data= HomeActivity.dataBase.getData("SELECT * FROM THIETBI WHERE MATB = "+ maTB);
        if(data.moveToNext()){
            return true;
        }
        return false;
    }
}
