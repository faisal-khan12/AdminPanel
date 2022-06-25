package com.dotaustere.adminpanel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dotaustere.adminpanel.Activities.InfoActivity;
import com.dotaustere.adminpanel.Models.UserDataModel;
import com.dotaustere.adminpanel.R;
import com.dotaustere.adminpanel.databinding.UserlayoutBinding;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {

    ArrayList<UserDataModel> arrayList;
    Context context;

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

        holder.binding.fullname.setText("Full Name: " + model.getFullName());
        holder.binding.username.setText("Username: " + model.getUserName());
        holder.binding.gender.setText("Gender: " + model.getGender());
        holder.binding.email.setText("Email Address: " + model.getEmailAddress());
        holder.binding.mobileNo.setText("Mobile No: " + model.getMobileNumber());

        if (model.isVerification()) {
            holder.binding.greendone.setVisibility(View.VISIBLE);
        } else {
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
