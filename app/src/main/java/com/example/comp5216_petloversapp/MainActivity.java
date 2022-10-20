package com.example.comp5216_petloversapp;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.comp5216_petloversapp.databinding.ActivityMainBinding;
import com.google.firebase.database.*;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");


    private OhFragment fragment1;
    private OhFragment fragment2;

    private TextView addPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        OhFragment fragment1 = new OhFragment();
        MineFragment fragment2 = new MineFragment();
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

        addPost = findViewById(R.id.add_post);
        addPost.setOnClickListener(v -> {
            startActivity(new Intent(this, Add_post.class));
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }

}