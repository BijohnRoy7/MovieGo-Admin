package com.donate_blood.invenz.moviegoadmin.adapters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donate_blood.invenz.moviegoadmin.R;
import com.donate_blood.invenz.moviegoadmin.activities.MainActivity;
import com.donate_blood.invenz.moviegoadmin.models.SharedMovie;
import com.donate_blood.invenz.moviegoadmin.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SharedMovieCustomAdapter extends RecyclerView.Adapter<SharedMovieCustomAdapter.MyViewHolder> {

    private static final String TAG = "ROY" ;
    private Context context;
    private List<SharedMovie> sharedMovieList;

    public SharedMovieCustomAdapter(Context context, List<SharedMovie> sharedMovieList) {
        this.context = context;
        this.sharedMovieList = sharedMovieList;
    }

    @NonNull
    @Override
    public SharedMovieCustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.single_shared_movie, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final SharedMovieCustomAdapter.MyViewHolder holder, final int position) {

        final SharedMovie sharedMovie = sharedMovieList.get(position);

        //Toast.makeText(context, ""+sharedMovie.getMovieName(), Toast.LENGTH_SHORT).show();
        holder.tvName.setText(sharedMovie.getMovieName());
        holder.tvCatagory.setText(sharedMovie.getCatagory());
        holder.tvLink.setText(sharedMovie.getMovieLink());

        final String id = String.valueOf(sharedMovie.getId());


        /*##      copying link      ###*/
        holder.ivCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String link = holder.tvLink.getText().toString().trim();

                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", link);
                clipboard.setPrimaryClip(clip);

            }
        });


        /*###        deleting info     ####*/
        holder.btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest stringRequest = new StringRequest(StringRequest.Method.POST, Constants.DELETE_SHARED_MOVIE_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String code = jsonObject.getString("code");
                                    String msg = jsonObject.getString("message");
                                    Toast.makeText(context, ""+msg, Toast.LENGTH_SHORT).show();

                                    if (code.equals("Delete_success")){
                                        sharedMovieList.remove(position);
                                        notifyDataSetChanged();
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: "+error);
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String,String> map = new HashMap<>();
                        map.put("movieId",id);
                        return map;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);

            }
        });

    }

    @Override
    public int getItemCount() {
        return sharedMovieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvLink, tvCatagory;
        ImageView ivCopy;
        Button btDelete;

        public MyViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.idMovieName_sharedLink);
            tvCatagory = itemView.findViewById(R.id.idCatagory_sharedLink);
            tvLink = itemView.findViewById(R.id.idMovieLink_sharedLink);
            btDelete = itemView.findViewById(R.id.idDelete_sharedLink);
            ivCopy = itemView.findViewById(R.id.idCopy_sharedLink);

        }
    }
}
