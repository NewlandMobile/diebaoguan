package com.lin.diebaoguan.network;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonRequest;

/**
 * It's Created by NewLand-JianFeng on 2016/12/28.
 */

public class VolleyRequest extends JsonRequest {
    public VolleyRequest(int method, String url, String requestBody, Response.Listener listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse networkResponse) {
        return null;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
