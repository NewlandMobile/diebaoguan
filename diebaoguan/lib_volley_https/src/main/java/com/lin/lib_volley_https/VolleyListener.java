package com.lin.lib_volley_https;


import com.android.volley.Response;

public interface VolleyListener <T> extends Response.ErrorListener, Response.Listener<T> {

}
