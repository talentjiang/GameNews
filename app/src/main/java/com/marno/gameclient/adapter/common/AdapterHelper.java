package com.marno.gameclient.adapter.common;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marno.gameclient.R;


final public class AdapterHelper extends BaseAdapterHelper<AdapterHelper> {

    protected View convertView;
    protected int position = -1;

    private AdapterHelper(Context context, ViewGroup parent, int layoutId, int position) {
        this.position = position;
        this.views = new SparseArray<>();
        this.convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.convertView.setTag(R.id.tag_adapter_helper, this);
    }

    static AdapterHelper get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new AdapterHelper(context, parent, layoutId, position);
        }
        AdapterHelper helper = (AdapterHelper) convertView.getTag(R.id.tag_adapter_helper);
        helper.position = position;
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
