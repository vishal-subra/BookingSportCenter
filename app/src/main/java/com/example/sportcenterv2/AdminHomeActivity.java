package com.example.sportcenterv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminHomeActivity extends AppCompatActivity {

    Button viewAll,pending,accept,reject,logout;
    RecyclerView recyclerView;
    AdminBookingAdapter adminBookingAdapter;
    List<BookingModel> bookingModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        getSupportActionBar().setTitle("AdminHome Page");

        logout = findViewById(R.id.btnlogout);
        viewAll = findViewById(R.id.btnViewAll);
        pending = findViewById(R.id.btnPending);
        accept = findViewById(R.id.btnAccepted);
        reject = findViewById(R.id.btnRejected);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHomeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

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

    private void firebaseSearch(String searchtext){
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Booking");
        String query = searchtext;
        Query firebaseQuery= ref.orderByChild("DoDate").startAt(query).endAt(query+"\uf8ff");


        bookingModels = new ArrayList<>();
        adminBookingAdapter = new AdminBookingAdapter(this,bookingModels);
        recyclerView.setAdapter(adminBookingAdapter);

        firebaseQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    BookingModel user = dataSnapshot.getValue(BookingModel.class);
                    bookingModels.add(user);

                }
                adminBookingAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        MenuItem item = menu.findItem(R.id.search_firebase);
        androidx.appcompat.widget.SearchView searchView = (androidx.appcompat.widget.SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void all() {

        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Booking");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookingModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    BookingModel bookingModel = ds.getValue(BookingModel.class);
                    bookingModels.add(bookingModel);
                    adminBookingAdapter = new AdminBookingAdapter(getApplicationContext(), bookingModels);
                    recyclerView.setAdapter(adminBookingAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadAll() {

        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Booking");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookingModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    BookingModel bookingModel = ds.getValue(BookingModel.class);
                    bookingModels.add(bookingModel);
                    adminBookingAdapter = new AdminBookingAdapter(getApplicationContext(), bookingModels);
                    recyclerView.setAdapter(adminBookingAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadAccepted() {

        String status = "Accepted";
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Booking")
                .orderByChild("DoSport").equalTo(status);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookingModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    BookingModel bookingModel = ds.getValue(BookingModel.class);
                    bookingModels.add(bookingModel);
                    adminBookingAdapter = new AdminBookingAdapter(getApplicationContext(), bookingModels);
                    recyclerView.setAdapter(adminBookingAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadRejected() {

        String status = "Rejected";
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Booking")
                .orderByChild("DoStatus").equalTo(status);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookingModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    BookingModel bookingModel = ds.getValue(BookingModel.class);
                    bookingModels.add(bookingModel);
                    adminBookingAdapter = new AdminBookingAdapter(getApplicationContext(), bookingModels);
                    recyclerView.setAdapter(adminBookingAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadPending() {

        String status = "Pending";
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Booking")
                .orderByChild("DoStatus").equalTo(status);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                bookingModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    BookingModel bookingModel = ds.getValue(BookingModel.class);
                    bookingModels.add(bookingModel);
                    adminBookingAdapter = new AdminBookingAdapter(getApplicationContext(), bookingModels);
                    recyclerView.setAdapter(adminBookingAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}