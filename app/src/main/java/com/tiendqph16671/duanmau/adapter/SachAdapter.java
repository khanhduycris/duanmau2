package com.tiendqph16671.duanmau.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tiendqph16671.duanmau.R;
import com.tiendqph16671.duanmau.dao.SachDao;
import com.tiendqph16671.duanmau.model.Sach;

import java.util.ArrayList;
import java.util.HashMap;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder>{
    private Context context;
    private ArrayList<Sach> list;
    private ArrayList<HashMap<String, Object>> listHM;
    private SachDao sachDao;

    public SachAdapter(Context context, ArrayList<Sach> list, ArrayList<HashMap<String, Object>> listHM, SachDao sachDao) {
        this.context = context;
        this.list = list;
        this.listHM = listHM;
        this.sachDao = sachDao;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_recycler_sach, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaSach.setText("Mã sách: " + list.get(position).getMasach());
        holder.txtTenSach.setText("Tên sách: " + list.get(position).getTensach());
        holder.txtGiaThue.setText("Giá thuê: " + list.get(position).getGiathue());
        holder.txtMaLoai.setText("Mã loại: " + list.get(position).getMaloai());
        holder.txtTenLoai.setText("Tên loại: " + list.get(position).getTenLoai());

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(list.get(holder.getAdapterPosition()));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogSach(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = sachDao.xoaSach(list.get(holder.getAdapterPosition()).getMasach());
                switch (check){
                    case 1:
                        Toast.makeText(context, "Đã xóa sách", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Không xóa được sách này", Toast.LENGTH_SHORT).show();
                    default:
                        break;
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaSach, txtTenSach, txtGiaThue, txtMaLoai, txtTenLoai;
        ImageView ivEdit, ivDel;

        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtGiaThue = itemView.findViewById(R.id.txtGiaThue);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDel = itemView.findViewById(R.id.ivDel);
        }
    }

    private void showDialogSach(final Sach sach) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_sach);
        Window window = dialog.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView textma = dialog.findViewById(R.id.textMaSach);
        TextView textName = dialog.findViewById(R.id.textTenSach);
        TextView textYear = dialog.findViewById(R.id.textGia);
        TextView textGender = dialog.findViewById(R.id.textMaLoai);
        TextView textTenLoai = dialog.findViewById(R.id.textTenLoai);
        Button buttonOk = dialog.findViewById(R.id.buttonOk);

        textma.setText("Id: " + sach.getMasach()+"");
        textName.setText("TenSach: " + sach.getTensach()+"");
        textYear.setText("Gia: " + sach.getGiathue()+"");
        textGender.setText("MaLoai: " + sach.getMaloai()+"");
        textTenLoai.setText("TenLoai: " + sach.getTenLoai()+"");
//        textTenLoai.setText(
//                (thanhVien.getGioiTinh() == 1) ? "Gioi tinh: Nam" : "Gioi tinh: Nu"
//        );

//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();


        buttonOk.setOnClickListener(view -> dialog.cancel());
    }

    private void  showDialog (Sach  sach){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_suasach,null);
        builder.setView(view);

        EditText edtTenSach = view.findViewById(R.id.edtTenSach);
        EditText edtTien = view.findViewById(R.id.edtTien);
        TextView txtMaSach = view.findViewById(R.id.txtMaSach);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);

        txtMaSach.setText("Mã Sách: " + sach.getMasach());
        edtTenSach.setText(sach.getTensach());
        edtTien.setText(String.valueOf(sach.getGiathue()));

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                context,
                listHM,
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1}
        );
        spnLoaiSach.setAdapter(simpleAdapter);

        int index = 0;
        int postion = -1;
        for (HashMap<String, Object> item : listHM){
            if ((int)item.get("maLoai") == sach.getMaloai()){
                postion = index;
            }else {
                index++;
            }
        }
        spnLoaiSach.setSelection(postion);

        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tensach = edtTenSach.getText().toString();
                int tien = Integer.parseInt(edtTien.getText().toString());
                HashMap<String, Object> hs = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
                int maloai = (int)hs.get("maLoai");

                boolean check = sachDao.capNhatThongTinSach(sach.getMasach(),tensach, tien, maloai);

                if (check){
                    Toast.makeText(context, "Cập nhật sách thành công", Toast.LENGTH_SHORT).show();
                    //load data
                    loadData();
                }else {
                    Toast.makeText(context, "Cập nhật sách thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

    private void loadData(){
        list.clear();
        list = sachDao.getDSDauSach();
        notifyDataSetChanged();
    }
}
