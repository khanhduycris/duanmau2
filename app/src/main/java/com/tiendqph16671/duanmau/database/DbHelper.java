package com.tiendqph16671.duanmau.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    static final String dbName="PNLIB";
    static final int dbVersion=1;

    public DbHelper(Context context){
        super(context, dbName,null,dbVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbThuThu=
                "CREATE TABLE THUTHU (" +
                        "maTT TEXT PRIMARY KEY," +
                        "hoTen TEXT NOT NULL," +
                        "matKhau TEXT NOT NULL," +
                        "loaiTaiKhoan TEXT NOT NULL)";
        db.execSQL(dbThuThu);

        String dbThanhVien=
                "CREATE TABLE THANHVIEN (" +
                        "maTV INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "hoTen TEXT NOT NULL," +
                        "namSinh TEXT NOT NULL, gioiTinh Integer not null)";
        db.execSQL(dbThanhVien);

        String dbLoaiSach=
                "CREATE TABLE LOAISACH (" +
                        "maLoai INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "tenLoai TEXT NOT NULL)";
        db.execSQL(dbLoaiSach);

        String dbSach=
                "CREATE TABLE SACH (" +
                        "maSach INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "tenSach TEXT NOT NULL," +
                        "giaThue INTEGER NOT NULL," +
                        "maLoai INTEGER REFERENCES LoaiSach(maLoai))";
        db.execSQL(dbSach);

        String dbPhieuMuon=
                "CREATE TABLE PHIEUMUON (" +
                        "maPM INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "maTT TEXT REFERENCES ThuThu(maTT)," +
                        "maTV INTEGER REFERENCES ThanhVien(maTV)," +
                        "maSach INTEGER REFERENCES Sach(maSach)," +
                        "tienThue INTEGER NOT NULL," +
                        "ngay DATE NOT NULL," +
                        "traSach INTEGER NOT NULL)";
        db.execSQL(dbPhieuMuon);
        //data mẫu
        db.execSQL("INSERT INTO LOAISACH VALUES (1,'Thiếu nhi'),"+"(2, 'Kinh dị'),"+"(3,'Tình cảm')");
        db.execSQL("INSERT INTO SACH VALUES (1,'Hãy đợi đấy',25000,1),"+"(2,'Ngôi nhà ma ám',35000,2),"+"(3,'Anh và em', 30000,3)");
        db.execSQL("INSERT INTO THUTHU VALUES ('thuthu', 'Trần Phương Thảo', '12345', 'Thủ thư'),"+"('admin', 'Đoàn Quốc Tiến', '12345', 'Admin')");
        db.execSQL("INSERT INTO THANHVIEN VALUES (1,'Đoàn Quốc Tiến','2002', 1),"+"(2,'Trần Phương Thảo','2003', 0)");
        //trả sách: 1: đã trả - 0: chưa trả
        db.execSQL("INSERT INTO PHIEUMUON VALUES (1,'thuthu',1,1,25000,'14/06/2023',1),"+"(2,'thuthu',2,2,35000,'10/06/2023',0),"+"(3,'thuthu',2,3,30000,'12/06/2023',0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
          if( i != i1){
              String dropTableThuThu = "drop table if exists THUTHU";
              db.execSQL(dropTableThuThu);
              String dropTableThanhVien = "drop table if exists THANHVIEN";
              db.execSQL(dropTableThanhVien);
              String dropTableLoaiSach = "drop table if exists LOAISACH";
              db.execSQL(dropTableLoaiSach);
              String dropTableSach = "drop table if exists SACH";
              db.execSQL(dropTableSach);
              String dropTablePhieuMuon = "drop table if exists PHIEUMUON   ";
              db.execSQL(dropTablePhieuMuon);

              onCreate(db);
        }
    }
}
