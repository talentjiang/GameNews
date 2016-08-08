package com.marno.gameclient.module.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioGroup;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;
import com.marno.gameclient.R;
import com.marno.gameclient.base.BaseActivity;
import com.marno.gameclient.data.repository.VideoRepo;
import com.marno.gameclient.utils.ActivityUtil;
import com.marno.gameclient.utils.ToastUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fabtoolbar_toolbar)
    RadioGroup mRdgToolbar;
    @BindView(R.id.fabtoolbar)
    FABToolbarLayout mFabMenu;

    private static final String QUIT_TOAST = "连按两次退出 游戏头条";

    private int isFirstBack;

    public static final int
            MODULE_NEWS = 0,
            MODULE_VIDEO = 1,
            MODULE_PIC = 2,
            MODULE_MINE = 3;

    private NewsFragment mNewsFragment;
    private VideoFragment mVideoFragment;
    private PictureFragment mPictureFragment;
    private MeFragment mMeFragment;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mTransaction;
    private List<Fragment> fragments;


    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected View getToolBarView() {
        return null;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        isFirstBack = 1;

        mFragmentManager = getSupportFragmentManager();
        mTransaction = mFragmentManager.beginTransaction();
        initFragments(savedInstanceState);
        showSelectedTab();
        changeNavigation();
    }


    public void onClick(View view) {
        mFabMenu.show();
        isFirstBack = 0;
    }

    @Override
    protected void initData() {
        VideoRepo.getIns().getUserToken(mContext);
    }

    private final void showSelectedTab() {
        String stringExtra = getIntent().getStringExtra(ActivityUtil.ARG_1);
        String modulePosition = stringExtra == null ? "" + MODULE_NEWS : stringExtra;
        int tabPosition = Integer.parseInt(modulePosition);
        showSelectFragment(tabPosition);
    }

    private final void initFragments(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            createFragment();
            mTransaction = mFragmentManager.beginTransaction();
            for (Fragment f : fragments) {
                mTransaction.add(R.id.llayout, f);
            }
            mTransaction.commit();
        } else {
            restoreFragment();
            hideAllFragments();
            mTransaction.show(fragments.get(MODULE_NEWS)).commit();
        }
    }

    private final void createFragment() {
        mNewsFragment = NewsFragment.newInstance();
        mVideoFragment = VideoFragment.newInstance();
        mPictureFragment = PictureFragment.newInstance();
        mMeFragment = MeFragment.newInstance();
        fragments = Arrays.asList(mNewsFragment, mVideoFragment, mPictureFragment, mMeFragment);
    }

    private final void restoreFragment() {
        List<Fragment> fragmentList = mFragmentManager.getFragments();
        for (Fragment fragment : fragmentList) {
            if (fragment instanceof NewsFragment) {
                mNewsFragment = (NewsFragment) fragment;
            } else if (fragment instanceof MeFragment) {
                mMeFragment = (MeFragment) fragment;
            } else if (fragment instanceof VideoFragment) {
                mVideoFragment = (VideoFragment) fragment;
            } else if (fragment instanceof PictureFragment) {
                mPictureFragment = (PictureFragment) fragment;
            }
        }
        fragments = Arrays.asList(mNewsFragment, mVideoFragment, mPictureFragment, mMeFragment);
    }

    private final void showSelectFragment(int position) {
        hideAllFragments();
        mTransaction = mFragmentManager.beginTransaction();
        if (position == MODULE_NEWS) mRdgToolbar.check(R.id.rdb_news);
        else if (position == MODULE_PIC) mRdgToolbar.check(R.id.rdb_video);
        else if (position == MODULE_VIDEO) mRdgToolbar.check(R.id.rdb_pic);
        else if (position == MODULE_MINE) mRdgToolbar.check(R.id.rdb_me);
        mTransaction.show(fragments.get(position)).commit();
    }

    private final void hideAllFragments() {
        for (Fragment f : fragments) {
            mTransaction.hide(f);
        }
    }

    private final void changeNavigation() {
        mRdgToolbar.setOnCheckedChangeListener((group, checkedId) -> {
                    mTransaction = mFragmentManager.beginTransaction();
                    int position = 0;
                    switch (checkedId) {

                        case R.id.rdb_news:
                            position = MODULE_NEWS;
                            break;
                        case R.id.rdb_video:
                            position = MODULE_VIDEO;
                            break;
                        case R.id.rdb_pic:
                            position = MODULE_PIC;
                            break;
                        case R.id.rdb_me:
                            position = MODULE_MINE;
                            break;
                    }
                    isFirstBack = 1;
                    mFabMenu.hide();
                    hideAllFragments();
                    mTransaction.show(fragments.get(position)).commit();
                }

        );
    }


    //双击退出程序
    @Override
    public void onBackPressed() {
        if (mFabMenu.isToolbar()) {
            mFabMenu.hide();
            isFirstBack = 1;
        } else if (isFirstBack == 1) {
            ToastUtil.show(QUIT_TOAST);
            isFirstBack = 3;
            //开启一个异步线程，当用户超过两秒没有再次点击返回键，则取消退出状态
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isFirstBack = 1; // 取消退出
                }
            }, 1000);
        } else if (isFirstBack == 3) {//单用户连续点击两次的时候，退出程序
            this.finish();
            System.exit(0);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        JCVideoPlayer.releaseAllVideos();
    }
}
