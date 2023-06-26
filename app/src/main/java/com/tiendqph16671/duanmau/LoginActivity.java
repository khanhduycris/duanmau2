package com.tiendqph16671.duanmau;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tiendqph16671.duanmau.dao.ThuThuDAO;

public class LoginActivity extends AppCompatActivity {
    EditText edUser, edPass;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edUser = findViewById(R.id.edUsername);
        edPass = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);

        ThuThuDAO thuThuDAO = new ThuThuDAO(this);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edUser.getText().toString();
                String pass = edPass.getText().toString();
                if (thuThuDAO.checkDangNhap(user,pass)){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }else{
                    Toast.makeText(LoginActivity.this, "Username và mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}