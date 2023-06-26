package com.tiendqph16671.duanmau.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.tiendqph16671.duanmau.database.DbHelper;

public class ThuThuDAO {
    DbHelper dbHelper;
    SharedPreferences sharedPreferences;
    public ThuThuDAO (Context context){
        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("THONGTIN", MODE_PRIVATE);
    }

    //đăng nhập
     public boolean checkDangNhap(String maTT, String matKhau){
         SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
         //maTT,hoTen,matKhau,loaiTaiKhoan
         Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE maTT = ? AND matKhau = ?", new String[]{maTT, matKhau});
         if(cursor.getCount() != 0){
             cursor.moveToFirst();
             //lưu data
             SharedPreferences.Editor editor = sharedPreferences.edit();
             editor.putString("maTT", cursor.getString(0));
             editor.putString("loaiTaiKhoan", cursor.getString(3));
             editor.putString("hoTen", cursor.getString(1));
             editor.commit();
             return true;
         }else {
             return false;
         }
     }

     public int capNhatMatKhau(String username, String oldPass, String newPass){
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE maTT = ? AND matKhau = ?", new String[]{username, oldPass});
        if (cursor.getCount() > 0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("matKhau", newPass);
            long check = sqLiteDatabase.update("THUTHU", contentValues, "maTT = ?", new String[]{username} );
            if (check == -1)
                return -1;
            return 1;
        }
        return 0;
     }
}
