package com.example.sportcenterv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserViewRequestDetailsActivity extends AppCompatActivity {

    EditText txtName,txtState,txtDate,txtSport
            ,txtTime,txtMaxPeople,txtStatus,txtCourt,txtUserName,txtContactNumber;
    DatabaseReference ref;
    ImageView delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_request_details);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Request Details Page");

        txtDate = findViewById(R.id.add_etDate);
        txtName = findViewById(R.id.add_etName);
        txtState = findViewById(R.id.add_etState);
        txtSport = findViewById(R.id.add_etSport);
        txtTime = findViewById(R.id.add_etSelectedTime);
        txtStatus = findViewById(R.id.etRequestStatus);
        txtUserName = findViewById(R.id.etUserName);
        txtMaxPeople = findViewById(R.id.etPeopleNumber);
        txtCourt = findViewById(R.id.etCourt);
        txtContactNumber = findViewById(R.id.etContactNumber);
        delete = findViewById(R.id.btnDelete);

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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(UserViewRequestDetailsActivity.this)
                        .setTitle("Delete Confirmation")
                        .setMessage("Do you want to delete the Request ?")
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

    }

    private void deleteRecord(String postFID) {

        DatabaseReference DbRef = FirebaseDatabase.getInstance().getReference("RequestGroup")
                .child(postFID);
        Task<Void> mTask = DbRef.removeValue();
        mTask.addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                startActivity(new Intent(UserViewRequestDetailsActivity.this, UserViewMatchUpActivity.class));
                Toast.makeText(UserViewRequestDetailsActivity.this,"Successfully Deleted",Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UserViewRequestDetailsActivity.this,"Not Successfully Deleted",Toast.LENGTH_SHORT).show();

            }
        });

    }

}