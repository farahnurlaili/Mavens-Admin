package com.example.mavensproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mavensproject.databinding.ActivityProfilePageBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class ProfilePage extends AppCompatActivity {
    ActivityProfilePageBinding binding;
    private OrderAdapter orderAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityProfilePageBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());

        binding.name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        binding.email.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        binding.phone.setText(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber());

        orderAdapter=new OrderAdapter(this);
        binding.ordersRecycler.setAdapter(orderAdapter);
        binding.ordersRecycler.setLayoutManager(new LinearLayoutManager(this));

        binding.buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilePage.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        getOrders();
    }
    private void getOrders() {
        FirebaseFirestore.getInstance()
                .collection("orders")
                .whereEqualTo("uid", FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot ds:dsList) {
                            OrderModel orderModel=ds.toObject(OrderModel.class);
                            orderAdapter.addProduct(orderModel);
                        }
                    }
                });
    }
}