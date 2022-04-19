package com.example.quanlithietbitruonghoc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlithietbitruonghoc.R;
import com.example.quanlithietbitruonghoc.model.ThongKe1;

import java.util.ArrayList;

public class ThongKe1Adapter extends ArrayAdapter<ThongKe1> {
    Context context;
    ArrayList<ThongKe1> thongKe1s;
    int resource;
    public ThongKe1Adapter(@NonNull Context context, int resource, @NonNull ArrayList<ThongKe1> objects) {
        super(context, resource, objects);
        this.context=context;
        this.thongKe1s=objects;
        this.resource=resource;
    }

    @Override
    public int getCount() {
        return thongKe1s.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(resource, null);

        TextView tvMaPhong= convertView.findViewById(R.id.tvMaPhong);
        TextView tvTenTB= convertView.findViewById(R.id.tvTenTB);
        TextView tvTenLoai= convertView.findViewById(R.id.tvTenLoai);
        TextView tvSoluong= convertView.findViewById(R.id.tvSoLuong);

        ThongKe1 thongKe1= thongKe1s.get(position);

        tvMaPhong.setText(String.valueOf(thongKe1.getMaPhong()));
        tvTenTB.setText(thongKe1.getTenTB());
        tvTenLoai.setText(thongKe1.getTenLoai());
        tvSoluong.setText(String.valueOf(thongKe1.getSoLuong()));

        return convertView;
    }
}
