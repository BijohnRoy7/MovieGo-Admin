package com.donate_blood.invenz.moviegoadmin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donate_blood.invenz.moviegoadmin.utils.Constants;
import com.donate_blood.invenz.moviegoadmin.models.MovieRequest;
import com.donate_blood.invenz.moviegoadmin.R;
import com.donate_blood.invenz.moviegoadmin.adapters.RequestsCustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowRequestsActivity extends AppCompatActivity {

    private static final String TAG = "ROY" ;
    private ListView requestsListView;
    private List<MovieRequest> movieRequests;
    private RequestsCustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_requests);


        requestsListView = findViewById(R.id.idRequests_ShowReqActivity);
        movieRequests = new ArrayList<>();

        JsonArrayRequest arrayRequest = new JsonArrayRequest(JsonArrayRequest.Method.POST, Constants.GET_MOVIE_REQUESTS, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i=0; i<response.length(); i++){

                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                int id = jsonObject.getInt("id");
                                String name = jsonObject.getString("movie_name");
                                String catagory = jsonObject.getString("catagory");
                                String req_no = jsonObject.getString("req_no");

                                movieRequests.add(new MovieRequest(id, name, catagory, req_no));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            customAdapter = new RequestsCustomAdapter(movieRequests,ShowRequestsActivity.this);
                            requestsListView.setAdapter(customAdapter);


                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse1 (ShowRequestsActivity): "+error);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(ShowRequestsActivity.this);
        requestQueue.add(arrayRequest);



        /*########### Delete a request ###########*/
        requestsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                MovieRequest movieRequest = movieRequests.get(position);

                final String requestId = String.valueOf(movieRequest.getId());
                //Toast.makeText(ShowRequestsActivity.this, ""+movieRequest.getMovieName(), Toast.LENGTH_SHORT).show();

                /*################ StringRequest ###################*/
                StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.DELETE_MOVIE_REQUEST,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    Log.d(TAG, "onResponse: "+jsonObject.getString("message"));
                                    Toast.makeText(ShowRequestsActivity.this, ""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse2 (ShowRequestsActivity): "+error);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> stringMap = new HashMap<>();
                        stringMap.put("requestId", requestId); /*##### Sending ID ######*/

                        Log.d(TAG, "getParams: "+requestId);
                        return stringMap;
                    }
                };

                RequestQueue requestQueue1 = Volley.newRequestQueue(ShowRequestsActivity.this);
                requestQueue1.add(stringRequest);


                return false;
            }
        });


    }
}
