package com.example.a2210900124dtntham;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.SanPhamAdapter;
import com.example.model.SanPham24;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    String dbName = "QLSanPham24.db";
    String dbPath = "/databases/";
    SQLiteDatabase db = null;
    SanPhamAdapter adapter;
    ListView lvSanPham;
    Button btnThem;
    SanPham24 sp;
    int posUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        xulyCopy();
        addView();
        hienThiSanPham();
        addEvent();
    }

    private void hienThiSanPham() {
        db = openOrCreateDatabase(dbName, MODE_PRIVATE, null);
        Cursor cursor = db.rawQuery("SELECT * FROM SanPham24", null);
        while (cursor.moveToNext()) {
            int maSP = cursor.getInt(0);
            String tenSP = cursor.getString(1);
            int soLuong = cursor.getInt(2);
            double donGia = cursor.getDouble(3);
            adapter.add(new SanPham24(maSP, tenSP, soLuong, donGia));
        }
    }

    private void addView() {
        lvSanPham = findViewById(R.id.lvSanPham24);
        adapter = new SanPhamAdapter(MainActivity.this, R.layout.sanpham_item);
        lvSanPham.setAdapter(adapter);
        btnThem = findViewById(R.id.btnThemSanPham);
        registerForContextMenu(lvSanPham);
    }

    private void xulyCopy() {
        try {
            File dbFile = getDatabasePath(dbName);
            if (!dbFile.exists()) {
                copyDataFromAsset();
                Toast.makeText(MainActivity.this, "Copy thành công", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "File đã tồn tại", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Log.e("Lỗi", e.toString());
        }
    }

    private void copyDataFromAsset() {
        try {
            InputStream myInput = getAssets().open(dbName);
            String outFileName = getApplicationInfo().dataDir + dbPath + dbName;
            File f = new File(getApplicationInfo().dataDir + dbPath);
            if (!f.exists()) f.mkdir();
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        } catch (Exception ex) {
            Log.e("Lỗi", ex.toString());
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context, menu);
    }

    private void addEvent() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ThemSuaActivity.class);
                intent.putExtra("TRANGTHAI", "THEM");
                startActivityForResult(intent, 113);
            }
        });

        lvSanPham.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                sp = adapter.getItem(i);
                posUpdate = i;
                return false;
            }
        });
    }

    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mnuXoa) {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Xác nhận xóa");
            builder.setMessage("Bạn có chắc chắn muốn xóa?");
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setCanceledOnTouchOutside(false);
            //hiển thị cửa sổ này lên:
            dialog.show();
        }
        return super.onContextItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SanPham24 spNew = (SanPham24) data.getSerializableExtra("SANPHAM");

        // Thêm mới
        if (resultCode == 114 && requestCode == 113) {
            adapter.add(spNew);
            try {
                ContentValues values = new ContentValues();
                values.put("MaSP", spNew.getMaSP());
                values.put("TenSP", spNew.getTenSP());
                values.put("SoLuong", spNew.getSoLuong());
                values.put("DonGia", spNew.getDonGia());
                if (db.insert("SanPham24", null, values) > 0) {
                    Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Log.e("Lỗi:", e.toString());
            }
        }

        // Cập nhật
        if (requestCode == 113 && resultCode == 115) {
            try {
                ContentValues values = new ContentValues();
                values.put("TenSP", spNew.getTenSP());
                values.put("SoLuong", spNew.getSoLuong());
                values.put("DonGia", spNew.getDonGia());
                db.update("SanPham24", values, "MaSP=?", new String[]{spNew.getMaSP() + ""});
                adapter.getItem(posUpdate).setTenSP(spNew.getTenSP());
                adapter.getItem(posUpdate).setSoLuong(spNew.getSoLuong());
                adapter.getItem(posUpdate).setDonGia(spNew.getDonGia());
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                Log.e("Lỗi:", e.toString());
            }
        }
    }
}
