package com.dotaustere.adminpanel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.dotaustere.adminpanel.databinding.ActivityButtonBinding;

public class ButtonActivity extends AppCompatActivity {
    ActivityButtonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityButtonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addPaidJobBtn.setOnClickListener(view ->
                startActivity(new Intent(ButtonActivity.this, MainActivity.class)));
        binding.allUsers.setOnClickListener(view ->
                startActivity(new Intent(ButtonActivity.this, AllUsers_Activity.class)));
    }
}