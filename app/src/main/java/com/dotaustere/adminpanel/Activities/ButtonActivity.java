package com.dotaustere.adminpanel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dotaustere.adminpanel.R;
import com.dotaustere.adminpanel.databinding.ActivityButtonBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

public class ButtonActivity extends AppCompatActivity implements View.OnClickListener {
    ActivityButtonBinding binding;
    DatabaseReference tokenRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityButtonBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        tokenRef = FirebaseDatabase.getInstance().getReference("AdminPanel");


        binding.addPaidJobBtn.setOnClickListener(this);
        binding.allUsers.setOnClickListener(this);
        binding.regDevice.setOnClickListener(this);

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(new OnSuccessListener<String>() {
            @Override
            public void onSuccess(String s) {
                Log.e("tokeennnn", s);
                tokenRef.child("adminToken").child("token").setValue(s);
//                binding.tokentext.setText(s);
//                Toast.makeText(ButtonActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ButtonActivity.this, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();

            }
        });


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