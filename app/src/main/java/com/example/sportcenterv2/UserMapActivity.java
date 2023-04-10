package com.example.sportcenterv2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class UserMapActivity extends AppCompatActivity implements View.OnClickListener {

    //grid view
    public CardView viewtextmap, viewmaps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_map);

        getSupportActionBar().setTitle("Map");

        //card view grid * add from here*
        viewtextmap = findViewById(R.id.grid1);
        viewtextmap.setOnClickListener(this);

        viewmaps = findViewById(R.id.grid2);
        viewmaps.setOnClickListener(this);

    }

    //card view
    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {

            case R.id.grid1:
                i = new Intent(this, UserMapText.class);
                startActivity(i);
                break;

            case R.id.grid2:
                i = new Intent(this, UserMapView.class);
                startActivity(i);
                break;
        }
    }

}