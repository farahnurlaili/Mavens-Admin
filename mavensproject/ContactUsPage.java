package com.example.mavensproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mavensproject.databinding.ActivityContactUsPageBinding;

public class ContactUsPage extends AppCompatActivity {
    ActivityContactUsPageBinding binding;
    ImageView facebook, instagram, tiktok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityContactUsPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        facebook = findViewById(R.id.facebook);
        instagram = findViewById(R.id.instagram);
        tiktok = findViewById(R.id.tiktok);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize link and package
                String sAppLink = "";
                String sPackage = "com.facebook.katana";
                String sWebLink = "https://www.facebook.com/mavensmy";

                //create method
                openLink(sAppLink,sPackage,sWebLink);
            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //initialize link and package
                String sAppLink = "https://www.instagram.com/mavensmy";
                String sPackage = "com.instagram.android";
                //call method
                openLink(sAppLink,sPackage,sAppLink);
            }
        });

        tiktok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sAppLink = "https://www.tiktok.com/@mavensmy";
                String sPackage = "com.tiktok.android";
                String sWebLink = "https://www.tiktok.com/@mavensmy";

                //call method
                openLink(sAppLink,sPackage,sWebLink);
            }
        });

        binding.buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ContactUsPage.this, Homepage.class));
            }
        });

        bottom_navigation();
    }

    private void openLink(String sAppLink, String sPackage, String sWebLink) {
        //use try catch
        try {
            //when app is installed
            //initialized uri
            Uri uri = Uri.parse(sAppLink);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setPackage(sPackage);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException activityNotFoundException) {
            //open link in browser
            Uri uri = Uri.parse(sWebLink);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    private void bottom_navigation() {
        LinearLayout homeBtn=findViewById(R.id.homeButton);
        LinearLayout aboutBtn=findViewById(R.id.aboutUsButton);
        LinearLayout faqBtn=findViewById(R.id.faqButton);
        LinearLayout contactBtn=findViewById(R.id.contactButton);


        //function home button
        homeBtn.setOnClickListener(v -> startActivity(new Intent(ContactUsPage.this,Homepage.class)));

        //function about us button
        aboutBtn.setOnClickListener(v -> startActivity(new Intent(ContactUsPage.this,AboutUsPage.class)));

        //function cart bottom button
        faqBtn.setOnClickListener(v -> startActivity(new Intent(ContactUsPage.this, FAQPage.class)));

        //function review button
        contactBtn.setOnClickListener(v -> startActivity(new Intent(ContactUsPage.this,ContactUsPage.class)));


    }
}