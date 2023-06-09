package com.example.fooddeliveryapp.ui.cart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.entities.Cart;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    List<Cart> listCart;

    public CartListAdapter(List<Cart> listCart) {
        this.listCart = listCart;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imgCartItem.setImageResource(R.drawable.ic_hotpot);
        holder.btnCartItemIncrease.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.txtCartItemQuantity.getText().toString());
            quantity++;
            holder.txtCartItemQuantity.setText(String.valueOf(quantity));
        });
        holder.btnCartItemDecrease.setOnClickListener(v -> {
            int quantity = Integer.parseInt(holder.txtCartItemQuantity.getText().toString());
            if (quantity > 1) {
                quantity--;
                holder.txtCartItemQuantity.setText(String.valueOf(quantity));
            }
        });
    }

    @Override
    public int getItemCount() {
        return 7;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCartItem;
        TextView txtCartItemTitle;
        TextView txtCartItemPrice;
        TextView txtCartItemQuantity;
        Button btnCartItemIncrease;
        Button btnCartItemDecrease;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCartItem = itemView.findViewById(R.id.imgCartItem);
            txtCartItemTitle = itemView.findViewById(R.id.txtCartItemTitle);
            txtCartItemPrice = itemView.findViewById(R.id.txtCartItemPrice);
            txtCartItemQuantity = itemView.findViewById(R.id.txtCartItemQuantity);
            btnCartItemIncrease = itemView.findViewById(R.id.btnCartItemIncrease);
            btnCartItemDecrease = itemView.findViewById(R.id.btnCartItemDecrease);
        }
    }
}
