package com.marno.gameclient.data.retrofit;

import com.marno.gameclient.data.models.BaseVideoEntity;
import com.marno.gameclient.data.repository.TokenEntity;
import com.marno.gameclient.data.repository.VideoListEntity;
import com.marno.gameclient.utils.C;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by marno on 2016/7/22/13:49.
 */
public interface VideoService {

    @GET("video/vlist")
    Observable<BaseVideoEntity<VideoListEntity>> videoList(
            @Query(C.PAGENO) int pageNum,
            @Query(C.PAGESIZE) int pageSize,
            @Query(C.CATEID) String cateId,
            @Query(C.TOKEN) String token);


    @GET("token")
    Observable<BaseVideoEntity<TokenEntity>> userToken(
            @Query(C.DIVICE) String divice);

}
