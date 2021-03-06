package com.lin.diebaoguan.common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.SharedPreferencesCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.lin.diebaoguan.MyApplication;
import com.lin.diebaoguan.R;
import com.lin.diebaoguan.network.response.BaseResponseTemplate;
import com.lin.diebaoguan.network.send.BaseSendTemplate;
import com.lin.diebaoguan.network.send.CollectListDS;
import com.lin.diebaoguan.network.send.CommentDS;
import com.lin.diebaoguan.network.send.NormalDS;
import com.lin.diebaoguan.uibase.PullToRefreshBaseFragment;
import com.lin.lib_volley_https.VolleyListener;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

/**
 * Created by lin on 2016/12/2311:07.
 * mail :1057705307@QQ.com.
 * describe:工具类
 */

public class CommonUtils<T extends BaseResponseTemplate> {
    /* token 使用固定值 */
    private static String tokenA = "d19cf361181f5a169c107872e1f5b722";
    private static String URL = "http://api.cnmo.com/client";
    //  这个固定值用的页面也会比较多，可以考虑放到底层基类去。
    private static final String moduleString = "api_libraries_sjdbg_articlelist";

    private static CommonUtils commonUtils = new CommonUtils();

    public static CommonUtils getInstance() {
        return commonUtils;
    }

    /**
     * 获取token
     *
     * @return
     */
    public static String getToken(long timeMillis) {
        String token1;
        token1 = md5(tokenA + timeMillis);
        return token1;
    }

    public static String md5(String str) {

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");

            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));

            }

            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * post请求
     *
     * @param params
     * @param volleyListener
     */
    public static void httpPost(Map<String, String> params, VolleyListener volleyListener) {
        com.lin.lib_volley_https.HTTPUtils.post(MyApplication.getInstance(), URL, params, volleyListener);
    }

    /**
     * post请求
     *
     * @param fragment       fragment
     * @param params
     * @param volleyListener
     */
    public static void httpPost(Fragment fragment, Map<String, String> params, VolleyListener volleyListener) {
        HttpUtils.post(MyApplication.getInstance(), fragment, URL, params, volleyListener);
    }

    /**
     * get请求不带参数
     *
     * @param url
     * @param volleyListener
     */
    public static void httpGet(String url, VolleyListener volleyListener) {
        com.lin.lib_volley_https.HTTPUtils.get(MyApplication.getInstance(), url, volleyListener);
    }

    /**
     * get请求带参数
     *
     * @param params
     * @param volleyListener
     */
    public static void httpGet(Map<String, String> params, VolleyListener volleyListener) {
        StringBuilder encodedParams = new StringBuilder();
        String url = "";
        Iterator<Map.Entry<String, String>> var5 = params.entrySet().iterator();
        while (var5.hasNext()) {
            Map.Entry<String, String> uee = var5.next();
            encodedParams.append(uee.getKey());
            encodedParams.append('=');
            encodedParams.append(uee.getValue());
            encodedParams.append('&');
        }
        url = URL + "?" + encodedParams.toString();
        String finalUrl = url.substring(0, url.length() - 1);
        Log.e("get请求带参数", "==URL：" + finalUrl);
        com.lin.lib_volley_https.HTTPUtils.get(MyApplication.getInstance(), finalUrl, volleyListener);
    }

    /**
     * get请求带参数,传入fragment
     *
     * @param fragment       fragment
     * @param params
     * @param volleyListener
     */
    public static void httpGet(Fragment fragment, Map<String, String> params, VolleyListener volleyListener) {
        StringBuilder encodedParams = new StringBuilder();
        String url = "";
        Iterator<Map.Entry<String, String>> var5 = params.entrySet().iterator();
        while (var5.hasNext()) {
            Map.Entry<String, String> uee = var5.next();
            encodedParams.append(uee.getKey());
            encodedParams.append('=');
            encodedParams.append(uee.getValue());
            encodedParams.append('&');
        }
        url = URL + "?" + encodedParams.toString();
        String finalUrl = url.substring(0, url.length() - 1);
        Log.e("get请求带参数", "==URL：" + finalUrl);
        HttpUtils.get(MyApplication.getInstance(), fragment, finalUrl, volleyListener);
    }

    /**
     * 显示加载进度框
     *
     * @param contenxt
     */
    public static ProgressDialog showProgressDialog(Context contenxt) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(contenxt);
        progressDialog.setTitle("");
        progressDialog.setMessage("正在拼命加载。。。");
        return progressDialog;
    }

    /**
     * 显示加载进度框
     *
     * @param contenx
     */
    public static ProgressDialog showProgressDialog(Context contenx, String title, String message) {
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(contenx);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        return progressDialog;
    }

    /**
     * @param fragment
     * @param cid            传值1,2,3,4,5分别对应光影集：综合，精品原创，数码漫谈，手机美图，平板美图.
     * @param pageOffset     获取第几页
     * @param volleyListener 响应监听
     */
    public static void fetchDataAtGyjPage(PullToRefreshBaseFragment fragment, int cid, int pageOffset, VolleyListener volleyListener) {
        NormalDS params = new NormalDS();
        params.setModule("api_libraries_sjdbg_tulist");

        params.setCid(cid);
        params.setOffset(pageOffset * Const.ROWS);
        params.setRows(Const.ROWS);
        normalGetWayFetch(params, fragment, volleyListener);
    }

    public static void normalGetWayFetch(BaseSendTemplate sendParams, PullToRefreshBaseFragment fragment, VolleyListener volleyListener) {
        sendParams.initTimePart();
        CommonUtils.httpGet(fragment, sendParams.parseParams(), volleyListener);
    }

    /**
     * 根据 具体板块内容，获取后台信息
     * 用于谍报馆与风尚标模块
     *
     * @param pageOffset      获取第几页
     * @param isFengShangBiao 是否属于风尚标板块
     * @param detailPageNum   具体板块的数值 （当isclass=0时，传值1,2,3,4分别对应谍报馆：新品，价格，体验，应用.
     *                        当isclass=1时，传值1,2,3,4分别对应风尚标：综合，爱美妆，爱美访，雯琰文
     *                        ）
     */
    public static void fetchDataAtFsbOrDbg(int pageOffset, Fragment fragment, boolean isFengShangBiao, int detailPageNum, VolleyListener volleyListener) {
        NormalDS sendParams = new NormalDS();
        sendParams.setModule(moduleString);
        sendParams.setIsclass(isFengShangBiao ? 1 : 0);
        sendParams.setCid(detailPageNum);
        sendParams.setOffset(pageOffset * Const.ROWS);
        sendParams.setRows(Const.ROWS);
        sendParams.initTimePart();

        CommonUtils.httpGet(fragment, sendParams.parseParams(), volleyListener);
    }

    /**
     * 发表评论
     *
     * @param content        内容
     * @param docid          文章id
     * @param volleyListener 监听
     */
    public static void sendComment(String content, String docid, VolleyListener volleyListener) {
        CommentDS commentDS = new CommentDS();
        commentDS.setAuthkey(MyApplication.getKey());
        commentDS.setModule("api_libraries_sjdbg_comment");
        commentDS.setDocid(docid);
        commentDS.setUsername(MyApplication.getUserName());
        commentDS.setUid(MyApplication.getUid());
        commentDS.setContent(content);
        commentDS.initTimePart();

        CommonUtils.httpPost(commentDS.parseParams(), volleyListener);

    }

    /**
     * 删除SP项
     *
     * @param context
     * @param key     键
     */
    public static void deleteSp(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.spNmae), Context.MODE_PRIVATE);
        if (sp.contains(key)) {
            SharedPreferences.Editor edit = sp.edit();
            edit.remove(key);
            SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
        }
    }

    /**
     * 通过sp保存信息
     *
     * @param key    键
     * @param values 值
     */
    public static void saveBySp(Context context, String key, Object values) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.spNmae), Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (values instanceof String) {
            edit.putString(key, (String) values);
        } else if (values instanceof Boolean) {
            edit.putBoolean(key, (Boolean) values);
        } else if (values instanceof Integer) {
            edit.putInt(key, (Integer) values);
        } else if (values instanceof Float) {
            edit.putFloat(key, (Float) values);
        }

        SharedPreferencesCompat.EditorCompat.getInstance().apply(edit);
//        edit.commit();
    }

    /**
     * 获取sp保存的 数据
     *
     * @param context
     * @param key
     * @return
     */
    public static Object getSp(Context context, String key, Object defValue) {
        SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.spNmae), Context.MODE_PRIVATE);
        Object result = null;
        if (!sp.contains(key)) {
            return defValue;
        }
        if (defValue instanceof String) {
            result = sp.getString(key, (String) defValue);
        } else if (defValue instanceof Boolean) {
            result = sp.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Integer) {
            result = sp.getInt(key, (Integer) defValue);
        } else if (defValue instanceof Float) {
            result = sp.getFloat(key, (Float) defValue);
        }
        return result;
    }

    /**
     * 获取单个App版本名
     **/
    public static String getVersionName(Context context) {
        return getPackageInfo(context).versionName;
    }

    /**
     * 获取单个App版本号
     *
     * @param context
     */
    public static int getVersionCode(Context context) {
        return getPackageInfo(context).versionCode;
    }

    private static PackageInfo getPackageInfo(Context context) {
        PackageInfo pi = null;
        try {
            PackageManager pm = context.getPackageManager();
            pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pi;
    }

    /**
     * 获取个人收藏列表数据
     *
     * @param cid            cid 为 1，2,3 ，分别对应谍报收藏，生活收藏，图片收藏
     * @param pageOffset
     * @param volleyListener
     */
    public static void fetchDataAtCollect(Fragment fragment, int cid, int pageOffset, VolleyListener volleyListener) {
        CollectListDS collectListDS = new CollectListDS();
        collectListDS.setModule("api_libraries_sjdbg_collectlist");
        collectListDS.setCid("" + cid);
        if (MyApplication.hasLogined()) {
            collectListDS.setUid(MyApplication.getUid());
        }
        collectListDS.setOffset("" + pageOffset);
        collectListDS.initTimePart();
        CommonUtils.httpGet(fragment, collectListDS.parseParams(), volleyListener);
    }

    /**
     * 获取移动设备唯一识别码
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = manager.getDeviceId();
        return deviceId;
    }

    /**
     * 保存bitmap到指定位置
     *
     * @param view 图片源
     */
    public static void saveBitmapToSDCard(Context context, View view) {
        Bitmap mBitmap = convertViewToBitmap(view);
        String absolutePath = Environment.getExternalStorageDirectory().getPath();
        LogUtils.e("===absolutePath===" + absolutePath);
        String dir = absolutePath + "/diebaoguan/pic/";
        //通过时间来命名，如果1s内要存储多份文件，最好要加上文件名的判断，以防重复
        Calendar now = new GregorianCalendar();
        SimpleDateFormat simpleDate = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        String fileName = simpleDate.format(now.getTime());
        try {
            File file = new File(dir);
            if (!file.exists()) {
                //多层文件目录
                file.mkdirs();
            }
            File file1 = new File(dir + fileName + ".jpg");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream out = new FileOutputStream(file1);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            //将Bitmap对象保存成外部存储中的一个jpg格式的文件。但此时该文件只是保存在外部存储的一个目录中，
            // 必须进入其所在的目录中才可以看到。在系统图库，相册和其他应用中无法看到新建的图片文件。
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将view装成bitmap
     *
     * @param view
     * @return
     */
    public static Bitmap convertViewToBitmap(View view) {
//        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
//        view.buildDrawingCache();
//        Bitmap bitmap = view.getDrawingCache();
//        Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),
                Bitmap.Config.ARGB_8888);
        //利用bitmap生成画布
        Canvas canvas = new Canvas(bitmap);

        //把view中的内容绘制在画布上
        view.draw(canvas);
        return bitmap;
    }

    /**
     * 获取网络类型
     *
     * @return
     */
    public static String getNetworkType(Context context) {
        int networkClass = getNetworkClass(context);
        String type = "未知";
        switch (networkClass) {
            case NETWORK_CLASS_UNAVAILABLE:
                type = "无";
                break;
            case NETWORK_CLASS_WIFI:
                type = "Wi-Fi";
                break;
            case NETWORK_CLASS_2_G:
                type = "2G";
                break;
            case NETWORK_CLASS_3_G:
                type = "3G";
                break;
            case NETWORK_CLASS_4_G:
                type = "4G";
                break;
            case NETWORK_CLASS_UNKNOWN:
                type = "未知";
                break;
        }
        LogUtils.e("==网络类型==" + type);
        return type;
    }

    private static int getNetworkClass(Context context) {
        int networkType = NETWORK_TYPE_UNKNOWN;
        try {
            final NetworkInfo network = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE))
                    .getActiveNetworkInfo();
            if (network != null && network.isAvailable()
                    && network.isConnected()) {
                int type = network.getType();
                if (type == ConnectivityManager.TYPE_WIFI) {
                    networkType = NETWORK_TYPE_WIFI;
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                    networkType = telephonyManager.getNetworkType();
                }
            } else {
                networkType = NETWORK_TYPE_UNAVAILABLE;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return getNetworkClassByType(networkType);
    }

    private static int getNetworkClassByType(int networkType) {
        switch (networkType) {
            case NETWORK_TYPE_UNAVAILABLE:
                return NETWORK_CLASS_UNAVAILABLE;
            case NETWORK_TYPE_WIFI:
                return NETWORK_CLASS_WIFI;
            case NETWORK_TYPE_GPRS:
            case NETWORK_TYPE_EDGE:
            case NETWORK_TYPE_CDMA:
            case NETWORK_TYPE_1xRTT:
            case NETWORK_TYPE_IDEN:
                return NETWORK_CLASS_2_G;
            case NETWORK_TYPE_UMTS:
            case NETWORK_TYPE_EVDO_0:
            case NETWORK_TYPE_EVDO_A:
            case NETWORK_TYPE_HSDPA:
            case NETWORK_TYPE_HSUPA:
            case NETWORK_TYPE_HSPA:
            case NETWORK_TYPE_EVDO_B:
            case NETWORK_TYPE_EHRPD:
            case NETWORK_TYPE_HSPAP:
                return NETWORK_CLASS_3_G;
            case NETWORK_TYPE_LTE:
                return NETWORK_CLASS_4_G;
            default:
                return NETWORK_CLASS_UNKNOWN;
        }
    }

    private static final int NETWORK_TYPE_UNAVAILABLE = -1;
    // private static final int NETWORK_TYPE_MOBILE = -100;
    private static final int NETWORK_TYPE_WIFI = -101;

    private static final int NETWORK_CLASS_WIFI = -101;
    private static final int NETWORK_CLASS_UNAVAILABLE = -1;
    /**
     * Unknown network class.
     */
    private static final int NETWORK_CLASS_UNKNOWN = 0;
    /**
     * Class of broadly defined "2G" networks.
     */
    private static final int NETWORK_CLASS_2_G = 1;
    /**
     * Class of broadly defined "3G" networks.
     */
    private static final int NETWORK_CLASS_3_G = 2;
    /**
     * Class of broadly defined "4G" networks.
     */
    private static final int NETWORK_CLASS_4_G = 3;

    // 适配低版本手机
    /**
     * Network type is unknown
     */
    public static final int NETWORK_TYPE_UNKNOWN = 0;
    /**
     * Current network is GPRS
     */
    public static final int NETWORK_TYPE_GPRS = 1;
    /**
     * Current network is EDGE
     */
    public static final int NETWORK_TYPE_EDGE = 2;
    /**
     * Current network is UMTS
     */
    public static final int NETWORK_TYPE_UMTS = 3;
    /**
     * Current network is CDMA: Either IS95A or IS95B
     */
    public static final int NETWORK_TYPE_CDMA = 4;
    /**
     * Current network is EVDO revision 0
     */
    public static final int NETWORK_TYPE_EVDO_0 = 5;
    /**
     * Current network is EVDO revision A
     */
    public static final int NETWORK_TYPE_EVDO_A = 6;
    /**
     * Current network is 1xRTT
     */
    public static final int NETWORK_TYPE_1xRTT = 7;
    /**
     * Current network is HSDPA
     */
    public static final int NETWORK_TYPE_HSDPA = 8;
    /**
     * Current network is HSUPA
     */
    public static final int NETWORK_TYPE_HSUPA = 9;
    /**
     * Current network is HSPA
     */
    public static final int NETWORK_TYPE_HSPA = 10;
    /**
     * Current network is iDen
     */
    public static final int NETWORK_TYPE_IDEN = 11;
    /**
     * Current network is EVDO revision B
     */
    public static final int NETWORK_TYPE_EVDO_B = 12;
    /**
     * Current network is LTE
     */
    public static final int NETWORK_TYPE_LTE = 13;
    /**
     * Current network is eHRPD
     */
    public static final int NETWORK_TYPE_EHRPD = 14;
    /**
     * Current network is HSPA+
     */
    public static final int NETWORK_TYPE_HSPAP = 15;

    /**
     * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context
     */
    public static void cleanInternalCache(Context context) {
        deleteFilesByDirectory(context.getCacheDir());
        deleteFilesByDirectory(context.getExternalCacheDir());
//        cleanSharedPreference(context);
    }

    /**
     * 删除方法 这里只会删除某个文件夹下的文件
     * @param dir
     */
    private static boolean deleteFilesByDirectory(File dir) {

        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteFilesByDirectory(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    /**
     * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param
     * context
     */
    public static void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/"
                + context.getPackageName() + "/shared_prefs"));
    }

    /**
     * 获取缓存大小
     *
     * @param context
     * @return
     * @throws Exception
     */
    public static String getTotalCacheSize(Context context) throws Exception {
        long cacheSize = getFolderSize(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheSize += getFolderSize(context.getExternalCacheDir());
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 获取文件大小
     * Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
     * Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}

