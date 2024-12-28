package com.example.model;

public class SanPham24 {
        private int maSP;
        private String tenSP;
        private int soLuong;
        private double donGia;

        public SanPham24(int maSP, String tenSP, int soLuong, double donGia) {
            this.maSP = maSP;
            this.tenSP = tenSP;
            this.soLuong = soLuong;
            this.donGia = donGia;
        }

        public int getMaSP() { return maSP; }
        public String getTenSP() { return tenSP; }
        public int getSoLuong() { return soLuong; }
        public double getDonGia() { return donGia; }
        public double getThanhTien() { return soLuong * donGia; }

    public void setMaSP(int maSP) {
        this.maSP = maSP;
    }
    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public void setSoLuong(int soLuong) {
            this.soLuong =soLuong;
    }

    public void setDonGia(double donGia) {
            this.donGia=donGia;
    }
}
