package com.marno.gameclient.adapter.common;

import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.SparseArray;
import android.view.View;

import com.marno.gameclient.R;

final public class RecyclerAdapterHelper extends BaseAdapterHelper<RecyclerAdapterHelper> {

    protected ViewHolder viewHolder;

    private RecyclerAdapterHelper(ViewHolder viewHolder) {
        this.viewHolder = viewHolder;
        this.views = new SparseArray<>();
    }

    static RecyclerAdapterHelper get(ViewHolder viewHolder) {
        RecyclerAdapterHelper helper;
        if (viewHolder.itemView.getTag(R.id.tag_adapter_helper) == null) {
            helper = new RecyclerAdapterHelper(viewHolder);
            viewHolder.itemView.setTag(R.id.tag_adapter_helper, helper);
        } else {
            helper = (RecyclerAdapterHelper) viewHolder.itemView.getTag(R.id.tag_adapter_helper);
        }
        return helper;
    }

    @Override
    public View getItemView() {
        return viewHolder.itemView;
    }

    public int getItemViewType() {
        return viewHolder.getItemViewType();
    }

    public int getAdapterPosition() {
        return viewHolder.getAdapterPosition();
    }

    public int getLayoutPosition() {
        return viewHolder.getLayoutPosition();
    }

    public int getOldPosition() {
        return viewHolder.getOldPosition();
    }

    public boolean isRecyclable() {
        return viewHolder.isRecyclable();
    }

    public RecyclerAdapterHelper setIsRecyclable(boolean recyclable) {
        viewHolder.setIsRecyclable(recyclable);
        return this;
    }
}
