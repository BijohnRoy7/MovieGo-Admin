package com.donate_blood.invenz.moviegoadmin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.donate_blood.invenz.moviegoadmin.R;

public class ShowAllInfoActivity extends AppCompatActivity {

    private Button btEditInfo, btShowRequests, btSharedMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_info);

        btEditInfo = findViewById(R.id.idEdit);
        btShowRequests = findViewById(R.id.idMovieRequests);
        btSharedMovies = findViewById(R.id.idSharedMovies);

        btEditInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowAllInfoActivity.this, EditInfoActivity.class));
            }
        });


        btShowRequests.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShowAllInfoActivity.this, ShowRequestsActivity.class));
            }
        });


        btSharedMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ShowAllInfoActivity.this, SharedMoviesActivity.class));
            }
        });

    }
}
