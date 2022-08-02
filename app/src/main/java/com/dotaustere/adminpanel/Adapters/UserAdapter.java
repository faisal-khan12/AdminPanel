package com.dotaustere.adminpanel.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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
import com.dotaustere.adminpanel.FcmNotificationsSender;
import com.dotaustere.adminpanel.Models.UserDataModel;
import com.dotaustere.adminpanel.R;
import com.dotaustere.adminpanel.databinding.NotiSendingBinding;
import com.dotaustere.adminpanel.databinding.UserlayoutBinding;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.UserWriteRecord;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewHolder> {

    ArrayList<UserDataModel> arrayList;
    Context context;
    DatabaseReference userRef;
    FirebaseAuth auth;
    AlertDialog dialog;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        UserDataModel model = arrayList.get(position);
        userRef = FirebaseDatabase.getInstance().getReference("AllUsers");
        auth = FirebaseAuth.getInstance();

        holder.binding.fullname.setText(model.getFullName());
        holder.binding.username.setText(model.getUserName());
        holder.binding.gender.setText(model.getGender());
        holder.binding.email.setText(model.getEmailAddress());
        holder.binding.mobileNo.setText(model.getMobileNumber());
        holder.binding.paymentId.setText(model.getUserToken());


        if (model.getRazorPaymentID() != null) {
            holder.binding.paymentId.setText(model.getRazorPaymentID());
        } else {
            holder.binding.paymentId.setText("Not Yet");
        }

        holder.binding.switch1.setChecked(model.isVerification());

//        holder.binding.switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (holder.binding.switch1.isChecked()) {
//                    Toast.makeText(context, "ON", Toast.LENGTH_SHORT).show();
//                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
//                            model.getUserToken(), "Naukri Bazaar", "Your Device is blocked from NaukriBazaar India", context.getApplicationContext(), (Activity) context
//                    );
//                    notificationsSender.SendNotifications();
//
//                    holder.binding.fullname.setText(model.getFullName());
//                } else {
//                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
//                            model.getUserToken(), "Naukri Bazaar", "Congrats! Your Device is unblocked from NaukriBazaar India", context.getApplicationContext(), (Activity) context
//                    );
//                    notificationsSender.SendNotifications();
//                    holder.binding.fullname.setText(model.getUserToken());
//                    Toast.makeText(context, "OFF", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });


        if (model.isVerification()) {
            holder.binding.switch1.setText("Switch to deactivate Service   ");
//            holder.binding.switch1.setText("ON");
            holder.binding.greendone.setVisibility(View.VISIBLE);

        } else {
            holder.binding.switch1.setText("Switch to activate Service   ");
            holder.binding.yellowdone.setVisibility(View.VISIBLE);
//            FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
//                    model.getUserToken(), "Naukri Bazaar", "Your Device is blocked from NaukriBazaar India", context.getApplicationContext(), (Activity) context
//            );
//            notificationsSender.SendNotifications();
        }

//        holder.binding.click.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (model.getUserToken() != null) {
//                    holder.binding.paymentId.setText(model.getUserToken());
////                    Toast.makeText(context, model.getUserToken(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(context, "Not", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        holder.binding.click.setOnClickListener(view -> {
            if (model.isVerification()) {
                Intent in = new Intent(context, InfoActivity.class);
                in.putExtra("uid", model.getUuID());
                context.startActivity(in);
            } else {
                Toast.makeText(context, model.getFullName() + " Not Complete, it's Verification", Toast.LENGTH_SHORT).show();
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

                    FcmNotificationsSender notificationsSender = new FcmNotificationsSender(
                            model.getUserToken(), "Naukri Bazaar", "Your Job Sevice is Activated from NaukriBazaar India", context.getApplicationContext(), (Activity) context
                    );
                    notificationsSender.SendNotifications();
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

//        holder.binding.fullname.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.show();
////               Toast.makeText(context, model.getRazorPaymentID(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        NotiSendingBinding notiSendingBinding = NotiSendingBinding.inflate(LayoutInflater.from(context.getApplicationContext()));
//        dialog = new AlertDialog.Builder(context)
//                .setView(notiSendingBinding.getRoot()).create();
//        if (model.getUserToken() != null) {
//            notiSendingBinding.token.setText(model.getUserToken());
////                    Toast.makeText(context, model.getUserToken(), Toast.LENGTH_SHORT).show();
//        } else {
//            notiSendingBinding.token.setText(model.getUserName());
//        }
//
//
//        notiSendingBinding.subbtn.setOnClickListener(view -> dialog.dismiss());
//        dialog.setCanceledOnTouchOutside(false);
//        dialog.setCancelable(false);
//        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

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
