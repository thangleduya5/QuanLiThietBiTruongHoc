package com.example.quanlithietbitruonghoc.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.quanlithietbitruonghoc.R;
import com.example.quanlithietbitruonghoc.adapter.ThongKe1Adapter;
import com.example.quanlithietbitruonghoc.model.ChiTietSuDung;
import com.example.quanlithietbitruonghoc.model.PhongHoc;
import com.example.quanlithietbitruonghoc.model.ThongKe1;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
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
import java.util.HashMap;

public class Statitics1Activity extends AppCompatActivity {

    TabHost tabHost;
    Spinner spLoaiPhong;
    ArrayList<String> loaiPhongs;
    ListView lv1;
    ThongKe1Adapter thongKe1Adapter;
    ArrayList<ThongKe1> thongKe1s;
    BarChart bc1;
    ImageView ivTroLai, ivExport, ivMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statitics1);
        setControl();
        initSpiner();
        addArr(0);
        thongKe1Adapter.notifyDataSetChanged();
        initBarChart();
        setEvent();
    }

    private void setEvent(){
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
                    Toast.makeText(Statitics1Activity.this, "Tạo dpf thất bại: "+ e.toString(), Toast.LENGTH_SHORT).show();
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
                        startActivity(new Intent(Statitics1Activity.this, HomeActivity.class));
                        break;
                    case R.id.itemChungLoai:
                        startActivity(new Intent(Statitics1Activity.this, LoaiThietBiActivity.class));
                        break;
                    case R.id.itemThietBi:
                        startActivity(new Intent(Statitics1Activity.this, ThietBiActivity.class));
                        break;
                    case R.id.itemCTSD:
                        startActivity(new Intent(Statitics1Activity.this, ChiTietSuDungActivity.class));
                        break;
                    case R.id.itemPhongHoc:
                        startActivity(new Intent(Statitics1Activity.this, PhongHocActivity.class));
                        break;
                    case R.id.itemThongKe:
                        startActivity(new Intent(Statitics1Activity.this, StatiticsActivity.class));
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
        File file = new File(pdfPath, "ThongKe1.pdf");
        OutputStream outputStream = new FileOutputStream(file);

        PdfWriter writer = new PdfWriter(file);
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document document = new Document(pdfDocument);


        pdfDocument.setDefaultPageSize(PageSize.A4);
        document.setMargins(12, 12, 12, 12);

        Paragraph Title = new Paragraph("Danh sach cac thiet bi su dung theo loai phong hoc").setBold().setFontSize(20).setTextAlignment(TextAlignment.CENTER);
        float[] width = {10f, 15f, 15f, 10f};
        Table table = new Table(width);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
        table.setVerticalAlignment(VerticalAlignment.MIDDLE);

        table.addCell(new Cell().add(new Paragraph("Ma phong")));
        table.addCell(new Cell().add(new Paragraph("Ten thiet bi")));
        table.addCell(new Cell().add(new Paragraph("Loai")));
        table.addCell(new Cell().add(new Paragraph("So luong")));

        for(ThongKe1 k: thongKe1s){
            table.addCell(new Cell().add(new Paragraph(k.getMaPhong()+"")));
            table.addCell(new Cell().add(new Paragraph(k.getTenTB()+"")));
            table.addCell(new Cell().add(new Paragraph(k.getTenLoai()+"")));
            table.addCell(new Cell().add(new Paragraph(k.getSoLuong()+"")));
        }
        document.add(Title);
        document.add(table);
        document.close();
        Toast.makeText(this, "Đã tạo file pdf", Toast.LENGTH_SHORT).show();
    }


    private void initSpiner(){
        loaiPhongs = PhongHoc.getAllLoaiPhong();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, loaiPhongs);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spLoaiPhong.setAdapter(adapter);
        spLoaiPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Statitics1Activity.this, loaiPhongs.get(i), Toast.LENGTH_SHORT).show();
                addArr(i);
                thongKe1Adapter.notifyDataSetChanged();
                initBarChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initBarChart(){
        bc1.clear();
        HashMap<String, Integer> map= new HashMap<>(getDataforBarChart());
        ArrayList<BarEntry> barEntries= new ArrayList<>();
        ArrayList<String> labels = new ArrayList<String>();

        int i=0;
        for(String k: map.keySet()){
            barEntries.add(new BarEntry(i, map.get(k), k));
            labels.add(k);
            i++;
        }

        XAxis xAxis = bc1.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        BarDataSet dataSet = new BarDataSet(barEntries, "Số lượng");
        BarData data = new BarData(dataSet);
        bc1.setData(data);
    }

    private HashMap<String, Integer> getDataforBarChart(){
        HashMap<String, Integer> map= new HashMap<>();
        for(ThongKe1 k: thongKe1s){
            String x = k.getTenTB();
            int y =k.getSoLuong();
            if (map.containsKey(x)) {
                map.put(x, map.get(x) + y);
            } else {
                map.put(x, y);
            }
        }
        return map;
    }

    private void addArr(int i){
        thongKe1s.clear();
        for(ThongKe1 k: ThongKe1.getDSThongKe1(loaiPhongs.get(i))){
            thongKe1s.add(k);
        }
    }

    private void setControl(){
        tabHost = (TabHost) findViewById(R.id.tabHost1);
        tabHost.setup();

        TabHost.TabSpec tabSpec1, tabSpec2;

        tabSpec1 = tabHost.newTabSpec("t1");
        tabSpec1.setContent(R.id.tab1);
        tabSpec1.setIndicator("Số liệu");
        tabHost.addTab(tabSpec1);

        tabSpec2 = tabHost.newTabSpec("t2");
        tabSpec2.setContent(R.id.tab2);
        tabSpec2.setIndicator("Biểu đồ");
        tabHost.addTab(tabSpec2);

        spLoaiPhong = (Spinner) findViewById(R.id.spLoaiPhong);
        lv1 = (ListView) findViewById(R.id.lv1);

        loaiPhongs = new ArrayList<>();
        thongKe1s = new ArrayList<>();
        thongKe1Adapter = new ThongKe1Adapter(this, R.layout.thongke1_layout, thongKe1s);
        lv1.setAdapter(thongKe1Adapter);

        bc1 = (BarChart) findViewById(R.id.bc1);

        ivTroLai =(ImageView) findViewById(R.id.ivTrolai);
        ivExport =(ImageView) findViewById(R.id.ivExport);
        ivMenu =(ImageView) findViewById(R.id.ivMenu);
    }
}

