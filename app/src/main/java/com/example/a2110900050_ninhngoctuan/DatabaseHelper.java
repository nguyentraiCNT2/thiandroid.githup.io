package com.example.a2110900050_ninhngoctuan;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SanPhamDB";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột
    private static final String TABLE_SANPHAM = "SanPham";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TENSP = "tenSP";
    private static final String COLUMN_GIATIEN = "giaTien";
    private static final String COLUMN_KHUYENMAI = "khuyenmai";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng SanPham
        String createTable = "CREATE TABLE " + TABLE_SANPHAM + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_TENSP + " TEXT, " +
                COLUMN_GIATIEN + " REAL, " +
                COLUMN_KHUYENMAI + " INTEGER)";
        db.execSQL(createTable);

        // Thêm dữ liệu mẫu
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENSP, "Sản phẩm 1");
        values.put(COLUMN_GIATIEN, 100.0);
        values.put(COLUMN_KHUYENMAI, 1); // 1 nếu có khuyến mãi, 0 nếu không
        db.insert(TABLE_SANPHAM, null, values);

        // Thêm các sản phẩm mẫu khác tương tự ở đây
    }
    public List<SanPham> getAllSanPham() {
        List<SanPham> sanPhams = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_SANPHAM,
                null,
                null,
                null,
                null,
                null,
                COLUMN_GIATIEN + " DESC" // Sắp xếp giảm dần theo giá
        );

        if (cursor != null && ((Cursor) cursor).moveToFirst()) {
            do {
                @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
                @SuppressLint("Range")  String tenSP = cursor.getString(cursor.getColumnIndex(COLUMN_TENSP));
                @SuppressLint("Range")   double giaTien = cursor.getDouble(cursor.getColumnIndex(COLUMN_GIATIEN));
                @SuppressLint("Range")  boolean khuyenmai = cursor.getInt(cursor.getColumnIndex(COLUMN_KHUYENMAI)) == 1;

                SanPham sanPham = new SanPham(id, tenSP, giaTien, khuyenmai);
                sanPhams.add(sanPham);
            } while (cursor.moveToNext());

            cursor.close();
        }

        return sanPhams;
    }
    // Phương thức thêm/sửa sản phẩm
    public long addOrUpdateSanPham(SanPham sanPham) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TENSP, sanPham.getTenSP());
        values.put(COLUMN_GIATIEN, sanPham.getGiaTien());
        values.put(COLUMN_KHUYENMAI, sanPham.isKhuyenmai() ? 1 : 0);

        if (sanPham.getMaSP() > 0) {
            // Nếu sản phẩm đã có id, là sản phẩm cần sửa
            return db.update(TABLE_SANPHAM, values, COLUMN_ID + "=?", new String[]{String.valueOf(sanPham.getMaSP())});
        } else {
            // Nếu sản phẩm chưa có id, là sản phẩm mới cần thêm
            return db.insert(TABLE_SANPHAM, null, values);
        }
    }
    // Phương thức xóa sản phẩm
    public void deleteSanPham(long sanPhamId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SANPHAM, COLUMN_ID + "=?", new String[]{String.valueOf(sanPhamId)});
    }
    // Phương thức lấy thông tin của một sản phẩm dựa trên id
    public SanPham getSanPham(long sanPhamId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_SANPHAM,
                null,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(sanPhamId)},
                null,
                null,
                null
        );

        SanPham sanPham = null;

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String tenSP = cursor.getString(cursor.getColumnIndex(COLUMN_TENSP));
            @SuppressLint("Range") double giaTien = cursor.getDouble(cursor.getColumnIndex(COLUMN_GIATIEN));
            @SuppressLint("Range")   boolean khuyenmai = cursor.getInt(cursor.getColumnIndex(COLUMN_KHUYENMAI)) == 1;

            sanPham = new SanPham((int) sanPhamId, tenSP, giaTien, khuyenmai);

            cursor.close();
        }

        return sanPham;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ nếu tồn tại và tạo lại
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SANPHAM);
        onCreate(db);
    }
}
