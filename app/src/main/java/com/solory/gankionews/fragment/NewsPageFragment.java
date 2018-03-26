package com.solory.gankionews.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
public class NewsPageFragment extends Fragment implements IGetNewsContract.View {

    private static final String typeTag = "tag";
    //当前翻到的页数
    private int pageCount=0;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;
    Unbinder unbinder;

    //Fragment的类型
    private String type;
    //请求News数据的Presenter
    IGetNewsContract.Presenter presenter;
    //recyclerView适配器
    RvAdapter rvAdapter;
    //ResultBean数组
    List<NewsResponse.ResultsBean> list;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            type = getArguments().getString(typeTag);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_android, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        initView();
        //请求一下网络
        presenter.doGetNews(type, 10, ++pageCount);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    /**
     * ------------------------------以上为Fragment生命周期函数-----------------------------
     */

    private void initData() {
        list=new ArrayList<>();
        presenter=new GetNewsPresenter(this);
    }

    private void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        rvAdapter=new RvAdapter(this,list);
        recyclerView.setAdapter(rvAdapter);

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                recyclerView.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                //下滑加载新的数据的时候传入++pageCount
                presenter.doGetNews(type, 10, ++pageCount);
            }
        });
    }
    public NewsPageFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance(String type) {

        Log.d("****** ","重新加载了"+type);
        Bundle args = new Bundle();
        args.putString(typeTag, type);
        NewsPageFragment fragment = new NewsPageFragment();
        fragment.setArguments(args);

        return fragment;
    }

    //当网络请求成功之后，数据返回，Presenter会调用这个方法
    //用来在View层更新数据
    @Override
    public void onGetSucceed(NewsResponse newsResponse) {
        list.addAll(newsResponse.getResults());
        recyclerView.notifyItemInserted(list,10*pageCount);
        
        //recyclerView加载完成
        recyclerView.loadMoreComplete();
    }

    @Override
    public void onError() {
        if (getContext()!=null){
            Toast.makeText(getContext(), "出错了...", Toast.LENGTH_SHORT).show();
        }
    }
    public String getType() {
        return type;
    }

}
