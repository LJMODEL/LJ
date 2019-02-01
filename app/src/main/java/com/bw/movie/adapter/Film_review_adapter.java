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
import com.bw.movie.bean.Query_ReviewBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Film_review_adapter extends RecyclerView.Adapter<Film_review_adapter.ViewHolder> {
    private List<Query_ReviewBean.ResultBean> list_review;
    private Context context;
    public Film_review_adapter(List<Query_ReviewBean.ResultBean> list_review, Context context) {
        this.list_review = list_review;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.film_review_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(list_review.get(position).getCommentHeadPic()).into(holder.film_review_image);
        holder.film_review_name.setText(list_review.get(position).getCommentUserName());
        holder.film_review_content.setText(list_review.get(position).getMovieComment());
        //转化时间格式 yyyy-hh-mm
            long commentTime = list_review.get(position).getCommentTime();
            String s = getloToDate(commentTime);
        holder.film_review_data.setText(s+"");
        //看状态是否点过赞
            int isGreat = list_review.get(position).getIsGreat();
            if (isGreat==0){
                holder.film_review_like.setImageResource(R.mipmap.com_icon_praise_default_hdpi);
            }else{
                holder.film_review_like.setImageResource(R.mipmap.com_icon_praise_selected_hdpi);
            }
        holder.film_review_like_number.setText(list_review.get(position).getGreatNum()+"");
        int hotComment = list_review.get(position).getHotComment();
        if (hotComment==0){
            holder.film_review_comment.setImageResource(R.mipmap.com_icon_comment_default_hdpi);
        }else{
            holder.film_review_comment.setImageResource(R.mipmap.com_icon_comment_default_hdpi);
        }
        holder.film_review_comment_number.setText(list_review.get(position).getReplyNum()+"");
    }

    @Override
    public int getItemCount() {
        return list_review.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView film_review_image;
        private TextView film_review_name;
        private TextView film_review_content;
        private TextView film_review_data;
        private ImageView film_review_like;
        private TextView film_review_like_number;
        private ImageView film_review_comment;
        private TextView film_review_comment_number;
        public ViewHolder(View itemView) {
            super(itemView);
            film_review_image = itemView.findViewById(R.id.film_review_image);
            film_review_name = itemView.findViewById(R.id.film_review_name);
            film_review_content = itemView.findViewById(R.id.film_review_content);
            film_review_data = itemView.findViewById(R.id.film_review_data);
            film_review_like = itemView.findViewById(R.id.film_review_like);
            film_review_like_number = itemView.findViewById(R.id.film_review_like_number);
            film_review_comment = itemView.findViewById(R.id.film_review_comment);
            film_review_comment_number = itemView.findViewById(R.id.film_review_comment_number);
        }
    }
    /**
     * 转换时间的
     *
     * @param lo
     * @return
     */
    public static String getloToDate(long lo) {
        Date date = new Date(lo);
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
        return sd.format(date);
    }
}
