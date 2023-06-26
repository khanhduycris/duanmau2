package com.tiendqph16671.duanmau.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tiendqph16671.duanmau.R;
import com.tiendqph16671.duanmau.adapter.SachAdapter;
import com.tiendqph16671.duanmau.dao.LoaiSachDAO;
import com.tiendqph16671.duanmau.dao.SachDao;
import com.tiendqph16671.duanmau.model.LoaiSach;
import com.tiendqph16671.duanmau.model.Sach;

import java.util.ArrayList;
import java.util.HashMap;

public class QLSachFragment extends Fragment {
    SachDao sachDao;
    RecyclerView recyclerSach;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qlsach, container, false);

        recyclerSach = view.findViewById(R.id.recyclerQLSach);
        FloatingActionButton floatAdd = view.findViewById(R.id.floatAdd);

        sachDao = new SachDao(getContext());
        loadData();


        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        return view;
    }

    private void loadData(){
        ArrayList<Sach> list = sachDao.getDSDauSach();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerSach.setLayoutManager(linearLayoutManager);
        SachAdapter adapter = new SachAdapter(getContext(), list, getDSLoaiSach(), sachDao);
        recyclerSach.setAdapter(adapter);
    }

    private void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_add_sach, null);
        builder.setView(view);

        EditText edtTenSach = view.findViewById(R.id.edtTenSach);
        EditText edtTien = view.findViewById(R.id.edtTien);
        Spinner spnLoaiSach = view.findViewById(R.id.spnLoaiSach);

        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getContext(),
                getDSLoaiSach(),
                android.R.layout.simple_list_item_1,
                new String[]{"tenLoai"},
                new int[]{android.R.id.text1}
        );
        spnLoaiSach.setAdapter(simpleAdapter);

        builder.setNegativeButton("Thêm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tensach = edtTenSach.getText().toString();

                if (tensach.isEmpty() || edtTien.getText().toString().trim().isEmpty()) {
                    Toast.makeText(requireContext(), "Vui long dien day du thong tin!", Toast.LENGTH_SHORT).show();
                    return;
                }

                int tien = Integer.parseInt(edtTien.getText().toString());
                HashMap<String, Object> hs = (HashMap<String, Object>) spnLoaiSach.getSelectedItem();
                int maloai = (int)hs.get("maLoai");

                if (tensach.isEmpty()) {
                    Toast.makeText(requireContext(), "Khong duoc de trong", Toast.LENGTH_SHORT).show();
                    return;
                } if (tensach.length() < 6) {
                    Toast.makeText(requireContext(), "Ten sach phai lon hon 6 ki t", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!tensach.matches(".*[A-Z].*")) {
                    Toast.makeText(requireContext(), "Ten sach phai chua it nhat 1 chu viet hoa!", Toast.LENGTH_SHORT).show();
                    return;
                }


                boolean check = sachDao.themSachMoi(tensach, tien, maloai);

                if (check){
                    Toast.makeText(getContext(), "Thêm sách thành công", Toast.LENGTH_SHORT).show();
                    //load data
                    loadData();
                }else {
                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
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

    private ArrayList<HashMap<String, Object>> getDSLoaiSach(){
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSach> list = loaiSachDAO.getDSLoaiSach();
        ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();

        for (LoaiSach loai: list){
            HashMap<String, Object> hs = new HashMap<>();
            hs.put("maLoai", loai.getId());
            hs.put("tenLoai", loai.getTenLoai());
            listHM.add(hs);
        }

        return listHM;
    }
}
