package com.example.comp5216_petloversapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comp5216_petloversapp.databinding.ItemHomeBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    private List<Integer> images = new ArrayList<>();

    private Random random = new Random();

    private int count = random.nextInt(100) + 50;

    public HomeAdapter() {
        images.add(R.drawable.iamge_1);
        images.add(R.drawable.image_2);
        images.add(R.drawable.iamge_3);
        images.add(R.drawable.image_4);
        images.add(R.drawable.image_5);
        images.add(R.drawable.image_6);
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.binding.iv.getLayoutParams().height = getHeight();
        holder.binding.iv.setImageResource(images.get(random.nextInt(images.size())));
        holder.binding.ivHead.setImageResource(images.get(random.nextInt(images.size())));
        holder.binding.ivFavorite.setImageResource(random.nextInt(2) == 1 ? R.drawable.ic_baseline_favorite_24_red : R.drawable.ic_baseline_favorite_24_gray);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                context.startActivity(new Intent(context, DetailActivity.class));
            }
        });
    }

    private int getHeight() {
        int width = (Resources.getSystem().getDisplayMetrics().widthPixels - dip2px(20)) / 2;
        if (random.nextInt(2) == 1) {
            return width;
        } else {
            return width * 2 / 3;
        }
    }

    private int dip2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().getDisplayMetrics());
    }

    @Override
    public int getItemCount() {
        return count;
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        public ItemHomeBinding binding;

        public HomeViewHolder(ItemHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
