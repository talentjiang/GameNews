package com.marno.gameclient.data.models;

/**
 * Created by marno on 2016/7/24/21:38.
 */
public class BaseVideoEntity<T> {

    /**
     * returnCode : 200
     * returnMsg : Success
     * returnData : {}
     */

    public String returnCode;
    public String returnMsg;
    public T returnData;

}
