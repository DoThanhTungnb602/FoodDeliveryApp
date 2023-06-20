package com.example.fooddeliveryapp.ui.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.repositories.CartRepository;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.data.db.entities.Cart;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    List<Food> listfood;
    List<Cart> listCart;
    Cart cart;
    CartFragment cartFragment;
    int Sum = 0;

    public CartListAdapter(List<Cart> listCart, CartFragment cartFragment) {
        this.listCart = listCart;
        this.cartFragment = cartFragment;
    }
    public void setListCart(List<Cart> listCart){
        this.listCart = listCart;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        holder.imgCartItem.setImageResource(R.drawable.ic_hotpot);
        holder.btnCartItemIncrease.setOnClickListener(v -> {
            cart = listCart.get(position);
            int quantity = Integer.parseInt(holder.txtCartItemQuantity.getText().toString());
            quantity++;
            cart.setQuantity(quantity);
            holder.cartRepository.updateCart(cart);
            holder.txtCartItemQuantity.setText(String.valueOf(quantity));
            cartFragment.updatePrice();
        });
        holder.btnCartItemDecrease.setOnClickListener(v -> {
            cart = listCart.get(position);
            int quantity = Integer.parseInt(holder.txtCartItemQuantity.getText().toString());
            if (quantity > 1) {
                quantity--;
                cart.setQuantity(quantity);
                holder.cartRepository.updateCart(cart);
                holder.txtCartItemQuantity.setText(String.valueOf(quantity));
                cartFragment.updatePrice();
            }else{
                holder.cartRepository.deleteCart(cart);
                listCart.remove(cart);
                cartFragment.updatePrice();
                notifyDataSetChanged();
            }
        });
        Food food = holder.foodRepository.getFoodById(listCart.get(position).getFoodId());
        holder.txtCartItemTitle.setText(food.name);
        holder.txtCartItemPrice.setText(String.valueOf(numberFormat.format(food.price)));
        holder.txtCartItemQuantity.setText(String.valueOf(listCart.get(position).getQuantity()));
    }


    @Override
    public int getItemCount() {
        if (this.listCart == null) {
            return 0;
        }
        return this.listCart.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCartItem;
        TextView txtCartItemTitle;
        TextView txtCartItemPrice;
        TextView txtCartItemQuantity;
        TextView txtTotal;
        Button btnCartItemIncrease;
        Button btnCartItemDecrease;
        FoodRepository foodRepository;
        CartRepository cartRepository;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            foodRepository = new FoodRepository(AppDatabase.getDatabase(itemView.getContext()));
            cartRepository = new CartRepository(AppDatabase.getDatabase(itemView.getContext()));
            imgCartItem = itemView.findViewById(R.id.imgCartItem);
            txtCartItemTitle = itemView.findViewById(R.id.txtCartItemTitle);
            txtCartItemPrice = itemView.findViewById(R.id.txtCartItemPrice);
            txtTotal = itemView.findViewById(R.id.txtTotal);
            txtCartItemQuantity = itemView.findViewById(R.id.txtCartItemQuantity);
            btnCartItemIncrease = itemView.findViewById(R.id.btnCartItemIncrease);
            btnCartItemDecrease = itemView.findViewById(R.id.btnCartItemDecrease);
        }
    }
}
