package com.example.mavensproject;

import static com.example.mavensproject.CartPage.cartItemList;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.IInterface;
import android.view.View;

import com.example.mavensproject.databinding.ActivityOrderPlacingBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Random;
import java.util.UUID;

public class OrderPlacing extends AppCompatActivity {
    ActivityOrderPlacingBinding binding;
    int mainTotal=0;
    private String name,phone, address,cityName,postCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderPlacingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OrderPlacing.this, CartPage.class));
            }
        });

        binding.name.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        binding.placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name=binding.name.getText().toString();
                phone=binding.phone.getText().toString();
                address=binding.address.getText().toString();
                cityName=binding.cityName.getText().toString();
                postCode=binding.postCode.getText().toString();
                placeOrder();
            }
        });
    }

    private void placeOrder() {
        //123456
        //100000 99999
        ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Placing");
        progressDialog.setMessage("your order");
        progressDialog.show();

        String orderNumber=String.valueOf(getRandomNumber(100000,999999));
        OrderModel orderModel=new OrderModel(orderNumber
        ,name,phone,cityName,address,String.valueOf(mainTotal), "5", null, "J&T",
                String.valueOf(Calendar.getInstance().getTimeInMillis()), "Pending", FirebaseAuth.getInstance().getUid());

        FirebaseFirestore.getInstance()
                .collection("orders")
                .document(orderNumber)
                .set(orderModel)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        for (int i=0; i<cartItemList.size();i++) {
                            CartModel cartModel=cartItemList.get(i);
                            cartModel.setOrderNumber(orderNumber);

                            String id= UUID.randomUUID().toString();
                            cartModel.setCartId(id);
                            FirebaseFirestore.getInstance()
                                    .collection("orderProducts")
                                    .document(id)
                                    .set(cartModel);
                        }

                        Intent intent = new Intent(OrderPlacing.this, PaymentActivity.class);
                        startActivity(intent);

                        finish();
                        progressDialog.cancel();
                    }
                });
    }
    public static int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }

    @Override
    protected void onStart() {
        super.onStart();
        for (int i=0; i<cartItemList.size();i++) {
            CartModel cartModel=cartItemList.get(i);
            int price=Integer.parseInt(cartModel.getProductPrice());
            int qty=Integer.parseInt(cartModel.getProductQty());
            int total=price*qty;
            mainTotal+=total;
        }
        binding.expense.setText(String.valueOf(mainTotal));
        binding.delivery.setText("5");
        binding.total.setText(String.valueOf(mainTotal+5));
    }
}