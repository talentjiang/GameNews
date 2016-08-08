package com.marno.gameclient.data.retrofit;

import rx.Subscriber;

/**
 * Created by marno on 2016/7/21/22:44.
 */
public abstract class DefaultSubscriber<T> extends Subscriber<T> {


    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onNext(T entity) {
        _onNext(entity);
    }

    public abstract void _onNext(T entity);
}
