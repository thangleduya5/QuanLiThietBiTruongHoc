package com.example.quanlithietbitruonghoc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.quanlithietbitruonghoc.R;
import com.example.quanlithietbitruonghoc.common.DataBase;

public class HomeActivity extends AppCompatActivity {

    public static DataBase dataBase;
    Button btnChungLoai, btnThietBi, btnChiTietSD, btnPhongHoc, btnThongKe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initDB();
        setControl();
        setEvent();
    }

    private void setEvent(){
        btnChungLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LoaiThietBiActivity.class);
                startActivity(intent);
            }
        });

        btnThietBi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ThietBiActivity.class);
                startActivity(intent);
            }
        });

        btnPhongHoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PhongHocActivity.class);
                startActivity(intent);
            }
        });

        btnChiTietSD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ChiTietSuDungActivity.class);
                startActivity(intent);
            }
        });

        btnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, StatiticsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setControl(){
        btnChungLoai = (Button) findViewById(R.id.btnChungLoai);
        btnThietBi = (Button) findViewById(R.id.btnThietBi);
        btnPhongHoc = (Button) findViewById(R.id.btnPhongHoc);
        btnChiTietSD = (Button) findViewById(R.id.btnChiTietSuDung);
        btnThongKe = (Button) findViewById(R.id.btnThongKe);
    }

    private void initDB(){
        dataBase = new DataBase(this, "QLTB", null, 1);
        dataBase.queryData("CREATE TABLE IF NOT EXISTS LOAITHIETBI(MALOAI INTEGER PRIMARY KEY AUTOINCREMENT, TENLOAI VARCHAR(200))");
        dataBase.queryData("CREATE TABLE IF NOT EXISTS THIETBI(MATB INTEGER PRIMARY KEY AUTOINCREMENT, TENTB VARCHAR(200), XUATXU VARCHAR(200), MALOAI INTEGER, " +
                                "FOREIGN KEY(MALOAI) REFERENCES LOAITHIETBI(MALOAI))");
        dataBase.queryData("CREATE TABLE IF NOT EXISTS PHONGHOC(MAPHONG INTEGER PRIMARY KEY AUTOINCREMENT, LOAIPHONG VARCHAR(200), TANG INTEGER)");
        dataBase.queryData("CREATE TABLE IF NOT EXISTS CHITIETSUDUNG(MAPHONG INTEGER, MATB INTEGER, NGAYSUDUNG DATETIME, SOLUONG INTEGER, " +
                                "FOREIGN KEY(MAPHONG) REFERENCES PHONGHOC(MAPHONG), " +
                                "FOREIGN KEY(MATB) REFERENCES THIETBI(MATB), " +
                                "PRIMARY KEY(MAPHONG, MATB))");
        dataBase.queryData("DELETE FROM CHITIETSUDUNG");
        dataBase.queryData("DELETE FROM THIETBI");
        dataBase.queryData("DELETE FROM PHONGHOC");
        dataBase.queryData("DELETE FROM LOAITHIETBI");

        dataBase.queryData("INSERT INTO PHONGHOC VALUES(1, 'Ph??ng l?? thuy???t', 2)");
        dataBase.queryData("INSERT INTO PHONGHOC VALUES(2, 'Ph??ng l?? thuy???t', 1)");
        dataBase.queryData("INSERT INTO PHONGHOC VALUES(3, 'Ph??ng th???c h??nh', 2)");
        dataBase.queryData("INSERT INTO PHONGHOC VALUES(4, 'Ph??ng h???p b??o', 1)");
        dataBase.queryData("INSERT INTO PHONGHOC VALUES(5, 'Ph??ng h???i tr?????ng', 3)");
        dataBase.queryData("INSERT INTO PHONGHOC VALUES(6, 'Ph??ng h???i tr?????ng', 4)");

        dataBase.queryData("INSERT INTO LOAITHIETBI VALUES(1, 'D???NG C??? CHI???U S??NG')");
        dataBase.queryData("INSERT INTO LOAITHIETBI VALUES(2, 'D???NG C??? D???Y H???C')");
        dataBase.queryData("INSERT INTO LOAITHIETBI VALUES(3, 'D???NG C??? ??I???U H??A')");

        dataBase.queryData("INSERT INTO THIETBI VALUES(1, '????N HU???NH QUANG', 'Vi???t Nam', 1)");
        dataBase.queryData("INSERT INTO THIETBI VALUES(2, '??i???u h??a', 'Trung Qu???c', 3)");
        dataBase.queryData("INSERT INTO THIETBI VALUES(3, 'Qu???t treo t?????ng', 'L??o', 3)");
        dataBase.queryData("INSERT INTO THIETBI VALUES(4, 'M??y chi???u samsung', 'Nh???t B???n', 2)");
        dataBase.queryData("INSERT INTO THIETBI VALUES(5, 'PC lenovo', 'M???', 2)");

        dataBase.queryData("INSERT INTO CHITIETSUDUNG VALUES(1, 1, '1/1/2009', 5)");
        dataBase.queryData("INSERT INTO CHITIETSUDUNG VALUES(1, 2, '6/1/2010', 1)");
        dataBase.queryData("INSERT INTO CHITIETSUDUNG VALUES(1, 3, '7/1/2009', 2)");
        dataBase.queryData("INSERT INTO CHITIETSUDUNG VALUES(2, 1, '12/2/2009', 10)");
        dataBase.queryData("INSERT INTO CHITIETSUDUNG VALUES(2, 3, '12/2/2009', 8)");
        dataBase.queryData("INSERT INTO CHITIETSUDUNG VALUES(2, 4, '12/3/2010', 2)");
        dataBase.queryData("INSERT INTO CHITIETSUDUNG VALUES(3, 4, '3/3/2009', 45)");
        dataBase.queryData("INSERT INTO CHITIETSUDUNG VALUES(3, 2, '3/3/2009', 4)");
        dataBase.queryData("INSERT INTO CHITIETSUDUNG VALUES(3, 3, '3/3/2010', 4)");

    }
}