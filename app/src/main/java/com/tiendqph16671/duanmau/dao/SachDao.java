package com.tiendqph16671.duanmau.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tiendqph16671.duanmau.database.DbHelper;
import com.tiendqph16671.duanmau.model.Sach;

import java.util.ArrayList;

public class SachDao {
    DbHelper dbHelper;

    public SachDao(Context context){
        dbHelper = new DbHelper(context);
    }

    //Lấy toàn bộ đầu sách có trong thư viện
    public ArrayList<Sach> getDSDauSach(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT sc.maSach, sc.tenSach, sc.giaThue, sc.maLoai, lo.tenLoai FROM SACH sc, LOAISACH lo WHERE sc.maLoai = lo.maLoai ", null);
        if(cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
               list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getInt(3), cursor.getString(4)));
            }while (cursor.moveToNext());
        }

        return list;
    }

    public boolean themSachMoi(String tensach, int giatien, int maloai){
         SQLiteDatabase  sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenSach", tensach);
        contentValues.put("giaThue", giatien);
        contentValues.put("maLoai", maloai);
        long check = sqLiteDatabase.insert("SACH", null, contentValues);

        if (check == -1 ){
            return false;
        }else {
            return true;
        }
    }

    public boolean capNhatThongTinSach(int masach,String tensach, int giathue, int maloai){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenSach", tensach);
        contentValues.put("giaThue", giathue);
        contentValues.put("maLoai", maloai);
        long check = sqLiteDatabase.update("SACH", contentValues, "maSach = ?", new String[]{String.valueOf(masach)});

        if (check == -1 ){
            return false;
        }else {
            return true;
        }
    }

    //1: xóa thành công, 0: xóa thất bại, -1: không được phép xóa
    public int xoaSach(int masach){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE maSach = ?", new String[]{String.valueOf(masach)});

        if (cursor.getCount() != 0){
            return -1;
        }

        long check = sqLiteDatabase.delete("SACH", "maSach = ?", new String[]{String.valueOf(masach)});

        if (check == -1){
             return 0;
        }else {
            return 1;
        }
    }

}
