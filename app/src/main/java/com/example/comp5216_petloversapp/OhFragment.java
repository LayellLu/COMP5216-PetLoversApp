package com.example.comp5216_petloversapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.comp5216_petloversapp.databinding.FragmentOhBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OhFragment extends Fragment {
    private FragmentOhBinding binding;
    private ImagePageAdapter imageAdapter = new ImagePageAdapter();
    private HomeAdapter homeAdapter ;
    List<Blog> blogs = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOhBinding.inflate(inflater, container, false);
        initBanner();
        initTab();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Blogs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blogs.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Blog blog = snapshot.getValue(Blog.class);
                    blogs.add(blog);

                }
                homeAdapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void initTab() {
        homeAdapter = new HomeAdapter(blogs);
        binding.rvData.setAdapter(homeAdapter);
        binding.tl.addTab(binding.tl.newTab().setText("Tab1"));
        binding.tl.addTab(binding.tl.newTab().setText("Tab2"));
        binding.tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                homeAdapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initBanner() {
        binding.llIndicator.removeAllViews();
        for (Integer image : imageAdapter.images) {
            View child = new View(getContext());
            child.setPadding(10, 10, 10, 10);
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(50, 50);
            params.setMarginEnd(20);
            child.setBackgroundResource(R.drawable.shape_indicator1);
            binding.llIndicator.addView(child, params);
        }
        binding.banner.setAdapter(imageAdapter);
        binding.banner.setCurrentItem(1);
        binding.banner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                position = fixPosition(position);
                int count = binding.llIndicator.getChildCount();
                for (int i = 0; i < count; i++) {
                    binding.llIndicator.getChildAt(i).setBackgroundResource(R.drawable.shape_indicator1);
                }
                binding.llIndicator.getChildAt(position).setBackgroundResource(R.drawable.shape_indicator);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (binding.banner.getCurrentItem() == 0) {
                        binding.banner.setCurrentItem(imageAdapter.getImages().size(), false);
                    } else if (binding.banner.getCurrentItem() == imageAdapter.getCount() - 1) {
                        binding.banner.setCurrentItem(1, false);
                    }
                }
            }
        });
        handler.sendEmptyMessageDelayed(1, 3000);
    }

    private int fixPosition(int position) {
        if (position == 0) {
            return imageAdapter.images.size() - 1;
        } else if (position == imageAdapter.getCount() - 1) {
            return 0;
        }
        return position - 1;
    }

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            handler.sendEmptyMessageDelayed(1, 3000);
            binding.banner.setCurrentItem(binding.banner.getCurrentItem() + 1);
        }
    };
}
