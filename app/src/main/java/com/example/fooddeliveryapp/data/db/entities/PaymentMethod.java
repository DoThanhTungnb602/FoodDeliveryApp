package com.example.fooddeliveryapp.data.db.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(
        tableName = "payment_method",
        foreignKeys = {
                @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "userId", onDelete = ForeignKey.CASCADE)
        })
public class PaymentMethod {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int userId;
    public String methodType;
    public String accountName;
    public String accountNumber;
    public String bankName;
}
