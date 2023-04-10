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

public class AdminUpdateBookingActivity extends AppCompatActivity {

    EditText txtName,txtState,txtContactNumber,txtEmail,txtDate,txtSport
            ,txtStatus,txtRemark,txtFee,txtTime,txtItem,txtCourt;
    DatabaseReference ref;
    String [] status = {"Select Status", "Accepted", "Rejected"};
    Button update;
    ProgressDialog progressDialog;
    String pUid;
    String pFid;
    ImageView delete;
    String [] court = {"Select Court","Futsal Court F1","Futsal Court F2","Futsal Court F3","Badminton Court B1","Badminton Court B2",
            "Badminton Court B3","Frisbee Court F1","Frisbee Court F2","Frisbee Court F3","Netball Court F2","Netball Court F3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_booking);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Update Booking Page");

        update = findViewById(R.id.btnUpdate);
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
        txtItem = findViewById(R.id.add_etRentItems);
        delete = findViewById(R.id.btnDelete);
        txtCourt = findViewById(R.id.etCourt);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setCanceledOnTouchOutside(false);

        Spinner spinner2 = findViewById(R.id.spinner2);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(AdminUpdateBookingActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,court);
        adapter2.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).equals("Select Court")){
                    //empty
                }
                else {
                    String value = adapterView.getItemAtPosition(i).toString();
                    txtCourt.setText(value);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String postFID = getIntent().getExtras().getString("DoTimestamp");

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(AdminUpdateBookingActivity.this)
                        .setTitle("Delete Confirmation")
                        .setMessage("Do you want to delete the Booking ?")
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
                    txtItem.setText(DoItems);
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

        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AdminUpdateBookingActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,status);
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

        pUid = getIntent().getExtras().getString("DoUid");
        pFid = getIntent().getExtras().getString("DoTimestamp");

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private void deleteRecord(String postFID) {

        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("Booking")
                .child(postFID);
        Task<Void> mTask = DbRef.removeValue();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                startActivity(new Intent(AdminUpdateBookingActivity.this, AdminHomeActivity.class));
                Toast.makeText(AdminUpdateBookingActivity.this,"Successfully Deleted",Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AdminUpdateBookingActivity.this,"Not Successfully Deleted",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private String Name="",State="",ContactNumber="",Email="",Date="",Sport="",Uid="",Fid=""
            ,Fee="",Time="",Status="",Remark="",Item="",Court="";
    private void validateData() {

        Name = txtName.getText().toString().trim();
        State = txtState.getText().toString().trim();
        ContactNumber = txtContactNumber.getText().toString().trim();
        Email = txtEmail.getText().toString().trim();
        Date = txtDate.getText().toString().trim();
        Sport = txtSport.getText().toString().trim();
        Fee = txtFee.getText().toString().trim();
        Time = txtTime.getText().toString().trim();
        Status = txtStatus.getText().toString().trim();
        Remark = txtRemark.getText().toString().trim();
        Item = txtItem.getText().toString().trim();
        Court = txtCourt.getText().toString().trim();
        Uid = pUid;
        Fid = pFid;

        if (TextUtils.isEmpty(Status)){
            Toast.makeText(this, "Please Select Status..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Remark)){
            Toast.makeText(this, "Please Enter Remark..", Toast.LENGTH_SHORT).show();
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            if (checkSelfPermission(android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                uploadData();
            } else {
                requestPermissions(new String[] {Manifest.permission.SEND_SMS}, 1);
            }
        }
    }

    private void uploadData() {

        progressDialog.setMessage("Uploading Booking Details...");
        progressDialog.show();

        String timestamp = String.valueOf(System.currentTimeMillis());
        String statusUid = Uid+Status;
        String textUp = Name + " Your Booking for "+ Sport + " On " + Date + " Has Been Updated Status As " + Status;
        String Number = txtContactNumber.getText().toString().trim();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("DoTimestamp" ,Fid);
        hashMap.put("DoName" ,Name);
        hashMap.put("DoState" ,State);
        hashMap.put("DoContactNumber" ,ContactNumber);
        hashMap.put("DoEmail" ,Email);
        hashMap.put("DoDate" ,Date);
        hashMap.put("DoSport" ,Sport);
        hashMap.put("DoUid" ,Uid);
        hashMap.put("DoItem" ,Item);
        hashMap.put("DoStatusUid" ,statusUid);
        hashMap.put("DoStatus" ,Status);
        hashMap.put("DoTotalFee" ,Fee);
        hashMap.put("DoTime" ,Time);
        hashMap.put("DoCourt" ,Court);
        hashMap.put("DoRemark" ,Remark);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Booking");
        ref.child(Fid)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(Number, null, textUp, null,null);
                        progressDialog.dismiss();
                        Toast.makeText(AdminUpdateBookingActivity.this, "Booking Details Updated...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AdminUpdateBookingActivity.this,AdminHomeActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(AdminUpdateBookingActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}