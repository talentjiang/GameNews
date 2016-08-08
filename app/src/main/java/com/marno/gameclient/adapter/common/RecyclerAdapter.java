package com.marno.gameclient.adapter.common;

import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;

import com.yqritc.recyclerviewflexibledivider.FlexibleDividerDecoration;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

public abstract class RecyclerAdapter<T> extends BaseRecyclerAdapter<T, RecyclerAdapterHelper>
        implements FlexibleDividerDecoration.PaintProvider,
//        FlexibleDividerDecoration.SizeProvider,
//        FlexibleDividerDecoration.ColorProvider,
        FlexibleDividerDecoration.VisibilityProvider,
        HorizontalDividerItemDecoration.MarginProvider {

    public RecyclerAdapter(Context context, @NonNull int... layoutResIds) {
        super(context, layoutResIds);
    }

    public RecyclerAdapter(Context context, @Nullable List<T> data, @NonNull int... layoutResIds) {
        super(context, data, layoutResIds);
    }

    @Override
    protected RecyclerAdapterHelper getAdapterHelper(ViewHolder viewHolder) {
        return RecyclerAdapterHelper.get(viewHolder);
    }

    @Override
    public int dividerLeftMargin(int position, RecyclerView parent) {
        return 0;
    }

    @Override
    public int dividerRightMargin(int position, RecyclerView parent) {
        return 0;
    }

    @Override
    public Paint dividerPaint(int position, RecyclerView parent) {
        return null;
    }

    @Override
    public boolean shouldHideDivider(int position, RecyclerView parent) {
        return false;
    }
}
