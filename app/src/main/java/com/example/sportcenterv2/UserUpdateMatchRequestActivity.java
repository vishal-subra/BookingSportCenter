package com.example.sportcenterv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserUpdateMatchRequestActivity extends AppCompatActivity {

    EditText txtName,txtState,txtDate,txtSport
            ,txtTime,txtMaxPeople,txtStatus,txtCourt,txtUserName,txtContactNumber;
    DatabaseReference ref;
    String [] status = {"Select Status", "Accepted", "Rejected"};
    Button update;
    ProgressDialog progressDialog;
    String hostUid;
    String askedUid;
    String pFid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_match_request);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Request Page");

        txtDate = findViewById(R.id.add_etDate);
        txtName = findViewById(R.id.add_etName);
        txtState = findViewById(R.id.add_etState);
        txtSport = findViewById(R.id.add_etSport);
        txtTime = findViewById(R.id.add_etSelectedTime);
        txtStatus = findViewById(R.id.etRequestStatus);
        txtUserName = findViewById(R.id.etUserName);
        txtMaxPeople = findViewById(R.id.etPeopleNumber);
        txtCourt = findViewById(R.id.etCourt);
        update = findViewById(R.id.btnUpdate);
        txtContactNumber = findViewById(R.id.etContactNumber);

        String postFID = getIntent().getExtras().getString("ReqTimestamp");

        ref = FirebaseDatabase.getInstance().getReference("RequestGroup")
                .child(postFID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String ReqUserName = dataSnapshot.child("ReqUserName").getValue().toString();
                    txtName.setText(ReqUserName);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String ReqAskedName = dataSnapshot.child("ReqAskedName").getValue().toString();
                    txtUserName.setText(ReqAskedName);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String ReqSportName = dataSnapshot.child("ReqSportName").getValue().toString();
                    txtSport.setText(ReqSportName);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String ReqLocation = dataSnapshot.child("ReqLocation").getValue().toString();
                    txtState.setText(ReqLocation);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String ReqTime = dataSnapshot.child("ReqTime").getValue().toString();
                    txtTime.setText(ReqTime);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String ReqDate = dataSnapshot.child("ReqDate").getValue().toString();
                    txtDate.setText(ReqDate);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String ReqStatus = dataSnapshot.child("ReqStatus").getValue().toString();
                    txtStatus.setText(ReqStatus);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String ReqMaxPeople = dataSnapshot.child("ReqMaxPeople").getValue().toString();
                    txtMaxPeople.setText(ReqMaxPeople);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String ReqCourt = dataSnapshot.child("ReqCourt").getValue().toString();
                    txtCourt.setText(ReqCourt);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String ReqContactNumber = dataSnapshot.child("ReqContactNumber").getValue().toString();
                    txtContactNumber.setText(ReqContactNumber);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserUpdateMatchRequestActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,status);
        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).equals("Select Status")){
                    //empty
                }
                else {
                    String value = adapterView.getItemAtPosition(i).toString();
                    txtStatus.setText(value);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setCanceledOnTouchOutside(false);

        hostUid = getIntent().getExtras().getString("ReqHostUid");
        askedUid = getIntent().getExtras().getString("ReqAskedUid");
        pFid = getIntent().getExtras().getString("ReqTimestamp");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private String UserName="",HostUid="",SportName="",location="",time="",Status="",MaxNumber="",Court="",
            Date="",AskedUid="",userName="",TimeStamp="",ContactNumber;
    private void validateData() {

        UserName = txtName.getText().toString().trim();
        SportName = txtSport.getText().toString().trim();
        location = txtState.getText().toString().trim();
        time = txtTime.getText().toString().trim();
        Status = txtStatus.getText().toString().trim();
        MaxNumber = txtMaxPeople.getText().toString().trim();
        Court = txtCourt.getText().toString().trim();
        Date = txtDate.getText().toString().trim();
        userName = txtUserName.getText().toString().trim();
        ContactNumber = txtContactNumber.getText().toString().trim();
        HostUid = hostUid;
        AskedUid = askedUid;
        TimeStamp = pFid;

        if (TextUtils.isEmpty(Status)){
            Toast.makeText(this, "Please Select Status..", Toast.LENGTH_SHORT).show();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if (checkSelfPermission(android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                uploadData();
            } else {
                requestPermissions(new String[] {Manifest.permission.SEND_SMS}, 1);
            }
        }

    }

    private void uploadData() {

        progressDialog.setMessage("Joining Group Details...");
        progressDialog.show();

        String timestamp = String.valueOf(System.currentTimeMillis());
        String HostStatus = HostUid+Status;
        String AskedStatus = AskedUid+Status;
        String textUp = userName + " Your Request For joining the Match Has been Updated as " + Status;
        String Number = txtContactNumber.getText().toString().trim();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("ReqHostUid" ,HostUid);
        hashMap.put("ReqUserName" ,UserName);
        hashMap.put("ReqAskedName" ,userName);
        hashMap.put("ReqSportName" ,SportName);
        hashMap.put("ReqLocation" ,location);
        hashMap.put("ReqHostStatus" ,HostStatus);
        hashMap.put("ReqAskedStatus" ,AskedStatus);
        hashMap.put("ReqTime" ,time);
        hashMap.put("ReqDate" ,Date);
        hashMap.put("ReqStatus" ,Status);
        hashMap.put("ReqAskedUid" ,AskedUid);
        hashMap.put("ReqMaxPeople" ,MaxNumber);
        hashMap.put("ReqCourt" ,Court);
        hashMap.put("ReqTimestamp" ,TimeStamp);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("RequestGroup");
        ref.child(TimeStamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(Number, null, textUp, null,null);
                        progressDialog.dismiss();
                        Toast.makeText(UserUpdateMatchRequestActivity.this, "Request Details Uploaded...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserUpdateMatchRequestActivity.this,UserViewMatchUpActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(UserUpdateMatchRequestActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}