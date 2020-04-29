package com.horizontal.java.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.horizontal.java.R;

import java.util.List;

public class HorizontalRecyclerAdapter extends RecyclerView.Adapter<HorizontalRecyclerAdapter.HorizontalRecyclerItemViewHolder> {

    private List<String> data;
    private RequestManager requestManager;

    public HorizontalRecyclerAdapter(List<String> data,RequestManager requestManager) {
        this.data = data;
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public HorizontalRecyclerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HorizontalRecyclerItemViewHolder(View.inflate(parent.getContext(),R.layout.item_recycler_view,null));
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalRecyclerItemViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();
        requestManager.load(data.get(position)).apply(requestOptions).into(holder.coverIv);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    static class HorizontalRecyclerItemViewHolder extends RecyclerView.ViewHolder{

        private ImageView coverIv;

        public HorizontalRecyclerItemViewHolder(@NonNull View itemView) {
            super(itemView);
            coverIv = itemView.findViewById(R.id.iv_cover);
        }
    }
}
