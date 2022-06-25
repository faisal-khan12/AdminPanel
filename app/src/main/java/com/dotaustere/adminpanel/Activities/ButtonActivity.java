package com.dotaustere.adminpanel.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dotaustere.adminpanel.R;
import com.dotaustere.adminpanel.databinding.ActivityButtonBinding;

public class ButtonActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityButtonBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityButtonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addPaidJobBtn.setOnClickListener(this);
        binding.allUsers.setOnClickListener(this);
        binding.regDevice.setOnClickListener(this);
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.addPaidJobBtn:
                startActivity(new Intent(ButtonActivity.this, MainActivity.class));
                break;
            case R.id.allUsers:
                startActivity(new Intent(ButtonActivity.this, AllUsers_Activity.class));
                break;
            case R.id.regDevice:
                startActivity(new Intent(ButtonActivity.this, RegDeviceActivity.class));
                break;


        }

    }


}