package com.tiendqph16671.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tiendqph16671.duanmau.database.DbHelper;
import com.tiendqph16671.duanmau.model.PhieuMuon;

import java.util.ArrayList;

public class PhieuMuonDAO {
    DbHelper dbHelper;
    public PhieuMuonDAO (Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<PhieuMuon> getDSPhieuMuon(){
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.maPM, pm.maTV, tv.hoTen, pm.maTT, tt.hoTen, pm.maSach, sc.tenSach, pm.tienThue, pm.ngay, pm.traSach FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc WHERE pm.maTV = tv.maTV AND pm.maTT = tt.maTT AND pm.maSach = sc.maSach ORDER BY pm.maPM DESC", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new PhieuMuon(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getInt(7),cursor.getString  (8), cursor.getInt(9) ));

            }while (cursor.moveToNext());
        }
        return list;
    }

//    public ArrayList<PhieuMuon> getPhieuMuonById(String key) {
//        ArrayList<PhieuMuon> list = new ArrayList<>();
//        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
//        Cursor cursor =
//                sqLiteDatabase
//                        .rawQuery
//                                ("SELECT pm.maPM, pm.maTV, tv.hoTen, pm.maTT, tt.hoTen, pm.maSach, sc.tenSach, pm.tienThue, pm.ngay, pm.traSach FROM PHIEUMUON pm, THANHVIEN tv, THUTHU tt, SACH sc WHERE pm.maTV = tv.maTV AND pm.maTT = tt.maTT AND pm.maSach = sc.maSach ORDER BY pm.maPM DESC", null);
//        if (cursor.getCount() != 0){
//            cursor.moveToFirst();
//            do {
//                list.add(new PhieuMuon(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getInt(5), cursor.getString(6), cursor.getInt(7),cursor.getString  (8), cursor.getInt(9) ));
//
//            }while (cursor.moveToNext());
//        }
//        return list;
//    }

    public boolean thayDoiTrangThai(int mapm){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("traSach", 1);
        long check = sqLiteDatabase.update("PHIEUMUON",contentValues, "maPM = ?", new String[]{String.valueOf(mapm)});
        if (check == -1 ){
            return false;
        }else {
            return true;
        }
    }

    public boolean themPhieuMuon(PhieuMuon phieuMuon){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
        //                        "maTT TEXT REFERENCES ThuThu(maTT)," +
        //                        "maTV INTEGER REFERENCES ThanhVien(maTV)," +
        //                        "maSach INTEGER REFERENCES Sach(maSach)," +
        //                        "tienThue INTEGER NOT NULL," +
        //                        "ngay DATE NOT NULL," +
        //                        "traSach INTEGER NOT NULL)"
        //contentValues.put("maPM",phieuMuon.getMaPM());
        contentValues.put("maTT",phieuMuon.getMaTT());
        contentValues.put("maTV",phieuMuon.getMaTV());
        contentValues.put("maSach",phieuMuon.getMaSach());
        contentValues.put("tienThue",phieuMuon.getTienThue());
        contentValues.put("ngay",phieuMuon.getNgay());
        contentValues.put("traSach",phieuMuon.getTraSach());

        long check = sqLiteDatabase.insert("PHIEUMUON", null, contentValues);
        if(check == -1){
            return false;
        }else {
            return true;
        }
    }
}
