package com.example.a2110900050_ninhngoctuan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SanphamAdapter  extends ArrayAdapter<SanPham> {

    public SanphamAdapter(@NonNull Context context, @NonNull List<SanPham> SanphamList){
        super(context, 0, SanphamList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.view, parent, false);
        }
        SanPham sanpham =getItem(position);
        TextView TenSP = convertView.findViewById(R.id.TenSP);
        TextView GiaTien = convertView.findViewById(R.id.Giatien);
        TextView Kuyenmai = convertView.findViewById(R.id.GiamGia);


        if (sanpham != null) {
            TenSP.setText("" + sanpham.getTenSP());
            GiaTien.setText("" + sanpham.getGiaTien());
            Kuyenmai.setText("" + sanpham.isKhuyenmai());
        }

        return convertView;
    }
}
