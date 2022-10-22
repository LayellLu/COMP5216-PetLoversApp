package com.example.comp5216_petloversapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.comp5216_petloversapp.databinding.FragmentProfileBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    private FirebaseAuth auth;
    private FragmentProfileBinding binding;
    private MyPostAdapter MyPosts;
    private HomeAdapter LikedPosts;

    List<Blog> MyBlogs = new ArrayList<>();
    List<Blog> LikedBlogs = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        initTab();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    @Nullable
    private void initData() {
        auth = FirebaseAuth.getInstance();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Blogs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                MyBlogs.clear();
                LikedBlogs.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Blog blog = snapshot.getValue(Blog.class);
                    blog.setKey(snapshot.getKey());

//                    if(blog.getFav().contains(auth.getCurrentUser().getUid())) {
//                        LikedBlogs.add(blog);
//                    }

                    if(blog.getUserEmail().equals(auth.getCurrentUser().getEmail())) {
                        MyBlogs.add(blog);
                    }

                    // bug fixing update later
                    if(LikedBlogs.size() < 3) {
                        LikedBlogs.add(blog);
                    }
                }

                MyPosts.notifyDataSetChanged();
                LikedPosts.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void initTab() {
        MyPosts = new MyPostAdapter(MyBlogs);
        LikedPosts = new HomeAdapter(LikedBlogs);

        binding.profilePosts.setAdapter(MyPosts);

        binding.profileTab.addTab(binding.profileTab.newTab().setText("My Posts").setId(0));
        binding.profileTab.addTab(binding.profileTab.newTab().setText("Liked Posts").setId(1));

        binding.profileTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getId() == 0) {
                    binding.profilePosts.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                    binding.profilePosts.setAdapter(MyPosts);

                } else if(tab.getId() == 1){
                    binding.profilePosts.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    binding.profilePosts.setAdapter(LikedPosts);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }



}
