package com.jiuyu.utils;

import cn.hutool.http.HttpUtil;

import java.util.Map;

/**
 * @author chengliang
 * @date 2021/08/14 11:11
 */
public class HttpUtils {

    private HttpUtils() {
        throw new AssertionError("No java.nio.charset.StandardCharsets instances for you!");
    }

    /**
     * 设置超时时间60秒
     */
    public static final int TIMEOUT = 60 * 1000;


    /**
     * http get 参数传参
     *
     * @param url
     * @param param
     * @return
     */
    public static String httpGet(String url, Map<String, Object> param) {
        return HttpUtil.get(url, param, TIMEOUT);
    }

    /**
     * http post + map
     *
     * @param url
     * @param param
     * @return
     */
    public static String httpPost(String url, Map<String, Object> param) {
        return HttpUtil.post(url, param, TIMEOUT);
    }

    /**
     * http post + json
     *
     * @param url
     * @param jsonStr
     * @return
     */
    public static String httpPostByJson(String url, String jsonStr) {
        return HttpUtil.post(url, jsonStr, TIMEOUT);
    }

}
