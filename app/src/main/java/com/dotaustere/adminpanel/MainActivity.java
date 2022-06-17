package com.dotaustere.adminpanel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.dotaustere.adminpanel.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    DatabaseReference databaseReference;
    Bitmap selectedImage;
    private StorageReference storageReference;
    String uniqueKey;
    String jobImg;

    ProgressDialog dialog;
    Uri uri;
    String jobTitle, companyName, jobType, jobTime, salaryRange;
    String postDate, applyBefore, jobPriceINr, jobLocation, jobDes, jobRoleAndRes;
    String downloadurl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new ProgressDialog(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("AdminPanel").child("job");
        storageReference = FirebaseStorage.getInstance().getReference("AdminPanel").child("job");


        binding.sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();

            }
        });

        binding.idpick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);

            }
        });


    }

    private void uploadImage() {
        dialog.setMessage("Please wait....");
        dialog.setCancelable(false);
        dialog.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] finalImage = baos.toByteArray();
        StorageReference path;
        path = storageReference.child(finalImage + "jpg");
        UploadTask uploadTask = path.putBytes(finalImage);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            path.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadurl = String.valueOf(uri);
                                    uploadData();

                                }
                            });
                        }
                    });
                } else {
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "someThing went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadData() {
        jobTitle = binding.etjobTitle.getText().toString();
        companyName = binding.etcompanyName.getText().toString();
        jobType = binding.etJobType.getText().toString();
        salaryRange = binding.etSalary.getText().toString();
        jobTime = binding.etjobtime.getText().toString();

        uniqueKey = UUID.randomUUID().toString();

        applyBefore = binding.applyBefore.getText().toString();
        jobPriceINr = binding.jobPriceINR.getText().toString();
        jobLocation = binding.jobLocation.getText().toString();
        jobDes = binding.jobDescription.getText().toString();
        jobRoleAndRes = binding.rolesAndRes.getText().toString();


        Users model = new Users(downloadurl, jobTitle, companyName, jobType, uniqueKey, jobTime,
                "", applyBefore, jobPriceINr, jobDes, jobRoleAndRes, salaryRange,jobLocation);
        databaseReference.child(uniqueKey).setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, "data added", Toast.LENGTH_SHORT).show();
                emptyBoxes();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void emptyBoxes() {

        binding.etjobTitle.setText("");
        binding.etcompanyName.setText("");
        binding.etJobType.setText("");
        binding.etSalary.setText("");
        binding.etjobtime.setText("");

        binding.applyBefore.setText("");
        binding.jobPriceINR.setText("");
        binding.jobLocation.setText("");
        binding.jobDescription.setText("");
        binding.rolesAndRes.setText("");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();


            try {
                selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            binding.imgUser.setImageBitmap(selectedImage);


        }
    }

    private void validation() {
        if (selectedImage == null) {
            Toast.makeText(MainActivity.this, "Select An Image", Toast.LENGTH_SHORT).show();
        } else if (binding.etjobTitle.getText().toString().isEmpty()) {
            binding.etjobTitle.setError("Please Type jobTitle");
        } else if (binding.etcompanyName.getText().toString().isEmpty()) {
            binding.etcompanyName.setError("Please Type Company name");
        } else if (binding.etJobType.getText().toString().isEmpty()) {
            binding.etJobType.setError("Please Enter JobType");
        } else if (binding.etSalary.getText().toString().isEmpty()) {
            binding.etSalary.setError("Please Enter SalaryRange");
        } else if (binding.etjobtime.getText().toString().isEmpty()) {
            binding.etjobtime.setError("Please Enter Job Time");
        } else if (binding.applyBefore.getText().toString().isEmpty()) {
            binding.applyBefore.setError("Please Enter Apply Before");
        } else if (binding.jobPriceINR.getText().toString().isEmpty()) {
            binding.jobPriceINR.setError("Please Enter Job Price in INr");
        } else if (binding.jobLocation.getText().toString().isEmpty()) {
            binding.jobLocation.setError("Please Enter Job Location");
        } else if (binding.jobDescription.getText().toString().isEmpty()) {
            binding.jobDescription.setError("Please Enter Job Description");
        } else if (binding.rolesAndRes.getText().toString().isEmpty()) {
            binding.rolesAndRes.setError("Please Enter Job Roles and Responsibilities");
        } else {
            uploadImage();

        }


    }


}