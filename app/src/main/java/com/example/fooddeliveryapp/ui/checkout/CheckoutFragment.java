package com.example.fooddeliveryapp.ui.checkout;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavHostController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.entities.Cart;
import com.example.fooddeliveryapp.data.db.entities.Food;
import com.example.fooddeliveryapp.data.db.entities.PaymentMethod;
import com.example.fooddeliveryapp.data.db.entities.User;
import com.example.fooddeliveryapp.data.repositories.CartRepository;
import com.example.fooddeliveryapp.data.repositories.FoodRepository;
import com.example.fooddeliveryapp.data.repositories.OrderRepository;
import com.example.fooddeliveryapp.data.repositories.UserRepository;
import com.example.fooddeliveryapp.databinding.FragmentCheckoutBinding;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class CheckoutFragment extends Fragment {
}