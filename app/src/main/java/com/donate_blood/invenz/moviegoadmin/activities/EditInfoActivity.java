package com.donate_blood.invenz.moviegoadmin.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.donate_blood.invenz.moviegoadmin.R;

public class EditInfoActivity extends AppCompatActivity {

    private ListView catagoryListView;
    private ArrayAdapter<String> arrayAdapter;
    private String[] catagories = {"Bangla Movies", "Chinese Movies", "English Movies", "Hindi Movies", "Korean Movies", "Kolkata Bangla Movies", "South Indian Movies", "Tv Series", "Others"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        catagoryListView = findViewById(R.id.idCatagoryList);
        arrayAdapter = new ArrayAdapter<String>(EditInfoActivity.this, android.R.layout.simple_list_item_1, catagories);
        catagoryListView.setAdapter(arrayAdapter);


        catagoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String catagory = catagories[position];
                Toast.makeText(EditInfoActivity.this, ""+catagory, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(EditInfoActivity.this, MovieListActivity.class);
                intent.putExtra("catagory", catagory);
                startActivity(intent);
            }
        });


        catagoryListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });
    }
}
