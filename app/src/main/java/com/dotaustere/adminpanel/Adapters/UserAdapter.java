package com.dotaustere.adminpanel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dotaustere.adminpanel.Activities.InfoActivity;
import com.dotaustere.adminpanel.Models.UserDataModel;
import com.dotaustere.adminpanel.R;
import com.dotaustere.adminpanel.databinding.UserlayoutBinding;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {

    ArrayList<UserDataModel> arrayList;
    Context context;
    DatabaseReference userRef;

    public UserAdapter(ArrayList<UserDataModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userlayout, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        UserDataModel model = arrayList.get(position);
        userRef = FirebaseDatabase.getInstance().getReference("AllUsers");

        holder.binding.fullname.setText("Full Name: " + model.getFullName());
        holder.binding.username.setText("Username: " + model.getUserName());
        holder.binding.gender.setText("Gender: " + model.getGender());
        holder.binding.email.setText("Email Address: " + model.getEmailAddress());
        holder.binding.mobileNo.setText("Mobile No: " + model.getMobileNumber());

        holder.binding.switch1.setChecked(model.isVerification());

        if (model.isVerification()) {
            holder.binding.switch1.setText("Switch to deactivate Service   ");
//            holder.binding.switch1.setText("ON");
            holder.binding.greendone.setVisibility(View.VISIBLE);
        } else {
            holder.binding.switch1.setText("Switch to activate Service   ");
            holder.binding.yellowdone.setVisibility(View.VISIBLE);
        }

        holder.binding.click.setOnClickListener(view -> {
            if (model.isVerification()) {
                Intent in = new Intent(context, InfoActivity.class);
                in.putExtra("uid", model.getUuID());
                context.startActivity(in);
            } else {
                Toast.makeText(context, model.getFullName() + " User Not Complete, it's Verification", Toast.LENGTH_SHORT).show();
            }

        });
//        holder.binding.onoff.setLabelOn("service ON");
//        holder.binding.onoff.setLabelOff("service OFF");

        holder.binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (compoundButton.isChecked()) {
                    userRef.child(model.getUuID()).child("verification").setValue(true);
                    holder.binding.switch1.setText("Switch to deactivate Service   ");
                    Toast.makeText(context, model.getFullName() + "\nService is ON", Toast.LENGTH_SHORT).show();
                } else {
                    userRef.child(model.getUuID()).child("verification").setValue(false);
                    holder.binding.switch1.setText("Switch to activate Service   ");
                    Toast.makeText(context, model.getFullName() + "\nService is OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        holder.binding.onoff.setOnToggledListener(new OnToggledListener() {
//            @Override
//            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
//                if (isOn){
//                    userRef.child(model.getUuID()).child("verification").setValue(true);
//                    Toast.makeText(context, model.getFullName()+"\nService is ON", Toast.LENGTH_SHORT).show();
//                }else {
//                    userRef.child(model.getUuID()).child("verification").setValue(false);
//                    Toast.makeText(context, model.getFullName()+"\nService is OFF", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//        });


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        UserlayoutBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = UserlayoutBinding.bind(itemView);
        }
    }

}
