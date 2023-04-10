package com.example.sportcenterv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserUpdateMatchDetailsActivity extends AppCompatActivity {

    EditText txtName,txtState,txtDate,txtSport
            ,txtTime,txtMaxPeople,txtGameStatus,txtCourt;
    DatabaseReference ref;
    ProgressDialog progressDialog;
    String [] status = {"Select Status", "Open", "Close"};
    String pUid;
    String pFid;
    ImageView delete;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_match_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update MatchUp Page");

        btnSubmit = findViewById(R.id.btnSubmit);
        delete = findViewById(R.id.rbtnDelete);
        txtDate = findViewById(R.id.add_etDate);
        txtName = findViewById(R.id.add_etName);
        txtState = findViewById(R.id.add_etState);
        txtSport = findViewById(R.id.add_etSport);
        txtTime = findViewById(R.id.add_etSelectedTime);
        txtGameStatus = findViewById(R.id.etGameStatus);
        txtMaxPeople = findViewById(R.id.etPeopleNumber);
        txtCourt = findViewById(R.id.etCourt);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setCanceledOnTouchOutside(false);

        String postFID = getIntent().getExtras().getString("MaTimestamp");

        ref = FirebaseDatabase.getInstance().getReference("Group")
                .child(postFID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String MaUserName = dataSnapshot.child("MaUserName").getValue().toString();
                    txtName.setText(MaUserName);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String MaSportName = dataSnapshot.child("MaSportName").getValue().toString();
                    txtSport.setText(MaSportName);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String MaLocation = dataSnapshot.child("MaLocation").getValue().toString();
                    txtState.setText(MaLocation);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String MaTime = dataSnapshot.child("MaTime").getValue().toString();
                    txtTime.setText(MaTime);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String MaDate = dataSnapshot.child("MaDate").getValue().toString();
                    txtDate.setText(MaDate);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String MaStatus = dataSnapshot.child("MaStatus").getValue().toString();
                    txtGameStatus.setText(MaStatus);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String MaMaxPeople = dataSnapshot.child("MaMaxPeople").getValue().toString();
                    txtMaxPeople.setText(MaMaxPeople);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String MaCourt = dataSnapshot.child("MaCourt").getValue().toString();
                    txtCourt.setText(MaCourt);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Spinner spinner2 = findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(UserUpdateMatchDetailsActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,status);
        adapter2.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).equals("Select Status")){
                    //empty
                }
                else {
                    String value = adapterView.getItemAtPosition(i).toString();
                    txtGameStatus.setText(value);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pUid = getIntent().getExtras().getString("MaUid");
        pFid = getIntent().getExtras().getString("MaTimestamp");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(UserUpdateMatchDetailsActivity.this)
                        .setTitle("Delete Confirmation")
                        .setMessage("Do you want to delete the Group ?")
                        .setPositiveButton("OK", null)
                        .setNegativeButton("Cancel",null)
                        .show();

                Button positiveButton = dialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE);
                positiveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        deleteRecord(postFID);
                    }
                });

            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private String UserName="",Uid="",SportName="",location="",time="",Status="",MaxNumber="",Court="",Date="",Fid="";
    private void validateData() {

        UserName = txtName.getText().toString().trim();
        SportName = txtSport.getText().toString().trim();
        location = txtState.getText().toString().trim();
        time = txtTime.getText().toString().trim();
        Status = txtGameStatus.getText().toString().trim();
        MaxNumber = txtMaxPeople.getText().toString().trim();
        Court = txtCourt.getText().toString().trim();
        Date = txtDate.getText().toString().trim();
        Uid = pUid;
        Fid = pFid;

        if (TextUtils.isEmpty(Status)){
            Toast.makeText(this, "Please Select Status..", Toast.LENGTH_SHORT).show();
        } else {
            uploadData();
        }

    }

    private void uploadData() {

        progressDialog.setMessage("Saving Match Up Details...");
        progressDialog.show();

        String timestamp = String.valueOf(System.currentTimeMillis());

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("MaUid" ,Uid);
        hashMap.put("MaUserName" ,UserName);
        hashMap.put("MaSportName" ,SportName);
        hashMap.put("MaLocation" ,location);
        hashMap.put("MaTime" ,time);
        hashMap.put("MaDate" ,Date);
        hashMap.put("MaStatus" ,Status);
        hashMap.put("MaMaxPeople" ,MaxNumber);
        hashMap.put("MaCourt" ,Court);
        hashMap.put("MaTimestamp" ,Fid);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Group");
        ref.child(Fid)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        progressDialog.dismiss();
                        Toast.makeText(UserUpdateMatchDetailsActivity.this, "Match Up Details Uploaded...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserUpdateMatchDetailsActivity.this,UserViewMyMatchActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(UserUpdateMatchDetailsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void deleteRecord(String postFID) {

        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("Group")
                .child(postFID);
        Task<Void> mTask =DbRef.removeValue();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                startActivity(new Intent(UserUpdateMatchDetailsActivity.this, UserViewMyMatchActivity.class));
                Toast.makeText(UserUpdateMatchDetailsActivity.this,"Successfully Deleted",Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserUpdateMatchDetailsActivity.this,"Not Successfully Deleted",Toast.LENGTH_SHORT).show();

            }
        });

    }

}