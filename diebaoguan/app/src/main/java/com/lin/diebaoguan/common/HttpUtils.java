package com.lin.diebaoguan.common;

/**
 * Copyright 2013 Ognyan Bankov
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lin.diebaoguan.uibase.PullToRefreshBaseFragment;
import com.lin.lib_volley_https.UTFStringRequest;
import com.lin.lib_volley_https.VolleyListener;

import java.util.Map;

/**
 * Helper class that is used to provide references to initialized
 * RequestQueue(s) and ImageLoader(s)
 *
 * @author Ognyan Bankov
 */
public class HttpUtils {
    private static RequestQueue mRequestQueue;
    private final static String tag = HttpUtils.class.getName();

    private HttpUtils() {
    }


    public static RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    public static void setmRequestQueue(RequestQueue mRequestQueue) {
        HttpUtils.mRequestQueue = mRequestQueue;
    }

    public static void init(Context context) {

        mRequestQueue = Volley.newRequestQueue(context);

    }

    public static void post(Context context, Fragment fragment, String url, final Map<String, String> params, final VolleyListener listener) {
        Log.d(tag, "postParams:\n" + params);
        final PullToRefreshBaseFragment pullToRefreshBaseFragment = (PullToRefreshBaseFragment) fragment;
        pullToRefreshBaseFragment.showProgress();
        StringRequest myReq = new UTFStringRequest(Method.POST, url, new Listener<String>() {
            public void onResponse(String response) {
                Log.d(tag, "onResponse:\n" + response);
                listener.onResponse(response);
                pullToRefreshBaseFragment.dismissProgress();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                listener.onErrorResponse(error);
                pullToRefreshBaseFragment.dismissProgress();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                return params;
            }

        };
        if (mRequestQueue == null) {
            init(context);
        }

        // 请用缓存
        myReq.setShouldCache(true);
        // 设置缓存时间10分钟
        // myReq.setCacheTime(10*60);
        mRequestQueue.add(myReq);
    }

    public static void get(Context context, Fragment fragment, String url, final VolleyListener listener) {
        final PullToRefreshBaseFragment pullToRefreshBaseFragment = (PullToRefreshBaseFragment) fragment;
        if (pullToRefreshBaseFragment != null)
            pullToRefreshBaseFragment.showProgress();
        StringRequest myReq = new UTFStringRequest(Method.GET, url, new Listener<String>() {
            public void onResponse(String response) {
                listener.onResponse(response);
                if (pullToRefreshBaseFragment != null)
                    pullToRefreshBaseFragment.dismissProgress();
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                if (pullToRefreshBaseFragment != null) {
                    if (error instanceof TimeoutError) {
                        pullToRefreshBaseFragment.showToast("网络超时");
                    } else {
                        pullToRefreshBaseFragment.showToast("网络异常");
                    }
                    pullToRefreshBaseFragment.dismissProgress();
                }
                listener.onErrorResponse(error);

            }
        });
        if (mRequestQueue == null) {
            init(context);
        }
        myReq.setRetryPolicy(new DefaultRetryPolicy(10 * 1000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(myReq);
    }

    public static RequestQueue getRequestQueue(Context context) {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }

    /**
     * Returns instance of ImageLoader initialized with {@see FakeImageCache}
     * which effectively means that no memory caching is used. This is useful
     * for images that you know that will be show only once.
     *
     * @return
     */
    // public static ImageLoader getImageLoader() {
    // if (mImageLoader != null) {
    // return mImageLoader;
    // } else {
    // throw new IllegalStateException("ImageLoader not initialized");
    // }
    // }
}
