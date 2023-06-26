package com.tiendqph16671.duanmau.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiendqph16671.duanmau.R;
import com.tiendqph16671.duanmau.dao.LoaiSachDAO;
import com.tiendqph16671.duanmau.model.ItemClick;
import com.tiendqph16671.duanmau.model.LoaiSach;
import com.tiendqph16671.duanmau.model.Sach;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder>{
    private Context context;
    private ArrayList<LoaiSach> list;

    private LoaiSachAdapter loaiSachAdapter;

    private ArrayList<LoaiSach> originalList;
    private ItemClick itemClick;

    public LoaiSachAdapter(Context context, ArrayList<LoaiSach> list, ItemClick itemClick) {
        this.context = context;
        this.list = list;
        this.originalList = new ArrayList<>(list);
        this.itemClick = itemClick;
        this.loaiSachAdapter = LoaiSachAdapter.this;
    }

    public void filter(String query) {
        list.clear();

        for (LoaiSach loaiSach : originalList) {
            if (String.valueOf(loaiSach.getId()).toLowerCase().contains(query.toLowerCase())) {
                list.add(loaiSach);
            }
        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_loaisach,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaLoai.setText("Mã loại sách: " + String.valueOf(list.get(position).getId()));
        holder.txtTenLoai.setText("Tên loại sách: " + list.get(position).getTenLoai());

        holder.ivDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                int check = loaiSachDAO.xoaLoaiSach(list.get(holder.getAdapterPosition()).getId());
                switch (check){
                    case 1 :
                        //loadData
                        list.clear();
                        list = loaiSachDAO.getDSLoaiSach();
                        notifyDataSetChanged();
                        Toast.makeText(context, "Đã xóa", Toast.LENGTH_SHORT).show();
                        break;
                    case -1 :
                        Toast.makeText(context, "Không thể xóa loại sách này", Toast.LENGTH_SHORT).show();
                        break;
                    case 0 :
                        Toast.makeText(context, "Xóa không thành công", Toast.LENGTH_SHORT).show();
                    default:
                        break;
                }
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogLoaiSach(list.get(holder.getAdapterPosition()));
            }
        });

        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoaiSach loaiSach = list.get(holder.getAdapterPosition());
                itemClick.onClickLoaiSach(loaiSach);
            }
        });

    }



    private void showDialogLoaiSach(final LoaiSach loaiSach) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_quan_ly_sach);
        Window window = dialog.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView textma = dialog.findViewById(R.id.textMaLoaiSach);
        TextView textName = dialog.findViewById(R.id.textTenLoaiSach);
        Button buttonOk = dialog.findViewById(R.id.buttonOk);

        textma.setText("Id: " + loaiSach.getId()+"");
        textName.setText("TenSach: " + loaiSach.getTenLoai()+"");
//        textTenLoai.setText(
//                (thanhVien.getGioiTinh() == 1) ? "Gioi tinh: Nam" : "Gioi tinh: Nu"
//        );

//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();


        buttonOk.setOnClickListener(view -> dialog.cancel());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtMaLoai, txtTenLoai;
        ImageView ivDel, ivEdit;

        SearchView searchView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaLoai = itemView.findViewById(R.id.txtMaLoai);
            txtTenLoai = itemView.findViewById(R.id.txtTenLoai);
            ivDel = itemView.findViewById(R.id.ivDel);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            searchView = itemView.findViewById(R.id.search_view);
//            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextChange(String newText) {
//                    LoaiSachAdapter.this.filter(newText);
//                    return true;
//                }
//            });

        }
    }


}
