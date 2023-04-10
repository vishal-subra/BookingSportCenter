package com.example.sportcenterv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class UserProfileActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    Button save;
    ImageView logout;
    EditText txtName,txtAddress,txtState,txtContactNumber,txtEmail;
    ProgressDialog pd ;
    FirebaseAuth auth ;
    DatabaseReference ref;
    String [] state = {"Select State","Kuala Lumpur","Labuan","Putrajaya","Johor","Kedah",
            "Kelantan","Melaka","Negeri Sembilan","Pahang","Perak","Perlis","Penang","Sabah"
            ,"Sarawak","Selangor","Terengganu"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        getSupportActionBar().setTitle("Profile Page");

        save = findViewById(R.id.profile_BtnSave);
        txtAddress = findViewById(R.id.profile_etAddress);
        txtContactNumber = findViewById(R.id.profile_etContactNumber);
        txtEmail = findViewById(R.id.profile_etEmail);
        txtName = findViewById(R.id.profile_etName);
        txtState = findViewById(R.id.profile_etState);
        logout = findViewById(R.id.profile_BtnLogout);

        pd = new ProgressDialog(this);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String emailName = user.getEmail();
        txtEmail.setText(emailName);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(intent);
                auth.signOut();
                finish();
            }
        });


        Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserProfileActivity.this, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,state);
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

        bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setSelectedItemId(R.id.profileId);
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
                    String prAddress = snapshot.child("prAddress").getValue().toString();
                    txtAddress.setText(prAddress);
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateDate();
            }
        });
        
    }

    private String prName="",prAddress="",prContactNumber="",prEmail="",prState="",prof_ID="";
    private void validateDate() {

        prName = txtName.getText().toString().trim();
        prAddress = txtAddress.getText().toString().trim();
        prContactNumber = txtContactNumber.getText().toString().trim();
        prEmail = txtEmail.getText().toString().trim();
        prState = txtState.getText().toString().trim();
        FirebaseUser user = auth.getCurrentUser();
        prof_ID = user.getUid();

        if (TextUtils.isEmpty(prName)){
            txtName.setError("Please Enter Your Name");
        } else if (TextUtils.isEmpty(prContactNumber)){
            txtContactNumber.setError("Please Enter Your Contact Number");
        } else if (TextUtils.isEmpty(prAddress)){
            txtAddress.setError("Please Enter Your Address");
        }else if (TextUtils.isEmpty(prState)){
            txtState.setError("Please Select State");
        } else {
            uploadData();
        }
    }

    private void uploadData() {
        pd.setMessage("Saving profile Details...");
        pd.show();

        String timestamp = String.valueOf(System.currentTimeMillis());
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid" , prof_ID);
        hashMap.put("uEmail" , prEmail);
        hashMap.put("pId" , timestamp);
        hashMap.put("prName" , prName);
        hashMap.put("prContactNumber" , prContactNumber);
        hashMap.put("prAddress" , prAddress);
        hashMap.put("prEmail" , prEmail);
        hashMap.put("prState" , prState);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Profile");
        ref.child(prof_ID)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        pd.dismiss();
                        Toast.makeText(UserProfileActivity.this, "Profile Details Saved...", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        pd.dismiss();
                        Toast.makeText(UserProfileActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


}