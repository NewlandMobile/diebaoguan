package com.lin.diebaoguan.common;

/**
 * Created by lin on 2016/12/2311:07.
 * mail :1057705307@QQ.com.
 * describe:工具类
 */

public class CommonUtils {
    /* token 使用固定值 */
    private static String tokenA = "d19cf361181f5a169c107872e1f5b722";
    private static String url = "http://api.cnmo.com/client";
    private static String apiid ="3";
    

    /**
     * 获取token
     *
     * @return
     */
    public static String getToken(long timeMillis) {
        String token1;
        token1 = MD5Utils.md5(tokenA + timeMillis);
        return token1;
    }

}
