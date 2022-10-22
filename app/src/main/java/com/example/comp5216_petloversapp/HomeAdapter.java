package com.example.comp5216_petloversapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.comp5216_petloversapp.databinding.ItemHomeBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    List<Blog> blogs;

    public HomeAdapter(List<Blog> blogs) {
        this.blogs = blogs;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.binding.iv.getLayoutParams().height = getHeight(position);
        Glide.with(holder.itemView.getContext()).load(blogs.get(position).getImage()).into(holder.binding.iv);
        FirebaseFirestore.getInstance().
                collection("users").whereEqualTo("email", blogs.get(position).getUserEmail())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Log.d("Task length", String.valueOf(task.getResult().size()));
                                Log.d("User", doc.getId() + "=>" + doc.getData());
                                String userName = doc.getData().get("userName").toString();
                                holder.binding.tvName.setText(userName);
                            }
                        }
                    }
                });
        holder.binding.tvLocation.setText(blogs.get(position).getLocation());
        holder.binding.tvTitle.setText(blogs.get(position).getBlogTitle());
        if (blogs.get(position).getFav()!=null&&blogs.get(position).getFav().contains(FirebaseAuth.getInstance().getUid())){
            holder.binding.ivFavorite.setImageResource( R.drawable.ic_baseline_favorite_24_red);
        }  else{
            holder.binding.ivFavorite.setImageResource( R.drawable.ic_baseline_favorite_24_gray);
        }
        holder.binding.ivFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onFavListener!=null){
                    onFavListener.onClickFav(position);
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent =  new Intent(context, DetailActivity.class);
                intent.putExtra("data",blogs.get(position));
                context.startActivity(intent);
            }
        });
    }

    private int getHeight(int position) {
        int width = (Resources.getSystem().getDisplayMetrics().widthPixels - dip2px(20)) / 2;
        if (position %2== 0) {
            return width;
        } else {
            return width * 2 / 3;
        }
    }

    private int dip2px(float value) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, Resources.getSystem().getDisplayMetrics());
    }
    public interface OnFavListener{
        void onClickFav(int position);
    }
    OnFavListener onFavListener;

    public void setOnFavListener(OnFavListener onFavListener) {
        this.onFavListener = onFavListener;
    }

    @Override
    public int getItemCount() {
        return blogs.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder {
        public ItemHomeBinding binding;

        public HomeViewHolder(ItemHomeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
