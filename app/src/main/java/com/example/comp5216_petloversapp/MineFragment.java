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

public class MineFragment extends Fragment {
    private View root;
    private ImageView imageView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (root == null) {
            //解析fragment的xml
            root = inflater.inflate(R.layout.activity_main3, container, false);
            imageView = root.findViewById(R.id.iv_modify);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(),MainActivity2.class));
                }
            });
        }
        return root;
    }
}
