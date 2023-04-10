package com.example.sportcenterv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserViewMatchUpActivity extends AppCompatActivity {

    Button myMatch;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton history;
    RecyclerView recyclerView;
    UserMatchAdapter userViewMatchAdapter;
    List<GroupModel> groupModels;
    FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_match_up);

        getSupportActionBar().setTitle("MatchUp Page");

        myMatch = findViewById(R.id.btnViewMyMatch);
        history = findViewById(R.id.history);

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserViewMatchUpActivity.this, UserViewHistroyRequestActivity.class));
            }
        });

        myMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserViewMatchUpActivity.this, UserViewMyMatchActivity.class));
            }
        });

        bottomNavigationView = findViewById(R.id.bottomView);
        bottomNavigationView.setSelectedItemId(R.id.MatchId);
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

        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        groupModels = new ArrayList<>();

        all();

    }

    private void all() {

        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Group");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    GroupModel bookingModel = ds.getValue(GroupModel.class);
                    groupModels.add(bookingModel);
                    userViewMatchAdapter = new UserMatchAdapter(getApplicationContext(), groupModels);
                    recyclerView.setAdapter(userViewMatchAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}