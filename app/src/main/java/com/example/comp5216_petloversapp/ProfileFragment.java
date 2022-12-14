package com.example.comp5216_petloversapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.example.comp5216_petloversapp.databinding.FragmentProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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
        initUserName();
        onEditClick();

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

                    if(blog.getUserEmail().equals(auth.getCurrentUser().getEmail())) {
                        MyBlogs.add(blog);
                    }

                    try {
                        if(blog.getFav().contains(auth.getCurrentUser().getUid())) {
                            LikedBlogs.add(blog);
                        }
                    }catch (Exception e) {
                        System.out.println("THAT'S OK");
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
        MyPosts = new MyPostAdapter(getContext(), MyBlogs);
        LikedPosts = new HomeAdapter(LikedBlogs);

        LikedPosts.setOnFavListener(new HomeAdapter.OnFavListener() {
            @Override
            public void onClickFav(int position) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Blogs")
                        .child(LikedBlogs.get(position).getKey());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Blogs");
                        Blog blog = snapshot.getValue(Blog.class);
                        if (blog.getFav() != null && blog.getFav().contains(FirebaseAuth.getInstance().getUid())) {

                            blog.getFav().remove(FirebaseAuth.getInstance().getUid());

                            ref.child(snapshot.getKey()).setValue(blog);
                        } else {
                            if (blog.getFav() == null) {
                                blog.setFav(new ArrayList<>());
                            }
                            blog.getFav().add(FirebaseAuth.getInstance().getUid());

                            ref.child(snapshot.getKey()).setValue(blog);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

        MyPosts.setOnFavListener(new MyPostAdapter.OnFavListener() {
            @Override
            public void onClickFav(int position) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Blogs")
                        .child(MyBlogs.get(position).getKey());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Blogs");
                        Blog blog = snapshot.getValue(Blog.class);
                        if (blog.getFav() != null && blog.getFav().contains(FirebaseAuth.getInstance().getUid())) {

                            blog.getFav().remove(FirebaseAuth.getInstance().getUid());

                            ref.child(snapshot.getKey()).setValue(blog);
                        } else {
                            if (blog.getFav() == null) {
                                blog.setFav(new ArrayList<>());
                            }
                            blog.getFav().add(FirebaseAuth.getInstance().getUid());

                            ref.child(snapshot.getKey()).setValue(blog);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });

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

    private void initUserName() {
        auth = FirebaseAuth.getInstance();

        try {
            FirebaseFirestore.getInstance()
                    .collection("users").document(auth.getCurrentUser().getUid()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            String userName = task.getResult().getData().get("userName").toString();
                            binding.userName.setText(userName);
                        }
                    });
        }catch (Exception e) {
            System.out.println("No such name");
        }
    }

    private void onEditClick() {

        binding.ivModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getActivity(), EditProfileActivity.class), 1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1) {
            initUserName();
        }
    }





}
