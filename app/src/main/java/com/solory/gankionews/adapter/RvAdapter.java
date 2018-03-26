package com.solory.gankionews.adapter;
/*
 *
 * Created by 黄伟杰 on 2018/3/8.
 */


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.solory.gankionews.R;
import com.solory.gankionews.Util.TimeParse;
import com.solory.gankionews.WebViewActivity;
import com.solory.gankionews.net.NewsResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.MyViewHolder> {
    private Fragment fragment;
    private List<NewsResponse.ResultsBean> resultsBeanList;
    private final TimeParse timeParse = TimeParse.getInstance();
    //Glide用的option
    private RequestOptions options = new RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .priority(Priority.HIGH);

    public RvAdapter(Fragment fragment, List<NewsResponse.ResultsBean> resultsBeanList) {
        this.fragment = fragment;
        this.resultsBeanList = resultsBeanList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(fragment.getContext()).inflate(R.layout.item_news, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        final String url=resultsBeanList.get(position).getUrl();
        if (!TextUtils.isEmpty(url)){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(fragment.getActivity(), WebViewActivity.class);
                    intent.putExtra("web_view_url",url);
                    fragment.getActivity().startActivity(intent);
                }
            });
        }
        holder.tvDesc.setText(resultsBeanList.get(position).getDesc());
        holder.tvPublishedAt.setText(timeParse.getTime(resultsBeanList.get(position).getPublishedAt()));
        Object who = resultsBeanList.get(position).getWho();
        if (who != null) {
            holder.tvWho.setText((String) who);
        }else {
            //防止ViewHolder复用导致上一个tvWho的内容遗留
            holder.tvWho.setText("");
        }

        //处理imageView--------------
        final List<String> imagesUrl = resultsBeanList.get(position).getImages();

        if (imagesUrl == null) {
            //当ViewHolder复用的时候，如果当前返回的图片url为null，为了防止上一个复用的viewHolder图片
            //遗留，要clear并且将图片设置为空。
            Glide.with(fragment).clear(holder.ivImage);
            holder.ivImage.setImageDrawable(null);
            holder.ivImage.setTag(R.id.image_tag, position);
            return;
        }
        final Object tag=holder.ivImage.getTag(R.id.image_tag);
        if (tag!=null&&(int) tag!= position) {
            //如果tag不是Null,并且同时tag不等于当前的position。
            //说明当前的viewHolder是复用来的
            //Cancel any pending loads Glide may have for the view
            //and free any resources that may have been loaded for the view.
            Glide.with(fragment).clear(holder.ivImage);
        }
        final String imageUrl = imagesUrl.get(0);
        Glide.with(fragment)
                .load(imageUrl + "?imageView2/0/w/100")
                .apply(options)
                .into(holder.ivImage);
        //给ImageView设置唯一标记。
        holder.ivImage.setTag(R.id.image_tag, position);
    }

    @Override
    public int getItemCount() {
        return resultsBeanList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_desc)
        TextView tvDesc;
        @BindView(R.id.tv_who)
        TextView tvWho;
        @BindView(R.id.tv_publishedAt)
        TextView tvPublishedAt;
        @BindView(R.id.iv_image)
        ImageView ivImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
