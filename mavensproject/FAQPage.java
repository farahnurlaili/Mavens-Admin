package com.example.mavensproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.mavensproject.databinding.ActivityFaqpageBinding;

public class FAQPage extends AppCompatActivity {
    ActivityFaqpageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityFaqpageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottom_navigation();
    }

    private void bottom_navigation() {
        LinearLayout homeBtn=findViewById(R.id.homeButton);
        LinearLayout aboutBtn=findViewById(R.id.aboutUsButton);
        LinearLayout faqBtn=findViewById(R.id.faqButton);
        LinearLayout contactBtn=findViewById(R.id.contactButton);


        //function home button
        homeBtn.setOnClickListener(v -> startActivity(new Intent(FAQPage.this,Homepage.class)));

        //function about us button
        aboutBtn.setOnClickListener(v -> startActivity(new Intent(FAQPage.this,AboutUsPage.class)));

        //function cart bottom button
        faqBtn.setOnClickListener(v -> startActivity(new Intent(FAQPage.this, FAQPage.class)));

        //function review button
        contactBtn.setOnClickListener(v -> startActivity(new Intent(FAQPage.this,ContactUsPage.class)));


    }
}


