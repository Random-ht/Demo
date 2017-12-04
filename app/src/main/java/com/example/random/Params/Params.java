package com.example.random.Params;


import com.alibaba.fastjson.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by John on 2017/11/14.
 */

public class Params {

    /**
     * 登录
     *
     * @param name
     * @param pwd
     * @return
     */
    public static RequestBody login(String name, String pwd) {

        JSONObject object = new JSONObject();
        object.put("mobile", name);
        object.put("password", pwd);
        object.put("device", "2");
        object.put("deviceNum", "1111xin111");

        RequestBody requesrBody = RequestBody.create(MediaType.parse("application/json"), object.toString());

        return requesrBody;
    }

    public static RequestBody getDetail(String missionNo) {

        JSONObject object = new JSONObject();
        object.put("missionNo", missionNo);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), object.toString());

        return requestBody;
    }

}
