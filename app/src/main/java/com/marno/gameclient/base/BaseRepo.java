package com.marno.gameclient.base;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by marno on 2016/7/14/13:47.
 */
public abstract class BaseRepo {

    //对rst进行判断再做处理
    protected Observable transform(Observable observable) {
        return observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
