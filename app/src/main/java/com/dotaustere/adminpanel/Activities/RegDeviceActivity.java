package com.dotaustere.adminpanel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.dotaustere.adminpanel.Adapters.DeviceAdapter;
import com.dotaustere.adminpanel.Models.DeviceModel;
import com.dotaustere.adminpanel.R;
import com.dotaustere.adminpanel.databinding.ActivityRegDeviceBinding;
import com.dotaustere.adminpanel.databinding.LoadingLayoutBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegDeviceActivity extends AppCompatActivity {

    ActivityRegDeviceBinding binding;
    AlertDialog loadingDialog;

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
        loadingAlertDialog();

    }

    public void initDataretrieve() {
        reference.child("RegDevices").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    arrayList.clear();
                    for (DataSnapshot s : snapshot.getChildren()) {
                        DeviceModel data = s.getValue(DeviceModel.class);
                        arrayList.add(data);
                        loadingDialog.dismiss();
//                        if (arrayList.isEmpty()){
//                            Toast.makeText(RegDeviceActivity.this, "Not Data Found", Toast.LENGTH_SHORT).show();
//                            loadingDialog.dismiss();
//                        }else {
//                            arrayList.add(data);
//                            loadingDialog.dismiss();
//                        }


                        adapter = new DeviceAdapter(arrayList, RegDeviceActivity.this);
                        binding.recyclerViewDevice.setLayoutManager(
                                new LinearLayoutManager(RegDeviceActivity.this));
                        binding.recyclerViewDevice.setAdapter(adapter);
                    }
                } else {
                    Toast.makeText(RegDeviceActivity.this, "Not Data Found", Toast.LENGTH_SHORT).show();
                    loadingDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingDialog.dismiss();
                Toast.makeText(RegDeviceActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void loadingAlertDialog() {

        LoadingLayoutBinding bindingDialog = LoadingLayoutBinding.inflate(getLayoutInflater());
        loadingDialog = new AlertDialog.Builder(RegDeviceActivity.this).
                setView(bindingDialog.getRoot()).create();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }


}