package com.example.mavensproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import pl.droidsonroids.gif.GifDrawable;
import android.widget.ImageView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.mavensproject.databinding.ActivityHomepageBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;

import java.util.List;

public class Homepage extends AppCompatActivity {
    ActivityHomepageBinding binding;
    private ProductAdapter productAdapter;
    private FirebaseFirestore firebaseFirestore;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.myToolbar);
        productAdapter=new ProductAdapter(this);
        binding.productRecycler.setAdapter(productAdapter);
        binding.productRecycler.setLayoutManager(new LinearLayoutManager(this));

        getProducts();

        binding.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homepage.this,CartPage.class));
            }
        });
        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Homepage.this,ProfilePage.class));
            }
        });

        bottom_navigation();
    }

    private void bottom_navigation() {
        LinearLayout homeBtn=findViewById(R.id.homeButton);
        LinearLayout aboutBtn=findViewById(R.id.aboutUsButton);
        LinearLayout faqBtn=findViewById(R.id.faqButton);
        LinearLayout contactBtn=findViewById(R.id.contactButton);


        //function home button
        homeBtn.setOnClickListener(v -> startActivity(new Intent(Homepage.this,Homepage.class)));

        //function about us button
        aboutBtn.setOnClickListener(v -> startActivity(new Intent(Homepage.this,AboutUsPage.class)));

        //function cart bottom button
        faqBtn.setOnClickListener(v -> startActivity(new Intent(Homepage.this, FAQPage.class)));

        //function review button
        contactBtn.setOnClickListener(v -> startActivity(new Intent(Homepage.this,ContactUsPage.class)));


    }

    private void getProducts() {
        FirebaseFirestore.getInstance()
                .collection("products")
                .whereEqualTo("show", true)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> dsList=queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot ds:dsList) {
                                ProductModel productModel=ds.toObject(ProductModel.class);
                                productAdapter.addProduct(productModel);
                            }
                        }
                    });
    }
}