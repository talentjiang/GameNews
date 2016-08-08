package com.marno.gameclient.adapter.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class Adapter<T> extends BaseAdapter<T, AdapterHelper> {

    public Adapter(Context context, @NonNull int... layoutResIds) {
        super(context, layoutResIds);
    }

    public Adapter(Context context, @Nullable List<T> data, @NonNull int... layoutResIds) {
        super(context, data, layoutResIds);
    }

    @Override
    protected AdapterHelper getAdapterHelper(int position, View convertView, ViewGroup parent, int layoutResId) {
        return AdapterHelper.get(context, convertView, parent, layoutResId, position);
    }
}
