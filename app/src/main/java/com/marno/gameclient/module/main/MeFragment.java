package com.marno.gameclient.module.main;

import android.os.Bundle;
import android.view.View;

import com.marno.gameclient.R;
import com.marno.gameclient.base.BaseFragment;

/**
 * Created by marno on 2016/7/21/21:43.
 */
public class MeFragment extends BaseFragment {

    public static MeFragment newInstance() {
        return new MeFragment();
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_msv_recycler;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }

    @Override
    protected View getToolBarView() {
        return null;
    }
}
