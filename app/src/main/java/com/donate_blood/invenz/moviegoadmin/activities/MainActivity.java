package com.donate_blood.invenz.moviegoadmin.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donate_blood.invenz.moviegoadmin.R;
import com.donate_blood.invenz.moviegoadmin.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "ROY";
    private EditText etMovieName, etMovieDesc, etMovieLink,etMovieLink1, etMovieLink2, etMovieLink3, etReleaseYear, etVideoId, etSubtitle1, etSubtitle2;
    private Button btChooseImage, btAddMovie, btShowMovieInfo;
    private Spinner spCatagory;
    //private ImageView imageViewMovie;
    private String[] catagories = {"Bangla Movies", "Chinese Movies", "English Movies", "Hindi Movies", "Korean Movies", "Kolkata Bangla Movies", "South Indian Movies", "Tv Series", "Others"};
    private ArrayAdapter<String> arrayAdapterCatagories;
    private ProgressDialog progressDialog;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri, imgUri;
    private Bitmap imageBitmap;
    private byte[] imageData;
    private StorageReference mStorageRef;
    private RequestQueue requestQueue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMovieName = findViewById(R.id.idMovieName);
        etReleaseYear = findViewById(R.id.idMovieYear);
        etMovieDesc =findViewById(R.id.idMovieDescription);
        etMovieLink =findViewById(R.id.idMovieLink);
        etMovieLink1 =findViewById(R.id.idMovieLink1);
        etMovieLink2 =findViewById(R.id.idMovieLink2);
        etMovieLink3 =findViewById(R.id.idMovieLink3);
        etSubtitle1 =findViewById(R.id.idSubtitleLink);
        etSubtitle2 =findViewById(R.id.idSubtitleLink2);
        etVideoId = findViewById(R.id.idMovieVideoId);
        btChooseImage = findViewById(R.id.idMovieImageBtn);
        btAddMovie = findViewById(R.id.idAddMovie);
        btShowMovieInfo = findViewById(R.id.idShowAllMovie);
        spCatagory = findViewById(R.id.idMovieCatagory);

        //imageViewMovie = findViewById(R.id.idMovieImage);
        progressDialog = new ProgressDialog(MainActivity.this);

        arrayAdapterCatagories = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, catagories);
        spCatagory.setAdapter(arrayAdapterCatagories);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        /*######## Select Image ##########*/
        btChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyImageChosser();
            }
        });


        /*########## Submit Information #############*/
        btAddMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String sMovieName, sMovieDesc, sMovieLink,sMovieLink1, sMovieLink2, sMovieLink3, sReleaseYear, sCatagory, sVideoId, sSubtitleLink1, sSubtitleLink2;

                sCatagory = spCatagory.getSelectedItem().toString().trim();
                sMovieName = etMovieName.getText().toString().trim();
                sMovieDesc = etMovieDesc.getText().toString().trim();
                sMovieLink = etMovieLink.getText().toString().trim();
                sMovieLink1 = etMovieLink1.getText().toString().trim();
                sMovieLink2 = etMovieLink2.getText().toString().trim();
                sMovieLink3 = etMovieLink3.getText().toString().trim();
                sReleaseYear = etReleaseYear.getText().toString().trim();
                sVideoId = etVideoId.getText().toString().trim();
                sSubtitleLink1 = etSubtitle1.getText().toString().trim();
                sSubtitleLink2 = etSubtitle2.getText().toString().trim();


                if (sCatagory.isEmpty() || sMovieName.isEmpty() || sMovieDesc.isEmpty() || sMovieLink.isEmpty() || sReleaseYear.isEmpty() || imageUri==null){
                    Toast.makeText(MainActivity.this, "Please Provide All Information", Toast.LENGTH_SHORT).show();
                }
                else {

                    //showing progressDialog
                    progressDialog.setTitle("Storing Data");
                    progressDialog.setMessage("Information Storing, Please wait...");
                    progressDialog.show();

                    //Toast.makeText(MainActivity.this, "Ok", Toast.LENGTH_SHORT).show();
                    Date date = new Date();
                    String seconds = String.valueOf(date.getSeconds());
                    String imageName = sMovieName+sReleaseYear+seconds+".jpg";//image name in firebase cloud storage



                    /*###### Uploading the image #######*/
                    StorageReference movieImages = mStorageRef.child(imageName);
                    //movieImages.putBytes(imageData).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    movieImages.putFile(imageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                //Toast.makeText(MainActivity.this, "Image Successfully uploaded", Toast.LENGTH_SHORT).show();

                                /*### Getting the image download url ####*/
                                final String imageDownloadUrl = task.getResult().getDownloadUrl().toString();
                                Log.d(TAG, "onComplete: Image URL "+imageDownloadUrl);

                                /*####### STORE DATA ON MY SERVER ###########*/
                                StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.ADD_MOVIE_URL,
                                        new Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                try {
                                                    JSONObject myJsonObject = new JSONObject(response);

                                                    /*###### getting all response values from php file ########*/
                                                    String connection = myJsonObject.getString("connection");
                                                    String code= myJsonObject.getString("code");
                                                    String message = myJsonObject.getString("message");

                                                    Toast.makeText(MainActivity.this, ""+message, Toast.LENGTH_LONG).show();

                                                    if (code.equals("stored_success")){

                                                        Log.d(TAG, "onResponse (MainActivity): "+connection);
                                                        Log.d(TAG, "onResponse (MainActivity): "+code);
                                                        Log.d(TAG, "onResponse (MainActivity): "+message);


                                                        /*####              Sending Notification                     #####*/
                                                        sendNotification(sMovieName, sCatagory);

                                                        etMovieName.setText("");
                                                        etMovieDesc.setText("");
                                                        etMovieLink.setText("");
                                                        etMovieLink1.setText("");
                                                        etMovieLink2.setText("");
                                                        etMovieLink3.setText("");
                                                        etReleaseYear.setText("");
                                                        etVideoId.setText("");
                                                        etSubtitle1.setText("");
                                                    }

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }
                                        }, new Response.ErrorListener() { //for getting error
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Log.d(TAG, "onErrorResponse (String Resquest): "+error);
                                    }
                                }){
                                    /*######### sending data ########*/

                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String , String> movieInfoMap = new HashMap<>();

                                        movieInfoMap.put("movie_name", sMovieName);
                                        movieInfoMap.put("movie_desc", sMovieDesc);
                                        movieInfoMap.put("movie_link", sMovieLink);
                                        movieInfoMap.put("movie_link1", sMovieLink1);
                                        movieInfoMap.put("movie_link2", sMovieLink2);
                                        movieInfoMap.put("movie_link3", sMovieLink3);
                                        movieInfoMap.put("movie_image_link", imageDownloadUrl);
                                        movieInfoMap.put("movie_release_year", sReleaseYear);
                                        movieInfoMap.put("movie_catagory", sCatagory);
                                        movieInfoMap.put("video_id", sVideoId);
                                        movieInfoMap.put("subtitle1", sSubtitleLink1);
                                        movieInfoMap.put("subtitle2", sSubtitleLink2);

                                        return movieInfoMap;
                                    }
                                };

                                /*####### ADDING REQUEST TO THE QUEUE #######*/
                                stringRequest.setTag(TAG);
                                requestQueue = Volley.newRequestQueue(MainActivity.this);
                                requestQueue.add(stringRequest);

                                progressDialog.dismiss(); /* Dismiss progressDialog */
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: "+e.getMessage());
                            progressDialog.dismiss();
                        }
                    });

                }
            }
        });



        /*########*/
        btShowMovieInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowAllInfoActivity.class));
            }
        });

    }



    /*####                       sending notification                            ###*/
    private void sendNotification(final String sMovieName, final String sCatagory) {

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.SEND_NOTIFICATION_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

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

                Map<String, String> notificationMap = new HashMap<>();
                notificationMap.put("title", sCatagory);
                notificationMap.put("message", sMovieName);

                return notificationMap;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);

    }


    /*######### onStop method #############*/
    @Override
    protected void onStop() {
        super.onStop();
        if (requestQueue != null) {
            requestQueue.cancelAll(TAG);
        }
    }




    /*###### My method to choose image ########*/
    private void openMyImageChosser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();

            //Picasso.get().load(imageUri).into(imageViewMovie);
            //imageViewMovie.setVisibility(View.VISIBLE);

            /*###### Without piccasso ########*/
            //imageViewMovie.setImageURI(imageUri);

        }
    }
}
