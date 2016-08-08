package com.marno.gameclient.module.main;


import android.os.Bundle;
import android.view.View;

import com.marno.gameclient.R;
import com.marno.gameclient.adapter.common.RecyclerAdapter;
import com.marno.gameclient.adapter.common.RecyclerAdapterHelper;
import com.marno.gameclient.base.BaseFragment;
import com.marno.gameclient.data.models.BaseVideoEntity;
import com.marno.gameclient.data.models.VideoEntity;
import com.marno.gameclient.data.repository.VideoListEntity;
import com.marno.gameclient.data.repository.VideoRepo;
import com.marno.gameclient.data.retrofit.DefaultSubscriber;
import com.marno.gameclient.utils.GlideUtil;
import com.marno.gameclient.utils.LayoutHelper;
import com.marno.gameclient.widgets.MultipleStatusView;
import com.marno.gameclient.widgets.mRecyclerview.XRecyclerView;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 视频列表
 */
public class VideoFragment extends BaseFragment {

    @BindView(R.id.content_view)
    XRecyclerView mRecyclerView;
    @BindView(R.id.msv_layout)
    MultipleStatusView mMsvLayout;
    private RecyclerAdapter<VideoEntity> mAdapter;

    private int mPage = 1;

    public static VideoFragment newInstance() {
        return new VideoFragment();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if (hidden) {
//            JCVideoPlayer.releaseAllVideos();
//        }
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_msv_recycler;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        mRecyclerView.setLayoutManager(LayoutHelper.getLinearLayout(mContext));
        mRecyclerView.setLoadingListener(this);

        setAdapter();

    }

    @Override
    protected void initData() {
        initVideoListData(mPage);
    }

    private void initVideoListData(int page) {
        VideoRepo.getIns().getVideoList(page, mContext)
                .compose(bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new DefaultSubscriber<BaseVideoEntity<VideoListEntity>>() {
                    @Override
                    public void _onNext(BaseVideoEntity<VideoListEntity> entity) {
                        if (isRefresh) mAdapter.clear();
                        VideoListEntity videos = entity.returnData;
                        List<VideoEntity> videoList = videos.videoList;
                        mAdapter.addAll(videoList);
                    }

                    @Override
                    public void onCompleted() {
                        mRecyclerView.loadMoreComplete();
                        mRecyclerView.refreshComplete();
                    }
                });
    }

    private void setAdapter() {
        mAdapter = new RecyclerAdapter<VideoEntity>(mContext, R.layout.item_video) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, VideoEntity item) {
                helper.setText(R.id.tv_pubtime, item.videoAddtime)
                        .setText(R.id.tv_watchNum, "播放次数：" + String.valueOf(item.videoViews))
                        .setText(R.id.tv_sourceName, item.videoProviders);

                JCVideoPlayerStandard jcPlayer = (JCVideoPlayerStandard) helper.getItemView().findViewById(R.id.video_view);
                jcPlayer.setUp(item.videoUrl, JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, item.videoTitle);
                GlideUtil.loadImg(item.videoThumb, jcPlayer.thumbImageView);
            }
        };

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(LayoutHelper.getHorizontalDivider_6(mContext));
    }

    @Override
    protected View getToolBarView() {
        return null;
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPage = 1;
        initVideoListData(mPage);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        initVideoListData(++mPage);
    }
}
