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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlithietbitruonghoc.R;
import com.example.quanlithietbitruonghoc.adapter.AdapterPhonghoc;
import com.example.quanlithietbitruonghoc.model.ChiTietSuDung;
import com.example.quanlithietbitruonghoc.model.PhongHoc;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PhongHocActivity extends AppCompatActivity {

    ImageView ivTrolai, ivExport, ivMenu;
    Button btnThem;
    ListView lvDanhsach;
    EditText edtMaphong, edtSearchMaPhong;
    Spinner spnLoaiphong, spnTang;
    ArrayList<PhongHoc> phonghocs;
    AdapterPhonghoc adapterPhonghoc;
    ImageView ivsua,ivxoa, ivSearchPhong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phonghoc);
        setControl();
        showListPH();
        setEvent();

    }

    private void setControl() {
        ivTrolai = findViewById(R.id.ivTrolai);
        ivExport = findViewById(R.id.ivExport);
        ivMenu = findViewById(R.id.ivMenu);
        btnThem= findViewById(R.id.btnThem);
        lvDanhsach = findViewById(R.id.lvDanhsach);
        ivsua= findViewById(R.id.ivSua);
        ivxoa= findViewById(R.id.ivXoa);
        ivSearchPhong = findViewById(R.id.ivSearch);
        edtSearchMaPhong = findViewById(R.id.edtSearchPhong);
        phonghocs =  new ArrayList<>();
        adapterPhonghoc= new AdapterPhonghoc(this, R.layout.phonghoc_item, phonghocs);
        lvDanhsach.setAdapter(adapterPhonghoc);

    }

    private void setEvent(){
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Themdialog();
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
                    Toast.makeText(PhongHocActivity.this, "Tạo dpf thất bại: "+ e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivSearchPhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maPhong = edtSearchMaPhong.getText().toString().trim();
                if(!maPhong.equals("")) {
                    showListPHSearch(Integer.parseInt(maPhong));
                } else{
                    showListPH();
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
                        startActivity(new Intent(PhongHocActivity.this, HomeActivity.class));
                        break;
                    case R.id.itemChungLoai:
                        startActivity(new Intent(PhongHocActivity.this, LoaiThietBiActivity.class));
                        break;
                    case R.id.itemThietBi:
                        startActivity(new Intent(PhongHocActivity.this, ThietBiActivity.class));
                        break;
                    case R.id.itemCTSD:
                        startActivity(new Intent(PhongHocActivity.this, ChiTietSuDungActivity.class));
                        break;
                    case R.id.itemPhongHoc:
                        break;
                    case R.id.itemThongKe:
                        startActivity(new Intent(PhongHocActivity.this, StatiticsActivity.class));
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
        File file = new File(pdfPath, "PhongHoc.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);


        pdfDocument.setDefaultPageSize(PageSize.A4);
        document.setMargins(12, 12, 12, 12);

        Paragraph Title = new Paragraph("Danh sach phong hoc").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER);
        float[] width = {10f, 30f, 10f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setVerticalAlignment(VerticalAlignment.MIDDLE);

        table.addCell(new Cell().add(new Paragraph("Ma phong")));
        table.addCell(new Cell().add(new Paragraph("Loai phong")));
        table.addCell(new Cell().add(new Paragraph("Tang")));

        for(PhongHoc k: phonghocs){
            table.addCell(new Cell().add(new Paragraph(k.getMaPhong()+"")));
            table.addCell(new Cell().add(new Paragraph(k.getLoaiPhong()+"")));
            table.addCell(new Cell().add(new Paragraph(k.getTang()+"")));
        }
        document.add(Title);
        document.add(table);
        document.close();
        Toast.makeText(this, "Đã tạo file pdf", Toast.LENGTH_SHORT).show();
    }


    public void Themdialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("ResourceType") View dialogView = inflater.inflate(R.layout.them_sua_phonghoc_dialog,(ViewGroup) findViewById(R.layout.phonghoc));
        edtMaphong = dialogView.findViewById(R.id.edtMaphong);
        spnLoaiphong = dialogView.findViewById(R.id.spnLoaiphong);
        spnTang = dialogView.findViewById(R.id.spnTang);


        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this, R.array.loaiphong, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoaiphong.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(this, R.array.tang, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTang.setAdapter(adapter2);

        dialogBuilder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String maPH= edtMaphong.getText().toString().trim();
                String loaiPh= spnLoaiphong.getSelectedItem().toString().trim();
                int tang= Integer.parseInt(spnTang.getSelectedItem().toString());

                if(checkPh(maPH) == false){
                    dialogInterface.dismiss();
                    showListPH();
                    Toast.makeText(PhongHocActivity.this, "Phòng đã tồn tại", Toast.LENGTH_LONG).show();
                }else{
                    HomeActivity.dataBase.queryData("INSERT INTO PHONGHOC VALUES('"+maPH+"','"+loaiPh+"','"+tang+"')");
                    Toast.makeText(PhongHocActivity.this, "Thêm thành công", Toast.LENGTH_LONG).show();
                    showListPH();
                    dialogInterface.cancel();
                }
            }
        });
        dialogBuilder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("THÊM PHÒNG HỌC");
        AlertDialog a = dialogBuilder.create();
        a.show();
    }

    public void Suadialog(PhongHoc phonghoc){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        @SuppressLint("ResourceType") View dialogView = inflater.inflate(R.layout.them_sua_phonghoc_dialog,(ViewGroup) findViewById(R.layout.phonghoc));
        edtMaphong = dialogView.findViewById(R.id.edtMaphong);
        spnLoaiphong = dialogView.findViewById(R.id.spnLoaiphong);
        spnTang = dialogView.findViewById(R.id.spnTang);

        edtMaphong.setText(phonghoc.getMaPhong());

        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this, R.array.loaiphong, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnLoaiphong.setAdapter(adapter1);

        ArrayAdapter<CharSequence> adapter2= ArrayAdapter.createFromResource(this, R.array.tang, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTang.setAdapter(adapter2);

        dialogBuilder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String maPH= edtMaphong.getText().toString().trim();
                String loaiPh= spnLoaiphong.getSelectedItem().toString().trim();
                int tang= Integer.parseInt(spnTang.getSelectedItem().toString());
                HomeActivity.dataBase.queryData("UPDATE PHONGHOC SET LOAIPHONG='"+loaiPh+"',TANG='"+tang+"' WHERE MAPHONG='"+maPH+"'");
                Toast.makeText(PhongHocActivity.this, "Sửa thành công", Toast.LENGTH_LONG).show();
                showListPH();
                dialogInterface.cancel();
            }
        });
        dialogBuilder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("SỬA PHÒNG HỌC");
        AlertDialog a = dialogBuilder.create();
        a.show();
    }

    public void Xoamh(String maPh){
        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Cảnh báo!");
        b.setMessage("Bạn có chắc chắn muốn xóa?");
        b.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HomeActivity.dataBase.queryData("DELETE FROM PHONGHOC WHERE MAPHONG='"+maPh+"'");
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
        phonghocs.clear();
        Cursor dataPhonghoc=HomeActivity.dataBase.getData("SELECT * FROM PHONGHOC ORDER BY MAPHONG ASC");
        while(dataPhonghoc.moveToNext()){
            phonghocs.add(new PhongHoc(dataPhonghoc.getString(0), dataPhonghoc.getString(1), dataPhonghoc.getInt(2)));
        }
        adapterPhonghoc.notifyDataSetChanged();
    }

    private boolean checkPh(String maPh){
        Cursor check = HomeActivity.dataBase.getData("SELECT * FROM PHONGHOC WHERE MAPHONG= " +maPh);
        if(check.moveToNext()){
            return false;
        }else{
            return true;
        }
    }

    private void showListPHSearch(int maPhong){
        phonghocs.clear();
        Cursor dataPhonghoc=HomeActivity.dataBase.getData("SELECT * FROM PHONGHOC WHERE MAPHONG = " +maPhong);
        while(dataPhonghoc.moveToNext()){
            phonghocs.add(new PhongHoc(dataPhonghoc.getString(0), dataPhonghoc.getString(1), dataPhonghoc.getInt(2)));
        }
        adapterPhonghoc.notifyDataSetChanged();
    }
}