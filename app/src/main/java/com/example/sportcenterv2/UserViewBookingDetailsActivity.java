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
import android.widget.Spinner;
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

public class UserViewBookingDetailsActivity extends AppCompatActivity {

    EditText txtName,txtState,txtContactNumber,txtEmail,txtDate,txtSport
            ,txtStatus,txtRemark,txtFee,txtTime,txtRent,txtMaxPeople,txtGameStatus,txtCourt;
    DatabaseReference ref;
    Button submit;
    FirebaseAuth auth ;
    ProgressDialog progressDialog;
    String [] status = {"Select Status", "Open", "Close"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_booking_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Booking Details Page");

        txtDate = findViewById(R.id.add_etDate);
        txtName = findViewById(R.id.add_etName);
        txtState = findViewById(R.id.add_etState);
        txtContactNumber = findViewById(R.id.add_etContactNumber);
        txtEmail = findViewById(R.id.add_etEmail);
        txtSport = findViewById(R.id.add_etSport);
        txtTime = findViewById(R.id.add_etSelectedTime);
        txtStatus = findViewById(R.id.add_etStatus); 
        txtFee = findViewById(R.id.add_etFee);
        txtRemark = findViewById(R.id.add_etRemark);
        txtRent = findViewById(R.id.add_etRentItems);
        txtGameStatus = findViewById(R.id.etMatchStatus);
        txtMaxPeople = findViewById(R.id.etPeopleNumber);
        txtCourt = findViewById(R.id.etCourt);
        submit = findViewById(R.id.btnSubmit);
        auth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setCanceledOnTouchOutside(false);

        String postFID = getIntent().getExtras().getString("DoTimestamp");

        ref = FirebaseDatabase.getInstance().getReference("Booking")
                .child(postFID);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    String DoName = dataSnapshot.child("DoName").getValue().toString();
                    txtName.setText(DoName);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DoState = dataSnapshot.child("DoState").getValue().toString();
                    txtState.setText(DoState);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DoContactNumber = dataSnapshot.child("DoContactNumber").getValue().toString();
                    txtContactNumber.setText(DoContactNumber);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DoEmail = dataSnapshot.child("DoEmail").getValue().toString();
                    txtEmail.setText(DoEmail);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DoDate = dataSnapshot.child("DoDate").getValue().toString();
                    txtDate.setText(DoDate);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DoSport = dataSnapshot.child("DoSport").getValue().toString();
                    txtSport.setText(DoSport);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DoStatus = dataSnapshot.child("DoStatus").getValue().toString();
                    txtStatus.setText(DoStatus);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DoTotalFee = dataSnapshot.child("DoTotalFee").getValue().toString();
                    txtFee.setText(DoTotalFee);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DoTime = dataSnapshot.child("DoTime").getValue().toString();
                    txtTime.setText(DoTime);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DoRemark = dataSnapshot.child("DoRemark").getValue().toString();
                    txtRemark.setText(DoRemark);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DoItems = dataSnapshot.child("DoItem").getValue().toString();
                    txtRent.setText(DoItems);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try {
                    String DoCourt = dataSnapshot.child("DoCourt").getValue().toString();
                    txtCourt.setText(DoCourt);
                } catch (NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

        Spinner spinner2 = findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(UserViewBookingDetailsActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,status);
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
        

    }

    private String UserName="",Uid="",SportName="",location="",time="",Status="",MaxNumber="",Court="",Date="";
    private void validateData() {

        String oldStatus = txtStatus.getText().toString().trim();
        UserName = txtName.getText().toString().trim();
        SportName = txtSport.getText().toString().trim();
        location = txtState.getText().toString().trim();
        time = txtTime.getText().toString().trim();
        Status = txtGameStatus.getText().toString().trim();
        MaxNumber = txtMaxPeople.getText().toString().trim();
        Court = txtCourt.getText().toString().trim();
        Date = txtDate.getText().toString().trim();
        FirebaseUser user = auth.getCurrentUser();
        Uid = user.getUid();

        if (!oldStatus.equals("Accepted")){
            Toast.makeText(this, "Booking Status is Not Accepted..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(MaxNumber)){
            Toast.makeText(this, "Please Enter Max Number of People..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Status)){
            Toast.makeText(this, "Please Select Game Status...", Toast.LENGTH_SHORT).show();
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
        hashMap.put("MaTimestamp" ,timestamp);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Group");
        ref.child(timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        progressDialog.dismiss();
                        Toast.makeText(UserViewBookingDetailsActivity.this, "Match Up Details Uploaded...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserViewBookingDetailsActivity.this,UserHomeActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(UserViewBookingDetailsActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}