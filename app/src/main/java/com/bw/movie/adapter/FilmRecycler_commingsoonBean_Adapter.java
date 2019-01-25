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
import com.bw.movie.bean.FilmRecycler_actionBean;
import com.bw.movie.bean.FilmRecycler_commingsoonBean;

import java.util.List;

public class FilmRecycler_commingsoonBean_Adapter extends RecyclerView.Adapter<FilmRecycler_commingsoonBean_Adapter.Viewholder> {
    List<FilmRecycler_commingsoonBean.ResultBean> result;
    private Context context;


    public FilmRecycler_commingsoonBean_Adapter(List<FilmRecycler_commingsoonBean.ResultBean> result, Context context) {
        this.result = result;
        this.context = context;
    }

    @NonNull
    @Override
    public FilmRecycler_commingsoonBean_Adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.film_recycler_item, null);
        FilmRecycler_commingsoonBean_Adapter.Viewholder viewholder = new FilmRecycler_commingsoonBean_Adapter.Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilmRecycler_commingsoonBean_Adapter.Viewholder holder, int position) {
        Glide.with(context).load(result.get(position).getImageUrl()).into(holder.film_image);
        holder.film_name.setText(result.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView film_image;
        private TextView film_name;
        public Viewholder(View itemView) {
            super(itemView);
            film_image=itemView.findViewById(R.id.film_image);
            film_name=itemView.findViewById(R.id.film_name);
        }
    }
}
