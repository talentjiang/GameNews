package com.marno.gameclient.adapter.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

abstract class BaseRecyclerAdapter<T, H extends RecyclerAdapterHelper> extends RecyclerView.Adapter<ViewHolder> implements DataIO<T> {

    protected final Context context;
    protected final LayoutInflater layoutInflater;
    protected final int[] layoutResIds;
    protected final ArrayList<T> data;

    public BaseRecyclerAdapter(Context context, int... layoutResIds) {
        this(context, null, layoutResIds);
    }

    public BaseRecyclerAdapter(Context context, List<T> data, int... layoutResIds) {
        this.context = context;
        this.layoutResIds = layoutResIds;
        this.layoutInflater = LayoutInflater.from(context);
        this.data = data == null ? new ArrayList<T>() : new ArrayList<>(data);
    }

    @Override
    public int getItemViewType(int position) {
        if (getViewTypeCount() == 1) {
            return super.getItemViewType(position);
        }
        throw new RuntimeException("Required method getItemViewType was not overridden");
    }

    public int getViewTypeCount() {
        return layoutResIds.length;
    }

    public int getLayoutResId(int viewType) {
        throw new RuntimeException("Required method getLayoutResId was not overridden");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutResId;
        if (getViewTypeCount() > 1) {
            layoutResId = getLayoutResId(getItemViewType(viewType));
        } else {
            layoutResId = layoutResIds[0];
        }
        return new ViewHolder(layoutInflater.inflate(layoutResId, parent, false)) {
        };
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        H helper = getAdapterHelper(holder);
        T item = get(position);
        convert(helper, item);
    }

    @Override
    public int getItemCount() {
        return getSize();
    }

    @Override
    public void add(T elem) {
        data.add(elem);
        notifyDataSetChanged();
        onDataSetChanged();
    }

    @Override
    public void addAt(int location, T elem) {
        data.add(location, elem);
        notifyDataSetChanged();
        onDataSetChanged();
    }

    @Override
    public void addAll(List<T> elements) {
        data.addAll(elements);
        notifyDataSetChanged();
        onDataSetChanged();
    }

    @Override
    public void addAllAt(int location, List<T> elements) {
        data.addAll(location, elements);
        notifyDataSetChanged();
        onDataSetChanged();
    }

    @Override
    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
        onDataSetChanged();
    }

    @Override
    public void removeAt(int index) {
        data.remove(index);
        notifyDataSetChanged();
        onDataSetChanged();
    }

    @Override
    public void removeAll(List<T> elements) {
        data.removeAll(elements);
        notifyDataSetChanged();
        onDataSetChanged();
    }

    @Override
    public void clear() {
        if (data != null && data.size() > 0) {
            data.clear();
            notifyDataSetChanged();
            onDataSetChanged();
        }
    }

    @Override
    public void replace(T oldElem, T newElem) {
        replaceAt(data.indexOf(oldElem), newElem);
    }

    @Override
    public void replaceAt(int index, T elem) {
        data.set(index, elem);
        notifyDataSetChanged();
    }

    @Override
    public void replaceAll(List<T> elements) {
        if (data.size() > 0) {
            data.clear();
        }
        data.addAll(elements);
        notifyDataSetChanged();
        onDataSetChanged();
    }

    @Override
    public T get(int position) {
        if (position >= data.size())
            return null;
        return data.get(position);
    }

    @Override
    public ArrayList<T> getAll() {
        return data;
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public boolean contains(T elem) {
        return data.contains(elem);
    }

    @Override
    public void onEmptyData() {

    }

    @Override
    public void onHasData() {

    }

    public void onDataSetChanged() {
        if (getSize() == 0) {
            onEmptyData();
        } else {
            onHasData();
        }
    }

    protected abstract void convert(H helper, T item);

    protected abstract H getAdapterHelper(ViewHolder viewHolder);
}
