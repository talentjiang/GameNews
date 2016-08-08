package com.marno.gameclient.adapter.common;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.StringRes;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.marno.gameclient.utils.GlideUtil;


public abstract class BaseAdapterHelper<T> {
    protected SparseArray<View> views;

    public <V extends View> V getView(int viewId) {
        return retrieveView(viewId);
    }

    public T setText(int viewId, CharSequence value) {
        TextView view = retrieveView(viewId);
        view.setText(value);
        return (T) this;
    }

    public T setText(int viewId, @StringRes int stringRes) {
        TextView view = retrieveView(viewId);
        view.setText(stringRes);
        return (T) this;
    }

    public T setEnable(int viewId, boolean isEnable) {
        View view = retrieveView(viewId);
        view.setEnabled(isEnable);
        return (T) this;
    }

    public T setImageResource(int viewId, @DrawableRes int imageRes) {
        ImageView view = retrieveView(viewId);
        GlideUtil.loadImg(imageRes, view);
        return (T) this;
    }

    public T setBackgroundColor(int viewId, @ColorInt int color) {
        View view = retrieveView(viewId);
        view.setBackgroundColor(color);
        return (T) this;
    }

    public T setBackgroundRes(int viewId, @DrawableRes int backgroundRes) {
        View view = retrieveView(viewId);
        view.setBackgroundResource(backgroundRes);
        return (T) this;
    }

    public T setTextColor(int viewId, @ColorInt int textColor) {
        TextView view = retrieveView(viewId);
        view.setTextColor(textColor);
        return (T) this;
    }


    public T setTextColorRes(int viewId, @ColorRes int textColorRes) {
        TextView view = retrieveView(viewId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            view.setTextColor(getItemView().getContext().getResources().getColor(textColorRes, null));
        } else {
            view.setTextColor(getItemView().getContext().getResources().getColor(textColorRes));
        }
        return (T) this;
    }

    public T setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = retrieveView(viewId);
        view.setImageDrawable(drawable);
        return (T) this;
    }

    public T setImageUrl(int viewId, String imageUrl, boolean isRound) {
        ImageView view = retrieveView(viewId);
        if (isRound) GlideUtil.loadRoundImg(imageUrl, view);
        else GlideUtil.loadImg(imageUrl, view);
        return (T) this;
    }

    public T setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = retrieveView(viewId);
        view.setImageBitmap(bitmap);
        return (T) this;
    }

    public T setAlpha(int viewId, @FloatRange(from = 0.0, to = 1.0) float value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            retrieveView(viewId).setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            retrieveView(viewId).startAnimation(alpha);
        }
        return (T) this;
    }

    public T setVisible(int viewId, int visibility) {
        View view = retrieveView(viewId);
        view.setVisibility(visibility);
        return (T) this;
    }

    public T linkify(int viewId) {
        TextView view = retrieveView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return (T) this;
    }

    public T setTypeface(int viewId, Typeface typeface) {
        TextView view = retrieveView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return (T) this;
    }

    public T setTypeface(Typeface typeface, int... viewIds) {
        for (int viewId : viewIds) {
            TextView view = retrieveView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return (T) this;
    }

    public T setProgress(int viewId, int progress) {
        ProgressBar view = retrieveView(viewId);
        view.setProgress(progress);
        return (T) this;
    }

    public T setProgress(int viewId, int progress, int max) {
        ProgressBar view = retrieveView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return (T) this;
    }

    public T setMax(int viewId, int max) {
        ProgressBar view = retrieveView(viewId);
        view.setMax(max);
        return (T) this;
    }

    public T setRating(int viewId, float rating) {
        RatingBar view = retrieveView(viewId);
        view.setRating(rating);
        return (T) this;
    }

    public T setRating(int viewId, float rating, int max) {
        RatingBar view = retrieveView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return (T) this;
    }

    public T setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = retrieveView(viewId);
        view.setOnClickListener(listener);
        return (T) this;
    }

    public T setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = retrieveView(viewId);
        view.setOnTouchListener(listener);
        return (T) this;
    }

    public T setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = retrieveView(viewId);
        view.setOnLongClickListener(listener);
        return (T) this;
    }

    public T setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
        CompoundButton view = retrieveView(viewId);
        view.setOnCheckedChangeListener(listener);
        return (T) this;
    }

    public T setRadioGroupOnCheckedChangeListener(int viewId, RadioGroup.OnCheckedChangeListener listener) {
        RadioGroup view = retrieveView(viewId);
        view.setOnCheckedChangeListener(listener);
        return (T) this;
    }

    public T setTag(int viewId, Object tag) {
        View view = retrieveView(viewId);
        view.setTag(tag);
        return (T) this;
    }

    public T setTag(int viewId, int key, Object tag) {
        View view = retrieveView(viewId);
        view.setTag(key, tag);
        return (T) this;
    }

    public T setChecked(int viewId, boolean checked) {
        View view = retrieveView(viewId);
        if (view instanceof CompoundButton) {
            ((CompoundButton) view).setChecked(checked);
        } else if (view instanceof CheckedTextView) {
            ((CheckedTextView) view).setChecked(checked);
        } else {
            ((Checkable) view).setChecked(checked);
        }
        return (T) this;
    }

    public T setAdapter(int viewId, Adapter adapter) {
        AdapterView view = retrieveView(viewId);
        view.setAdapter(adapter);
        return (T) this;
    }

    private <V extends View> V retrieveView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = getItemView().findViewById(viewId);
            views.put(viewId, view);
        }
        return (V) view;
    }

    public abstract View getItemView();
}
