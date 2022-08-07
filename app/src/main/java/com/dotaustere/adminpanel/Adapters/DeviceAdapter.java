package com.dotaustere.adminpanel.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dotaustere.adminpanel.FcmNotificationsSender;
import com.dotaustere.adminpanel.Models.DeviceModel;
import com.dotaustere.adminpanel.R;
import com.dotaustere.adminpanel.databinding.DeviceLayoutBinding;
import com.dotaustere.adminpanel.databinding.UserlayoutBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class DeviceAdapter extends RecyclerView.Adapter<DeviceAdapter.viewHolder> {

    ArrayList<DeviceModel> arrayList;
    Context context;
    DatabaseReference deviceRef;

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
        deviceRef = FirebaseDatabase.getInstance().getReference().child("RegDevices");


        holder.binding.devMobileNo.setText(model.getUserPhoneNumber());
        holder.binding.deviceName.setText(model.getDeviceName());
        holder.binding.androidOs.setText(model.getAndroidOs());
        holder.binding.appVersion.setText(model.getAppVersion());
        holder.binding.deviceId.setText(model.getDeviceID());


        if (model.isValid()) {
            holder.binding.blockSwitch.setText("Account is Active  ");
//            holder.binding.switch1.setText("ON");
            holder.binding.greenview.setVisibility(View.VISIBLE);
        } else {
            holder.binding.blockSwitch.setText("Account is Blocked  ");
            holder.binding.yellowview.setVisibility(View.VISIBLE);
        }

        holder.binding.blockSwitch.setChecked(model.isValid());


//        holder.binding.blockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (compoundButton.isChecked()) {
//                    deviceRef.child(model.getDeviceID()).child("valid").setValue(true);
////                    Toast.makeText(context, model.getDeviceID(), Toast.LENGTH_SHORT).show();
////                    holder.binding.blockSwitch.setText("Switch to deactivate Service   ");
//
//                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
//                            model.getToken(), "Naukri Bazaar", "Congrats! Your Device is unblocked from NaukriBazaar India", context.getApplicationContext(), (Activity) context
//                    );
//                    notificationsSender.SendNotifications();
//
//                    Toast.makeText(context, model.getUserPhoneNumber() + "\n is Activate Now", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    deviceRef.child(model.getDeviceID()).child("valid").setValue(false);
////                    Toast.makeText(context, model.getDeviceID(), Toast.LENGTH_SHORT).show();
////                    holder.binding.blockSwitch.setText("Switch to activate Service   ");
//
//                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
//                            model.getToken(), "Naukri Bazaar", "Your Device is now! blocked from NaukriBazaar India", context.getApplicationContext(), (Activity) context
//                    );
//                    notificationsSender.SendNotifications();
//                    Toast.makeText(context, model.getUserPhoneNumber() + "\n is Blocked Now", Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        });

        holder.binding.blockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()) {
                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                            model.getToken(), "Naukri Bazaar", "Congrats! Your Device is UNBLOCKED from NaukriBazaar India", context.getApplicationContext(), (Activity) context
                    );
                    notificationsSender.SendNotifications();
                    deviceRef.child(model.getDeviceID()).child("valid").setValue(true);
                    Toast.makeText(context, "ON", Toast.LENGTH_SHORT).show();
                } else {
                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                            model.getToken(), "Naukri Bazaar", "Your Device is now! blocked from NaukriBazaar India", context.getApplicationContext(), (Activity) context
                    );
                    notificationsSender.SendNotifications();
                    deviceRef.child(model.getDeviceID()).child("valid").setValue(false);

                    Toast.makeText(context, "OFF", Toast.LENGTH_SHORT).show();
                }

            }
        });


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
