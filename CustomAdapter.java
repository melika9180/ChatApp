package com.sxillocc.androidchat;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private ArrayList<Messagee> dataSet;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mEmail;
        TextView mMsg;
        //ImageView mImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.mEmail = (TextView) itemView.findViewById(R.id.cv_email);
            this.mMsg = (TextView) itemView.findViewById(R.id.cv_message);
            //Add image in id.cv_image
        }
    }

    public CustomAdapter(ArrayList<Messagee> data) {
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_card, parent, false);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        holder.mEmail.setText(dataSet.get(listPosition).email);
        holder.mMsg.setText(dataSet.get(listPosition).message);

    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}