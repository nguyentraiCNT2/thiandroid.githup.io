package com.example.a2110900050_ninhngoctuan;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class chucnangactivity  extends AppCompatActivity {
    private EditText ettTensp,ettgia;
    private Switch swgiamgia;
    private Button btndongy,btnBack;
    private  SanPham sanPham;
    private DatabaseHelper databaseHelper;
    private SanphamAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chucnang_activity);
        map();
        // Nhận thông tin thí sinh từ Intent
        Long MaspToUpdate = Long.valueOf(getIntent().getStringExtra("SanPham"));
        if (MaspToUpdate != null) {
            sanPham = databaseHelper.getSanPham(MaspToUpdate);
            loadData();
        }
        btndongy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                themSanPham();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void loadData() {
        ettTensp.setText(sanPham.getTenSP());
        ettgia.setText(String.valueOf(sanPham.getGiaTien()));
        swgiamgia.setText(String.valueOf(sanPham.isKhuyenmai()));
    }

    private void themSanPham() {
        String tenSP = ettTensp.getText().toString().trim();
        String giaTienStr = ettgia.getText().toString().trim();
        boolean khuyenMai = swgiamgia.isChecked();

        if (tenSP.isEmpty() || giaTienStr.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin sản phẩm.", Toast.LENGTH_SHORT).show();
            return;
        }

        double giaTien = Double.parseDouble(giaTienStr);

        // Tạo đối tượng SanPham từ thông tin nhập vào
        SanPham sanPham = new SanPham(0, tenSP, giaTien, khuyenMai);

        // Thêm sản phẩm vào SQLite và cập nhật ListView
        long result = databaseHelper.addOrUpdateSanPham(sanPham);

        if (result != -1) {
            Toast.makeText(this, "Thêm sản phẩm thành công.", Toast.LENGTH_SHORT).show();
            finish(); // Đóng màn hình hiện tại
        } else {
            Toast.makeText(this, "Thêm sản phẩm thất bại.", Toast.LENGTH_SHORT).show();
        }
    }
    public  void map(){
        ettTensp = findViewById(R.id.ettTensp);
        ettgia = findViewById(R.id.ettgia);
        swgiamgia = findViewById(R.id.swgiamgia);
        btndongy = findViewById(R.id.btndongy);
        btnBack = findViewById(R.id.btnBack);

    }
}
