package com.example.a2110900050_ninhngoctuan;

public class SanPham {
    private int MaSP;
    private String TenSP;
    private double GiaTien;
    private boolean khuyenmai;

    public SanPham() {
    }

    public SanPham(int maSP, String tenSP, double giaTien, boolean khuyenmai) {
        MaSP = maSP;
        TenSP = tenSP;
        GiaTien = giaTien;
        this.khuyenmai = khuyenmai;
    }

    public int getMaSP() {
        return MaSP;
    }

    public void setMaSP(int maSP) {
        MaSP = maSP;
    }

    public String getTenSP() {
        return TenSP;
    }

    public void setTenSP(String tenSP) {
        TenSP = tenSP;
    }

    public double getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(double giaTien) {
        GiaTien = giaTien;
    }

    public boolean isKhuyenmai() {
        return khuyenmai;
    }

    public void setKhuyenmai(boolean khuyenmai) {
        this.khuyenmai = khuyenmai;
    }
}
