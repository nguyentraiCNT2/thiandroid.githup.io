package com.example.a2110900050_ninhngoctuan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private ListView lvSanpham;
private Button btnCreate;
private DatabaseHelper databaseHelper;
private SanphamAdapter adapter;
    private List<SanPham> sanpham;
    private static final int REQUEST_CODE_THEM = 1;
    private static final int REQUEST_CODE_SUA_XOA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        map();

        sanpham = GetAll();
        updateListView(sanpham);
        adapter = new SanphamAdapter(this, sanpham);
        lvSanpham.setAdapter(adapter);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Mở màn hình "Thêm sản phẩm mới"
                Intent intent = new Intent(MainActivity.this, chucnangactivity.class);
                startActivityForResult(intent, REQUEST_CODE_THEM);
                startActivity(intent);
            }
        });
        lvSanpham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {



                SanPham selectedSanPham = sanpham.get(position);

                // Chuyển sang màn hình sửa sản phẩm với dữ liệu tương ứng
                Intent intent = new Intent(MainActivity.this, chucnangactivity.class);
                intent.putExtra("SanPham", selectedSanPham.getMaSP());
                startActivityForResult(intent, REQUEST_CODE_SUA_XOA);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_THEM) {
            if (resultCode == RESULT_OK) {
                sanpham = GetAll();
                adapter.clear();
                adapter.addAll(sanpham);
                adapter.notifyDataSetChanged();
            }
        }

        if (requestCode == REQUEST_CODE_SUA_XOA) {
            if (resultCode == RESULT_OK) {
                sanpham = GetAll();
                adapter.clear();
                adapter.addAll(sanpham);
                adapter.notifyDataSetChanged();
            }
        }
    }
    private List<SanPham> GetAll() {
        List<SanPham> ls = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        ls = dbHelper.getAllSanPham();
        return ls;
    }
    private void updateListView(List<SanPham> danhSach) {
        adapter = new SanphamAdapter(this, danhSach);
        lvSanpham.setAdapter(adapter);
    }
    public void map(){
        lvSanpham = findViewById(R.id.lvSanpham);
        btnCreate= findViewById(R.id.btnCreate);
    }
}