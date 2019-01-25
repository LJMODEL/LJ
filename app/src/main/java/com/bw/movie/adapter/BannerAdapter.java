package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;

/**
 * Author 汪巍
 * DATE 2019/1/25
 */
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {

    private Context mContext;
    private int[] imageList = {R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground, R.mipmap.ic_launcher,
            R.mipmap.icon1, R.mipmap.icon2};

    private String[] mFilm = {"宝贝儿", "嗝嗝老师", "铁血战士", "暮光巴黎", "雪怪大冒险"};

    //private String[] mTime = {"104分钟", "117分钟", "113分钟", "106分钟", "101分钟"};

    public BannerAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.item_image_name.setText(mFilm[i]);
        //viewHolder.item_image_time.setText(mTime[i]);
        viewHolder.item_banner_image.setBackgroundResource(imageList[i]);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView item_banner_image;
        private final TextView item_image_name;
        //private final TextView item_image_time;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_banner_image = itemView.findViewById(R.id.item_banner_image);
            item_image_name = itemView.findViewById(R.id.item_image_name);
            //item_image_time = itemView.findViewById(R.id.item_image_time);
        }
    }

    public interface OnItemClick {
        //void clickItem(int )
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}
