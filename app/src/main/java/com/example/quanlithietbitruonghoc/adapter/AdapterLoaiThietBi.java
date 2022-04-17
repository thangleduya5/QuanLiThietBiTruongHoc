package com.example.quanlithietbitruonghoc.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanlithietbitruonghoc.R;
import com.example.quanlithietbitruonghoc.activity.LoaiThietBiActivity;
import com.example.quanlithietbitruonghoc.model.LoaiThietBi;

import java.util.ArrayList;

public class AdapterLoaiThietBi extends ArrayAdapter<LoaiThietBi>{
    Context context;
    ArrayList<LoaiThietBi> loaiThietBis;
    int resource;

    public AdapterLoaiThietBi(@NonNull Context context, int resource, @NonNull ArrayList<LoaiThietBi> loaiThietBi) {
        super(context, resource, loaiThietBi);
        this.context= context;
        this.resource= resource;
        this.loaiThietBis= loaiThietBi;
    }

    @Override
    public int getCount(){return loaiThietBis.size();}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        convertView= LayoutInflater.from(context).inflate(resource, null);
        TextView tvTenLoaiThietBi= convertView.findViewById(R.id.tvTenLoaiThietBi);
        ImageView ivsua= convertView.findViewById(R.id.ivSua);
        ImageView ivxoa= convertView.findViewById(R.id.ivXoa);

        LoaiThietBi loaiThietBi= loaiThietBis.get(position);
        tvTenLoaiThietBi.setText(loaiThietBi.getTenLoai());
        ivsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoaiThietBiActivity)context).Suadialog(loaiThietBi);
            }
        });

        ivxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((LoaiThietBiActivity)context).Xoaltb(loaiThietBi.getMaLoai());
            }
        });

        return convertView;
    }
}
