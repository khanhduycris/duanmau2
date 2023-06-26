package com.tiendqph16671.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tiendqph16671.duanmau.database.DbHelper;
import com.tiendqph16671.duanmau.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienDAO {
    DbHelper dbHelper;
    public ThanhVienDAO(Context context){
        dbHelper = new DbHelper(context);
    }

    public ArrayList<ThanhVien> getDSThanhVien (){
        ArrayList<ThanhVien> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THANHVIEN", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new ThanhVien(cursor.getInt(0), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3))));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean themThanhVien(String hoten, String namsinh, String tinh, int gioiTinh){
        SQLiteDatabase  sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTen", hoten);
        contentValues.put("namSinh", namsinh);
        contentValues.put("gioiTinh", gioiTinh);
        long check = sqLiteDatabase.insert("THANHVIEN", null, contentValues);

        if (check == -1 ){
            return false;
        }else {
            return true;
        }

    }

    // cho them thanh vien dau ???

    public boolean capNhatThongTinTV(int matv, String hoten, String namsinh){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTen", hoten);
        contentValues.put("namSinh", namsinh);
        long check = sqLiteDatabase.update("THANHVIEN", contentValues, "maTV = ?", new String[]{String.valueOf(matv)});

        if (check == -1 ){
            return false;
        }else {
            return true;
        }
    }
   // int: 1 - Xóa thành công, 0 - Xóa thất bại, -1 - Tìm thấy thành viên đang có phiếu mượn
    public int xoaThongTinTV(int matv){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE maTV = ?", new String[]{String.valueOf(matv)});
        if (cursor.getCount() != 0){
            return -1;
        }

        long check = sqLiteDatabase.delete("THANHVIEN", "maTV = ?", new String[]{String.valueOf(matv)});
        if (check == -1){
            return 0;
        }else {
            return 1;
        }
    }
}
