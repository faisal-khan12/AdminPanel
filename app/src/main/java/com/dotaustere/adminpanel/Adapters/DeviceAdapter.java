package com.dotaustere.adminpanel.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dotaustere.adminpanel.Models.DeviceModel;
import com.dotaustere.adminpanel.R;
import com.dotaustere.adminpanel.databinding.DeviceLayoutBinding;
import com.dotaustere.adminpanel.databinding.UserlayoutBinding;

import java.util.ArrayList;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.viewHolder> {

    ArrayList<DeviceModel> arrayList;
    Context context;

    public DeviceAdapter(ArrayList<DeviceModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.device_layout, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        DeviceModel model = arrayList.get(position);

//        holder.binding.fullname.setText("Full Name: " + model.getFullName());
        holder.binding.devMobileNo.setText(model.getUserPhoneNumber());
        holder.binding.deviceId.setText(model.getDeviceID());



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        DeviceLayoutBinding binding;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            binding = DeviceLayoutBinding.bind(itemView);
        }
    }

}
