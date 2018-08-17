package com.donate_blood.invenz.moviegoadmin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donate_blood.invenz.moviegoadmin.utils.Constants;
import com.donate_blood.invenz.moviegoadmin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditMovieActivity extends AppCompatActivity {

    private static final String TAG = "ROY";
    private EditText etName, etReleaseYear, etDesc, etLink1, etLink2, etLink3, etLink4, etVideoId;
    private ImageView movieImageView;
    private Button btEdit, btDelete, btSelectImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        /*############# Initializing Components ###############*/
        etName = findViewById(R.id.idName_editMovie);
        etReleaseYear = findViewById(R.id.idCatagory_editMovie);
        etDesc = findViewById(R.id.idDesc_editMovie);
        etLink1 = findViewById(R.id.idLink1_editMovie);
        etLink2 = findViewById(R.id.idLink2_editMovie);
        etLink3 = findViewById(R.id.idLink3_editMovie);
        etLink4 = findViewById(R.id.idLink4_editMovie);
        etVideoId = findViewById(R.id.idVideoId_editMovie);
        btSelectImage = findViewById(R.id.idSelectImageButton_editMovie);
        btEdit = findViewById(R.id.idEditButton_editMovie);
        btDelete = findViewById(R.id.idDeleteButton_editMovie);


        /*###### Getting bundle ########*/
        Bundle movieInfo = getIntent().getExtras().getBundle("MovieInfo");

        /*########## Getting values from bundle ##############*/
        final String movieId = String.valueOf(movieInfo.getInt("id"));
        String movieName = movieInfo.getString("MovieName");
        String movieDesc = movieInfo.getString("MovieDesc");
        String movieLink1 = movieInfo.getString("MovieLink1");
        String movieLink2 = movieInfo.getString("MovieLink2");
        String movieLink3 = movieInfo.getString("MovieLink3");
        String movieLink4 = movieInfo.getString("MovieLink4");
        String movieYear = movieInfo.getString("MovieYear");
        String imageURL = movieInfo.getString("MovieImage");
        String videoId = movieInfo.getString("VideoId");


        etName.setText(movieName);
        etReleaseYear.setText(movieYear);
        etDesc.setText(movieDesc);
        etLink1.setText(movieLink1);
        etLink2.setText(movieLink2);
        etLink3.setText(movieLink3);
        etLink4.setText(movieLink4);
        etVideoId.setText(videoId);

        /*########### Load the image using Glide ###########*/
        //Picasso.with(EditMovieActivity.this).load(imageURL).resize(600, 200).into(movieImageView);


        btEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String id  = movieId;
                final String name = etName.getText().toString().trim();
                final String desc = etDesc.getText().toString().trim();
                final String year = etReleaseYear.getText().toString().trim();
                final String link1 = etLink1.getText().toString().trim();
                final String link2 = etLink2.getText().toString().trim();
                final String link3 = etLink3.getText().toString().trim();
                final String link4 = etLink4.getText().toString().trim();
                final String sVideoId = etVideoId.getText().toString().trim();
                //String url = et.getText().toString().trim();

                if (id!=null && !name.isEmpty() && !desc.isEmpty() && !year.isEmpty() && !link1.isEmpty()){

                    StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.EDIT_MOVIE_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String serverResponse = jsonObject.getString("message");
                                        Toast.makeText(EditMovieActivity.this, ""+serverResponse, Toast.LENGTH_SHORT).show();


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "onErrorResponse: "+error);
                            error.getStackTrace();
                        }
                    }){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String , String > editInfoMap = new HashMap<>();

                            editInfoMap.put("id", id);
                            editInfoMap.put("name", name);
                            editInfoMap.put("desc", desc);
                            editInfoMap.put("year", year);
                            editInfoMap.put("link1", link1);
                            editInfoMap.put("link2", link2);
                            editInfoMap.put("link3", link3);
                            editInfoMap.put("link4", link4);
                            editInfoMap.put("video_id", sVideoId);

                            return editInfoMap;
                        }
                    };


                    RequestQueue requestQueue = Volley.newRequestQueue(EditMovieActivity.this);
                    requestQueue.add(stringRequest);

                }else {
                    Toast.makeText(EditMovieActivity.this, "Please provide all information", Toast.LENGTH_SHORT).show();
                }

            }
        });



        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.DELETE_MOVIE_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String code = jsonObject.getString("code");
                                    String message = jsonObject.getString("message");
                                    Log.d(TAG, "onResponse1 (EditMovieActivity): "+message);
                                    Toast.makeText(EditMovieActivity.this, ""+message, Toast.LENGTH_SHORT).show();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse 1 (EditMovieActivity): "+error);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> idMap = new HashMap<>();
                        idMap.put("movieId", movieId);
                        return idMap;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(EditMovieActivity.this);
                requestQueue.add(stringRequest);

            }
        });

    }
}
