package com.example.fooddeliveryapp.ui.user;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


import com.bumptech.glide.Glide;
import com.example.fooddeliveryapp.R;
import com.example.fooddeliveryapp.data.db.AppDatabase;
import com.example.fooddeliveryapp.data.db.entities.User;
import com.example.fooddeliveryapp.data.repositories.UserRepository;
import com.example.fooddeliveryapp.databinding.FragmentUserInformationBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class UserInformationFragment extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    FragmentUserInformationBinding binding;
    NavHostFragment navHostFragment;
    NavController navController;
    FirebaseUser userFirebase = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        StorageReference storageRef = storage.getReference().child("images/myImage.jpg");
        binding = FragmentUserInformationBinding.inflate(inflater, container, false);
        navHostFragment = (NavHostFragment) requireActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_activity_main);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();
        AppDatabase database = AppDatabase.getDatabase(requireActivity());
        String email = userFirebase.getEmail();
        UserRepository userRepository = new UserRepository(database);
        User user = userRepository.getUserByEmail(email);
        binding.edtUserEmail.setText(email);
        if (user.deliveryAddress==null){
            binding.edtAdress.setText("Hãy nhập địa chỉ!");
        }
        binding.edtAdress.setText(user.deliveryAddress);
        binding.edtName.setText(user.name);
        // Inflate the layout for this fragment
        binding.btnBack.setOnClickListener(v -> navController.popBackStack());
        if (user.image==null){
            binding.imgAvatar.setImageResource(R.drawable.ic_user);
        }else {
            Glide.with(binding.imgAvatar.getContext()).load(user.image).into(binding.imgAvatar);
        }
        binding.btnCamera.setOnClickListener(v->{
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        });

        binding.btnLogout2.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Navigation.findNavController(binding.getRoot()).navigate(R.id.navigation_auth);
        });
        binding.btnChangePassword.setOnClickListener(v -> {
            navController.navigate(R.id.action_userInformationFragment_to_updatePasswordFragment);
        });
        binding.txtChangeInfor.setOnClickListener(v -> {
            binding.edtAdress.setEnabled(true);
            binding.btnUpdateUserInfor.setVisibility(View.VISIBLE);
            binding.btnUpdateUserInfor.setOnClickListener(v1 -> {
                user.setDeliveryAddress(binding.edtAdress.getText().toString());
                userRepository.updateUser(user);
                binding.edtAdress.setText(user.deliveryAddress);
                binding.btnUpdateUserInfor.setVisibility(View.GONE);
                binding.edtAdress.setEnabled(false);
            });
        });
        return binding.getRoot();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AppDatabase database = AppDatabase.getDatabase(requireActivity());
        String email = userFirebase.getEmail();
        UserRepository userRepository = new UserRepository(database);
        User user = userRepository.getUserByEmail(email);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            Uri uri = data.getData();
            StorageReference storageRef = storage.getReference().child("images/user"+email+"Avata.jpg");
            UploadTask uploadTask = storageRef.putFile(uri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadUrl = storageRef.getDownloadUrl();
                    downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String imageUrl = uri.toString();
                            Glide.with(binding.imgAvatar.getContext()).load(imageUrl).into(binding.imgAvatar);
                            user.setImage(imageUrl);
                            userRepository.updateUser(user);

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                }
            });
        }
    }
}