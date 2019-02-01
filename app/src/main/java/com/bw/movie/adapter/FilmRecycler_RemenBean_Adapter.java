package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.bean.FilmRecycler_RemenBean;
import com.bw.movie.bean.FilmRecycler_actionBean;

import java.util.List;

public class FilmRecycler_RemenBean_Adapter extends RecyclerView.Adapter<FilmRecycler_RemenBean_Adapter.Viewholder> {
    List<FilmRecycler_RemenBean.ResultBean> result;
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
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        Glide.with (context)
                .load (result.get(position).getImageUrl())
                .apply (RequestOptions.errorOf (R.drawable.zhan))        //加载失败 默认的加载图片
                .apply (RequestOptions.placeholderOf (R.drawable.dong)) //加载中 默认的加载图片
                .into (holder.film_image);
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
            film_image = itemView.findViewById(R.id.film_image);
            text_cinema_flow1 = itemView.findViewById(R.id.text_cinema_flow1);
        }
    }
    public interface setOnItem{
        void setlocition(int position);
    }
    private setOnItem setOnitem;
    public void setOnItemClick(setOnItem setOnitem){
        this.setOnitem=setOnitem;
    }

}
