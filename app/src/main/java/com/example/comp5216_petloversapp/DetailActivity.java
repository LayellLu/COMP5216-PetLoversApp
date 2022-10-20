package com.example.comp5216_petloversapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.comp5216_petloversapp.databinding.ActivityDetailBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private List<CommentItem> list = new ArrayList<>();
    Blog blog;
    private ActivityDetailBinding binding;
    private CommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Page Title");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        blog =  getIntent().getParcelableExtra("data");
        Glide.with(this).load(blog.getImage()).into(binding.iv);
        binding.tvName.setText(blog.getUserEmail());
        binding.tvContent.setText(blog.getBlogDescription());
        binding.tvTitle.setText(blog.getBlogTitle());
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommentAdapter(this,list);
        binding.recyclerview.setAdapter(adapter);
        binding.btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment();
            }
        });
        initData();
    }
    private void comment(){
        String content = binding.etContent.getText().toString();
        if (TextUtils.isEmpty(content)){
            return;
        }
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("userId", FirebaseAuth.getInstance().getCurrentUser().getUid());
        hashMap.put("content", content);
        hashMap.put("blogId",blog.getBlogId());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("comment").push().setValue(hashMap);
    }
    private void initData() {
        DatabaseReference   databaseReference = FirebaseDatabase.getInstance().getReference().child("comment");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    CommentItem commentItem = snapshot.getValue(CommentItem.class);
                    if (commentItem.getBlogId().equals(blog.getBlogId())) {
                        list.add(commentItem);
                    }
                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}