package com.example.a2210900124dtntham;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.a2210900124dtntham.R;
import com.example.model.SanPham24;

public class ThemSuaActivity extends AppCompatActivity {
    Intent intent; // intent nhận dữ liệu
    EditText edtMaSP, edtTenSP, edtSoLuong, edtDonGia;
    Button btnThemSua, btnThoat;
    String trangThai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themsua);

        addView();
        addEvent();
    }

    private void addView() {
        intent = getIntent();
        trangThai = intent.getStringExtra("TRANGTHAI");
        edtMaSP=findViewById(R.id.edtMaSP);
        edtTenSP = findViewById(R.id.edtTenSP);
        edtSoLuong = findViewById(R.id.edtSoLuong);
        edtDonGia = findViewById(R.id.edtDonGia);
        btnThemSua = findViewById(R.id.btnThemSua);
        btnThoat = findViewById(R.id.btnThoat);


        if (trangThai.equals("THEM")) {
            btnThemSua.setText("Thêm");
        } else {
            btnThemSua.setText("Sửa");
            SanPham24 sp = (SanPham24) intent.getSerializableExtra("SANPHAM");
            edtMaSP.setText(sp.getMaSP() + "");
            edtMaSP.setEnabled(false); // Không cho sửa mã sản phẩm
            edtTenSP.setText(sp.getTenSP());
            edtSoLuong.setText(sp.getSoLuong() + "");
            edtDonGia.setText(sp.getDonGia() + "");
        }
    }

    private void addEvent() {
        btnThemSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SanPham24 sp = new SanPham24(
                        Integer.parseInt(edtMaSP.getText().toString()),
                        edtTenSP.getText().toString(),
                        Integer.parseInt(edtSoLuong.getText().toString()),
                        Double.parseDouble(edtDonGia.getText().toString())
                );

                if (trangThai.equals("THEM")) {
                    intent.putExtra("SANPHAM24", String.valueOf(sp));
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    intent.putExtra("SANPHAM24", String.valueOf(sp));
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }
}
