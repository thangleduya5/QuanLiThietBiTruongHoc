package com.example.quanlithietbitruonghoc.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quanlithietbitruonghoc.R;
import com.example.quanlithietbitruonghoc.adapter.ChiTietSuDungAdapter;
import com.example.quanlithietbitruonghoc.model.ChiTietSuDung;
import com.example.quanlithietbitruonghoc.model.PhongHoc;
import com.example.quanlithietbitruonghoc.model.ThietBi;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfName;
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
import java.util.Calendar;
import java.util.Date;

public class ChiTietSuDungActivity extends AppCompatActivity {
    ChiTietSuDungAdapter chiTietSuDungAdapter;
    ArrayList<ChiTietSuDung> chiTietSuDungs;
    ImageView ivThem, ivTroLai, ivExport, ivSearch, ivMenu;
    ListView listviewDS;
    TextView titleAction;
    EditText edMaPhong;
    EditText edMaThietBi;
    EditText edSoLuong;
    EditText etNgaySD;
    EditText edtSearchPhong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsudung);
        setControl();
        getData();

        ivThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog("Thêm Chi Tiết Sử Dụng", "Thêm", null);
            }
        });
        ivTroLai.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(ChiTietSuDungActivity.this, "Tạo dpf thất bại: "+ e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maPhong = edtSearchPhong.getText().toString().trim();
                if(!maPhong.equals("")) {
                    showListPHSearch(Integer.parseInt(maPhong));
                } else{
                    getData();
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
    public void setControl() {
        listviewDS = (ListView) findViewById(R.id.lvDanhsach);
        ivThem = (ImageView) findViewById(R.id.ivThem);
        ivTroLai = (ImageView) findViewById(R.id.ivTrolai);
        ivExport = (ImageView) findViewById(R.id.ivExport);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
        ivMenu = (ImageView) findViewById(R.id.ivMenu);
        edtSearchPhong = (EditText) findViewById(R.id.edtSearchPhong);
        chiTietSuDungs = new ArrayList<>();
        chiTietSuDungAdapter = new ChiTietSuDungAdapter(this, R.layout.chitietsudung_item, chiTietSuDungs);
        listviewDS.setAdapter(chiTietSuDungAdapter);
    }
    public void showMenu(){
        PopupMenu popupMenu = new PopupMenu(this, ivMenu);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.itemTrangChu:
                        startActivity(new Intent(ChiTietSuDungActivity.this, HomeActivity.class));
                        break;
                    case R.id.itemChungLoai:
                        startActivity(new Intent(ChiTietSuDungActivity.this, LoaiThietBiActivity.class));
                        break;
                    case R.id.itemThietBi:
                        startActivity(new Intent(ChiTietSuDungActivity.this, ThietBiActivity.class));
                        break;
                    case R.id.itemCTSD:
                        break;
                    case R.id.itemPhongHoc:
                        startActivity(new Intent(ChiTietSuDungActivity.this, PhongHocActivity.class));
                        break;
                    case R.id.itemThongKe:
                        startActivity(new Intent(ChiTietSuDungActivity.this, StatiticsActivity.class));
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
        File file = new File(pdfPath, "ChiTietSuDung.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);


        pdfDocument.setDefaultPageSize(PageSize.A4);
        document.setMargins(12, 12, 12, 12);

        Paragraph Title = new Paragraph("Danh sach chi tiet su dung").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER);
        float[] width = {5f, 15f, 5f, 10f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setVerticalAlignment(VerticalAlignment.MIDDLE);

        table.addCell(new Cell().add(new Paragraph("Ma phong")));
        table.addCell(new Cell().add(new Paragraph("Ten thiet bi")));
        table.addCell(new Cell().add(new Paragraph("So luong")));
        table.addCell(new Cell().add(new Paragraph("Ngay su dung")));

        for(ChiTietSuDung k: chiTietSuDungs){
            table.addCell(new Cell().add(new Paragraph(k.getMaPhong()+"")));
            table.addCell(new Cell().add(new Paragraph(k.getMaTB()+"")));
            table.addCell(new Cell().add(new Paragraph(k.getSoLuong()+"")));
            table.addCell(new Cell().add(new Paragraph(new SimpleDateFormat("dd/MM/yyyy").format(k.getNgaySuDung()))));
        }
        document.add(Title);
        document.add(table);
        document.close();
        Toast.makeText(this, "Đã tạo file pdf", Toast.LENGTH_SHORT).show();
    }
    private void getData(){
        chiTietSuDungs.clear();
        Cursor dataCTSD = HomeActivity.dataBase.getData("SELECT * FROM CHITIETSUDUNG");
        while(dataCTSD.moveToNext()){
            try {
                chiTietSuDungs.add(new ChiTietSuDung(dataCTSD.getInt(0), dataCTSD.getInt(1), new SimpleDateFormat("dd/MM/yyyy").parse(dataCTSD.getString(2)),dataCTSD.getInt(3)));
                chiTietSuDungAdapter.notifyDataSetChanged();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
    public void Dialog(String name, String action, ChiTietSuDung chiTietSuDung) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.them_sua_ctsd_dialog);
        titleAction = (TextView) dialog.findViewById(R.id.titleAction);
        edMaPhong = (EditText) dialog.findViewById(R.id.etMaPhong);
        edMaThietBi = (EditText) dialog.findViewById(R.id.etMaThietBi);
        edSoLuong = (EditText) dialog.findViewById(R.id.etsoLuong);
        etNgaySD = (EditText) dialog.findViewById(R.id.etngaySD);
        Button btnThem = (Button) dialog.findViewById(R.id.btnThem);
        Button btnhuy = (Button) dialog.findViewById(R.id.btnHuy);
        titleAction.setText(name);
        btnThem.setText(action);

        if(chiTietSuDung != null) {
            edMaPhong.setText(String.valueOf(chiTietSuDung.getMaPhong()));
            edMaThietBi.setText(String.valueOf(chiTietSuDung.getMaTB()));
            edSoLuong.setText(String.valueOf(chiTietSuDung.getSoLuong()));
            etNgaySD.setText(new SimpleDateFormat("dd/MM/yyyy").format(chiTietSuDung.getNgaySuDung()));
            edMaPhong.setEnabled(false);
            edMaThietBi.setEnabled(false);
        }
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        etNgaySD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgay();
            }
        });
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String maPhong = edMaPhong.getText().toString().trim();
                String maThietBi = edMaThietBi.getText().toString().trim();
                String ngaySD = etNgaySD.getText().toString().trim();
                String soLuongs = edSoLuong.getText().toString();
                if(maPhong.equals("")){
                    Toast.makeText(ChiTietSuDungActivity.this, "Vui lòng nhập mã phòng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(maThietBi.equals("")){
                    Toast.makeText(ChiTietSuDungActivity.this, "Vui lòng nhập mã thiết bị", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(soLuongs.equals("")){
                    Toast.makeText(ChiTietSuDungActivity.this, "Vui lòng nhập số lượng", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(ngaySD.equals("")){
                    Toast.makeText(ChiTietSuDungActivity.this, "Vui lòng nhập ngày sử dụng", Toast.LENGTH_SHORT).show();
                    return;
                }
                int soLuong = Integer.parseInt(soLuongs);
                if(chiTietSuDung != null) {
                   update(soLuong, ngaySD, Integer.parseInt(edMaPhong.getText().toString()), Integer.parseInt(edMaThietBi.getText().toString()));
                    dialog.dismiss();
                }else {
                    Toast.makeText(ChiTietSuDungActivity.this, maPhong, Toast.LENGTH_SHORT).show();
                    if(!PhongHoc.checkTonTaiMaPhong(Integer.parseInt(maPhong))){
                        Toast.makeText(ChiTietSuDungActivity.this, "Mã phòng không tồn tại", Toast.LENGTH_SHORT).show();
                        edMaPhong.requestFocus();
                        return;
                    }
                    if(!ThietBi.checkTonTaiMaThietBi(Integer.parseInt(maThietBi))){
                        Toast.makeText(ChiTietSuDungActivity.this, "Mã thiết bị không tồn tại", Toast.LENGTH_SHORT).show();
                        edMaThietBi.requestFocus();
                        return;
                    }
                    if(check(Integer.parseInt(maPhong), Integer.parseInt(maThietBi))) {
                        insert(Integer.parseInt(maPhong), Integer.parseInt(maThietBi), ngaySD, soLuong);
                        dialog.dismiss();
                    }else {
                        Toast.makeText(ChiTietSuDungActivity.this, "Mã phòng và mã thiết bị đã tồn tại", Toast.LENGTH_SHORT).show();
                        edMaPhong.requestFocus();
                    }
                }
            }
        });

        dialog.show();
    }
    private void ChonNgay() {
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                etNgaySD.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }
    private boolean check(int maPH, int maTB){
        Cursor check = HomeActivity.dataBase.getData("SELECT * FROM CHITIETSUDUNG WHERE MAPHONG='"+maPH+"' AND MATB='"+maTB+"'");
        if(check.moveToNext()){
            return false;
        }else{
            return true;
        }
    }
    public void update(int soLuong, String ngaySd, int maPhong, int maThietBi) {
        HomeActivity.dataBase.queryData("UPDATE CHITIETSUDUNG SET NGAYSUDUNG= '"+ngaySd+"', SOLUONG = "+soLuong+" WHERE MAPHONG = '"+maPhong+"' AND MATB = '"+maThietBi+"'");
        Toast.makeText(this, "Đã Cập Nhật", Toast.LENGTH_SHORT).show();
        getData();
    }

    public void insert(int maPhong, int maThietBi, String ngaySD, int soLuong){
        HomeActivity.dataBase.queryData("INSERT INTO CHITIETSUDUNG VALUES('"+maPhong+"', '"+maThietBi+"', '"+ngaySD+"', "+soLuong+")");
        Toast.makeText(this, "Thêm Thành công", Toast.LENGTH_SHORT).show();
        getData();
    }
    public void Xoa(int maPhong, int maThietBi){
        AlertDialog.Builder confirm = new AlertDialog.Builder(this);
        confirm.setTitle("Cảnh báo!");
        confirm.setMessage("Bạn có chắc chắn muốn xóa?");
        confirm.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                HomeActivity.dataBase.queryData("DELETE FROM CHITIETSUDUNG  WHERE MAPHONG = '"+maPhong+"' AND MATB = '"+maThietBi+"'");
                getData();
                dialogInterface.cancel();
            }
        });
        confirm.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog al = confirm.create();
        al.show();
    }

    private void showListPHSearch(int maPhong){
        chiTietSuDungs.clear();
        Cursor dataCTSD=HomeActivity.dataBase.getData("SELECT * FROM CHITIETSUDUNG WHERE MAPHONG = " + maPhong);
        while(dataCTSD.moveToNext()){
            try {
                chiTietSuDungs.add(new ChiTietSuDung(dataCTSD.getInt(0), dataCTSD.getInt(1), new SimpleDateFormat("dd/MM/yyyy").parse(dataCTSD.getString(2)),dataCTSD.getInt(3)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        chiTietSuDungAdapter.notifyDataSetChanged();
    }
}