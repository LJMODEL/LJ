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
import com.bw.movie.bean.Cinema_PingLun_Bean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Cinmea_PingLun_Adapter extends RecyclerView.Adapter<Cinmea_PingLun_Adapter.Viewholder> {

    private List<Cinema_PingLun_Bean.ResultBean> list;
    private Context context;
    private String time;

    public Cinmea_PingLun_Adapter(Context context) {
        this.context = context;
    }

    public void setList(List<Cinema_PingLun_Bean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.details_itme, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Viewholder holder, final int position) {

        RequestOptions mRequestOptions = RequestOptions.circleCropTransform();
        Glide.with(context)
                .load(list.get(position)
                        .getCommentHeadPic())
                .apply(mRequestOptions)
                .into(holder.imageView);
        holder.name.setText(list.get(position).getCommentUserName());
        holder.content.setText(list.get(position).getCommentContent());
        long commentTime = list.get(position).getCommentTime();
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //如果它本来就是long类型的,则不用写这一步
        Date date = new Date(commentTime);
        res = simpleDateFormat.format(date);
        holder.teme.setText(res+ "");
        holder.num.setText(list.get(position).getGreatNum() + "");

        if (list.get(position).getIsGreat() == 0) {
            holder.give.setImageResource(R.mipmap.com_icon_praise_default_hdpi);
        } else {
            holder.give.setImageResource(R.mipmap.com_icon_praise_selected_hdpi);
        }


        holder.give.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int commentId = list.get(position).getCommentId();
                dianItem.Item(commentId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView imageView, give;
        private TextView name, content, teme, num;

        public Viewholder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.dead);
            name = itemView.findViewById(R.id.name);
            content = itemView.findViewById(R.id.content);
            teme = itemView.findViewById(R.id.teme);
            num = itemView.findViewById(R.id.num);
            give = itemView.findViewById(R.id.give);

        }
    }


    private DianItem dianItem;

    public interface DianItem {
        void Item(int i);
    }

    public void setDianItem(DianItem dianItem) {
        this.dianItem = dianItem;
    }


}
