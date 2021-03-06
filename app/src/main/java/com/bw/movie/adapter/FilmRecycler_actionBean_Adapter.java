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
import com.bw.movie.bean.FilmRecycler_actionBean;

import java.util.List;

public class FilmRecycler_actionBean_Adapter extends RecyclerView.Adapter<FilmRecycler_actionBean_Adapter.Viewholder> {
    private List<FilmRecycler_actionBean.ResultBean> result;
    private Context context;


    public FilmRecycler_actionBean_Adapter(List<FilmRecycler_actionBean.ResultBean> result, Context context) {
        this.result = result;
        this.context = context;
    }

    @NonNull
    @Override
    public FilmRecycler_actionBean_Adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.film_recycler_item, null);
        FilmRecycler_actionBean_Adapter.Viewholder viewholder = new FilmRecycler_actionBean_Adapter.Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilmRecycler_actionBean_Adapter.Viewholder holder, final int position) {
        Glide.with(context).load(result.get(position).getImageUrl()).into(holder.film_image);
        holder.text_cinema_flow1.setText(result.get(position).getName());
        holder.film_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setOnitem!=null){
                    setOnitem.setlocition(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView film_image;
        private TextView text_cinema_flow1;
        public Viewholder(View itemView) {
            super(itemView);
            film_image=itemView.findViewById(R.id.film_image);
            text_cinema_flow1=itemView.findViewById(R.id.text_cinema_flow1);
        }
    }
    public interface setOnItem{
        void setlocition(int position);
    }
    private FilmRecycler_actionBean_Adapter.setOnItem setOnitem;
    public void setOnItemClick(FilmRecycler_actionBean_Adapter.setOnItem setOnitem){
        this.setOnitem=setOnitem;
    }
}
