package com.lin.diebaoguan.common;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.lin.diebaoguan.MyApplication;
import com.lin.diebaoguan.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class IMAGEUtils {
    private static DisplayImageOptions options;
    private static DisplayImageOptions optionsCorner;
    private static ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            LogUtils.d("imageUri:" + imageUri);
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 3000);
                    displayedImages.add(imageUri);
                }
            }
        }
    }

    /**
     * 显示图片
     *
     * @param imageUrls  图片url
     * @param mImageView 目标控件
     */
    public static void displayImage(String imageUrls, ImageView mImageView) {
        // 如果 要屏蔽图片下载 就不执行具体
        if (MyApplication.isBlockImage()) {
            if (MyApplication.isWifi()) {
                initOptions();
                ImageLoader.getInstance().displayImage(imageUrls, mImageView, options, animateFirstListener);
            } else {
                mImageView.setBackgroundResource(R.drawable.default_article_list);
            }
            return;
        } else {
            initOptions();
            ImageLoader.getInstance().displayImage(imageUrls, mImageView, options, animateFirstListener);
        }
    }

    /**
     * 显示图片
     *
     * @param imageUrls
     * @param mImageView
     * @param cornerRadiusPixels
     * @param defaultImage       默认图片
     */
    public static void displayImageWithRounder(String imageUrls,
                                               ImageView mImageView, int cornerRadiusPixels, int defaultImage) {
        initOptions(cornerRadiusPixels, defaultImage);
        ImageLoader.getInstance().displayImage(imageUrls, mImageView,
                optionsCorner, animateFirstListener);
    }

    /**
     * 默认初始化
     */
    private static void initOptions() {
        if (options == null) {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.default_article_list)
                    .showImageForEmptyUri(R.drawable.default_article_list)
                    .showImageOnFail(R.drawable.default_article_list).cacheInMemory(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .cacheOnDisk(true).considerExifParams(true).build();
        }
    }

    /**
     * 对要显示的图片的各种格式displayImageOptions的设置
     * <p>
     *
     * @param cornerRadiusPixels 弧度,圆角
     * @param defaultImage       设置默认图片
     */

    private static void initOptions(int cornerRadiusPixels, int defaultImage) {
        if (optionsCorner == null) {
            optionsCorner = new DisplayImageOptions.Builder()
                    .showImageOnLoading(defaultImage)// 设置图片在下载期间显示的图片
                    .showImageForEmptyUri(defaultImage)// 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(defaultImage) // 设置图片加载/解码过程中错误时候显示的图片
                    .cacheInMemory(true)// 设置下载的图片是否缓存在内存
                    .cacheOnDisk(true)// 设置下载的图片是否缓存在SD卡中
                    .considerExifParams(true) // 是否考虑JPEG图像EXIF参数（旋转，翻转�?
                    .displayer(new RoundedBitmapDisplayer(cornerRadiusPixels))// 是否设置为圆角，弧度
                    // .displayer(new
                    // FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时�?
                    // .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示
                    // .bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类�?/
                    // .decodingOptions(android.graphics.BitmapFactory.Options
                    // decodingOptions)//设置图片的解码配�?
                    // .delayBeforeLoading(int delayInMillis)//int
                    // delayInMillis为你设置的下载前的延迟时�?
                    // 设置图片加入缓存前，对bitmap进行设置
                    // .preProcessor(BitmapProcessor preProcessor)
                    // .resetViewBeforeLoading(true)//设置图片在下载前是否重置
                    .build();// 构建完成
        }
    }
}
