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
 * Author 李凯
 * DATE 2019/1/25
 */
public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {

    private Context mContext;
    private int[] imageList = {R.mipmap.iicon1, R.mipmap.iicon2, R.mipmap.iicon3,
            R.mipmap.iicon4, R.mipmap.iicon5, R.mipmap.iicon6, R.mipmap.iicon7, R.mipmap.iicon8, R.mipmap.iicon9, R.mipmap.iicon10};

    private String[] mFilm = {"宝贝儿", "胡桃子","功夫","暮光巴黎", "铁血战士", "为你写诗", "我的间谍前男友", "无双", "找到你", "昨日星空"};

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.item_image_name.setText(mFilm[i]);
        viewHolder.item_banner_image.setBackgroundResource(imageList[i]);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClick!=null){
                    onItemClick.clickItem(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView item_banner_image;
        private final TextView item_image_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item_banner_image = itemView.findViewById(R.id.item_banner_image);
            item_image_name = itemView.findViewById(R.id.item_image_name);
        }
    }

    public interface OnItemClick {
        void clickItem(int position);
    }

    private OnItemClick onItemClick;

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }
}
