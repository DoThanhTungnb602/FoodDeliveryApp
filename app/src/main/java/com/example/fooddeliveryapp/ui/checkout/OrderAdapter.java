package com.example.fooddeliveryapp.ui.checkout;

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
    // Danh sách đơn hàng
    private final List<Order> orderList;
    // Repository đơn hàng
    private final OrderRepository orderRepository;
    // Repository món ăn
    private final FoodRepository foodRepository;
    // Định dạng tiền tệ
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

    // Hàm khởi tạo
    public OrderAdapter(List<Order> orderList, OrderRepository orderRepository, FoodRepository foodRepository) {
        this.orderList = orderList;
        this.orderRepository = orderRepository;
        this.foodRepository = foodRepository;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Gán layout cho item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    // Gán dữ liệu cho item
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Gán trạng thái đơn hàng
        holder.txtOrderStatus.setText("Trạng thái: " + orderList.get(position).getStatus());
        // Gán tổng tiền của hóa đơn
        holder.txtOrderPrice.setText(currencyFormat.format(orderList.get(position).getTotalCost()));
        // Gán ngày đặt hàng
        holder.txtOrderDate.setText("Ngày đặt: " + orderList.get(position).getOrderDate());
        // Gán địa chỉ nhận hàng
        holder.txtOrderAddress.setText("Địa chỉ nhận hàng: " + orderList.get(position).getAddress());
        // Gán phương thức thanh toán
        holder.txtOrderPaymentMethod.setText("Phương thức thanh toán: " + orderList.get(position).getPaymentMethod());
        // Lấy danh sách order details
        List<OrderDetails> orderDetailsList = orderRepository.getOrderDetails(orderList.get(position));
        // Lấy danh sách tên món ăn
        List<String> orderProductName = new ArrayList<>();
        for (OrderDetails orderDetails : orderDetailsList) {
            Food food = foodRepository.getFoodById(orderDetails.getFoodId());
            orderProductName.add(orderDetails.getQuantity() + "x " + food.getName());
        }
        // Gán danh sách tên món ăn cho view
        holder.txtOrderProducts.setText("Món ăn: " + String.join(", ", orderProductName));
    }

    @Override
    // Đếm số lượng đơn hàng
    public int getItemCount() {
        return orderList.size();
    }

    // Class ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Các view trong item
        TextView txtOrderStatus, txtOrderDate, txtOrderPrice, txtOrderAddress, txtOrderPaymentMethod, txtOrderProducts;

        public ViewHolder(@NonNull View itemView) {
            // Ánh xạ view
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
