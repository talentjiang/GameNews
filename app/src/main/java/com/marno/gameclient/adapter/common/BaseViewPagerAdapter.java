package com.marno.gameclient.adapter.common;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

abstract class BaseViewPagerAdapter<T, H extends PagerAdapterHelper> extends PagerAdapter implements DataIO<T> {
    protected final Context context;
    protected final int layoutResId;
    protected final ArrayList<T> data;
    protected Queue<View> cacheViews;
    protected int currentPosition = -1;
    protected View currentTarget;

    public BaseViewPagerAdapter(Context context) {
        this(context, null, 0);
    }

    public BaseViewPagerAdapter(Context context, int layoutResId) {
        this(context, null, layoutResId);
    }

    public BaseViewPagerAdapter(Context context, List<T> data, int layoutResId) {
        this.data = data == null ? new ArrayList<T>() : new ArrayList<>(data);
        this.context = context;
        this.layoutResId = layoutResId;
        this.cacheViews = new LinkedList<>();
    }

    @Override
    public void add(T elem) {
        data.add(elem);
        notifyDataSetChanged();
    }

    @Override
    public void addAt(int location, T elem) {
        data.add(location, elem);
        notifyDataSetChanged();
    }

    @Override
    public void addAll(List<T> elements) {
        data.addAll(elements);
        notifyDataSetChanged();
    }

    @Override
    public void addAllAt(int location, List<T> elements) {
        data.addAll(location, elements);
        notifyDataSetChanged();
    }

    @Override
    public void remove(T elem) {
        data.remove(elem);
        notifyDataSetChanged();
    }

    @Override
    public void removeAt(int index) {
        data.remove(index);
        notifyDataSetChanged();
    }

    @Override
    public void removeAll(List<T> elements) {
        data.removeAll(elements);
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        if (data != null && data.size() > 0) {
            data.clear();
            notifyDataSetChanged();
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

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (getSize() == 0) {
            onEmptyData();
        } else {
            onHasData();
        }
    }

    @Override
    public int getCount() {
        return getSize();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        H helper = getAdapterHelper(position, cacheViews.poll(), container);
        T item = get(position);
        convert(helper, item);
        return helper.getItemView();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object instanceof View) {
            container.removeView((View) object);
            cacheViews.add((View) object);
        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        this.currentPosition = position;
        if (object instanceof View) {
            this.currentTarget = (View) object;
        }
    }

    protected View createView(ViewGroup container, int position) {
        throw new RuntimeException("Required method createView was not overridden");
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public View getCurrentTarget() {
        return currentTarget;
    }

    protected abstract void convert(H helper, T item);

    protected abstract H getAdapterHelper(int position, View convertView, ViewGroup parent);
}
