package com.solory.gankionews.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.solory.gankionews.IContract.IGetNewsContract;
import com.solory.gankionews.Presenter.GetNewsPresenter;
import com.solory.gankionews.R;
import com.solory.gankionews.adapter.RvAdapter;
import com.solory.gankionews.net.NewsResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PictureFragment extends Fragment implements IGetNewsContract.View {
    private RequestOptions glideOptions = new RequestOptions()
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher)
            .priority(Priority.HIGH);
    //图片列表适配器
    class PictureRvAdapter extends RecyclerView.Adapter<PictureRvAdapter.MyViewHolder> {
        Fragment fragment;
        PictureRvAdapter(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(getActivity()).inflate(R.layout.item_beauty_pic,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            if (holder==null){
                return;
            }
            final String imageUrl=list.get(position).getUrl();
            final Object tag=holder.imgBeauty.getTag(R.id.image_tag);
            if (imageUrl!=null){
                if (tag!=null&&(int) tag!= position) {
                    //如果tag不是Null,并且同时tag不等于当前的position。
                    //说明当前的viewHolder是复用来的
                    //Cancel any pending loads Glide may have for the view
                    //and free any resources that may have been loaded for the view.
                    Glide.with(fragment).clear(holder.imgBeauty);
                }
                Glide.with(fragment).load(imageUrl).into(holder.imgBeauty);
                //给ImageView设置唯一标记。
                holder.imgBeauty.setTag(R.id.image_tag, position);
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.img_beauty)
            ImageView imgBeauty;
            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this,itemView);
            }
        }
    }

    @BindView(R.id.rv_beautyPic)
    XRecyclerView rvBeautyPic;
    Unbinder unbinder;
    //当前翻到的页数
    private int pageCount=0;
    private IGetNewsContract.Presenter presenter;
    private List<NewsResponse.ResultsBean> list;
    private PictureRvAdapter rvAdapter;
    public PictureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_picture, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        presenter.doGetNews("福利",3,++pageCount);
        return view;
    }

    private void initData() {
        list=new ArrayList<>();
        presenter=new GetNewsPresenter(this);
    }

    private void initView() {
        rvBeautyPic.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAdapter=new PictureRvAdapter(this);
        rvBeautyPic.setAdapter(rvAdapter);

        rvBeautyPic.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                rvBeautyPic.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                //下滑加载新的数据的时候传入++pageCount
                presenter.doGetNews("福利", 3, ++pageCount);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    
    @Override
    public void onGetSucceed(NewsResponse newsResponse) {
        list.addAll(newsResponse.getResults());

        rvBeautyPic.notifyItemInserted(list,3*pageCount);

        //rvBeautyPic加载完成
        rvBeautyPic.loadMoreComplete();
    }

    @Override
    public void onError() {
        
    }
}
