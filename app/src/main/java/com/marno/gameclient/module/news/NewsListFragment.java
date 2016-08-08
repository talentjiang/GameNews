package com.marno.gameclient.module.news;


import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateInterpolator;

import com.marno.gameclient.R;
import com.marno.gameclient.adapter.NewsAdapter;
import com.marno.gameclient.adapter.common.RecyclerAdapter;
import com.marno.gameclient.adapter.common.RecyclerAdapterHelper;
import com.marno.gameclient.base.BaseFragment;
import com.marno.gameclient.base.BaseNewsEntity;
import com.marno.gameclient.data.models.NewsEntity;
import com.marno.gameclient.data.repository.NewsRepo;
import com.marno.gameclient.data.retrofit.DefaultSubscriber;
import com.marno.gameclient.utils.C;
import com.marno.gameclient.utils.LayoutHelper;
import com.marno.gameclient.utils.MLog;
import com.marno.gameclient.widgets.MultipleStatusView;
import com.marno.gameclient.widgets.mRecyclerview.XRecyclerView;
import com.trello.rxlifecycle.FragmentEvent;

import java.util.List;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * 文章分类展示列表
 */
public class NewsListFragment extends BaseFragment {

    @BindView(R.id.content_view)
    XRecyclerView mRecyclerView;
    @BindView(R.id.msv_layout)
    MultipleStatusView mMsvLayout;

    private int mCateId;
    private int mPage;

    private RecyclerAdapter mAdapter;
    private Realm mRealm;
    private RealmResults<NewsEntity> mRealmResults;

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
        getBundle();
        mRealm = Realm.getDefaultInstance();
        mRealmResults = mRealm.where(NewsEntity.class).equalTo("cateId", mCateId).findAll();
        mRealmResults.addChangeListener(mRealeListener);

        mMsvLayout.loading();
        mRecyclerView.setLoadingListener(this);
        mRecyclerView.setLayoutManager(LayoutHelper.getLinearLayout(mContext));
        mRecyclerView.addItemDecoration(LayoutHelper.getHorizontalDivider_6(mContext));

        setAdapter();
    }

    @Override
    protected void initData() {

//        if (mRealmResults.isEmpty()) {
        loadNewsData(mPage);
//        } else {
//            mMsvLayout.content();
//            mAdapter.addAllAt(0, mRealmResults);
//        }
    }

    private void getBundle() {
        mCateId = getArguments().getInt(C.CATE_ID);
    }

    private void loadNewsData(int page) {
        NewsRepo.getIns().getNewsList(mCateId, page)
                .compose(bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new DefaultSubscriber<BaseNewsEntity<List<NewsEntity>>>() {
                    @Override
                    public void _onNext(BaseNewsEntity<List<NewsEntity>> entity) {
//                        saveToRealm(entity.data);
                        if (isRefresh)mAdapter.clear();
                        mAdapter.addAll(entity.data);
                    }

                    @Override
                    public void onCompleted() {
                        mMsvLayout.content();
                        mRecyclerView.loadMoreComplete();
                        mRecyclerView.refreshComplete();
                    }
                });
    }

    private void setAdapter() {
        mAdapter = new NewsAdapter(mContext) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, NewsEntity item) {
                super.convert(helper, item);
                int position = helper.getAdapterPosition();
                int size = mAdapter.getSize();
                if (position == size - 1) {
                    mPage = Integer.parseInt(item.getId().trim());
                }
            }
        };

        SlideInBottomAnimationAdapter slideAdapter = new SlideInBottomAnimationAdapter(mAdapter);
        slideAdapter.setFirstOnly(true);
        slideAdapter.setDuration(500);
        slideAdapter.setInterpolator(new AnticipateInterpolator(1f));

        mRecyclerView.setAdapter(slideAdapter);
    }

    //保存到realm中
    private void saveToRealm(List<NewsEntity> entities) {
        mRealm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                for (int i = 0, size = entities.size(); i < size; i++) {
                    NewsEntity entity = entities.get(i);
                    //如果已经有该条数据则不保存
                    if (realm.where(NewsEntity.class)
                            .equalTo("cateId", mCateId)
                            .contains("id", entity.getId()).count() == 0) {
                        MLog.i("realm 数据库中添加数据>>>id" + entity.getId());
                        NewsEntity newsEntity = realm.createObject(NewsEntity.class);
                        newsEntity.setId(entity.getId());
                        newsEntity.setTitle(entity.getTitle());
                        //realm保存图片的时候有些问题
//                        newsEntity.setImg(entity.getImg());
                        newsEntity.setCreate_time(entity.getCreate_time());
                        newsEntity.setSource_id(entity.getSource_id());
                        newsEntity.setSource_name(entity.getSource_name());
                        newsEntity.setStatus(entity.getStatus());
                        newsEntity.setTemplate_id(entity.getTemplate_id());
                        newsEntity.setUrl(entity.getUrl());
                        newsEntity.setCateId(mCateId);
                    }
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                MLog.i("realm 添加数据后>>> size:" + mRealmResults.size());
            }
        });
    }

    //监听realm查询结果的变化，如果realm数据库更新了，该结果会自动变化为更新后的结果
    RealmChangeListener mRealeListener = new RealmChangeListener<RealmResults<NewsEntity>>() {
        @Override
        public void onChange(RealmResults<NewsEntity> element) {
            MLog.i("realm >>> 监听器中size:" + element.size());
            mAdapter.addAllAt(0, element);
        }
    };

    @Override
    public void onRefresh() {
        super.onRefresh();
        mPage = 0;
        loadNewsData(mPage);
    }

    @Override
    public void onLoadMore() {
        super.onLoadMore();
        loadNewsData(mPage);
    }
}



