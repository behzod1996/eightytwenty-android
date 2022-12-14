package kr.sns.ui_expandable_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.annotation.LayoutRes
import androidx.core.view.isGone

fun View.expand(
    maxExpandedHeight: Int,
    animationDuration: Long,
    completionListener: (() -> Unit)? = null
) {
    measure(MATCH_PARENT, WRAP_CONTENT)
    val targetHeight = Math.min(measuredHeight, maxExpandedHeight)

    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    layoutParams.height = 1
    visibility = View.VISIBLE

    val animation = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            layoutParams.height = when (interpolatedTime) {
                1f -> {
                    completionListener?.invoke()
                    WRAP_CONTENT
                }
                else -> (targetHeight * interpolatedTime).toInt()
            }
            requestLayout()
        }

        override fun willChangeBounds() = true
    }
    animation.duration = animationDuration
    startAnimation(animation)
}

fun View.collapse(
    animationDuration: Long,
    completionListener: (() -> Unit)? = null
) {
    val initialHeight = measuredHeight

    val a = object : Animation() {
        override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
            if (interpolatedTime == 1f) {
                isGone = true
                completionListener?.invoke()
            } else {
                layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                requestLayout()
            }
        }

        override fun willChangeBounds() = true
    }
    a.duration = animationDuration
    startAnimation(a)
}

fun ViewGroup.inflate(@LayoutRes resId: Int, attachToRoot: Boolean = false): View =
    LayoutInflater.from(context).inflate(resId, this, attachToRoot)