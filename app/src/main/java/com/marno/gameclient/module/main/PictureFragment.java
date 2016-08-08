package com.marno.gameclient.module.main;


import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateInterpolator;

import com.marno.gameclient.R;
import com.marno.gameclient.adapter.common.RecyclerAdapter;
import com.marno.gameclient.adapter.common.RecyclerAdapterHelper;
import com.marno.gameclient.base.BaseFragment;
import com.marno.gameclient.data.models.ImageEntity;
import com.marno.gameclient.data.models.ImgListEntity;
import com.marno.gameclient.data.repository.ImageRepo;
import com.marno.gameclient.data.retrofit.DefaultSubscriber;
import com.marno.gameclient.utils.LayoutHelper;
import com.marno.gameclient.widgets.MultipleStatusView;
import com.marno.gameclient.widgets.mRecyclerview.XRecyclerView;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import butterknife.BindView;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;


/**
 * Created by marno on 2016/4/11/09:03.
 */
public class PictureFragment extends BaseFragment {

    @BindView(R.id.content_view)
    XRecyclerView mRecyclerView;
    @BindView(R.id.msv_layout)
    MultipleStatusView mMsvLayout;
    private RecyclerAdapter<ImageEntity> mAdapter;

    private int mPageNum = 1;


    public static PictureFragment newInstance() {
        return new PictureFragment();
    }


    @Override
    protected int getLayout() {
        return R.layout.layout_msv_recycler;
    }


    @Override
    protected View getToolBarView() {
        return null;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(LayoutHelper.getVerticalStagLayout());
        mRecyclerView.setLoadingListener(this);

        setAdapter();
    }

    @Override
    protected void initData() {
        getImageListData(mPageNum);
    }

    private void getImageListData(int page) {
        ImageRepo.getIns().getImageList(page)
                .compose(bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new DefaultSubscriber<ImgListEntity>() {
                    @Override
                    public void _onNext(ImgListEntity entity) {
                        if (isRefresh) mAdapter.clear();
                        List<ImageEntity> imgList = entity.results;
                        mAdapter.addAll(imgList);
                    }

                    @Override
                    public void onCompleted() {
                        mRecyclerView.loadMoreComplete();
                        mRecyclerView.refreshComplete();
                    }
                });
    }


    private void setAdapter() {
        mAdapter = new RecyclerAdapter<ImageEntity>(mContext, R.layout.item_image) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, ImageEntity item) {
                helper.setImageUrl(R.id.iv_meizi, item.getUrl(), false);
            }
        };

        SlideInBottomAnimationAdapter slideAdapter = new SlideInBottomAnimationAdapter(mAdapter);
        slideAdapter.setFirstOnly(true);
        slideAdapter.setDuration(500);
        slideAdapter.setInterpolator(new AnticipateInterpolator(1f));

        mRecyclerView.setAdapter(slideAdapter);
    }


    @Override
    public void onRefresh() {
        super.onRefresh();
        mPageNum = 1;
        getImageListData(mPageNum);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        getImageListData(++mPageNum);
    }
}
