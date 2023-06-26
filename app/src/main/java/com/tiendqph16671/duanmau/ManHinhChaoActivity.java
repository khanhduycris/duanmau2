package com.tiendqph16671.duanmau;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class ManHinhChaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);

        ImageView ivLogo = findViewById(R.id.ivLogo);

        Glide.with(this).load(R.mipmap.read).into(ivLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                  startActivity(new Intent(ManHinhChaoActivity.this, LoginActivity.class));
            }
        },3000);
    }
}