package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.bean.Film_details_QueryBean;

import java.util.List;
import java.util.Random;

import cn.jzvd.JZVideoPlayerStandard;

public class Fore_show_picture_adapter extends RecyclerView.Adapter<Fore_show_picture_adapter.ViewHolder> {
    private Film_details_QueryBean.ResultBean list;
    private Context context;


    public Fore_show_picture_adapter(Film_details_QueryBean.ResultBean list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fore_show_picture_item, parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        Random random=new Random();
        int height = random.nextInt(300) + 300;
        layoutParams.height=height;
        holder.itemView.setLayoutParams(layoutParams);
        List<String> posterList = list.getPosterList();
        for (int p = 0; p < posterList.size(); p++) {
            Glide.with(context).load(posterList.get(p)).into(holder.fore_show_image);
        }
        /*List<Film_details_QueryBean.ResultBean.ShortFilmListBean> shortFilmList = list.getShortFilmList();
            String imageUrl = shortFilmList.get(position).getImageUrl();*/

    }

    @Override
    public int getItemCount() {
        return list.getShortFilmList().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView fore_show_image;
        public ViewHolder(View itemView) {
            super(itemView);
            fore_show_image=itemView.findViewById(R.id.fore_show_image);
        }
    }

}
