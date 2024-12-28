package com.example;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.a2210900124dtntham.R;
import com.example.model.SanPham24;

public class SanPhamAdapter extends ArrayAdapter<SanPham24> {
    Activity context;
    int resource;

    // Constructor
    public SanPhamAdapter(@NonNull Activity context, int resource) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
        // Inflate layout cho từng item
        LayoutInflater inflater = this.context.getLayoutInflater();
        View customView = inflater.inflate(resource, null);

        // Ánh xạ các thành phần trong layout
        TextView txtMaSP = customView.findViewById(R.id.txtMaSP);
        TextView txtTenSP = customView.findViewById(R.id.txtTenSP);
        TextView txtSoLuong = customView.findViewById(R.id.txtSoLuong);
        TextView txtDonGia = customView.findViewById(R.id.txtDonGia);
        SanPham24 sp = getItem(position);
        txtMaSP.setText(sp.getMaSP() + "");
        txtTenSP.setText(sp.getTenSP());
        txtSoLuong.setText(sp.getSoLuong() + "");
        txtDonGia.setText(sp.getDonGia() + "");

        return customView;
    }
}
