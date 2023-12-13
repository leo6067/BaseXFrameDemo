package com.xy.xframework.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import jp.wasabeef.glide.transformations.*
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation
import jp.wasabeef.glide.transformations.gpu.PixelationFilterTransformation
import jp.wasabeef.glide.transformations.gpu.SepiaFilterTransformation
import jp.wasabeef.glide.transformations.gpu.SketchFilterTransformation


/**
 * author: Leo
 * createDate: 2023/12/6 14:29
 */
class GlideUtil : AppGlideModule() {


    companion object {


        /**
         * @param obj       这里obj 只加载 url bitmap  drawable
         * @param imageView 需要加载的图片
         * @describe 加载正常图片
         * @note 这里并没有加载错图，在有错图的时候设置 error()
         */

        @JvmStatic
        fun loadImage(obj: Any?, imageView: ImageView) {
            if (obj is String) {
                Glide.with(imageView.context).load(obj)
                    .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                    .fallback(getErrorImage()).placeholder(getPlaceholder()).into(imageView)
            }
            if (obj is Bitmap) {
                Glide.with(imageView.context).load(obj)
                    .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                    .fallback(getErrorImage()).placeholder(getPlaceholder()).into(imageView)
            }
            if (obj is Drawable) {
                Glide.with(imageView.context).load(obj)
                    .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                    .fallback(getErrorImage()).placeholder(getPlaceholder()).into(imageView)
            }
        }

        /**
         * @param context   当前Activity的上下文对象
         * @param obj
         * @param imageView
         * @describe 与没有context的方法相比 不易导致 内存泄漏问题，原因 activity销毁的时候 imageView 的上下文对象自然不存在
         */
        @JvmStatic
        fun loadImage(context: Context, obj: Any?, imageView: ImageView) {
            if (obj is String) {
                Glide.with(context).load(obj)
                    .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                    .fallback(getErrorImage()).placeholder(getPlaceholder()).into(imageView)
            }
            if (obj is Bitmap) {
                Glide.with(context).load(obj)
                    .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                    .fallback(getErrorImage()).placeholder(getPlaceholder()).into(imageView)
            }
            if (obj is Drawable) {
                Glide.with(context).load(obj)
                    .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                    .fallback(getErrorImage()).placeholder(getPlaceholder()).into(imageView)
            }
        }

        /**
         * @describe 加载圆形图
         * @param context   当前Activity的上下文对象
         * @param imageView
         */
        @JvmStatic
        fun loadCircleImage(context: Context, url: String?, imageView: ImageView) {
            Glide.with(context).load(url)
                .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                .placeholder(getPlaceholder())
                .fallback(getErrorImage()).circleCrop().into(imageView)
        }

        /**
         * @describe 加载正方形图片
         * @param context   当前Activity的上下文对象
         * @param imageView
         */
        @JvmStatic
        fun loadSquareImage(context: Context, url: String?, imageView: ImageView) {
            Glide.with(context).load(url).apply(initOptions(CropSquareTransformation()))
                .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                .placeholder(getPlaceholder())
                .fallback(getErrorImage()).circleCrop().into(imageView)
        }

        /**
         * @describe 加载黑白图片
         * @param context   当前Activity的上下文对象
         * @param imageView
         */
        @JvmStatic
        fun loadGrayscaleImage(context: Context, url: String?, imageView: ImageView) {
            Glide.with(context).load(url).apply(initOptions(GrayscaleTransformation()))
                .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                .placeholder(getPlaceholder())
                .fallback(getErrorImage()).circleCrop().into(imageView)
        }

        /**
         * @describe 加载圆角图片  默认所有圆角
         * @param context   当前Activity的上下文对象
         * @param imageView
         * @param radius 圆角
         */
         @JvmStatic
        fun loadGrayscaleImage(context: Context, url: String?, imageView: ImageView, radius: Int) {
            Glide.with(context).load(url)
                .apply(
                    initOptions(
                        RoundedCornersTransformation(
                            radius,
                            0,
                            RoundedCornersTransformation.CornerType.ALL
                        )
                    )
                )
                .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                .placeholder(getPlaceholder())
                .fallback(getErrorImage()).circleCrop().into(imageView)
        }

        /**
         * @describe 加载圆角图片
         * @param context   当前Activity的上下文对象
         * @param imageView
         * @param radius 圆角
         * @param cornerType 圆角类型
         */
        @JvmStatic
        fun loadGrayscaleImage(
            context: Context,
            url: String?,
            imageView: ImageView,
            radius: Int,
            cornerType: RoundedCornersTransformation.CornerType?
        ) {
            Glide.with(context).load(url)
                .apply(initOptions(RoundedCornersTransformation(radius, 0, cornerType)))
                .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                .placeholder(getPlaceholder())
                .fallback(getErrorImage()).circleCrop().into(imageView)
        }

        /**
         * @describe 自定义裁剪
         * @param context   当前Activity的上下文对象
         * @param imageView
         * @param width,height 圆角宽高
         * @param cropType 裁剪位置
           */
        @JvmStatic
        fun loadCropTransformationImage(
            context: Context,
            url: String?,
            imageView: ImageView,
            width: Int,
            height: Int,
            cropType: CropTransformation.CropType?
        ) {
            Glide.with(context).load(url)
                .apply(initOptions(CropTransformation(width, height, cropType)))
                .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                .placeholder(getPlaceholder())
                .fallback(getErrorImage()).circleCrop().into(imageView)
        }

        /**
         * @describe 自定义裁剪 默认居中裁剪
         * @param context   当前Activity的上下文对象
         * @param imageView
         * @param width,height 圆角宽高
         */
        @JvmStatic
        fun loadCropTransformationImage(
            context: Context,
            url: String?,
            imageView: ImageView,
            width: Int,
            height: Int
        ) {
            Glide.with(context).load(url)
                .apply(
                    initOptions(
                        CropTransformation(
                            width,
                            height,
                            CropTransformation.CropType.CENTER
                        )
                    )
                )
                .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                .placeholder(getPlaceholder())
                .fallback(getErrorImage()).circleCrop().into(imageView)
        }

        /**
         * @describe 加载动图gif
         * @param context
         * @param url
         * @param imageView
         */
        @JvmStatic
        fun loadGifImage(
            context: Context, url: String?, imageView: ImageView

        ) {
            Glide.with(context).asGif()
                .skipMemoryCache(isSkipMemoryCache()).load(url).error(getErrorImage())
                .placeholder(getPlaceholder())
                .fallback(getErrorImage()).circleCrop().into(imageView)
        }

        /**
         * @describe 加载动图gif
         * @param url
         * @param imageView
         */
        @JvmStatic
        fun loadGifImage(url: String?, imageView: ImageView) {
            Glide.with(imageView.context).asGif()
                .skipMemoryCache(isSkipMemoryCache()).load(url).error(getErrorImage())
                .placeholder(getPlaceholder()).fallback(getErrorImage()).circleCrop()
                .into(imageView)
        }

        /**
         * @describe 加载高斯模糊大图
         * @param ambiguity 模糊度  eg ：80
         */
        @JvmStatic
        fun loadTransformImage(url: String?, imageView: ImageView, ambiguity: Int) {
            Glide.with(imageView.context).load(url).skipMemoryCache(isSkipMemoryCache())
                .fallback(getErrorImage()).placeholder(getPlaceholder()).error(getErrorImage())
                .apply(initOptions(BlurTransformation(ambiguity)))
                .into(imageView)
        }

        /**
         * @describe 加载缩略图
         * @param sizeMultiplier 如设置0.2f缩略
         */
        @JvmStatic
        fun loadThumbnailImage(url: String?, imageView: ImageView, sizeMultiplier: Float) {
            Glide.with(imageView.context).load(url)
                .skipMemoryCache(isSkipMemoryCache())
                .thumbnail()
                .thumbnail(sizeMultiplier) //缩略的参数
                .apply(initOptions())
                .into(imageView)
        }

        /**
         * @describe 设置滤镜 （陈旧）
         * @param context   当前Activity的上下文对象
         * @param imageView
         */
        @JvmStatic
        fun loadSepiaFilterTransformationImage(
            context: Context, url: String?, imageView: ImageView
        ) {
            Glide.with(context).load(url).apply(initOptions(SepiaFilterTransformation(1.0f)))
                .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                .placeholder(getPlaceholder())
                .fallback(getErrorImage()).circleCrop().into(imageView)
        }

        /**
         * @describe 设置滤镜 （亮度）
         * @param context   当前Activity的上下文对象
         * @param imageView
         */
        @JvmStatic
        fun loadBrightnessFilterTransformationImage(
            context: Context,
            url: String?,
            imageView: ImageView

        ) {
            Glide.with(context).load(url).apply(initOptions(BrightnessFilterTransformation(0.5f)))
                .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                .placeholder(getPlaceholder())
                .fallback(getErrorImage()).circleCrop().into(imageView)
        }

        /**
         * @describe 设置滤镜 （马赛克）
         * @param context   当前Activity的上下文对象
         * @param imageView
         */
        @JvmStatic
        fun loadPixelationFilterTransformationImage(
            context: Context,
            url: String?,
            imageView: ImageView

        ) {
            Glide.with(context).load(url).apply(initOptions(PixelationFilterTransformation(20f)))
                .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                .placeholder(getPlaceholder())
                .fallback(getErrorImage()).circleCrop().into(imageView)
        }

        /**
         * @describe 设置滤镜 （素描画）
         * @param context   当前Activity的上下文对象
         * @param imageView
         */
        @JvmStatic
        fun loadSketchFilterTransformationImage(
            context: Context,
            url: String?,
            imageView: ImageView

        ) {
            Glide.with(context).load(url).apply(initOptions(SketchFilterTransformation()))
                .skipMemoryCache(isSkipMemoryCache()).error(getErrorImage())
                .placeholder(getPlaceholder())
                .fallback(getErrorImage()).circleCrop().into(imageView)
        }


        /**
         * @return  设置全局的错误图片 防止更改时多地方修改
         * @describe 当图片加载失败的时候显示
         */
        @JvmStatic
        @DrawableRes
        private fun getErrorImage(): Int {
            return android.R.drawable.stat_notify_error

        }

        /**
         * @return 设置全局的占位图 防止更改时多地方修改
         * @describe 当图片没有加载出来的时候显示
         */
        @JvmStatic
        @DrawableRes
        private fun getPlaceholder(): Int {
            return android.R.drawable.stat_notify_error

        }

        /**
         * @return 返回当前石头 跳过内存缓存
         * true 不缓存 false 缓存
         */
        @JvmStatic
        private fun isSkipMemoryCache(): Boolean {
            return false
        }


        /**
         * @describe 设置缓存
         * Glide有两种缓存机制，一个是内存缓存，一个是硬盘缓存。
         * 内存缓存的主要作用是防止应用重复将图片数据读取到内存当中，
         * 而硬盘缓存的主要作用是防止应用重复从网络或其他地方重复下载和读取数据
         * @diskCacheStrategy参数
         * DiskCacheStrategy.NONE： 表示不缓存任何内容
         * DiskCacheStrategy.DATA： 表示只缓存原始图片
         * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片
         * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片
         * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）
         * @return 这里默认设置全部为禁止缓存
         */
        @JvmStatic
        private fun initOptions(transformation: BitmapTransformation): RequestOptions {
            return RequestOptions()
                .transform(transformation)
                .skipMemoryCache(isSkipMemoryCache()) //是否允许内存缓存
                .onlyRetrieveFromCache(true) //是否只从缓存加载图片
                .diskCacheStrategy(DiskCacheStrategy.NONE) //禁止磁盘缓存
        }
        @JvmStatic
        private fun initOptions(): RequestOptions {
            return RequestOptions()
                .skipMemoryCache(isSkipMemoryCache()) //是否允许内存缓存
                .onlyRetrieveFromCache(true) //是否只从缓存加载图片
                .diskCacheStrategy(DiskCacheStrategy.NONE) //禁止磁盘缓存
        }

        /**
         * @describe 清除内容缓存
         */
        @JvmStatic
        fun clearMemory(
            context: Context
        ) {
            Glide.get(context).clearMemory()
        }

        /**
         * @describe 清除磁盘缓存
         */
        @JvmStatic
        fun clearDiskCache(
            context: Context
        ) {
            Glide.get(context).clearDiskCache()
        }

        /**
         * @describe 设置加载的效果
         * @param transformation
         * @return
         */
        @JvmStatic
        private fun bitmapTransform(transformation: BitmapTransformation): RequestOptions? {
            return RequestOptions()
        }
    }

}