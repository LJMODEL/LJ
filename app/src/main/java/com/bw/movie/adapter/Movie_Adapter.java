package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.Focus_Bean;

import java.util.List;

public class Movie_Adapter extends RecyclerView.Adapter<Movie_Adapter.Viewholder> {

    private List<Focus_Bean.ResultBean> list;
    private Context context;

    public Movie_Adapter(List<Focus_Bean.ResultBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setList(List<Focus_Bean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.movie_itmr, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {
        Glide.with(context)
                .load(list.get(position)
                        .getImageUrl())
                .into(holder.imageView);
        holder.tile.setText(list.get(position).getName());
        holder.address.setText(list.get(position).getSummary());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView tile, address;

        public Viewholder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            tile = itemView.findViewById(R.id.tile);
            address = itemView.findViewById(R.id.shu);

        }
    }
}
