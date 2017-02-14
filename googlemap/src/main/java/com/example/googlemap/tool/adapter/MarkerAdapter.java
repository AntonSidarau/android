package com.example.googlemap.tool.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.googlemap.R;
import com.example.googlemap.domain.Marker;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarkerAdapter extends RecyclerView.Adapter<MarkerAdapter.MarkerViewHolder> {
    private List<Marker> markers;

    public MarkerAdapter(List<Marker> markers) {
        this.markers = markers;
    }

    @Override
    public MarkerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_marker, null, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        return new MarkerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MarkerViewHolder holder, int position) {
        Marker marker = markers.get(position);
        holder.tvName.setText(marker.getName());
    }

    @Override
    public int getItemCount() {
        return markers.size();
    }

    static class MarkerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_marker_name) TextView tvName;

        public MarkerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
