package com.example.fooddeliveryapp.data.repositories;

import com.example.fooddeliveryapp.MainActivity;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.dao.PaymentMethodDao;
import com.example.fooddeliveryapp.data.db.dao.UserDao;
import com.example.fooddeliveryapp.data.db.entities.PaymentMethod;
import com.example.fooddeliveryapp.data.db.entities.User;

import java.util.List;

public class UserRepository {
    AppDatabase database;

    UserDao userDao;
    PaymentMethodDao paymentMethodDao;

    /**
     * Khởi tạo đối tượng UserRepository.
     *
     * @param database là đối tượng AppDatabase.
     */
    public UserRepository(AppDatabase database) {
        this.database = database;
        this.userDao = database.userDao();
        this.paymentMethodDao = database.paymentMethodDao();
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethodDao.getPaymentMethodByUserId(MainActivity.currentUserID);
    }

    public void updatePaymentMethod(PaymentMethod paymentMethod) {
        paymentMethodDao.updatePaymentMethod(paymentMethod);
    }

    /**
     * Lấy ra user theo id.
     *
     * @param id là id của user.
     * @return user theo id.
     */
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

    /**
     * Thêm user.
     *
     * @param user là user cần thêm.
     */
    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    /**
     * Cập nhật user.
     *
     * @param user là user cần cập nhật.
     */

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    /**
     * Xóa user.
     *
     * @param user là user cần xóa.
     */
    public void deleteUser(User user) {
        userDao.deleteUser(user);
    }

    public void insertUsers(List<User> users) {
        userDao.insertUsers(users);
    }

    public void deleteAllUser() {
        userDao.deleteAllUser();
    }

    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }
}
