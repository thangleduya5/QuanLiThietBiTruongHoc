package com.example.quanlithietbitruonghoc.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlithietbitruonghoc.R;
import com.example.quanlithietbitruonghoc.adapter.AdapterThietBi;
import com.example.quanlithietbitruonghoc.model.ChiTietSuDung;
import com.example.quanlithietbitruonghoc.model.LoaiThietBi;
import com.example.quanlithietbitruonghoc.model.ThietBi;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;
import com.itextpdf.layout.property.VerticalAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ThietBiActivity extends AppCompatActivity {
    ImageView ivTrolai, ivExport, ivThem, ivSearch, ivMenu;
    ListView lvDanhsach;
    EditText edtXuatXu,edtTenThietBi, edtSearchTB;
    Spinner spnTenLoai;
    ArrayList<ThietBi> thietBis;
    AdapterThietBi adapterThietBi;
    ImageView ivsua,ivxoa;
    ArrayList<LoaiThietBi> loaiThietBis;
    ArrayList<String> tenLoais;
    int maLoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thietbi);
        setControl();
        showListPH();
        setEvent();
    }
    private void setControl() {
        ivTrolai = findViewById(R.id.ivTrolai);
        ivExport = findViewById(R.id.ivExport);
        ivMenu = findViewById(R.id.ivMenu);
        ivThem = findViewById(R.id.ivThem);
        ivSearch = findViewById(R.id.ivSearch);
        lvDanhsach = findViewById(R.id.lvDanhsach);
        ivsua= findViewById(R.id.ivSua);
        ivxoa= findViewById(R.id.ivXoa);
        thietBis =  new ArrayList<>();
        adapterThietBi= new AdapterThietBi(this, R.layout.thietbi_item, thietBis);
        lvDanhsach.setAdapter(adapterThietBi);
        loaiThietBis = LoaiThietBi.getAll();
        tenLoais = new ArrayList<>();
        for(LoaiThietBi k: loaiThietBis){
            tenLoais.add(k.getTenLoai());
        }
    }

    private int getMaLoai(String tenLoai){
        for(LoaiThietBi k: loaiThietBis){
            if(k.getTenLoai().equals(tenLoai))
              return k.getMaLoai();
        }
        return -1;
    }

    private void setEvent(){
        ivThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Themdialog();
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edtSearchTB = findViewById(R.id.edtSearchTB);
                String SearchTB = edtSearchTB.getText().toString().trim();
                showListPHSearch(SearchTB);
            }
        });
        ivTrolai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivExport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createPDF();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(ThietBiActivity.this, "Tạo dpf thất bại: "+ e.toString(), Toast.LENGTH_SHORT).show();
                }
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
                        startActivity(new Intent(ThietBiActivity.this, HomeActivity.class));
                        break;
                    case R.id.itemChungLoai:
                        startActivity(new Intent(ThietBiActivity.this, LoaiThietBiActivity.class));
                        break;
                    case R.id.itemThietBi:
                        break;
                    case R.id.itemCTSD:
                        startActivity(new Intent(ThietBiActivity.this, ChiTietSuDungActivity.class));
                        break;
                    case R.id.itemPhongHoc:
                        startActivity(new Intent(ThietBiActivity.this, PhongHocActivity.class));
                        break;
                    case R.id.itemThongKe:
                        startActivity(new Intent(ThietBiActivity.this, StatiticsActivity.class));
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        popupMenu.show();
    }
    private void createPDF() throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
        File file = new File(pdfPath, "ThietBi.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);


        pdfDocument.setDefaultPageSize(PageSize.A4);
        document.setMargins(12, 12, 12, 12);

        Paragraph Title = new Paragraph("Danh sach thiet bi").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER);
        float[] width = {10f, 15f, 15f, 10f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setVerticalAlignment(VerticalAlignment.MIDDLE);

        table.addCell(new Cell().add(new Paragraph("Ma thiet bi")));
        table.addCell(new Cell().add(new Paragraph("Ten thiet bi")));
        table.addCell(new Cell().add(new Paragraph("Xuat xu")));
        table.addCell(new Cell().add(new Paragraph("Ma loai")));

        for(ThietBi k: thietBis){
            table.addCell(new Cell().add(new Paragraph(k.getMaTB()+"")));
            table.addCell(new Cell().add(new Paragraph(k.getTenTB()+"")));
            table.addCell(new Cell().add(new Paragraph(k.getXuatXu()+"")));
            table.addCell(new Cell().add(new Paragraph(k.getMaLoai()+"")));
        }
        document.add(Title);
        document.add(table);
        document.close();
        Toast.makeText(this, "Đã tạo file pdf", Toast.LENGTH_SHORT).show();
    }
    public void Themdialog(){
        maLoai = thietBis.get(0).getMaLoai();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("ResourceType") View dialogView = inflater.inflate(R.layout.them_sua_thietbi_dialog,(ViewGroup) findViewById(R.layout.activity_thietbi));
        edtTenThietBi = dialogView.findViewById(R.id.edtTenThietBi);
        edtXuatXu = dialogView.findViewById(R.id.edtXuatXu);
        spnTenLoai = dialogView.findViewById(R.id.spnTenLoai);
        ArrayAdapter adapter1= new ArrayAdapter(this, android.R.layout.simple_spinner_item, tenLoais);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTenLoai.setAdapter(adapter1);
        spnTenLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maLoai = getMaLoai(tenLoais.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        dialogBuilder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tenTB= edtTenThietBi.getText().toString().trim();
                String xuatXu= edtXuatXu.getText().toString().trim();
                String tenLoai= spnTenLoai.getSelectedItem().toString().trim();

                if(tenTB.equals("") || xuatXu.equals("")){
                    dialogInterface.dismiss();
                    Toast.makeText(ThietBiActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
                }else{
                    HomeActivity.dataBase.queryData("INSERT INTO THIETBI VALUES(null,'"+tenTB+"','"+xuatXu+"','"+maLoai+"')");
                    Toast.makeText(ThietBiActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                    dialogInterface.cancel();
                }
                showListPH();
            }
        });
        dialogBuilder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("THÊM THIẾT BỊ");
        AlertDialog a = dialogBuilder.create();
        a.show();
    }

    public void Suadialog(ThietBi thietBi) {
        maLoai = thietBis.get(0).getMaLoai();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("ResourceType") View dialogView = inflater.inflate(R.layout.them_sua_thietbi_dialog, (ViewGroup) findViewById(R.layout.activity_thietbi));
        edtTenThietBi = dialogView.findViewById(R.id.edtTenThietBi);
        edtXuatXu = dialogView.findViewById(R.id.edtXuatXu);
        spnTenLoai = dialogView.findViewById(R.id.spnTenLoai);

        edtTenThietBi.setText(thietBi.getTenTB());
        edtXuatXu.setText(thietBi.getXuatXu());

        ArrayAdapter adapter1= new ArrayAdapter(this, android.R.layout.simple_spinner_item, tenLoais);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTenLoai.setAdapter(adapter1);

        spnTenLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                maLoai = getMaLoai(tenLoais.get(i));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        dialogBuilder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tenTB = edtTenThietBi.getText().toString().trim();
                String xuatXu = edtXuatXu.getText().toString().trim();

                if (tenTB.equals("") || xuatXu.equals("")) {
                    dialogInterface.dismiss();
                    Toast.makeText(ThietBiActivity.this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_LONG).show();
                } else {
                    HomeActivity.dataBase.queryData("UPDATE THIETBI SET TENTB='" + tenTB + "',XUATXU='" + xuatXu + "', MALOAI='" + maLoai + "' WHERE MATB='" + thietBi.getMaTB() + "'");
                    Toast.makeText(ThietBiActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                    dialogInterface.cancel();
                }
                showListPH();
            }
        });
        dialogBuilder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("SỬA THIẾT BỊ");
        AlertDialog a = dialogBuilder.create();
        a.show();
        }
    public void Xoatb(int maTB){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Cảnh báo!");
        b.setMessage("Bạn có chắc chắn muốn xóa?");
        b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HomeActivity.dataBase.queryData("DELETE FROM THIETBI WHERE MATB='"+maTB+"'");
                showListPH();
                dialogInterface.cancel();
            }
        });
        b.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog al = b.create();
        al.show();
    }
    private void showListPH(){
        thietBis.clear();
        Cursor dataThietBi=HomeActivity.dataBase.getData("SELECT * FROM THIETBI ORDER BY MATB ASC");
        while(dataThietBi.moveToNext()){
            thietBis.add(new ThietBi(dataThietBi.getInt(0), dataThietBi.getString(1), dataThietBi.getString(2), dataThietBi.getInt(3)));
        }
        adapterThietBi.notifyDataSetChanged();
    }
    private void showListPHSearch(String serch){
        thietBis.clear();
        Cursor dataThietBi=HomeActivity.dataBase.getData("SELECT * FROM THIETBI WHERE TENTB LIKE '%"+serch+"%'");
        while(dataThietBi.moveToNext()){
            thietBis.add(new ThietBi(dataThietBi.getInt(0), dataThietBi.getString(1), dataThietBi.getString(2), dataThietBi.getInt(3)));
        }
        adapterThietBi.notifyDataSetChanged();
    }

}
