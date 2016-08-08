package com.marno.gameclient.adapter.common;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class ViewPagerAdapter<T> extends BaseViewPagerAdapter<T, PagerAdapterHelper> {

    public ViewPagerAdapter(Context context) {
        super(context);
    }

    public ViewPagerAdapter(Context context, @LayoutRes int layoutResId) {
        super(context, layoutResId);
    }

    public ViewPagerAdapter(Context context, @Nullable List<T> data, @LayoutRes int layoutResId) {
        super(context, data, layoutResId);
    }

    @Override
    protected PagerAdapterHelper getAdapterHelper(int position, View convertView, ViewGroup parent) {
        if (layoutResId != 0) {
            return PagerAdapterHelper.get(context, convertView, parent, layoutResId, position);
        } else {
            if (convertView == null) {
                return PagerAdapterHelper.get(context, createView(parent, position), parent, position, true);
            } else {
                return PagerAdapterHelper.get(context, convertView, parent, position, true);
            }
        }
    }
}
