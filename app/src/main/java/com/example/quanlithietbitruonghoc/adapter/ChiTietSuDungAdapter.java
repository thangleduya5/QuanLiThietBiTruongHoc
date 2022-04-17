package com.example.quanlithietbitruonghoc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanlithietbitruonghoc.R;
import com.example.quanlithietbitruonghoc.activity.ChiTietSuDungActivity;
import com.example.quanlithietbitruonghoc.model.ChiTietSuDung;

import java.text.SimpleDateFormat;
import java.util.List;

public class ChiTietSuDungAdapter extends BaseAdapter {
    private ChiTietSuDungActivity context;
    private int layout;
    private List<ChiTietSuDung> chiTietSuDungs;

    public ChiTietSuDungAdapter(ChiTietSuDungActivity context, int layout, List<ChiTietSuDung> chiTietSuDungs) {
        this.context = context;
        this.layout = layout;
        this.chiTietSuDungs = chiTietSuDungs;
    }

    @Override
    public int getCount() {
        return chiTietSuDungs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public class ViewHolder {
        TextView tvMaPhong, tvMaThietBi, tvNgaySuDung, tvSoLuong;
        ImageView update, delete;

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater)  context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder.tvMaPhong = (TextView) view.findViewById(R.id.tvMaPhong);
            viewHolder.tvMaThietBi = (TextView) view.findViewById(R.id.tvMaThietBi);
            viewHolder.tvNgaySuDung = (TextView) view.findViewById(R.id.tvNgaySuDung);
            viewHolder.tvSoLuong = (TextView) view.findViewById(R.id.tvSoLuong);
            viewHolder.update = (ImageView) view.findViewById(R.id.ivSua);
            viewHolder.delete = (ImageView) view.findViewById(R.id.ivXoa);
            view.setTag(viewHolder);

        }else  {
            viewHolder = (ViewHolder) view.getTag();
        }

        ChiTietSuDung chiTietSuDung = chiTietSuDungs.get(i);
        viewHolder.tvMaPhong.setText(String.valueOf(chiTietSuDung.getMaPhong()));
        viewHolder.tvMaThietBi.setText(String.valueOf(chiTietSuDung.getMaTB()));
        viewHolder.tvSoLuong.setText(String.valueOf(chiTietSuDung.getSoLuong()));
        viewHolder.tvNgaySuDung.setText( new SimpleDateFormat("dd/MM/yyyy").format(chiTietSuDung.getNgaySuDung()));

        viewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.Dialog("Cập Nhật Chi Tiết Sử Dụng", "Sửa", chiTietSuDungs.get(i));
            }
        });
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.Xoa(chiTietSuDungs.get(i).getMaPhong(), chiTietSuDungs.get(i).getMaTB());
            }
        });
        return view;
    }
}
