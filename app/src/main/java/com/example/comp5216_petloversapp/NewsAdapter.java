package com.example.comp5216_petloversapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.comp5216_petloversapp.databinding.ItemNewsBinding;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.VH> {
    private List<News> data;

    public NewsAdapter() {
        this.data = new ArrayList<>();
        data.add(new News("The Petspiration Foundation partners with Assistance Dogs Australia", "by Thomas Oakley-Newell", "October 17, 2022", R.drawable.news_1));
        data.add(new News("Laila and Me launch crowdfunding campaign due to growing demand for healthy pet treats", "by Rachel White", "October 17, 2022", R.drawable.news_2));
        data.add(new News("A new home for thousands of cats and dogs used in animal research", "by Rachel White", "October 17, 2022", R.drawable.news_3));
        data.add(new News("Cyber pets: dog ownership in the digital age", "by Rachel White", "October 17, 2022", R.drawable.news_4));
        data.add(new News("Podcast Episode 8: Shiva Greenhalgh, Pet Nutritionist", "by Thomas Oakley-Newell", "September 5, 2022", R.drawable.news_5));
        data.add(new News("Podcast Episode 9: Jenny Richards, The Paw Grocer",  "by Thomas Oakley-Newell", "September 19, 2022", R.drawable.news_6));
        data.add(new News("Australia’s Top 10 most popular pets revealed", "by Intermedia", "September 30, 2019", R.drawable.news_7));
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        News news = this.data.get(position);
        holder.binding.tvTitle.setText(news.title);
        holder.binding.tvContent.setText(news.description);
        Glide.with(holder.itemView).load(news.image).into(holder.binding.face);
        holder.binding.time.setText(news.time);
        holder.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                context.startActivity(new Intent(context, DetailNewsActivity.class).putExtra("news", news));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class VH extends RecyclerView.ViewHolder {
        ItemNewsBinding binding;

        public VH(ItemNewsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
