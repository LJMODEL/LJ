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
import com.bw.movie.bean.FilmRecycler_RemenBean;

import java.util.List;

public class FilmRecycler_RemenBean_Adapter extends RecyclerView.Adapter<FilmRecycler_RemenBean_Adapter.Viewholder> {
    private List<FilmRecycler_RemenBean.ResultBean> result;
    private Context context;


    public FilmRecycler_RemenBean_Adapter(List<FilmRecycler_RemenBean.ResultBean> result, Context context) {
        this.result = result;
        this.context = context;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.film_recycler_item, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
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
