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
import com.bw.movie.bean.Cinema_Bean;
import com.bw.movie.bean.Movie_Scheduling_Bean;

import java.util.List;

public class Schedule_Adapter extends RecyclerView.Adapter<Schedule_Adapter.Viewholder> {

    private List<Movie_Scheduling_Bean.ResultBean> list;
    private Context context;

    public Schedule_Adapter(List<Movie_Scheduling_Bean.ResultBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setList(List<Movie_Scheduling_Bean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.schedule_itme, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {
      ;
        holder.movie_here.setText(list.get(position).getScreeningHall());
        holder.movie_stra.setText(list.get(position).getBeginTime()+"");
        holder.cinema_shi.setText(list.get(position).getEndTime()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private TextView movie_here, movie_stra,cinema_shi,price;

        public Viewholder(View itemView) {
            super(itemView);
            movie_here = itemView.findViewById(R.id.movie_here);
            movie_stra = itemView.findViewById(R.id.movie_stra);
            cinema_shi = itemView.findViewById(R.id.cinema_shi);
            price = itemView.findViewById(R.id.price);

        }
    }
}
