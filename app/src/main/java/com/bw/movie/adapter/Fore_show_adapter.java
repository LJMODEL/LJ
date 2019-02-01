package com.bw.movie.adapter;

import android.content.Context;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.bean.Film_details_QueryBean;

import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

import static android.content.Context.SENSOR_SERVICE;

public class Fore_show_adapter extends RecyclerView.Adapter<Fore_show_adapter.ViewHolder> {
    private List<Film_details_QueryBean.ResultBean.ShortFilmListBean> shortFilmList;
    private Context context;


    public Fore_show_adapter(List<Film_details_QueryBean.ResultBean.ShortFilmListBean> shortFilmList, Context context) {
        this.shortFilmList = shortFilmList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.fore_show_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //设置视频路径和视频标题
        String videoUrl = shortFilmList.get(position).getVideoUrl();
        holder.dialog_advance_notice_item_vidoo.setUp(videoUrl,0);
        //设置缩略图
        //GlideUtils.showImageView(mContext,R.mipmap.ic_launcher,mVidooUrl.get(position).getImageUrl(),holder.item_vidoo.thumbImageView);
        //自动播放
//        holder.item_vidoo.startVideo();

        //重力感应切换横竖屏
        //sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        //sensorEventListener = new JZVideoPlayer.JZAutoFullscreenListener();

        //设置容器内播放器高,解决黑边（视频全屏）
    }

    @Override
    public int getItemCount() {
        return shortFilmList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private JZVideoPlayerStandard dialog_advance_notice_item_vidoo;
        public ViewHolder(View itemView) {
            super(itemView);
            dialog_advance_notice_item_vidoo=itemView.findViewById(R.id.dialog_advance_notice_item_vidoo);
        }
    }
}
