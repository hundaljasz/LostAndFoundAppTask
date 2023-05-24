package com.example.lostandfoundtaskjass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lostandfoundtaskjass.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.newAdvert.setOnClickListener(view -> {
            Intent advertScreen = new Intent(MainActivity.this, NewAdvertActivity.class);
            startActivity(advertScreen);
        });

        binding.showItems.setOnClickListener(view -> {
            Intent advertScreen = new Intent(MainActivity.this, ViewItemsActivity.class);
            startActivity(advertScreen);
        });

        binding.map.setOnClickListener(view -> {
            Intent mapScreen = new Intent(MainActivity.this,MapActivity.class);
            startActivity(mapScreen);
        });
    }
}