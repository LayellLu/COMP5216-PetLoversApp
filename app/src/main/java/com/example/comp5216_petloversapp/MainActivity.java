package com.example.comp5216_petloversapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.os.Bundle;

import com.example.comp5216_petloversapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private OhFragment fragment1;
    private OhFragment fragment2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        OhFragment fragment1 = new OhFragment();
        OhFragment fragment2 = new OhFragment();
        binding.flContent.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return fragment1;
                } else return fragment2;

            }

            @Override
            public int getCount() {
                return 1;
            }
        });
    }
}