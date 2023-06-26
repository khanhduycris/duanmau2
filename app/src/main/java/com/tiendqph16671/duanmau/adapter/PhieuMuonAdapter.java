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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tiendqph16671.duanmau.R;
import com.tiendqph16671.duanmau.dao.PhieuMuonDAO;
import com.tiendqph16671.duanmau.model.PhieuMuon;
import com.tiendqph16671.duanmau.model.Sach;

import java.util.ArrayList;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder>{

    private ArrayList<PhieuMuon> list;
    private Context context;

    public PhieuMuonAdapter(ArrayList<PhieuMuon> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_phieumuon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtMaPM.setText("Mã phiếu mượn: " + list.get(position).getMaPM());
        holder.txtMaTV.setText("Mã thành viên: " + list.get(position).getMaTV());
        holder.txtTenTV.setText("Tên thành viên: " + list.get(position).getTenTV());
        holder.txtMaTT.setText("Mã thủ thư: " + list.get(position).getMaTT());
        holder.txtTenTT.setText("Tên thủ thư: " + list.get(position).getTenTT());
        holder.txtMaSach.setText("Mã sách: " + list.get(position).getMaSach());
        holder.txtTenSach.setText("Tên sách: " + list.get(position).getTenSach());
        holder.txtTienThue.setText("Tiền thuê: " + list.get(position).getTienThue());
        holder.txtNgay.setText("Ngày: " + list.get(position).getNgay());
        String trangThai = "";
        if (list.get(position).getTraSach() == 1){
            trangThai = "Đã trả sách";
            holder.btnTraSach.setVisibility(View.GONE);
        }else {
            trangThai = "Chưa trả sách";
            holder.btnTraSach.setVisibility(View.VISIBLE);
        }
        holder.txtTrangThai.setText("Trạng thái: " + trangThai);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogPhieuMuon(list.get(holder.getAdapterPosition()));
            }
        });


        holder.btnTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuMuonDAO phieuMuonDAO = new PhieuMuonDAO(context);
                boolean kiemTra = phieuMuonDAO.thayDoiTrangThai(list.get(holder.getAdapterPosition()).getMaPM());
                if (kiemTra){
                    list.clear();
                    list = phieuMuonDAO.getDSPhieuMuon();
                    notifyDataSetChanged();
                }else {
                    Toast.makeText(context, "Thay đổi trạng thái không thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void showDialogPhieuMuon(final PhieuMuon phieuMuon) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_phieu_muon);
        Window window = dialog.getWindow();
        window.setLayout(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

        TextView textma = dialog.findViewById(R.id.textMaPhieuMuon);
        TextView textName = dialog.findViewById(R.id.textMaThanhVien);
        TextView textYear = dialog.findViewById(R.id.textTenThanhVien);
        TextView textGender = dialog.findViewById(R.id.textMaThuThu);
        TextView textTenLoai = dialog.findViewById(R.id.textTenThuThu);
        TextView textMaSach = dialog.findViewById(R.id.textMaSach);
        TextView textTenSach = dialog.findViewById(R.id.textTenSach);
        TextView textTienThue = dialog.findViewById(R.id.textTienThue);
        TextView textNgay = dialog.findViewById(R.id.textNgay);
        TextView textTrangThai = dialog.findViewById(R.id.textTrangThai);
        Button buttonOk = dialog.findViewById(R.id.buttonOk);

        textma.setText("Ma phieu mmuon: " + phieuMuon.getMaPM()+"");
        textName.setText("Ma thanh vien: " + phieuMuon.getMaTV()+"");
        textYear.setText("Ten thanh vien: " + phieuMuon.getTenTV()+"");
        textGender.setText("Ma thu thu: " + phieuMuon.getMaTT()+"");
        textTenLoai.setText("Ma sach: " + phieuMuon.getMaSach()+"");
        textMaSach.setText("Ten sach: " + phieuMuon.getTenSach()+"");
        textTenSach.setText("Tien thue: " + phieuMuon.getTienThue()+"");
        textTienThue.setText("Ngay: " + phieuMuon.getNgay()+"");
        textNgay.setText("Ma sach: " + phieuMuon.getMaSach()+"");
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
        TextView txtMaPM, txtMaTV, txtTenTV,txtMaTT,txtTenTT,txtMaSach,txtTenSach,txtTienThue,txtNgay,txtTrangThai;
        Button btnTraSach;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtMaPM = itemView.findViewById(R.id.txtMaPM);
            txtMaTV = itemView.findViewById(R.id.txtMaTV);
            txtTenTV = itemView.findViewById(R.id.txtTenTV);
            txtMaTT = itemView.findViewById(R.id.txtMaTT);
            txtTenTT = itemView.findViewById(R.id.txtTenTT);
            txtMaSach = itemView.findViewById(R.id.txtMaSach);
            txtTenSach = itemView.findViewById(R.id.txtTenSach);
            txtTienThue = itemView.findViewById(R.id.txtTienThue);
            txtNgay = itemView.findViewById(R.id.txtNgay);
            txtTrangThai = itemView.findViewById(R.id.txtTrangThai);
            btnTraSach = itemView.findViewById(R.id.btnTraSach);
        }
    }
}
