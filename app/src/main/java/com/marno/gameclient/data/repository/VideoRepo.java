package com.marno.gameclient.data.repository;

import android.content.Context;
import android.text.TextUtils;

import com.marno.gameclient.base.BaseRepo;
import com.marno.gameclient.data.models.BaseVideoEntity;
import com.marno.gameclient.data.retrofit.DefaultSubscriber;
import com.marno.gameclient.data.retrofit.RetrofitClient;
import com.marno.gameclient.utils.C;
import com.marno.gameclient.utils.SPUtil;

import rx.Observable;

/**
 * Created by marno on 2016/7/21/21:06.
 * 游戏数据仓库
 */
public class VideoRepo extends BaseRepo {

    private static volatile VideoRepo instance;

    private VideoRepo() {
    }

    public static VideoRepo getIns() {
        if (instance == null) {
            synchronized (VideoRepo.class) {
                if (instance == null) {
                    instance = new VideoRepo();
                }
            }
        }
        return instance;
    }


    public Observable<BaseVideoEntity<VideoListEntity>> getVideoList(int pageNum, Context context) {
        String token = C.token;
        if (TextUtils.isEmpty(token)) getUserToken(context);
        return transform(RetrofitClient.getVideoIns().VIDEO().videoList(pageNum, 5, "all", token));
    }

    public void getUserToken(Context mContext) {
        String token = (String) SPUtil.get(C.F_Token, mContext, C.TOKEN, "");
        if (!TextUtils.isEmpty(token)) {
            C.token = token;
            return;
        }
        transform(RetrofitClient.getVideoIns().VIDEO().userToken("android"))
                .subscribe(new DefaultSubscriber<BaseVideoEntity<TokenEntity>>() {
                    @Override
                    public void _onNext(BaseVideoEntity<TokenEntity> entity) {
                        TokenEntity tokenEntity = entity.returnData;
                        C.token = tokenEntity.userToken;
                        SPUtil.put(C.F_Token, mContext, C.TOKEN, tokenEntity.userToken);
                    }
                });
    }
}
