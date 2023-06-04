package com.example.fooddeliveryapp.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.entities.Food;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {
    List<Food> listFood;

    public FoodListAdapter(List<Food> listFood) {
        this.listFood = listFood;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        Food food = listFood.get(position);
        holder.foodImage.setImageResource(R.drawable.ic_hotpot);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView foodTitle;
        private final TextView foodDeliveryTime;
        private final TextView foodPrice;
        private final TextView foodRating;
        private final ImageView foodImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodTitle = itemView.findViewById(R.id.txtFoodTitle);
            foodDeliveryTime = itemView.findViewById(R.id.txtDeliveryTime);
            foodPrice = itemView.findViewById(R.id.txtFoodPrice);
            foodRating = itemView.findViewById(R.id.txtFoodRating);
            foodImage = itemView.findViewById(R.id.imageViewFoodItem);
        }
    }
}
