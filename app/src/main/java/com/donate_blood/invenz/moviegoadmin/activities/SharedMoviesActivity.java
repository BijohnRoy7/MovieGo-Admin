package com.donate_blood.invenz.moviegoadmin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.donate_blood.invenz.moviegoadmin.R;
import com.donate_blood.invenz.moviegoadmin.adapters.SharedMovieCustomAdapter;
import com.donate_blood.invenz.moviegoadmin.models.SharedMovie;
import com.donate_blood.invenz.moviegoadmin.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SharedMoviesActivity extends AppCompatActivity {

    private static final String TAG = "ROY";
    private RecyclerView recyclerView;
    private SharedMovieCustomAdapter customAdapter;
    private List<SharedMovie> sharedMovieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_movies);

        recyclerView = findViewById(R.id.idRecView_sharedMovie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sharedMovieList  = new ArrayList<>();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(JsonArrayRequest.Method.POST, Constants.GET_SHARED_MOVIES_URL, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        int size = response.length();

                        for (int i=0; i<size ; i++){
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);

                                int id = Integer.parseInt(jsonObject.getString("id"));
                                String movieName = jsonObject.getString("movie_name");
                                String movieLink = jsonObject.getString("movie_link");
                                String catagory = jsonObject.getString("catagory");

                                //Toast.makeText(SharedMoviesActivity.this, ""+id, Toast.LENGTH_SHORT).show();

                                SharedMovie sharedMovie = new SharedMovie(id, movieName, movieLink, catagory);
                                sharedMovieList.add(sharedMovie);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        customAdapter = new SharedMovieCustomAdapter(SharedMoviesActivity.this, sharedMovieList);
                        customAdapter.notifyDataSetChanged();
                        recyclerView.setAdapter(customAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse(SharedMoviesActivity): "+error);
                error.getStackTrace();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(SharedMoviesActivity.this);
        requestQueue.add(arrayRequest);

    }
}
