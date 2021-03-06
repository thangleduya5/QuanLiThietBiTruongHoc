package com.example.quanlithietbitruonghoc.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlithietbitruonghoc.R;
import com.example.quanlithietbitruonghoc.adapter.AdapterLoaiThietBi;
import com.example.quanlithietbitruonghoc.model.ChiTietSuDung;
import com.example.quanlithietbitruonghoc.model.LoaiThietBi;
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

public class LoaiThietBiActivity extends AppCompatActivity {
    ImageView ivTrolai, ivExport, ivThem, ivSearch, ivMenu;
    ListView lvDanhsach;
    EditText edtTenLoaiThietBi, edtSearchTB;
    ArrayList<LoaiThietBi> loaiThietBis;
    AdapterLoaiThietBi adapterLoaiThietBi;
    ImageView ivsua,ivxoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loaithietbi);
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
        loaiThietBis =  new ArrayList<>();
        adapterLoaiThietBi= new AdapterLoaiThietBi(this, R.layout.item_loaithietbi, loaiThietBis);
        lvDanhsach.setAdapter(adapterLoaiThietBi);
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
                    Toast.makeText(LoaiThietBiActivity.this, "T???o dpf th???t b???i: "+ e.toString(), Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(LoaiThietBiActivity.this, HomeActivity.class));
                        break;
                    case R.id.itemChungLoai:
                        break;
                    case R.id.itemThietBi:
                        startActivity(new Intent(LoaiThietBiActivity.this, ThietBiActivity.class));
                        break;
                    case R.id.itemCTSD:
                        startActivity(new Intent(LoaiThietBiActivity.this, ChiTietSuDungActivity.class));
                        break;
                    case R.id.itemPhongHoc:
                        startActivity(new Intent(LoaiThietBiActivity.this, PhongHocActivity.class));
                        break;
                    case R.id.itemThongKe:
                        startActivity(new Intent(LoaiThietBiActivity.this, StatiticsActivity.class));
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
        File file = new File(pdfPath, "LoaiThietBi.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);


        pdfDocument.setDefaultPageSize(PageSize.A4);
        document.setMargins(12, 12, 12, 12);

        Paragraph Title = new Paragraph("Danh sach loai thiet bi").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER);
        float[] width = {20f, 30f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setVerticalAlignment(VerticalAlignment.MIDDLE);

        table.addCell(new Cell().add(new Paragraph("Ma phong")));
        table.addCell(new Cell().add(new Paragraph("Ten loai")));

        for(LoaiThietBi k: loaiThietBis){
            table.addCell(new Cell().add(new Paragraph(k.getMaLoai()+"")));
            table.addCell(new Cell().add(new Paragraph(k.getTenLoai())));
        }
        document.add(Title);
        document.add(table);
        document.close();
        Toast.makeText(this, "???? t???o file pdf", Toast.LENGTH_SHORT).show();
    }

    public void Themdialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("ResourceType") View dialogView = inflater.inflate(R.layout.them_sua_loaithietbi_dialog,(ViewGroup) findViewById(R.layout.activity_loaithietbi));
        edtTenLoaiThietBi = dialogView.findViewById(R.id.edtTenLoaiThietBi);



        dialogBuilder.setPositiveButton("L??u", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tenloaiTB= edtTenLoaiThietBi.getText().toString().trim();
                if(checkTonTaiTenLoai(tenloaiTB)){
                    dialogInterface.dismiss();
                    showListPH();
                    Toast.makeText(LoaiThietBiActivity.this, "T??n lo???i ???? t???n t???i, vui l??ng nh???p t??n lo???i m???i", Toast.LENGTH_LONG).show();
                    return;
                }
                if(tenloaiTB.equals("") ){
                    dialogInterface.dismiss();
                    showListPH();
                    Toast.makeText(LoaiThietBiActivity.this, "Vui l??ng ??i???n ?????y ????? th??ng tin", Toast.LENGTH_LONG).show();
                }else{
                    HomeActivity.dataBase.queryData("INSERT INTO LOAITHIETBI VALUES(null,'"+tenloaiTB+"')");
                    Toast.makeText(LoaiThietBiActivity.this, "Th??m th??nh c??ng", Toast.LENGTH_LONG).show();
                    showListPH();
                    dialogInterface.cancel();
                }


            }
        });
        dialogBuilder.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("TH??M LO???I THI???T B???");
        AlertDialog a = dialogBuilder.create();
        a.show();
    }
    public void Suadialog(LoaiThietBi loaiThietBi) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("ResourceType") View dialogView = inflater.inflate(R.layout.them_sua_loaithietbi_dialog, (ViewGroup) findViewById(R.layout.activity_loaithietbi));
        edtTenLoaiThietBi = dialogView.findViewById(R.id.edtTenLoaiThietBi);


        edtTenLoaiThietBi.setText(loaiThietBi.getTenLoai());


        dialogBuilder.setPositiveButton("L??u", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tenloaiTB = edtTenLoaiThietBi.getText().toString().trim();

                if (tenloaiTB == null ) {
                    dialogInterface.dismiss();
                    showListPH();
                    Toast.makeText(LoaiThietBiActivity.this, "Vui l??ng ??i???n ?????y ????? th??ng tin", Toast.LENGTH_LONG).show();
                } else {
                    HomeActivity.dataBase.queryData("UPDATE LOAITHIETBI SET TENLOAI='" + tenloaiTB + "' WHERE MALOAI='" + loaiThietBi.getMaLoai() + "'");
                    Toast.makeText(LoaiThietBiActivity.this, "Th??m th??nh c??ng", Toast.LENGTH_LONG).show();
                    showListPH();
                    dialogInterface.cancel();
                }
            }
        });
        dialogBuilder.setNegativeButton("H???y", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("S???A LO???I THI???T B???");
        AlertDialog a = dialogBuilder.create();
        a.show();
    }

    public void Xoaltb(int maLoaiTB){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("C???nh b??o!");
        b.setMessage("B???n c?? ch???c ch???n mu???n x??a?");
        b.setPositiveButton("C??", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HomeActivity.dataBase.queryData("DELETE FROM LOAITHIETBI WHERE MALOAI='"+maLoaiTB+"'");
                showListPH();
                dialogInterface.cancel();
            }
        });
        b.setNegativeButton("Kh??ng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog al = b.create();
        al.show();
    }
    private void showListPH(){
        loaiThietBis.clear();
        Cursor dataThietBi=HomeActivity.dataBase.getData("SELECT * FROM LOAITHIETBI ORDER BY MALOAI ASC");
        while(dataThietBi.moveToNext()){
            loaiThietBis.add(new LoaiThietBi(dataThietBi.getInt(0), dataThietBi.getString(1)));
        }
        adapterLoaiThietBi.notifyDataSetChanged();
    }
    private void showListPHSearch(String serch){
        loaiThietBis.clear();
        Cursor dataThietBi=HomeActivity.dataBase.getData("SELECT * FROM LOAITHIETBI WHERE TENLOAI LIKE '%"+serch+"%'");
        while(dataThietBi.moveToNext()){
            loaiThietBis.add(new LoaiThietBi(dataThietBi.getInt(0), dataThietBi.getString(1)));
        }
        adapterLoaiThietBi.notifyDataSetChanged();
    }
    private Boolean checkTonTaiTenLoai(String tenLoai){
        Cursor data=HomeActivity.dataBase.getData("SELECT * FROM LOAITHIETBI WHERE TENLOAI = '"+ tenLoai +"'");
        if(data.moveToNext()){
            return true;
        }
        return false;
    }
}