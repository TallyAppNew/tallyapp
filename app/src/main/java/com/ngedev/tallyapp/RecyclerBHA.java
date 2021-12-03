package com.ngedev.tallyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

public class RecyclerBHA extends RecyclerView.Adapter<RecyclerBHA.ViewHolderData> {
    JSONArray mArrayData;

    public RecyclerBHA(JSONArray arrayData){
        mArrayData = arrayData;
    }

    @Override
    public int getItemViewType(int position) {
        try {
            JSONArray data = mArrayData.getJSONArray(position);
            if (data.getString(1).equals("") && data.getString(2).equals("") && data.getString(3).equals("") && data.getString(4).equals("")) {
                return 1;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v;


        if(viewType==0){
            v = inflater.inflate(R.layout.row_list_bha, parent, false);
        } else {
            v = inflater.inflate(R.layout.row_list_bha_title, parent, false);
        }
        return new ViewHolderData(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {

        if(holder.getItemViewType()==0){
            TextView tv_one = holder.itemView.findViewById(R.id.tv_one);
            TextView tv_two = holder.itemView.findViewById(R.id.tv_two);
            TextView tv_three = holder.itemView.findViewById(R.id.tv_three);
            TextView tv_four = holder.itemView.findViewById(R.id.tv_four);
            TextView tv_five = holder.itemView.findViewById(R.id.tv_five);

            try {
                JSONArray data = mArrayData.getJSONArray(position);
                String sizeOD       = data.getString(0);
                String sizeID       = data.getString(1);
                String weight       = data.getString(2);
                String capacity     = data.getString(3);
                String displacement = data.getString(4);

                tv_one.setText(sizeOD);
                tv_two.setText(sizeID);
                tv_three.setText(weight);
                tv_four.setText(capacity);
                tv_five.setText(displacement);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            TextView tv_title = holder.itemView.findViewById(R.id.tv_title);

            try {
                JSONArray data = mArrayData.getJSONArray(position);
                tv_title.setText(data.getString(0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int getItemCount() {
        return mArrayData.length();
    }

    static class ViewHolderData extends RecyclerView.ViewHolder {
        public ViewHolderData(@NonNull View itemView) {
            super(itemView);
        }
    }
}
