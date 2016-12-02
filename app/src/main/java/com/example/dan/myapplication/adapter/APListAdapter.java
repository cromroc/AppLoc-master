package com.example.dan.myapplication.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dan.myapplication.R;
import com.example.dan.myapplication.model.AccessPoint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DAN on 26/11/2016.
 */

public class APListAdapter extends RecyclerView.Adapter<APListAdapter.ViewHolder> {

    private List<AccessPoint> items = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ap,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AccessPoint ap = items.get(position);
        holder.ssid.setText(ap.SSID);
        holder.level.setText(ap.level+" dBm");
        holder.mac.setText(ap.MAC);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView ssid,mac,level;

        public ViewHolder(View itemView) {
            super(itemView);
            ssid = (TextView) itemView.findViewById(R.id.item_ssid);
            mac = (TextView) itemView.findViewById(R.id.item_mac);
            level = (TextView) itemView.findViewById(R.id.item_level);
        }
    }

    public void setList(List<AccessPoint> mItems) {
        items.clear();
        items.addAll(mItems);
        notifyDataSetChanged();
    }
}
