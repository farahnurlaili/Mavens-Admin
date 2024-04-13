package com.example.mavensadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.mavensadmin.databinding.ActivityWelcomePageBinding;

public class WelcomePage extends AppCompatActivity {
    ActivityWelcomePageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWelcomePageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomePage.this, AdminLoginPage.class);
                startActivity(intent);
                finish();
            }
        }, 30000);
    }
}