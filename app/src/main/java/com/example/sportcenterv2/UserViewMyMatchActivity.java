package com.example.sportcenterv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserViewMyMatchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UserUpdateMatchAdapter userViewMatchAdapter;
    List<GroupModel> groupModels;
    FirebaseAuth auth ;
    Button req;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_my_match);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My MatchUp Page");
        req = findViewById(R.id.btnRequestMatch);

        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserViewMyMatchActivity.this, UserViewRequestMatchUpActivity.class));
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

        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        String email = user.getUid();
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("Group")
                .orderByChild("MaUid").equalTo(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    GroupModel bookingModel = ds.getValue(GroupModel.class);
                    groupModels.add(bookingModel);
                    userViewMatchAdapter = new UserUpdateMatchAdapter(getApplicationContext(), groupModels);
                    recyclerView.setAdapter(userViewMatchAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}