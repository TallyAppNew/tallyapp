package com.ngedev.tallyapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

public class RecyclerConversion extends RecyclerView.Adapter<RecyclerConversion.ViewHolderData> {
    JSONArray mArrayData;

    public RecyclerConversion(JSONArray arrayData){
        mArrayData = arrayData;
    }

    @NonNull
    @Override
    public ViewHolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_list_conversion, parent, false);
        return new ViewHolderData(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderData holder, int position) {
        TextView tv_one = holder.itemView.findViewById(R.id.tv_one);
        TextView tv_two = holder.itemView.findViewById(R.id.tv_two);
        TextView tv_three = holder.itemView.findViewById(R.id.tv_three);

        try {
            JSONArray data = mArrayData.getJSONArray(position);
            String multiply   = data.getString(0);
            String by         = data.getString(1);
            String obtain     = data.getString(2);

            tv_one.setText(multiply);
            tv_two.setText(by);
            tv_three.setText(obtain);
        } catch (JSONException e) {
            e.printStackTrace();
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
