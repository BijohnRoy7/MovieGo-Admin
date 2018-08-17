package com.donate_blood.invenz.moviegoadmin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.donate_blood.invenz.moviegoadmin.models.MovieRequest;
import com.donate_blood.invenz.moviegoadmin.R;

import java.util.List;

public class RequestsCustomAdapter extends BaseAdapter {

    private List<MovieRequest> requests;
    private Context context;

    public RequestsCustomAdapter(List<MovieRequest> requests, Context context) {
        this.requests = requests;
        this.context = context;
    }

    @Override
    public int getCount() {
        return requests.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.single_request, parent, false);
        }

        MovieRequest request = requests.get(position);

        TextView tvMovieName, tvCatagory, tvReqNo;
        tvMovieName = convertView.findViewById(R.id.idMovieName_singleReq);
        tvCatagory = convertView.findViewById(R.id.idCatagory_singleReq);
        tvReqNo = convertView.findViewById(R.id.idRequestNo_singleReq);

        tvMovieName.setText(request.getMovieName());
        tvCatagory.setText(request.getCatagory());
        tvReqNo.setText(request.getRequestNo());

        return convertView;
    }
}
