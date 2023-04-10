package com.example.sportcenterv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserViewMatchDetailsActivity extends AppCompatActivity {

    EditText txtName,txtState,txtDate,txtSport
            ,txtTime,txtMaxPeople,txtGameStatus,txtCourt,txtUserName,txtUserEmail,txtContactNumber;
    DatabaseReference ref;
    String pUid;
    String pFid;
    Button submit;
    FirebaseAuth auth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_match_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("MatchUp Details Page");

        txtDate = findViewById(R.id.add_etDate);
        txtName = findViewById(R.id.add_etName);
        txtState = findViewById(R.id.add_etState);
        txtSport = findViewById(R.id.add_etSport);
        txtTime = findViewById(R.id.add_etSelectedTime);
        txtGameStatus = findViewById(R.id.etGameStatus);
        txtMaxPeople = findViewById(R.id.etPeopleNumber);
        txtUserName = findViewById(R.id.etUserName);
        txtUserEmail = findViewById(R.id.etUserEmail);
        txtCourt = findViewById(R.id.etCourt);
        submit = findViewById(R.id.btnSubmit);
        txtContactNumber = findViewById(R.id.etContactNumber);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String emailName = user.getEmail();
        txtUserEmail.setText(emailName);
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

        pUid = getIntent().getExtras().getString("MaUid");
        pFid = getIntent().getExtras().getString("MaTimestamp");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private String UserName="",HostUid="",SportName="",location="",time="",Status="",MaxNumber="",Court="",
            Date="",AskedUid="",userName="",userEmail="",ContactNumber="";
    private void validateData() {

        UserName = txtName.getText().toString().trim();
        SportName = txtSport.getText().toString().trim();
        location = txtState.getText().toString().trim();
        time = txtTime.getText().toString().trim();
        Status = txtGameStatus.getText().toString().trim();
        MaxNumber = txtMaxPeople.getText().toString().trim();
        Court = txtCourt.getText().toString().trim();
        Date = txtDate.getText().toString().trim();
        userEmail = txtUserEmail.getText().toString().trim();
        userName = txtUserName.getText().toString().trim();
        ContactNumber = txtContactNumber.getText().toString().trim();
        FirebaseUser user = auth.getCurrentUser();
        AskedUid = user.getUid();
        HostUid = pUid;

        if (Status.equals("Close")){
            submit.setEnabled(false);
        } else if (TextUtils.isEmpty(UserName)){
            Toast.makeText(this, "Please Enter Your Name..", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(ContactNumber)){
            Toast.makeText(this, "Please Enter Your Contact Number..", Toast.LENGTH_SHORT).show();
        } else {
            uploadData();
        }

    }

    private void uploadData() {

        progressDialog.setMessage("Joining Group Details...");
        progressDialog.show();

        String timestamp = String.valueOf(System.currentTimeMillis());
        String status = "Pending";
        String HostStatus = HostUid+status;
        String AskedStatus = AskedUid+status;

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ReqHostUid" ,HostUid);
        hashMap.put("ReqUserName" ,UserName);
        hashMap.put("ReqAskedName" ,userName);
        hashMap.put("ReqSportName" ,SportName);
        hashMap.put("ReqLocation" ,location);
        hashMap.put("ReqContactNumber" ,ContactNumber);
        hashMap.put("ReqHostStatus" ,HostStatus);
        hashMap.put("ReqAskedStatus" ,AskedStatus);
        hashMap.put("ReqTime" ,time);
        hashMap.put("ReqDate" ,Date);
        hashMap.put("ReqStatus" ,status);
        hashMap.put("ReqAskedUid" ,AskedUid);
        hashMap.put("ReqMaxPeople" ,MaxNumber);
        hashMap.put("ReqCourt" ,Court);
        hashMap.put("ReqTimestamp" ,timestamp);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("RequestGroup");
        ref.child(timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        progressDialog.dismiss();
                        Toast.makeText(UserViewMatchDetailsActivity.this, "Request Details Uploaded...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserViewMatchDetailsActivity.this,UserViewMatchUpActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(UserViewMatchDetailsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}