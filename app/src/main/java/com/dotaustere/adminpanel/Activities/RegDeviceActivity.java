package com.dotaustere.adminpanel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.dotaustere.adminpanel.Adapters.DeviceAdapter;
import com.dotaustere.adminpanel.Models.DeviceModel;
import com.dotaustere.adminpanel.R;
import com.dotaustere.adminpanel.databinding.ActivityRegDeviceBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegDeviceActivity extends AppCompatActivity {

    ActivityRegDeviceBinding binding;

    DatabaseReference reference;

    ArrayList<DeviceModel> arrayList;
    DeviceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegDeviceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        reference = FirebaseDatabase.getInstance().getReference();
        arrayList = new ArrayList<>();

        initDataretrieve();

    }

    public void initDataretrieve() {
        reference.child("RegDevices").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot s : snapshot.getChildren()) {
                        DeviceModel data = s.getValue(DeviceModel.class);
                        arrayList.add(data);

                        adapter = new DeviceAdapter(arrayList, RegDeviceActivity.this);
                        binding.recyclerViewDevice.setLayoutManager(
                                new LinearLayoutManager(RegDeviceActivity.this));
                        binding.recyclerViewDevice.setAdapter(adapter);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RegDeviceActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}