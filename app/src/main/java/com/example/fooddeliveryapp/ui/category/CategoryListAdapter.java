package com.example.fooddeliveryapp.ui.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.entities.Category;

import java.util.List;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    List<Category> categoryList;

    public CategoryListAdapter(List<Category> categoryList) {
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.categoryItem.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("categoryName", categoryList.get(position).getName());
            NavOptions navOptions = new NavOptions.Builder()
                    .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                    .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                    .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                    .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
                    .build();
            Navigation.findNavController(v).navigate(R.id.foodFragment, args, navOptions);
        });

        holder.txtCategoryItem.setText(categoryList.get(position).name);
        Glide.with(holder.imgCategoryItem.getContext())
                .load(categoryList.get(position).image)
                .into(holder.imgCategoryItem);
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout categoryItem;
        ImageView imgCategoryItem;
        TextView txtCategoryItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryItem = itemView.findViewById(R.id.categoryItem);
            imgCategoryItem = itemView.findViewById(R.id.imgCategoryItem);
            txtCategoryItem = itemView.findViewById(R.id.txtCategoryItem);
        }
    }
}
