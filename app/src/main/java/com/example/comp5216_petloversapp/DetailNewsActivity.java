package com.example.comp5216_petloversapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.comp5216_petloversapp.databinding.ActivityDetailNewsBinding;
import com.example.comp5216_petloversapp.databinding.ActivityMainBinding;

import java.io.Serializable;

public class DetailNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailNewsBinding binding = ActivityDetailNewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        News news = (News) getIntent().getSerializableExtra("news");
        binding.tvTitle.setText(news.title);
        binding.tvContent.setText(news.description);
        Glide.with(this).load(news.image).into(binding.ivImage);
    }
}