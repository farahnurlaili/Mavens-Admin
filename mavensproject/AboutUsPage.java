package com.example.mavensproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mavensproject.databinding.ActivityAboutusBinding;

public class AboutUsPage extends AppCompatActivity {
    ActivityAboutusBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAboutusBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        bottom_navigation();
    }

    private void bottom_navigation() {
        LinearLayout homeBtn=findViewById(R.id.homeButton);
        LinearLayout aboutBtn=findViewById(R.id.aboutUsButton);
        LinearLayout faqBtn=findViewById(R.id.faqButton);
        LinearLayout contactBtn=findViewById(R.id.contactButton);


        //function home button
        homeBtn.setOnClickListener(v -> startActivity(new Intent(AboutUsPage.this,Homepage.class)));

        //function about us button
        aboutBtn.setOnClickListener(v -> startActivity(new Intent(AboutUsPage.this,AboutUsPage.class)));

        //function cart bottom button
        faqBtn.setOnClickListener(v -> startActivity(new Intent(AboutUsPage.this, FAQPage.class)));

        //function contact button
        contactBtn.setOnClickListener(v -> startActivity(new Intent(AboutUsPage.this, ContactUsPage.class)));


    }
}