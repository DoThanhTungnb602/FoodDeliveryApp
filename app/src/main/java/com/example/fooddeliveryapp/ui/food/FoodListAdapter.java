package com.example.fooddeliveryapp.ui.food;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.entities.Food;

import java.util.List;

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder> {

    List<Food> foodList;

    public FoodListAdapter(List<Food> foodList) {
        this.foodList = foodList;
    }

    @NonNull
    @Override
    public FoodListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodListAdapter.ViewHolder holder, int position) {
        holder.cardView.setOnClickListener(v -> {
            NavOptions navOptions = new NavOptions.Builder()
                    .setEnterAnim(androidx.navigation.ui.R.anim.nav_default_enter_anim)
                    .setExitAnim(androidx.navigation.ui.R.anim.nav_default_exit_anim)
                    .setPopEnterAnim(androidx.navigation.ui.R.anim.nav_default_pop_enter_anim)
                    .setPopExitAnim(androidx.navigation.ui.R.anim.nav_default_pop_exit_anim)
                    .build();
            Navigation.findNavController(v).navigate(R.id.foodDetailsFragment, null, navOptions);
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imgSearchItem;
        TextView txtSearchTitle;
        TextView txtSearchPrice;
        TextView txtSearchOriginalPrice;
        TextView txtSearchRating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.foodItemContainer);
            imgSearchItem = itemView.findViewById(R.id.imgSearchItem);
            txtSearchTitle = itemView.findViewById(R.id.txtSearchTitle);
            txtSearchPrice = itemView.findViewById(R.id.txtSeachPrice);
            txtSearchRating = itemView.findViewById(R.id.txtSearchRating);
        }
    }
}
