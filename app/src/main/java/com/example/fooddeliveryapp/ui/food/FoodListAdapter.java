package com.example.fooddeliveryapp.ui.food;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.entities.Food;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {

    List<Food> foodList;
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public FoodListAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.ViewHolder holder, int position) {
        holder.cardView.setOnClickListener(v -> {
            NavOptions navOptions = new NavOptions.Builder().setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim).setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim).setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim).setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim).build();
            Navigation.findNavController(v).navigate(R.id.foodDetailsFragment, null, navOptions);
        });

        String imageUrl = foodList.get(position).getFoodImages().get(0).imageUrl;
        String deliveryTime = foodList.get(position).getDeliveryTime() + " phút";
        String name = foodList.get(position).getName();
        String price = currencyFormat.format(foodList.get(position).getPrice());
        String rating = String.valueOf(foodList.get(position).getAverageRating());

        Glide.with(holder.imgFoodItem.getContext()).load(imageUrl).into(holder.imgFoodItem);
        holder.txtDeliveryTimeFoodItem.setText(deliveryTime);
        holder.txtTitleFoodItem.setText(name);
        holder.txtPriceFoodItem.setText(price);
        holder.txtRatingFoodItem.setText(rating);
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imgFoodItem;
        TextView txtDeliveryTimeFoodItem;
        TextView txtTitleFoodItem;
        TextView txtPriceFoodItem;
        TextView txtRatingFoodItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.foodItemContainer);
            imgFoodItem = itemView.findViewById(R.id.imgFoodItem);
            txtDeliveryTimeFoodItem = itemView.findViewById(R.id.txtDeliveryTimeFoodItem);
            txtTitleFoodItem = itemView.findViewById(R.id.txtTitleFoodItem);
            txtPriceFoodItem = itemView.findViewById(R.id.txtPriceFoodItem);
            txtRatingFoodItem = itemView.findViewById(R.id.txtRatingFoodItem);
        }
    }
}
