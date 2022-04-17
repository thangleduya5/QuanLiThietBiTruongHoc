package com.example.quanlithietbitruonghoc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.example.quanlithietbitruonghoc.R;

public class StatiticsActivity extends AppCompatActivity {

    Button btnThongKe1, btnThongKe2;
    ImageView ivTroLai, ivMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statitics);
        setControl();
        setEvent();
    }

    private void setEvent(){
        btnThongKe1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(StatiticsActivity.this, Statitics1Activity.class);
                startActivity(intent);
            }
        });

        btnThongKe2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(StatiticsActivity.this, Statitics2Activity.class);
                startActivity(intent);
            }
        });
        ivTroLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu();
            }
        });
    }

    public void showMenu(){
        PopupMenu popupMenu = new PopupMenu(this, ivMenu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.itemTrangChu:
                        startActivity(new Intent(StatiticsActivity.this, HomeActivity.class));
                        break;
                    case R.id.itemChungLoai:
                        startActivity(new Intent(StatiticsActivity.this, LoaiThietBiActivity.class));
                        break;
                    case R.id.itemThietBi:
                        startActivity(new Intent(StatiticsActivity.this, ThietBiActivity.class));
                        break;
                    case R.id.itemCTSD:
                        startActivity(new Intent(StatiticsActivity.this, ChiTietSuDungActivity.class));
                        break;
                    case R.id.itemPhongHoc:
                        startActivity(new Intent(StatiticsActivity.this, PhongHocActivity.class));
                        break;
                    case R.id.itemThongKe:
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        popupMenu.show();
    }


    private void setControl(){
        btnThongKe1 = (Button) findViewById(R.id.btnThongKe1);
        btnThongKe2 = (Button) findViewById(R.id.btnThongKe2);
        ivTroLai = (ImageView) findViewById(R.id.ivTrolai);
        ivMenu = (ImageView) findViewById(R.id.ivMenu);
    }
}