package com.example.fooddeliveryapp.ui.search;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.ui.food.FoodListAdapter;

import java.util.List;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {
    List<Food> foodList;

    public SearchListAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public SearchListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchListAdapter.ViewHolder holder, int position) {
        System.out.println("Name from adapter: " + foodList.get(position).getName());
        String imageUrl = foodList.get(position).getFoodImages().get(0).imageUrl;
        holder.txtNameFood.setText(foodList.get(position).getName());
        Glide.with(holder.Foodimg.getContext()).load(imageUrl).fitCenter().into(holder.Foodimg);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameFood;
        ImageView Foodimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameFood = itemView.findViewById(R.id.txtNameFood);
            Foodimg = itemView.findViewById(R.id.Foodimg);
        }
    }
}
