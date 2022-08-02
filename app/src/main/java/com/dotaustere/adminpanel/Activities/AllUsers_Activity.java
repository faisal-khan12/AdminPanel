package com.dotaustere.adminpanel.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.dotaustere.adminpanel.Adapters.UserAdapter;
import com.dotaustere.adminpanel.Models.UserDataModel;
import com.dotaustere.adminpanel.databinding.ActivityAllUsersBinding;
import com.dotaustere.adminpanel.databinding.LoadingLayoutBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllUsers_Activity extends AppCompatActivity {
    ActivityAllUsersBinding binding;
    DatabaseReference userRef;
    AlertDialog loadingDialog;


    ArrayList<UserDataModel> modelArrayList;
    UserAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAllUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();
        loadingAlertDialog();

        userRef = FirebaseDatabase.getInstance().getReference("AllUsers");

        modelArrayList = new ArrayList<>();


        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    modelArrayList.clear();
                    for (DataSnapshot s : snapshot.getChildren()) {
                        UserDataModel data = s.getValue(UserDataModel.class);
                        modelArrayList.add(data);
                        loadingDialog.dismiss();
//                        Arrays.sort(new ArrayList[]{listPoet});
                        binding.recview.setLayoutManager(new LinearLayoutManager(AllUsers_Activity.this));

                        adapter = new UserAdapter(modelArrayList, AllUsers_Activity.this);

                        binding.recview.setAdapter(adapter);

//                        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
//                        binding.poetRecyclerView.setLayoutManager(gridLayoutManager);
//                        binding.loadingFragment.setVisibility(View.GONE);

                    }

                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                loadingDialog.dismiss();
                Toast.makeText(AllUsers_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void loadingAlertDialog() {

        LoadingLayoutBinding bindingDialog = LoadingLayoutBinding.inflate(getLayoutInflater());
        loadingDialog = new AlertDialog.Builder(AllUsers_Activity.this).
                setView(bindingDialog.getRoot()).create();
        loadingDialog.setCanceledOnTouchOutside(false);
        loadingDialog.setCancelable(false);
        loadingDialog.show();
        loadingDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

}