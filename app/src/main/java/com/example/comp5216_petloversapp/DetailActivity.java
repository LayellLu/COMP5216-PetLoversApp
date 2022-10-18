package com.example.comp5216_petloversapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.comp5216_petloversapp.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailBinding binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Page Title");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}