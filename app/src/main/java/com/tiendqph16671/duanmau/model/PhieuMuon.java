package com.tiendqph16671.duanmau.model;

public class PhieuMuon {
    public int maPM, maTV, maSach, tienThue, traSach;
    public String maTT;
    public String ngay;
    private String tenTV;
    private String tenTT;
    private String tenSach;

    public PhieuMuon() {
    }
   //                 pm.maPM,  pm.maTV, tv.hoTen,         pm.maTT,     tt.hoTen, pm.maSach, sc.tenSach,     pm.tienThue, pm.ngay,    pm.traSach
    public PhieuMuon(int maPM, int maTV, String tenTV, String maTT, String tenTT, int maSach,String tenSach, int tienThue,String ngay, int traSach) {
        this.maPM = maPM;
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.traSach = traSach;
        this.maTT = maTT;
        this.ngay = ngay;
        this.tenTV = tenTV;
        this.tenTT = tenTT;
        this.tenSach = tenSach;
    }

    public PhieuMuon(int maTV, int maSach, int tienThue, int traSach, String maTT, String ngay) {
        this.maTV = maTV;
        this.maSach = maSach;
        this.tienThue = tienThue;
        this.traSach = traSach;
        this.maTT = maTT;
        this.ngay = ngay;
    }

    public int getMaPM() {
        return maPM;
    }

    public void setMaPM(int maPM) {
        this.maPM = maPM;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public int getTienThue() {
        return tienThue;
    }

    public void setTienThue(int tienThue) {
        this.tienThue = tienThue;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }

    public String getMaTT() {
        return maTT;
    }

    public void setMaTT(String maTT) {
        this.maTT = maTT;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getTenTV() {
        return tenTV;
    }

    public void setTenTV(String tenTV) {
        this.tenTV = tenTV;
    }

    public String getTenTT() {
        return tenTT;
    }

    public void setTenTT(String tenTT) {
        this.tenTT = tenTT;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }
}
