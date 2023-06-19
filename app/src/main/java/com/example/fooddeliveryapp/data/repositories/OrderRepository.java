package com.example.fooddeliveryapp.data.repositories;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.OrderDao;
import com.example.fooddeliveryapp.data.db.dao.OrderDetailsDao;
import com.example.fooddeliveryapp.data.db.entities.Order;
import com.example.fooddeliveryapp.data.db.entities.OrderDetails;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class OrderRepository {
    AppDatabase database;

    OrderDao orderDao;
    OrderDetailsDao orderDetailsDao;
    private final LiveData<List<Order>> listOrder;

    /**
     * Khởi tạo đối tượng OrderRepository.
     *
     * @param database là đối tượng AppDatabase.
     */
    public OrderRepository(AppDatabase database) {
        this.database = database;
        this.orderDao = database.orderDao();
        this.orderDetailsDao = database.orderDetailsDao();
        this.listOrder = orderDao.getOrderListByUserID(MainActivity.currentUserID);
    }

    /**
     * Lấy danh sách các đơn hàng.
     *
     * @return danh sách các đơn hàng.
     */
    public LiveData<List<Order>> getOrderList() {
        return listOrder;
    }

    /**
     * Lấy ra chi tiết hóa đơn
     *
     * @param order là đơn hàng cần lấy chi tiết.
     * @return Chi tiết của hóa đơn. Là một danh sách các đối tượng OrderDetails.
     */
    public List<OrderDetails> getOrderDetails(Order order) {
        return orderDetailsDao.getOrderDetailsByOrderId(order.getId());
    }

    /**
     * Thêm một đơn hàng mới vào database.
     *
     * @param status    là trạng thái của đơn hàng.
     * @param totalCost là tổng giá trị của đơn hàng.
     */
    public void insertOrder(String status, int totalCost) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String date = simpleDateFormat.format(pattern);
        Order order = new Order(date, totalCost, status, MainActivity.currentUserID);
        orderDao.insertOrder(order);
    }

    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    public void deleteOrder(Order order) {
        orderDao.deleteOrder(order);
    }
}