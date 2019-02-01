package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.activity.Cinema_Activity;
import com.bw.movie.bean.Recommended_Bean;

import java.util.List;

public class Recommended_Adapter extends RecyclerView.Adapter<Recommended_Adapter.Viewholder> {

    private List<Recommended_Bean.ResultBean> list;
    private Context context;

    public Recommended_Adapter(List<Recommended_Bean.ResultBean> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.recommended_item, null);
        Viewholder viewholder = new Viewholder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        RequestOptions requestOptions = new RequestOptions()
                .override(100, 100);
        Glide.with(context)
                .load(list.get(position)
                        .getLogo())
                .apply(requestOptions)
                .into(holder.imageView);
        holder.tile.setText(list.get(position).getName());
        holder.address.setText(list.get(position).getAddress());

        if (list.get(position).getFollowCinema() == 2) {
            holder.xihuan.setImageResource(R.mipmap.com_icon_collection_default_hdpi);
        } else {
            holder.xihuan.setImageResource(R.mipmap.com_icon_collection_selected_hdpi);
        }


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = list.get(position).getId();
                Intent intent = new Intent(context, Cinema_Activity.class);
                intent.putExtra("cinemaId", id);
                context.startActivity(intent);
            }
        });
        holder.xihuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = list.get(position).getId();
                int followCinema = list.get(position).getFollowCinema();
                dianItem.Item(id,followCinema);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        private ImageView imageView, xihuan;
        private TextView tile, address, distance;

        public Viewholder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            tile = itemView.findViewById(R.id.tile);
            address = itemView.findViewById(R.id.dili);
            distance = itemView.findViewById(R.id.jili);
            xihuan = itemView.findViewById(R.id.xihuan);
        }
    }


    private LikeItem dianItem;

    public interface LikeItem {
        void Item(int i,int j);
    }

    public void setDianItem(LikeItem dianItem) {
        this.dianItem = dianItem;
    }


}
