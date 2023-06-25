package com.example.fooddeliveryapp.ui.search;


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
import com.example.fooddeliveryapp.data.db.entities.Food;

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
        String imageUrl = foodList.get(position).getFoodImages().get(0).imageUrl;
        holder.txtNameFood.setText(foodList.get(position).getName());
        Glide.with(holder.imgFood.getContext()).load(imageUrl).fitCenter().into(holder.imgFood);
        holder.searchItem.setOnClickListener(v -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                    .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                    .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                    .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim).build();

            Bundle args = new Bundle();
            args.putInt("food_id", foodList.get(position).getId());
            Navigation.findNavController(v).navigate(R.id.foodDetailsFragment, args, navOptions);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNameFood;
        ImageView imgFood;
        ConstraintLayout searchItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameFood = itemView.findViewById(R.id.txtFoodNameSearchItem);
            imgFood = itemView.findViewById(R.id.imgFoodSearchItem);
            searchItem = itemView.findViewById(R.id.searchItem);
        }
    }
}
