package com.example.sportcenterv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

public class UserMakeBookingActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    EditText txtName,txtState,txtContactNumber,txtEmail,txtDate,txtSport;
    ImageView PickDate;
    FirebaseAuth auth ;
    Button submit;
    DatabaseReference ref;
    ProgressDialog progressDialog;
    private int mYear, mMonth, mDay;
    String [] state = {"Select State","Kuala Lumpur","Labuan","Putrajaya","Johor","Kedah",
            "Kelantan","Melaka","Negeri Sembilan","Pahang","Perak","Perlis","Penang","Sabah"
            ,"Sarawak","Selangor","Terengganu"};
    String [] sport = {"Select Sport", "Futsal", "Badminton", "Frisbee", "Netball"};
    CheckBox futsalBall,frisbee,shuttle,netball;
    CheckBox cb10Am,cb11Am,cb12Pm,cb1Pm,cb2Pm,cb3Pm,cb4Pm,cb5Pm,cb6Pm,cb7Pm,cb8Pm,cb9Pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_make_booking);

        getSupportActionBar().setTitle("Booking Page");

        PickDate = findViewById(R.id.add_PickDate);
        txtDate = findViewById(R.id.add_etDate);
        txtName = findViewById(R.id.add_etName);
        txtState = findViewById(R.id.add_etState);
        txtContactNumber = findViewById(R.id.add_etContactNumber);
        txtEmail = findViewById(R.id.add_etEmail);
        txtSport = findViewById(R.id.add_etSport);
        submit = findViewById(R.id.btnSubmit);

        futsalBall = findViewById(R.id.cbFutsalBall);
        frisbee = findViewById(R.id.cbFrisbee);
        shuttle = findViewById(R.id.cbShuttle);
        netball = findViewById(R.id.cbNetball);

        cb10Am = findViewById(R.id.cb10Am);
        cb11Am = findViewById(R.id.cb11Am);
        cb12Pm = findViewById(R.id.cb12Pm);
        cb1Pm = findViewById(R.id.cb1Pm);
        cb2Pm = findViewById(R.id.cb2Pm);
        cb3Pm = findViewById(R.id.cb3Pm);
        cb4Pm = findViewById(R.id.cb4Pm);
        cb5Pm = findViewById(R.id.cb5Pm);
        cb6Pm = findViewById(R.id.cb6Pm);
        cb7Pm = findViewById(R.id.cb7Pm);
        cb8Pm = findViewById(R.id.cb8Pm);
        cb9Pm = findViewById(R.id.cb9Pm);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait..");
        progressDialog.setCanceledOnTouchOutside(false);
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String emailName = user.getEmail();
        txtEmail.setText(emailName);

        bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setSelectedItemId(R.id.AddId);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.homeId:
                        startActivity(new Intent(getApplicationContext(), UserHomeActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.profileId:
                        startActivity(new Intent(getApplicationContext(), UserProfileActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.AddId:
                        startActivity(new Intent(getApplicationContext(), UserMakeBookingActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                    case R.id.MatchId:
                        startActivity(new Intent(getApplicationContext(), UserViewMatchUpActivity.class));
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                }
                return false;
            }
        });

        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserMakeBookingActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,state);
        adapter.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).equals("Select State")){
                    //empty
                }
                else {
                    String value = adapterView.getItemAtPosition(i).toString();
                    txtState.setText(value);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //sport
        Spinner spinnerType = findViewById(R.id.spinnerType);

        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(UserMakeBookingActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,sport);
        adapterType.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item);
        spinnerType.setAdapter(adapterType);

        spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (adapterView.getItemAtPosition(i).equals("Select Sport")){
                    //empty
                }
                else {
                    String value = adapterView.getItemAtPosition(i).toString();
                    txtSport.setText(value);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ref = FirebaseDatabase.getInstance().getReference("Profile")
                .child(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try{
                    String prName = snapshot.child("prName").getValue().toString();
                    txtName.setText(prName);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try{
                    String prContactNumber = snapshot.child("prContactNumber").getValue().toString();
                    txtContactNumber.setText(prContactNumber);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try{
                    String prState = snapshot.child("prState").getValue().toString();
                    txtState.setText(prState);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
                try{
                    String prEmail = snapshot.child("uEmail").getValue().toString();
                    txtEmail.setText(prEmail);
                }
                catch(NullPointerException e) {
                    System.out.println("NullPointerException thrown!");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        PickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForDatePicker();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private String Name="",State="",ContactNumber="",Email="",Date="",Sport="",Uid="",finalTime="",finalItem="";
    private Integer a10=10, a35=35, totalRm=0,eTotal=0,finalTotal=0;
    private String cb10,cb11,cb12,cb1,cb2,cb3,cb4,cb5,cb6,cb7,cb8,cb9,i1,i2,i3,i4;

    private void validateData() {

        Name = txtName.getText().toString().trim();
        State = txtState.getText().toString().trim();
        ContactNumber = txtContactNumber.getText().toString().trim();
        Email = txtEmail.getText().toString().trim();
        Date = txtDate.getText().toString().trim();
        Sport = txtSport.getText().toString().trim();
        FirebaseUser user = auth.getCurrentUser();
        Uid = user.getUid();

        int total = 0;


        if (cb10Am.isChecked()){
            totalRm=totalRm+a35;
            cb10 = " 10Am-11Am ";
            total++;
        } else  {
            cb10 ="";

        } if (cb11Am.isChecked()){
            totalRm=totalRm+a35;
            cb11 = " 11Am-12Pm ";
            total++;
        } else {
            cb11 ="";
        } if (cb12Pm.isChecked()){
            totalRm=totalRm+a35;
            cb12 = " 12Pm-1Pm ";
            total++;
        } else {
            cb12 ="";
        } if (cb1Pm.isChecked()){
            totalRm=totalRm+a35;
            cb1 = " 1Pm-2Pm ";
            total++;
        } else {
            cb1 ="";
        } if (cb2Pm.isChecked()){
            totalRm=totalRm+a35;
            cb2 = " 2Pm-3Pm ";
            total++;
        } else {
            cb2 ="";
        } if (cb3Pm.isChecked()){
            totalRm=totalRm+a35;
            cb3 = " 3Pm-4Pm ";
            total++;
        } else {
            cb3 ="";
        } if (cb4Pm.isChecked()){
            totalRm=totalRm+a35;
            cb4 = " 4Pm-5Pm ";
            total++;
        } else {
            cb4 ="";
        } if (cb5Pm.isChecked()){
            totalRm=totalRm+a35;
            cb5 = " 5Pm-6Pm ";
            total++;
        } else {
            cb5 ="";
        } if (cb6Pm.isChecked()){
            totalRm=totalRm+a35;
            cb6 = " 6Pm-7Pm ";
            total++;
        } else {
            cb6 ="";
        } if (cb7Pm.isChecked()){
            totalRm=totalRm+a35;
            cb7 = " 7Pm-8Pm ";
            total++;
        } else {
            cb7 ="";
        } if (cb8Pm.isChecked()){
            totalRm=totalRm+a35;
            cb8 = " 8Pm-9Pm ";
            total++;
        } else {
            cb8 ="";
        } if (cb9Pm.isChecked()){
            totalRm=totalRm+a35;
            cb9 = " 9Pm-10Pm";
            total++;
        } else {
            cb9 ="";
        } if (futsalBall.isChecked()){
            eTotal=eTotal+a10;
            i1 = " Futsal Ball ";
        } else {
            i1 = "";
        } if (frisbee.isChecked()){
            eTotal=eTotal+a10;
            i2 = " Frisbee ";
        } else {
            i2 = "";
        } if (shuttle.isChecked()){
            eTotal=eTotal+a10;
            i3 = " Shuttle ";
        } else {
            i3 = "";
        } if (netball.isChecked()){
            eTotal=eTotal+a10;
            i4 = " Netball ";
        } else {
            i4 = "";
        }

        finalTotal = totalRm+eTotal;

        finalTime = cb10+cb11+cb12+cb1+cb2+cb3+cb4+cb5+cb6+cb7+cb8+cb9;
        finalItem = i1+i2+i3+i4;

        if (TextUtils.isEmpty(Name)){
            Toast.makeText(this, "Please Enter Your Name..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(State)){
            Toast.makeText(this, "Please Select Court..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(ContactNumber)){
            Toast.makeText(this, "Please Contact Number..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Date)){
            Toast.makeText(this, "Please Select Date..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Sport)){
            Toast.makeText(this, "Please Select Sport..", Toast.LENGTH_SHORT).show();
        } else {
            UploadData();
        }

    }

    private void UploadData() {

        progressDialog.setMessage("Uploading Booking Details...");
        progressDialog.show();

        String timestamp = String.valueOf(System.currentTimeMillis());
        String status="Pending";
        String court="";
        String statusUid = Uid+status;
        String remark="";
        String newValue = String.valueOf(finalTotal);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("DoTimestamp" ,timestamp);
        hashMap.put("DoName" ,Name);
        hashMap.put("DoState" ,State);
        hashMap.put("DoContactNumber" ,ContactNumber);
        hashMap.put("DoEmail" ,Email);
        hashMap.put("DoDate" ,Date);
        hashMap.put("DoSport" ,Sport);
        hashMap.put("DoUid" ,Uid);
        hashMap.put("DoItem" ,finalItem);
        hashMap.put("DoStatusUid" ,statusUid);
        hashMap.put("DoStatus" ,status);
        hashMap.put("DoCourt" ,court);
        hashMap.put("DoTotalFee" ,newValue);
        hashMap.put("DoTime" ,finalTime);
        hashMap.put("DoRemark" ,remark);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Booking");
        ref.child(timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        progressDialog.dismiss();
                        Toast.makeText(UserMakeBookingActivity.this, "Booking Details Uploaded...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserMakeBookingActivity.this,UserHomeActivity.class));
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(UserMakeBookingActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void ForDatePicker() {

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(android.widget.DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
        datePickerDialog.show();

    }

}