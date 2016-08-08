package com.marno.gameclient.module.main;


import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.marno.gameclient.R;
import com.marno.gameclient.base.BaseFragment;
import com.marno.gameclient.module.news.NewsListFragment;
import com.marno.gameclient.utils.C;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;


/**
 * Created by marno on 2016/4/11/09:03.
 * 新闻列表
 */
public class NewsFragment extends BaseFragment {

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        ViewPager mVpInfoList = (ViewPager) view.findViewById(R.id.vp_infolist);
        SmartTabLayout tabLayout = (SmartTabLayout) view.findViewById(R.id.viewpagertab);

        //设置Fragment的绑定参数
        Bundle HOME_Bundle = new Bundle();
        HOME_Bundle.putInt(C.CATE_ID, 0);

        Bundle NEWS_Bundle = new Bundle();
        NEWS_Bundle.putInt(C.CATE_ID, 1);

        Bundle ZATAN_Bundle = new Bundle();
        ZATAN_Bundle.putInt(C.CATE_ID, 2);

        Bundle TEST_Bundle = new Bundle();
        TEST_Bundle.putInt(C.CATE_ID,3);

        Bundle PRE_Bundle = new Bundle();
        PRE_Bundle.putInt(C.CATE_ID, 4);

        Bundle ORIGINAL_Bundle = new Bundle();
        ORIGINAL_Bundle.putInt(C.CATE_ID, 5);

        Bundle PANDIAN_Bundle = new Bundle();
        PANDIAN_Bundle.putInt(C.CATE_ID, 6);


        //将viewpager与TabLayout绑定
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), FragmentPagerItems.with(getActivity())
                .add(R.string.recommend, NewsListFragment.class, HOME_Bundle)
                .add(R.string.hot, NewsListFragment.class, NEWS_Bundle)
                .add(R.string.newgame, NewsListFragment.class, ZATAN_Bundle)
                .add(R.string.erciyuan, NewsListFragment.class, PRE_Bundle)
                .add(R.string.product, NewsListFragment.class, ORIGINAL_Bundle)
                .add(R.string.game, NewsListFragment.class, PANDIAN_Bundle)
                .add(R.string.pingce, NewsListFragment.class, TEST_Bundle)
                .create());

        mVpInfoList.setAdapter(adapter);
        tabLayout.setViewPager(mVpInfoList);
    }

    @Override
    protected View getToolBarView() {
        return null;
    }


}
