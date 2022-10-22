package com.example.comp5216_petloversapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MyPostAdapter extends RecyclerView.Adapter<MyPostAdapter.HomeViewHolder> {

    List<Blog> blogs;
    Context context ;

    public MyPostAdapter(Context context, List<Blog> blogs) {
        this.context = context;
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
//        holder.binding.tvName.setText(blogs.get(position).getUserEmail());
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
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.i("MyPostAdapter", "Long Clicked item " + position);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(R.string.dialog_delete_title)
                        .setMessage(R.string.dialog_delete_msg)
                        .setPositiveButton(R.string.delete, new
                                DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // Remove item from the ArrayList
                                        FirebaseAuth auth = FirebaseAuth.getInstance();
                                        String filename = auth.getCurrentUser().getEmail().split("@")[0];
                                        Log.i("auth is: ", auth.getCurrentUser().getEmail());
                                        Blog blog = blogs.get(position);
                                        String idStr = blog.getBlogId().split("_")[1];
                                        idStr = idStr + "_" + blog.getBlogId().split("_")[2];
                                        String blogId =filename + "_" + idStr;
                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                                                .child("Blogs").child(blogId);
                                        databaseReference.removeValue();
                                    }
                                })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // User cancelled the dialog
                                // Nothing happens
                            }
                        });
                builder.create().show();



                return true;
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
