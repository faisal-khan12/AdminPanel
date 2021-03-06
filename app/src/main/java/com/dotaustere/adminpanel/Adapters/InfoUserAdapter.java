package com.dotaustere.adminpanel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dotaustere.adminpanel.Models.InfoModel;
import com.dotaustere.adminpanel.R;
import com.dotaustere.adminpanel.databinding.InfouserLayoutBinding;

import java.util.ArrayList;

public class InfoUserAdapter extends RecyclerView.Adapter<InfoUserAdapter.viewHolder> {

    ArrayList<InfoModel> arrayList;
    Context context;


    public InfoUserAdapter(ArrayList<InfoModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.infouser_layout, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        InfoModel model = arrayList.get(position);


//        holder.binding.fullname.setText("Full Name: "+model.getFullName());
//        holder.binding.username.setText("Username: "+model.getUserName());
//        holder.binding.gender.setText("Gender: "+model.getGender());
//        holder.binding.email.setText("Email Address: "+model.getEmailAddress());
//        holder.binding.mobileNo.setText("Mobile No: "+model.getMobileNumber());
        holder.binding.zip.setText(model.getZipCode());
        holder.binding.address.setText(model.getUserAddress());
        holder.binding.gst.setText(model.getGst());
        holder.binding.docName.setText(model.getSelect());



        try {
            Glide.with(context).load(model.getFrontImage())
                    .into(holder.binding.front);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Glide.with(context).load(model.getBackImage())
                    .into(holder.binding.backimg);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        holder.binding.click.setOnClickListener(view -> {
//            Toast.makeText(context,model.getUuID(), Toast.LENGTH_SHORT).show();
//        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        InfouserLayoutBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = InfouserLayoutBinding.bind(itemView);
        }
    }

}
