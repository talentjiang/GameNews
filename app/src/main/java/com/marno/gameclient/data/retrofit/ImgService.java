package com.marno.gameclient.data.retrofit;

import com.marno.gameclient.data.models.ImgListEntity;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by marno on 2016/7/24/17:02.
 */
public interface ImgService {

    @GET("data/%E7%A6%8F%E5%88%A9/10/{page}")
    Observable<ImgListEntity> imgList(
            @Path("page") int pageNum);
}
