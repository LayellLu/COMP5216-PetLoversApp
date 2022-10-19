package com.example.comp5216_petloversapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.comp5216_petloversapp.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    Blog blog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Page Title");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        blog =  getIntent().getParcelableExtra("data");
        Glide.with(this).load(blog.getImage()).into(binding.iv);
        binding.tvName.setText(blog.getUserEmail());
        binding.tvContent.setText(blog.getBlogDescription());
        binding.tvTitle.setText(blog.getBlogTitle());
    }
}