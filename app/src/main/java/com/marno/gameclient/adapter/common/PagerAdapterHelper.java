package com.marno.gameclient.adapter.common;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marno.gameclient.R;


final public class PagerAdapterHelper extends BaseAdapterHelper<PagerAdapterHelper> {

    protected View convertView;
    protected int position = -1;

    private PagerAdapterHelper(Context context, ViewGroup parent, int layoutId, int position) {
        this.position = position;
        this.views = new SparseArray<>();
        this.convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.convertView.setTag(R.id.tag_adapter_helper, this);
        parent.addView(this.convertView);
    }

    private PagerAdapterHelper(Context context, ViewGroup parent, View convertView, int position) {
        this.position = position;
        this.views = new SparseArray<>();
        this.convertView = convertView;
        this.convertView.setTag(R.id.tag_adapter_helper, this);
        parent.addView(this.convertView);
    }

    static PagerAdapterHelper get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new PagerAdapterHelper(context, parent, layoutId, position);
        }
        PagerAdapterHelper helper = (PagerAdapterHelper) convertView.getTag(R.id.tag_adapter_helper);
        helper.position = position;
        parent.addView(convertView);
        return helper;
    }

    static PagerAdapterHelper get(Context context, View convertView, ViewGroup parent, int position, boolean isNew) {
        if (isNew) {
            return new PagerAdapterHelper(context, parent, convertView, position);
        }
        PagerAdapterHelper helper = (PagerAdapterHelper) convertView.getTag(R.id.tag_adapter_helper);
        helper.position = position;
        parent.addView(convertView);
        return helper;
    }

    @Override
    public View getItemView() {
        return convertView;
    }

    public int getPosition() {
        return position;
    }
}
