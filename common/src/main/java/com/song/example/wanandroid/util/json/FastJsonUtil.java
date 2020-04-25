package com.song.example.wanandroid.util.json;

import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.song.example.wanandroid.util.WanLog;

import java.util.Collections;
import java.util.List;

/**
 * @author Listensong
 * @time 19-10-24 下午4:06
 * @desc com.song.example.wanandroid.util.json.FastJsonUtil
 */
public class FastJsonUtil {

    public static final String TAG = "FastJsonUtil";

    private FastJsonUtil(){}

    @Nullable
    public static <T> T getObject(String jsonString, Class<T> clz) {
        T t = null;
        try {
            t = JSON.parseObject(jsonString, clz);
        } catch (Exception e) {
            WanLog.INSTANCE.e(TAG, "getObject e:" + e);
        }
        return  t;
    }

    public static <T> String toJson(T srcObj) {
        try {
            return srcObj instanceof String ? (String) srcObj: JSON.toJSONString(srcObj);
        } catch (Exception e) {
            WanLog.INSTANCE.e(TAG, "toJson e:" + e);
        }
        return "";
    }

    public static <T> String list2Json(List<T> jsonObj) {
        return toJson(jsonObj);
    }

    @Nullable
    public static <T> List<T> json2List(String jsonString, Class<T> clz) {
        try {
            return JSON.parseArray(jsonString, clz);
        } catch (Exception e) {
            WanLog.INSTANCE.e(TAG, "json2List e:" + e);
            return Collections.emptyList();
        }
    }
}
