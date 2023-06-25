package com.example.fooddeliveryapp.data.repositories;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.OrderDao;
import com.example.fooddeliveryapp.data.db.dao.OrderDetailsDao;
import com.example.fooddeliveryapp.data.db.dao.PaymentMethodDao;
import com.example.fooddeliveryapp.data.db.dao.UserDao;
import com.example.fooddeliveryapp.data.db.entities.Cart;
import com.example.fooddeliveryapp.data.db.entities.Order;
import com.example.fooddeliveryapp.data.db.entities.OrderDetails;
import com.example.fooddeliveryapp.data.db.entities.PaymentMethod;
import com.example.fooddeliveryapp.data.db.entities.User;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class OrderRepository {
    AppDatabase database;

    OrderDao orderDao;
    OrderDetailsDao orderDetailsDao;
    PaymentMethodDao paymentMethodDao;
    UserDao userDao;
    private final List<Order> listOrder;

    /**
     * Khởi tạo đối tượng OrderRepository.
     *
     * @param database là đối tượng AppDatabase.
     */
    public OrderRepository(AppDatabase database) {
        this.database = database;
        this.orderDao = database.orderDao();
        this.orderDetailsDao = database.orderDetailsDao();
        this.userDao = database.userDao();
        this.paymentMethodDao = database.paymentMethodDao();
        this.listOrder = orderDao.getOrderListByUserID(MainActivity.currentUserID);
    }

    /**
     * Lấy danh sách các đơn hàng.
     *
     * @return danh sách các đơn hàng.
     */
    public List<Order> getOrderList() {
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
    public void insertOrder(String status, int totalCost, List<Cart> cartList) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String date = currentDateTime.format(formatter);
        User user = userDao.getUserById(MainActivity.currentUserID);
        PaymentMethod paymentMethod = paymentMethodDao.getPaymentMethodByUserId(MainActivity.currentUserID);
        Order order = new Order(date, totalCost, status, user.getDeliveryAddress(), paymentMethod.getMethodType(), MainActivity.currentUserID);
        orderDao.insertOrder(order);
        Order ordered = orderDao.getOrderByDate(date);
        for (int i = 0; i < cartList.size(); i++) {
            OrderDetails orderDetails = new OrderDetails(ordered.getId(), cartList.get(i).getFoodId(), cartList.get(i).getQuantity());
            orderDetailsDao.insertOrderDetails(orderDetails);
        }
    }

    public void updateOrder(Order order) {
        orderDao.updateOrder(order);
    }

    public void deleteOrder(Order order) {
        orderDao.deleteOrder(order);
    }
}
