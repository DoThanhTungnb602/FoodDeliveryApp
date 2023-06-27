package com.example.fooddeliveryapp.ui.history;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.db.entities.Order;
import com.example.fooddeliveryapp.data.db.entities.OrderDetails;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.data.repositories.OrderRepository;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private final List<Order> orderList;
    private final OrderRepository orderRepository;
    private final FoodRepository foodRepository;
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    public OrderAdapter(List<Order> orderList, OrderRepository orderRepository, FoodRepository foodRepository) {
        this.orderList = orderList;
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtOrderStatus.setText("Trạng thái: " + orderList.get(position).getStatus());
        holder.txtOrderPrice.setText(currencyFormat.format(orderList.get(position).getTotalCost()));
        holder.txtOrderDate.setText("Ngày đặt: " + orderList.get(position).getOrderDate());
        holder.txtOrderAddress.setText("Địa chỉ nhận hàng: " + orderList.get(position).getAddress());
        holder.txtOrderPaymentMethod.setText("Phương thức thanh toán: " + orderList.get(position).getPaymentMethod());
        List<OrderDetails> orderDetailsList = orderRepository.getOrderDetails(orderList.get(position));
        List<String> orderProductName = new ArrayList<>();
        for (OrderDetails orderDetails : orderDetailsList) {
            Food food = foodRepository.getFoodById(orderDetails.getFoodId());
            orderProductName.add(orderDetails.getQuantity() + "x " + food.getName());
        }
        holder.txtOrderProducts.setText("Món ăn: " + String.join(", ", orderProductName));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtOrderStatus, txtOrderDate, txtOrderPrice, txtOrderAddress, txtOrderPaymentMethod, txtOrderProducts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
            txtOrderDate = itemView.findViewById(R.id.txtOrderDate);
            txtOrderPrice = itemView.findViewById(R.id.txtOrderPrice);
            txtOrderAddress = itemView.findViewById(R.id.txtOrderAddress);
            txtOrderPaymentMethod = itemView.findViewById(R.id.txtOrderPaymentMethod);
            txtOrderProducts = itemView.findViewById(R.id.txtOrderProducts);
        }
    }
}
