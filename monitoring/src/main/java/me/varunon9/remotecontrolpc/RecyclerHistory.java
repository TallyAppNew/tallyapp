package me.varunon9.remotecontrolpc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class RecyclerHistory extends RecyclerView.Adapter<RecyclerHistory.ViewHolderHistory> {
    ArrayList<String> mListIP;
    IfaceClickHistory mIfaceClick;

    public RecyclerHistory(ArrayList<String> listIP, IfaceClickHistory ifaceClick){
        mListIP = listIP;
        mIfaceClick = ifaceClick;
    }

    @NonNull
    @Override
    public ViewHolderHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_list_history, parent, false);
        return new ViewHolderHistory(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderHistory holder, final int position) {
        final String ip = mListIP.get(position);

        TextView tv_ip = holder.itemView.findViewById(R.id.tv_ip);
        ImageView btn_hapus = holder.itemView.findViewById(R.id.btn_hapus);
        tv_ip.setText(ip);

        tv_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIfaceClick.OnClickHistory(ip);
            }
        });
        btn_hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIfaceClick.OnDeleteHistory(ip);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListIP.size();
    }

    static class ViewHolderHistory extends RecyclerView.ViewHolder {
        public ViewHolderHistory(@NonNull View itemView) {
            super(itemView);
        }
    }
}
