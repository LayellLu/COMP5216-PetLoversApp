package com.example.comp5216_petloversapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.example.comp5216_petloversapp.databinding.FragmentOhBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.common.net.InternetDomainName;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OhFragment extends Fragment {
    private FragmentOhBinding binding;
    private ImagePageAdapter imageAdapter = new ImagePageAdapter();
    private HomeAdapter homeAdapter;
    private NewsAdapter newsAdapter;
    List<Blog> blogs = new ArrayList<>();
    private DatabaseReference mDatabase;
    private EditText search_txt_view;

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
        onSearchClick();
    }

    public void onSearchClick() {
        mDatabase = FirebaseDatabase.getInstance().getReference();

        search_txt_view = (EditText) getActivity().findViewById(R.id.search_txt);

        View search_btn_view = getActivity().findViewById(R.id.search_btn);
        search_btn_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTxt = search_txt_view.getText().toString().trim();
                Log.i("searchTxt is: ", searchTxt);
                mDatabase.child("Blogs").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        } else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            HashMap<String, Object> searchResultHM =
                                    (HashMap<String, Object>) task.getResult().getValue();
                            blogs.clear();
                            for (Map.Entry<String, Object> entry : searchResultHM.entrySet()) {
                                System.out.println(entry.getKey());
                                System.out.println(entry.getValue());
                                HashMap<String, String> blogHM = (HashMap<String, String>) entry.getValue();
                                String blogDescription = blogHM.get("blogDescription");
                                String blogId = blogHM.get("blogId");
                                String blogTitle = blogHM.get("blogTitle");
                                String location = blogHM.get("location");
                                String image = blogHM.get("image");
                                String time = blogHM.get("time");
                                String userEmail = blogHM.get("userEmail");

                                Blog blog = new Blog(blogDescription, blogId, blogTitle,
                                        location, image, time, userEmail);
                                if (blog.getBlogDescription().contains(searchTxt)) {
                                    blogs.add(blog);
                                }
                            }
                            homeAdapter.notifyDataSetChanged();
                        }
                    }
                });
            }
        });

    }

    private void initData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Blogs");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                blogs.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Blog blog = snapshot.getValue(Blog.class);
                    blog.setKey(snapshot.getKey());
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
        homeAdapter.setOnFavListener(new HomeAdapter.OnFavListener() {
            @Override
            public void onClickFav(int position) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Blogs")
                        .child(blogs.get(position).getKey());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Blogs");
                        Blog blog = snapshot.getValue(Blog.class);
                        if (blog.getFav() != null && blog.getFav().contains(FirebaseAuth.getInstance().getUid())) {
                            //
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
        binding.rvData.setAdapter(homeAdapter);

        newsAdapter = new NewsAdapter();
        binding.tl.addTab(binding.tl.newTab().setText("Tab1").setId(0));
        binding.tl.addTab(binding.tl.newTab().setText("News").setId(1));
        binding.tl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getId() == 0) {
                    binding.rvData.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
                    binding.rvData.setAdapter(homeAdapter);
                } else {
                    binding.rvData.setLayoutManager(new LinearLayoutManager(getContext()));
                    binding.rvData.setAdapter(newsAdapter);
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
