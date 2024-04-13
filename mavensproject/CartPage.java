package com.example.mavensproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.mavensproject.databinding.ActivityCartPageBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class CartPage extends AppCompatActivity {
    ActivityCartPageBinding binding;
    private CartAdapter cartAdapter;
    public static List<CartModel> cartItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCartPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        cartAdapter=new CartAdapter(this);
        binding.cartRecycler.setAdapter(cartAdapter);
        binding.cartRecycler.setLayoutManager(new LinearLayoutManager(this));
        getCartItems();

        binding.arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartPage.this, Homepage.class));
            }
        });

        binding.proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View view) {
                cartItemList=cartAdapter.getSelectedItems();
                startActivity(new Intent(CartPage.this, OrderPlacing.class));
            }
        });
    }

    private void getCartItems() {
        FirebaseFirestore.getInstance()
                .collection("cart")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot ds:dsList) {
                            CartModel cartModel=ds.toObject(CartModel.class);
                            cartAdapter.addProduct(cartModel);
                        }
                    }
                });
    }
}