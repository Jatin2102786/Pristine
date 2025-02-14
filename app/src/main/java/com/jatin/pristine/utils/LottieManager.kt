package com.jatin.pristine.utils

import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import android.widget.FrameLayout
import com.airbnb.lottie.LottieAnimationView

object LottieManager {
    private var lottieAnimationView: LottieAnimationView? = null


    fun initialize(context: Context, animationResId: Int) {
        if (lottieAnimationView == null) {
            lottieAnimationView = LottieAnimationView(context).apply {
                setAnimation(animationResId)
                repeatCount = -1
            }
        }
    }


    fun attachToParent(parent: ViewGroup) {
        lottieAnimationView?.let { view ->
            if (view.parent == null) {
                val layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER
                }
                view.layoutParams = layoutParams
                parent.addView(view)
            }
        }
    }


    fun startAnimation() {
        lottieAnimationView?.let {
            if (!it.isAnimating) {
                it.playAnimation()
            }
        }
    }


    fun stopAnimation() {
        lottieAnimationView?.let {
            if (it.isAnimating) {
                it.cancelAnimation()
            }
        }
    }


    fun detachFromParent() {
        lottieAnimationView?.let { view ->
            (view.parent as? ViewGroup)?.removeView(view)
        }
    }
}