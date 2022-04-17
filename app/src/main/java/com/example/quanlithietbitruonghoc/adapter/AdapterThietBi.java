
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
import com.example.quanlithietbitruonghoc.activity.ThietBiActivity;
import com.example.quanlithietbitruonghoc.model.LoaiThietBi;
import com.example.quanlithietbitruonghoc.model.ThietBi;

import java.util.ArrayList;

public class AdapterThietBi extends ArrayAdapter<ThietBi>{
    Context context;
    ArrayList<ThietBi> thietBis;
    int resource;

    public AdapterThietBi(@NonNull Context context, int resource, @NonNull ArrayList<ThietBi> thietBi) {
        super(context, resource, thietBi);
        this.context= context;
        this.resource= resource;
        this.thietBis= thietBi;
    }

    @Override
    public int getCount(){return thietBis.size();}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        convertView= LayoutInflater.from(context).inflate(resource, null);
        TextView tvTenThietBi= convertView.findViewById(R.id.tvTenThietBi);
        TextView tvXuatXu= convertView.findViewById(R.id.tvXuatXu);
        TextView tvTenLoai= convertView.findViewById(R.id.tvTenLoai);
        ImageView ivsua= convertView.findViewById(R.id.ivSua);
        ImageView ivxoa= convertView.findViewById(R.id.ivXoa);

        ThietBi thietBi= thietBis.get(position);
        tvTenThietBi.setText(thietBi.getTenTB());
        tvXuatXu.setText(thietBi.getXuatXu());

        tvTenLoai.setText(LoaiThietBi.getTenLoai(thietBi.getMaLoai()));



        ivsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ThietBiActivity)context).Suadialog(thietBi);
            }
        });

        ivxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ThietBiActivity)context).Xoatb(thietBi.getMaTB());
            }
        });

        return convertView;
    }
}