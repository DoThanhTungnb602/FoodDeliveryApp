package com.example.fooddeliveryapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fooddeliveryapp.data.db.entities.PaymentMethod;

@Dao
public interface PaymentMethodDao {
    @Query("SELECT * FROM payment_method WHERE userId=:userId")
    PaymentMethod getPaymentMethodByUserId(int userId);

    @Insert
    void insertPaymentMethod(PaymentMethod paymentMethod);

    @Update
    void updatePaymentMethod(PaymentMethod paymentMethod);

    @Delete
    void deletePaymentMethod(PaymentMethod paymentMethod);
}
