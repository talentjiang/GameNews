package com.marno.gameclient.data.retrofit;

import com.marno.gameclient.base.BaseNewsEntity;
import com.marno.gameclient.data.models.NewsEntity;
import com.marno.gameclient.utils.C;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by marno on 2016/7/21/20:55.
 */
public interface NewsService {

    @FormUrlEncoded
    @POST("toutiao/get_list")
    Observable<BaseNewsEntity<List<NewsEntity>>> newsList(
            @Field(C.CATE_ID) int cateId,
            @Field(C.MAX) int page);
}
