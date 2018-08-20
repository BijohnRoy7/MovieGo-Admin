package com.donate_blood.invenz.moviegoadmin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.donate_blood.invenz.moviegoadmin.R;

public class ShowAllInfoActivity extends AppCompatActivity {

    private Button btEditInfo, btShowRequests, btSharedMovies, btBlockUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_info);

        btEditInfo = findViewById(R.id.idEdit);
        btShowRequests = findViewById(R.id.idMovieRequests);
        btSharedMovies = findViewById(R.id.idSharedMovies);
        btBlockUser = findViewById(R.id.idBloackUser_showAllInfo);

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


        btBlockUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ShowAllInfoActivity.this, BlockUserActivity.class));
            }
        });


    }
}
