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
    public String IdentifyNumber;
    public String accountName;
    public String accountNumber;
    public String bankName;

    public PaymentMethod() {
    }

    public PaymentMethod(int userId, String methodType) {
        this.userId = userId;
        this.methodType = methodType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMethodType() {
        return methodType;
    }

    public void setMethodType(String methodType) {
        this.methodType = methodType;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getIdentifyNumber() {
        return IdentifyNumber;
    }

    public void setIdentifyNumber(String identifyNumber) {
        IdentifyNumber = identifyNumber;
    }
}
