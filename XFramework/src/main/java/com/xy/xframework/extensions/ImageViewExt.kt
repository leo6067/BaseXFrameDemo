package com.xy.xframework.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import java.io.File

/**
 *
 * 加载lottie图，缓存需要加名字
 */
@BindingAdapter("loadAssetLottieDrawable")
fun ImageView.loadAssetLottieDrawable(
    loadAssetLottieDrawable: String,
    cacheName: String? = null
): LottieDrawable {
    var mLottieDrawable = LottieDrawable()
    LottieCompositionFactory.fromAsset(
        context, loadAssetLottieDrawable, cacheName
    ).addListener { _it ->
        mLottieDrawable.composition = _it
        this.setImageDrawable(mLottieDrawable)
    }
    return mLottieDrawable
}


/**
 *
 * 加载lottie图，缓存需要加名字
 */
@BindingAdapter("loadFileLottieDrawable")
fun ImageView.loadFileLottieDrawable(
    loadFileLottieDrawable: String,
    cacheName: String? = null
): LottieDrawable? {
    var file = File(loadFileLottieDrawable)
    if (file.exists()) {
        var mLottieDrawable = LottieDrawable()
        LottieCompositionFactory.fromJsonInputStream(
            file.inputStream(), cacheName
        ).addListener { _it ->
            mLottieDrawable.composition = _it
            this.setImageDrawable(mLottieDrawable)
        }
        return mLottieDrawable
    } else {
        return null
    }
}


fun clearLottieDrawable(mImageView: ImageView? = null, lottie: LottieDrawable?) {
    mImageView?.setImageDrawable(null)
    lottie?.clearComposition()
    lottie?.callback = null
}