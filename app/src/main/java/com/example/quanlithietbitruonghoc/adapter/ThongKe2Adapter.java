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
import com.example.quanlithietbitruonghoc.model.ThongKe2;

import java.util.ArrayList;

public class ThongKe2Adapter extends ArrayAdapter<ThongKe2> {
    Context context;
    ArrayList<ThongKe2> thongKe2s;
    int resource;
    public ThongKe2Adapter(@NonNull Context context, int resource, @NonNull ArrayList<ThongKe2> objects) {
        super(context, resource, objects);
        this.context=context;
        this.thongKe2s=objects;
        this.resource=resource;
    }

    @Override
    public int getCount() {
        return thongKe2s.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(resource, null);

        TextView tvTenTB= convertView.findViewById(R.id.tvTenTBs);
        TextView tvTenLoai= convertView.findViewById(R.id.tvTenLoais);
        TextView tvSoluong= convertView.findViewById(R.id.tvSoLuongs);

        ThongKe2 thongKe2= thongKe2s.get(position);

        tvTenTB.setText(thongKe2.getTenTB());
        tvTenLoai.setText(thongKe2.getTenLoai());
        tvSoluong.setText(String.valueOf(thongKe2.getSoLuong()));

        return convertView;
    }
}