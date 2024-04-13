package com.example.mavensproject;

import static com.example.mavensproject.CartPage.cartItemList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mavensproject.databinding.ActivityDetailBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.UUID;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    private ProductModel productModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Intent intent=getIntent();

        binding.arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailActivity.this, Homepage.class));
            }
        });

        productModel=(ProductModel) intent.getSerializableExtra("model");

        binding.title.setText(productModel.getTitle());
        binding.description.setText(productModel.getDescription());
        binding.price.setText(productModel.getPrice());
        Glide.with(binding.getRoot())
                .load(productModel.getImage())
                .into(binding.image);

        binding.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(0);
                //addToCart();
            }
        });
        binding.buyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheet(1);
            }
        });
    }

    private void showBottomSheet(int i) {
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(this);
        View view= LayoutInflater.from(DetailActivity.this).inflate(R.layout.bottom_layout, null,
                false);
        bottomSheetDialog.setContentView(view);

        EditText qty=view.findViewById(R.id.qty);
        //RecyclerView recyclerViewSize = view.findViewById(R.id.recyclerViewSize);
        //recyclerViewSize.setAdapter(new SizeAdapter(list));
        //recyclerViewSize.setLayoutManager(new LinearLayoutManager(DetailActivity.this, LinearLayoutManager.HORIZONTAL, false));
        Button btn=view.findViewById(R.id.checkOut);
        bottomSheetDialog.show();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantity = qty.getText().toString();
                if (i == 0) {
                    //Add to Cart
                    addToCart(quantity);
                    bottomSheetDialog.cancel();
                }
                else if (i==1) {
                    CartModel cartModel=new CartModel(null,productModel.getTitle(),productModel.getImage(),productModel.getPrice(),quantity,FirebaseAuth.getInstance().getUid(),
                            null);
                    cartItemList=new ArrayList<>();
                    cartItemList.add(cartModel);
                    startActivity(new Intent(DetailActivity.this, OrderPlacing.class));
                    bottomSheetDialog.cancel();
                }
            }
        });

    }

    private void addToCart(String qty) {
        //ProgressDialog progressDialog=new ProgressDialog(this);
        //progressDialog.setTitle("Adding");
        //progressDialog.setMessage("Item In Cart");
        //progressDialog.show();
        String id= UUID.randomUUID().toString();
        CartModel cartModel=new CartModel(id,productModel.getTitle(), productModel.getImage(),productModel.getPrice(),qty, FirebaseAuth.getInstance().getUid(),null);
        FirebaseFirestore.getInstance()
                .collection("cart")
                .document(id)
                .set(cartModel);
        Toast.makeText(this, "Added to Cart", Toast.LENGTH_SHORT).show();
    }

    //private ArrayList<String> list = new ArrayList<>();
    //{
        //ArrayList<String> list =new ArrayList<>();
        //list.add("S");
        //list.add("M");
        //list.add("L");
        //list.add("XL");
        //list.add("XXL");
    //}
}

