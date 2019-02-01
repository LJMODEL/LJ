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
import com.bw.movie.bean.FilmRecycler_commingsoonBean;

import java.util.List;

public class FilmDateils_commingsoonBean_Adapter extends RecyclerView.Adapter<FilmDateils_commingsoonBean_Adapter.Viewholder> {
    List<FilmRecycler_commingsoonBean.ResultBean> result;
    private Context context;



    public FilmDateils_commingsoonBean_Adapter(List<FilmRecycler_commingsoonBean.ResultBean> result, Context context) {
        this.result = result;
        this.context = context;
    }

    @NonNull
    @Override
    public FilmDateils_commingsoonBean_Adapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.movie_list_recy_item, null);
        FilmDateils_commingsoonBean_Adapter.Viewholder viewholder = new FilmDateils_commingsoonBean_Adapter.Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilmDateils_commingsoonBean_Adapter.Viewholder holder, final int position) {
        Glide.with(context)
                .load(result.get(position).getImageUrl())
                .apply(RequestOptions.errorOf(R.drawable.zhan))        //加载失败 默认的加载图片
                .apply(RequestOptions.placeholderOf(R.drawable.dong)) //加载中 默认的加载图片
                .into(holder.movie_item_image);
        holder.movie_item_title.setText(result.get(position).getName());
        int followMovie = result.get(position).getFollowMovie();
        if (followMovie==1){
            holder.movie_item_love.setImageResource(R.mipmap.com_icon_collection_selected_hdpi);
        }else{
            holder.movie_item_love.setImageResource(R.mipmap.com_icon_collection_default_hdpi);
        }
        holder.movie_item_content.setText(result.get(position).getSummary()+"");
        holder.movie_item_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setonClick!=null){
                    setonClick.setAttention(position);
                }
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setonClick!=null){
                    setonClick.setData(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        private ImageView movie_item_image;
        private TextView movie_item_title;
        private ImageView movie_item_love;
        private TextView movie_item_content;

        public Viewholder(View itemView) {
            super(itemView);
            movie_item_image = itemView.findViewById(R.id.movie_item_image);
            movie_item_title = itemView.findViewById(R.id.movie_item_title);
            movie_item_love = itemView.findViewById(R.id.movie_item_love);
            movie_item_content = itemView.findViewById(R.id.movie_item_content);
        }
    }
    public interface setOnClick{
        void setAttention(int position);
        void setData(int position);
    }
    private FilmDateils_commingsoonBean_Adapter.setOnClick setonClick;
    public void setOnClickLinsenter(FilmDateils_commingsoonBean_Adapter.setOnClick setonClick){
        this.setonClick=setonClick;
    }
}
