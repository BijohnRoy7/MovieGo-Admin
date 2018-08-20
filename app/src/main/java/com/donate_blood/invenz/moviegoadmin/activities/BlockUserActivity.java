package com.donate_blood.invenz.moviegoadmin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donate_blood.invenz.moviegoadmin.R;
import com.donate_blood.invenz.moviegoadmin.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BlockUserActivity extends AppCompatActivity {

    private static final String TAG = "ROY";
    private EditText etUserId;
    private Button btBlock, btUnblock;
    private ArrayList<String> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_block_user);

        etUserId = findViewById(R.id.idUserId_blockUser);
        btBlock = findViewById(R.id.idBlock_blockUser);
        btUnblock = findViewById(R.id.idUnblock_blockUser);


        btBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String sUserId = etUserId.getText().toString().trim();

                if (!sUserId.isEmpty()){

                    StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.BLOCK_UNBLOCK_USER_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String msg = jsonObject.getString("message");
                                        Toast.makeText(BlockUserActivity.this, ""+msg, Toast.LENGTH_SHORT).show();

                                        etUserId.setText("");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "onErrorResponse(BlockUserAct): "+error);
                        }
                    }){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> blockUser = new HashMap<>();
                            blockUser.put("user_id", sUserId);
                            blockUser.put("block_unblock", "1");

                            return blockUser;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(BlockUserActivity.this);
                    requestQueue.add(stringRequest);

                }

            }
        });




        btUnblock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String sUserId = etUserId.getText().toString().trim();

                if (!sUserId.isEmpty()){

                    StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.BLOCK_UNBLOCK_USER_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {

                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        String msg = jsonObject.getString("message");
                                        Toast.makeText(BlockUserActivity.this, ""+msg, Toast.LENGTH_SHORT).show();

                                        etUserId.setText("");

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d(TAG, "onErrorResponse(BlockUserAct): "+error);
                        }
                    }){

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> blockUser = new HashMap<>();
                            blockUser.put("user_id", sUserId);
                            blockUser.put("block_unblock", "0");

                            return blockUser;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(BlockUserActivity.this);
                    requestQueue.add(stringRequest);

                }


            }
        });


    }
}
