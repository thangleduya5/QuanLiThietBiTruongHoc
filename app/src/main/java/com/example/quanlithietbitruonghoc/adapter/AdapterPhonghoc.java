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
import com.example.quanlithietbitruonghoc.activity.PhongHocActivity;
import com.example.quanlithietbitruonghoc.model.PhongHoc;

import java.util.ArrayList;

public class AdapterPhonghoc extends ArrayAdapter<PhongHoc> {

    Context context;
    ArrayList<PhongHoc> phonghocs;
    int resource;

    public AdapterPhonghoc(@NonNull Context context, int resource, @NonNull ArrayList<PhongHoc> phonghoc) {
        super(context, resource, phonghoc);
        this.context= context;
        this.resource= resource;
        this.phonghocs= phonghoc;
    }

    @Override
    public int getCount(){return phonghocs.size();}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        convertView= LayoutInflater.from(context).inflate(resource, null);
        TextView tvmaPh= convertView.findViewById(R.id.tvMaphong);
        TextView tvloaiPh= convertView.findViewById(R.id.tvLoaiphong);
        TextView tvtang= convertView.findViewById(R.id.tvTang);
        ImageView ivsua= convertView.findViewById(R.id.ivSua);
        ImageView ivxoa= convertView.findViewById(R.id.ivXoa);

        PhongHoc phonghoc= phonghocs.get(position);
        tvmaPh.setText(phonghoc.getMaPhong());
        tvloaiPh.setText(phonghoc.getLoaiPhong());
        tvtang.setText(String.valueOf(phonghoc.getTang()));

        ivsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PhongHocActivity)context).Suadialog(phonghoc);
            }
        });

        ivxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((PhongHocActivity)context).Xoamh(phonghoc.getMaPhong());
            }
        });

        return convertView;
    }
}
