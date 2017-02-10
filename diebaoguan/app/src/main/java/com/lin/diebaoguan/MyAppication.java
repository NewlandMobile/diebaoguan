package com.lin.diebaoguan;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v4.app.Fragment;
import android.text.TextUtils;

import com.lin.diebaoguan.common.CommonUtils;
import com.lin.diebaoguan.common.LogUtils;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.HashMap;

/**
 * It's Created by NewLand-JianFeng on 2016/12/27.
 */

public class MyAppication extends Application {

    private static MyAppication myAppication;
    private static String uid = null;
    private static String key = null;
    private static String userName = null;
    private static boolean isWifi = false;//网络类型是否是wifi状态
    private static boolean isPushNotification = false;//判断是否需要开启推送服务

    private static final String uidSPKey = "uid", keySPKey = "key", firstRunSPKey = "isFirstRun", userNameSPKey = "userName";
    //是否是第一次运行
    private static boolean isFirstRun = false;
    //是否已登录
    private static boolean isLogined;

    private static boolean blockImage = false;

    private static boolean settingWifi;

    private static boolean offlineDownload;

    private static int textSizeZoom;

    private static HashMap<String, Fragment> fragment_map = new HashMap<>();

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            LogUtils.e("======");
            initNetworkType();
        }
    };

    /**
     * 获取是否是wifi
     *
     * @return
     */
    public static boolean isWifi() {
        return isWifi;
    }

    public static boolean isPushNotification() {
        return isPushNotification;
    }

    public static void setIsPushNotification(boolean isPushNotification) {
        MyAppication.isPushNotification = isPushNotification;
        CommonUtils.saveBySp(MyAppication.getInstance(), "isPushNotification", isPushNotification);
    }


    @Override
    public void onCreate() {
        super.onCreate();
        myAppication = this;
        initGlobalValiable();
        initImageLoader(getApplicationContext());
        initNetworkType();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver, filter);
    }

    /**
     * 判断当前网络状态
     */
    private void initNetworkType() {
        String networkType = CommonUtils.getNetworkType(this);
        if ("Wi-Fi".equals(networkType)) {
            isWifi = true;
        } else {
            isWifi = false;
        }
    }

    /**
     * 对设置页相关的设置项做一个初始化，方便页面取用
     */
    private void initSettingValue() {
        blockImage = (boolean) CommonUtils.getSp(this, "blockImage", false);
        textSizeZoom = (int) CommonUtils.getSp(this, "textSizeZoom", 100);
        settingWifi = (boolean) CommonUtils.getSp(this, "settingWifi", false);
        offlineDownload = (boolean) CommonUtils.getSp(this, "offlineDownload", false);
        isPushNotification = (boolean) CommonUtils.getSp(this, "isPushNotification", false);
//        TODO  把设置项都初始化  从SP获取
    }

    /**
     * 从 SharePreference 做一个全局变量的初始化
     */
    private void initGlobalValiable() {
        initSettingValue();
        uid = (String) CommonUtils.getSp(this, uidSPKey, "");
        key = (String) CommonUtils.getSp(this, keySPKey, "");
        isFirstRun = (boolean) CommonUtils.getSp(this, firstRunSPKey, false);
        userName = (String) CommonUtils.getSp(this, userNameSPKey, "");
    }

    public static boolean hasLogined() {
        if (TextUtils.isEmpty(uid) || TextUtils.isEmpty(key)) {
            return false;
        } else {
            return true;
        }
    }

    public static void logout() {
        //TODO  退出账号  删除 账户相关信息
        CommonUtils.deleteSp(MyAppication.getInstance(), uidSPKey);
        CommonUtils.deleteSp(MyAppication.getInstance(), keySPKey);
    }

    public static MyAppication getInstance() {
        if (myAppication == null) {
            myAppication = new MyAppication();
        }
        return myAppication;
    }

    public static String getUid() {
        return uid;
    }

    public static void setUid(String uid) {
        MyAppication.uid = uid;
        CommonUtils.saveBySp(MyAppication.getInstance(), uidSPKey, uid);
    }

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        MyAppication.key = key;
        CommonUtils.saveBySp(MyAppication.getInstance(), keySPKey, key);
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        MyAppication.userName = userName;
        CommonUtils.saveBySp(MyAppication.getInstance(), userNameSPKey, userName);
    }

    public static boolean isBlockImage() {
        return blockImage;
    }

    public static void setBlockImage(boolean blockImage) {
        MyAppication.blockImage = blockImage;
        CommonUtils.saveBySp(MyAppication.getInstance(), "blockImage", blockImage);
    }

    public static int getTextSizeZoom() {
        return textSizeZoom;
    }

    public static void setTextSizeZoom(int textSizeZoom) {
        MyAppication.textSizeZoom = textSizeZoom;
        CommonUtils.saveBySp(MyAppication.getInstance(), "textSizeZoom", textSizeZoom);
    }

    public static boolean isSettingWifi() {
        return settingWifi;
    }

    public static void setSettingWifi(boolean settingWifi) {
        MyAppication.settingWifi = settingWifi;
        CommonUtils.saveBySp(MyAppication.getInstance(), "settingWifi", settingWifi);
    }

    public static boolean isOfflineDownload() {
        return offlineDownload;
    }

    public static void setOfflineDownload(boolean offlineDownload) {
        MyAppication.offlineDownload = offlineDownload;
        CommonUtils.saveBySp(MyAppication.getInstance(), "offlineDownload", offlineDownload);
    }

    /**
     * image
     *
     * @param context
     */
    private void initImageLoader(Context context) {
        // 使用默认的配置
        // ImageLoaderConfiguration configuration =
        // ImageLoaderConfiguration.createDefault(this);
        // 使用自定义的配置（官方版）
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(
                context);
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.diskCacheFileNameGenerator(new Md5FileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024); // 50 MiB
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        config.writeDebugLogs();

        // 可以设置自己想要的 不需要的不用设置（较全面）
        // File cacheDir = StorageUtils.getCacheDirectory(context); //缓存文件夹路径
        // ImageLoaderConfiguration config = new
        // ImageLoaderConfiguration.Builder(context)
        // .memoryCacheExtraOptions(480, 800) // default = device screen
        // dimensions 内存缓存文件的最大长宽
        // // .diskCacheExtraOptions(480, 800, null) //
        // 本地缓存的详细信息(缓存的最大长宽)，最好不要设置这个
        // // .taskExecutor(...)
        // // .taskExecutorForCachedImages(...)
        // .threadPoolSize(3) // default 线程池内加载的数量
        // .threadPriority(Thread.NORM_PRIORITY - 2) // default 设置当前线程的优先级
        // .tasksProcessingOrder(QueueProcessingType.LIFO)
        // .denyCacheImageMultipleSizesInMemory()
        // .memoryCache(new LruMemoryCache(2 * 1024 * 1024)) //可以通过自己的内存缓存实现
        // .memoryCacheSize(2 * 1024 * 1024) // 内存缓存的最大值
        // .memoryCacheSizePercentage(13) // default
        // .diskCache(new UnlimitedDiskCache(cacheDir)) // default 可以自定义缓存路径
        // .diskCacheSize(50 * 1024 * 1024) // 50 Mb sd卡(本地)缓存的最大值
        // .diskCacheFileCount(100) // 可以缓存的文件数量
        // // default为使用HASHCODE对UIL进行加密命名， 还可以用MD5(new
        // Md5FileNameGenerator())加密
        // .diskCacheFileNameGenerator(new Md5FileNameGenerator())
        // .imageDownloader(new BaseImageDownloader(context)) // default
        // .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) //
        // default
        // .writeDebugLogs() // 打印debug log
        // .build(); //开始构建
        // 初始化操作
        ImageLoader.getInstance().init(config.build());
    }


    public static void addFragment(String log, Fragment fragment) {
        fragment_map.put(log, fragment);
    }

    public static HashMap<String, Fragment> getFragmentList() {
        return fragment_map;
    }
}
