package com.dotaustere.adminpanel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.dotaustere.adminpanel.Adapters.InfoUserAdapter;
import com.dotaustere.adminpanel.Models.InfoModel;
import com.dotaustere.adminpanel.databinding.ActivityInfoBinding;
import com.dotaustere.adminpanel.databinding.LoadingLayoutBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {
    ActivityInfoBinding binding;
    DatabaseReference infoRef;
    AlertDialog loadingDialog;

    ArrayList<InfoModel> modelArrayList;
    InfoUserAdapter adapter;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        uid = getIntent().getStringExtra("uid");

        getSupportActionBar().hide();
        loadingAlertDialog();

        infoRef = FirebaseDatabase.getInstance().getReference("UsersInfo");

        modelArrayList = new ArrayList<>();

        infoRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    modelArrayList.clear();

                        InfoModel data = snapshot.getValue(InfoModel.class);

                        modelArrayList.add(data);
                        loadingDialog.dismiss();
//                        Arrays.sort(new ArrayList[]{listPoet});
                        binding.recviewinfo.setLayoutManager(new LinearLayoutManager(InfoActivity.this));

                        adapter = new InfoUserAdapter(modelArrayList, InfoActivity.this);

                        binding.recviewinfo.setAdapter(adapter);

//                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
//                        binding.poetRecyclerView.setLayoutManager(gridLayoutManager);
//                        binding.loadingFragment.setVisibility(View.GONE);



                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingDialog.dismiss();
                Toast.makeText(InfoActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }

    public void loadingAlertDialog() {

        LoadingLayoutBinding bindingDialog = LoadingLayoutBinding.inflate(getLayoutInflater());
        loadingDialog = new AlertDialog.Builder(InfoActivity.this).
                setView(bindingDialog.getRoot()).create();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

}