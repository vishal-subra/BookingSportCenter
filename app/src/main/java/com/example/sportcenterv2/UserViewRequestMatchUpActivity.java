package com.example.sportcenterv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

public class UserViewRequestMatchUpActivity extends AppCompatActivity {

    Button viewAll,pending,accept,reject;
    RecyclerView recyclerView;
    UserUpdateGroupRequestAdapter userViewMatchAdapter;
    List<RequestGroupModel> groupModels;
    FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_request_match_up);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Request MatchUp Page");

        viewAll = findViewById(R.id.btnViewAll);
        pending = findViewById(R.id.btnPending);
        accept = findViewById(R.id.btnAccepted);
        reject = findViewById(R.id.btnRejected);

        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setStackFromEnd(true);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        groupModels = new ArrayList<>();

        all();

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

    }

    private void loadPending() {

        String status="Pending";
        FirebaseUser user = auth.getCurrentUser();
        String email = user.getUid();
        String getStatus=email+status;
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("RequestGroup")
                .orderByChild("ReqHostStatus").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestGroupModel bookingModel = ds.getValue(RequestGroupModel.class);
                    groupModels.add(bookingModel);
                    userViewMatchAdapter = new UserUpdateGroupRequestAdapter(getApplicationContext(), groupModels);
                    recyclerView.setAdapter(userViewMatchAdapter);
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

        ref = FirebaseDatabase.getInstance().getReference("RequestGroup")
                .orderByChild("ReqHostStatus").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestGroupModel bookingModel = ds.getValue(RequestGroupModel.class);
                    groupModels.add(bookingModel);
                    userViewMatchAdapter = new UserUpdateGroupRequestAdapter(getApplicationContext(), groupModels);
                    recyclerView.setAdapter(userViewMatchAdapter);
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

        ref = FirebaseDatabase.getInstance().getReference("RequestGroup")
                .orderByChild("ReqHostStatus").equalTo(getStatus);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestGroupModel bookingModel = ds.getValue(RequestGroupModel.class);
                    groupModels.add(bookingModel);
                    userViewMatchAdapter = new UserUpdateGroupRequestAdapter(getApplicationContext(), groupModels);
                    recyclerView.setAdapter(userViewMatchAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadAll() {

        FirebaseUser user = auth.getCurrentUser();
        assert user != null;
        String email = user.getUid();
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("RequestGroup")
                .orderByChild("ReqHostUid").equalTo(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestGroupModel bookingModel = ds.getValue(RequestGroupModel.class);
                    groupModels.add(bookingModel);
                    userViewMatchAdapter = new UserUpdateGroupRequestAdapter(getApplicationContext(), groupModels);
                    recyclerView.setAdapter(userViewMatchAdapter);
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
        String email = user.getUid();
        Query ref;

        ref = FirebaseDatabase.getInstance().getReference("RequestGroup")
                .orderByChild("ReqHostUid").equalTo(email);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                groupModels.clear();
                for (DataSnapshot ds: dataSnapshot.getChildren()){
                    RequestGroupModel bookingModel = ds.getValue(RequestGroupModel.class);
                    groupModels.add(bookingModel);
                    userViewMatchAdapter = new UserUpdateGroupRequestAdapter(getApplicationContext(), groupModels);
                    recyclerView.setAdapter(userViewMatchAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}