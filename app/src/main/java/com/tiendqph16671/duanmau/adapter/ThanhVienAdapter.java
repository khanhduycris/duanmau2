package com.tiendqph16671.duanmau.adapter;

import android.app.Activity;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tiendqph16671.duanmau.R;
import com.tiendqph16671.duanmau.dao.ThanhVienDAO;
import com.tiendqph16671.duanmau.model.ThanhVien;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder>{
    private Context context;
    private ArrayList<ThanhVien> list;
    private ThanhVienDAO thanhVienDAO;

    public ThanhVienAdapter(Context context, ArrayList<ThanhVien> list, ThanhVienDAO thanhVienDAO) {
        this.context = context;
        this.list = list;
        this.thanhVienDAO = thanhVienDAO;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_thanhvien, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaTV.setText("Mã thành viên: " + list.get(position).getMaTV());
        holder.txtHoTen.setText("Họ tên: " + list.get(position).getHoTen());
        holder.txtNamSinh.setText("Năm sinh: " + list.get(position).getNamSinh());
        if (list.get(position).getGioiTinh() == 1) {
            holder.textGioiTinh.setText("Gioi tinh: Nam");
            holder.textGioiTinh.setTextColor(Color.GREEN);
        } else {
            holder.textGioiTinh.setText("Gioi tinh: Nu");
            holder.textGioiTinh.setTextColor(Color.YELLOW);
        }

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showDialogCapNhatTT(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int check = thanhVienDAO.xoaThongTinTV(list.get(holder.getAdapterPosition()).getMaTV());
                switch (check){
                    case 1:
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                        loadData();
                        break;
                    case 0:
                        Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                        break;
                    case -1:
                        Toast.makeText(context, "Thành viên tồn tại phiếu mượn", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });

        holder.cardView.setOnClickListener(view -> showDialogthanhvien(list.get(position)));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaTV, txtHoTen, txtNamSinh, textGioiTinh;
        ImageView ivEdit, ivDel;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaTV =  itemView.findViewById(R.id.txtMaTV);
            txtHoTen = itemView.findViewById(R.id.txtHoTen);
            txtNamSinh = itemView.findViewById(R.id.txtNamSinh);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDel = itemView.findViewById(R.id.ivDel);
            textGioiTinh = itemView.findViewById(R.id.textGioiTinh);
            cardView = itemView.findViewById(R.id.containerItemThanhVien);
        }
    }

    private void  showDialogCapNhatTT(ThanhVien thanhVien){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_chinhsua_thanhvien, null);
        builder.setView(view);

        TextView txtMaTV = view.findViewById(R.id.txtMaTV);
        EditText edtHoTen = view.findViewById(R.id.edtHoTen);
        EditText edtNamSinh = view.findViewById(R.id.edtNamSinh);

        txtMaTV.setText("Mã thành viên: " + thanhVien.getMaTV());
        edtHoTen.setText(thanhVien.getHoTen());
        edtNamSinh.setText(thanhVien.getNamSinh());

        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                  String hoten = edtHoTen.getText().toString();
                  String namsinh = edtNamSinh.getText().toString();
                  int id = thanhVien.getMaTV();

                  boolean check = thanhVienDAO.capNhatThongTinTV(id, hoten, namsinh);
                  if (check){
                      Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                      //load data
                      loadData();
                  }else {
                      Toast.makeText(context, "Cập nhật không thành công", Toast.LENGTH_SHORT).show();
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

    private void showDialogthanhvien(final ThanhVien thanhVien) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_infor_user);
        Window window = dialog.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView textma = dialog.findViewById(R.id.textMaThanhVien);
        TextView textName = dialog.findViewById(R.id.textTenThanhVien);
        TextView textYear = dialog.findViewById(R.id.textNamSinh);
        TextView textGender = dialog.findViewById(R.id.textGioiTinh);
        Button buttonOk = dialog.findViewById(R.id.buttonOk);

        textma.setText("Id: " + thanhVien.getMaTV()+"");
        textName.setText(thanhVien.getHoTen().toString());
        textYear.setText("Ten thanh vien: " + thanhVien.getNamSinh()+"");
        textGender.setText(
                (thanhVien.getGioiTinh() == 1) ? "Gioi tinh: Nam" : "Gioi tinh: Nu"
         );

        buttonOk.setOnClickListener(view -> dialog.cancel());
    }

    private void loadData(){
        list.clear();
        list = thanhVienDAO.getDSThanhVien();
        notifyDataSetChanged();
    }
}
