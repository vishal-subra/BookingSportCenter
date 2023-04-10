package com.example.sportcenterv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserHomeActivity extends AppCompatActivity {

    FloatingActionButton viewmap;
    BottomNavigationView bottomNavigationView;
    Button viewAll,pending,accept,reject;
    FirebaseAuth auth ;
    RecyclerView recyclerView;
    UserBookingAdapter userBookingAdapter;
    List<BookingModel> bookingModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        getSupportActionBar().setTitle("UserHome Page");

        viewAll = findViewById(R.id.btnViewAll);
        pending = findViewById(R.id.btnPending);
        accept = findViewById(R.id.btnAccepted);
        reject = findViewById(R.id.btnRejected);
        viewmap = findViewById(R.id.map);

        bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setSelectedItemId(R.id.homeId);
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

        viewmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(UserHomeActivity.this, UserMapActivity.class));
            }
        });

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String emailName = user.getEmail();

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        bookingModels = new ArrayList<>();

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPending();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadRejected();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAccepted();
            }
        });

        viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAll();
            }
        });

        all();

    }

    private void loadAll() {

        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        String email = user.getEmail();
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Booking")
                .orderByChild("DoEmail").equalTo(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookingModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    BookingModel bookingModel = ds.getValue(BookingModel.class);
                    bookingModels.add(bookingModel);
                    userBookingAdapter = new UserBookingAdapter(getApplicationContext(), bookingModels);
                    recyclerView.setAdapter(userBookingAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void all() {

        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        String email = user.getEmail();
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Booking")
                .orderByChild("DoEmail").equalTo(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookingModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    BookingModel bookingModel = ds.getValue(BookingModel.class);
                    bookingModels.add(bookingModel);
                    userBookingAdapter = new UserBookingAdapter(getApplicationContext(), bookingModels);
                    recyclerView.setAdapter(userBookingAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadAccepted() {

        String status="Accepted";
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String getStatus=email+status;
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Booking")
                .orderByChild("DoStatusUid").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookingModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    BookingModel bookingModel = ds.getValue(BookingModel.class);
                    bookingModels.add(bookingModel);
                    userBookingAdapter = new UserBookingAdapter(getApplicationContext(), bookingModels);
                    recyclerView.setAdapter(userBookingAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadRejected() {

        String status="Rejected";
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String getStatus=email+status;
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Booking")
                .orderByChild("DoStatusUid").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookingModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    BookingModel bookingModel = ds.getValue(BookingModel.class);
                    bookingModels.add(bookingModel);
                    userBookingAdapter = new UserBookingAdapter(getApplicationContext(), bookingModels);
                    recyclerView.setAdapter(userBookingAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadPending() {

        String status="Pending";
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String getStatus=email+status;
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Booking")
                .orderByChild("DoStatusUid").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookingModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    BookingModel bookingModel = ds.getValue(BookingModel.class);
                    bookingModels.add(bookingModel);
                    userBookingAdapter = new UserBookingAdapter(getApplicationContext(), bookingModels);
                    recyclerView.setAdapter(userBookingAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}