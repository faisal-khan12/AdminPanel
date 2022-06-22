package com.dotaustere.adminpanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.dotaustere.adminpanel.databinding.ActivityInfoBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {
    ActivityInfoBinding binding;
    DatabaseReference infoRef;

    ArrayList<InfoModel> modelArrayList;
    InfoUserAdapter adapter;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        uid = getIntent().getStringExtra("uid");

        infoRef = FirebaseDatabase.getInstance().getReference("UsersInfo");

        modelArrayList = new ArrayList<>();

        infoRef.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    modelArrayList.clear();

                        InfoModel data = snapshot.getValue(InfoModel.class);

                        modelArrayList.add(data);
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

            }
        });




    }

}