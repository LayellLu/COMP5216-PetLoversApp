package com.example.comp5216_petloversapp;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ImagePageAdapter extends PagerAdapter {
    List<Integer> images;

    public ImagePageAdapter() {
        images = new ArrayList<>();
        images.add(R.drawable.iamge_1);
        images.add(R.drawable.image_2);
        images.add(R.drawable.iamge_3);
        images.add(R.drawable.image_4);
        images.add(R.drawable.image_5);
        images.add(R.drawable.image_6);
    }

    public List<Integer> getImages() {
        return images;
    }

    @Override
    public int getCount() {
        return images.size() + 2;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        Log.d("TAG", "instantiateItem1: "+position);
        position = fixPosition(position);
        // 2 0 1 2 0

        Log.d("TAG", "instantiateItem2: "+position);
        ImageView imageView = new ImageView(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(images.get(position% images.size()));
        container.addView(imageView);
        return imageView;
    }

    private int fixPosition(int position) {
        if (position == 0) {
            return images.size()-1;
        } else if (position == getCount() -1) {
            return 0;
        }
        return position - 1;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
