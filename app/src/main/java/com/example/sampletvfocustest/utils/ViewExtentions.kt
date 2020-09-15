package com.example.sampletvfocustest.utils

import android.animation.Animator
import android.graphics.drawable.GradientDrawable
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.leanback.widget.BaseGridView
import com.example.sampletvfocustest.R

/**
 * setMessage method sets the text, change font color and font Size
 */
fun TextView.setMessage(message: String, @ColorInt textColor: Int, textSize: Float) {
    text = message
    setTextColor(textColor)
    this.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)
}

fun View.setGradientDrawable(@ColorInt backgroundColor: Int, cornerRadius: Float) {
    background = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        setColor(backgroundColor)
        this.cornerRadius = cornerRadius
    }
}

fun View.animateOnFocus(
    hasFocus: Boolean,
    focusScaleX: Float = Constants.VIEW_FOCUS_SCALE,
    focusScaleY: Float = Constants.VIEW_FOCUS_SCALE
) {
    if (hasFocus) {
        this.animate()
            .scaleX(focusScaleX)
            .scaleY(focusScaleY).duration =
            Constants.ANIMATE_FOCUS_DURATION_MS
    } else {
        this.animate().scaleX(1f).scaleY(1f).duration =
            Constants.ANIMATE_FOCUS_DURATION_MS
    }
}

fun TextView.leftDrawable(@DrawableRes id: Int = 0, @DimenRes sizeRes: Int) {
    val drawable = ContextCompat.getDrawable(context, id)
    val size = resources.getDimensionPixelSize(sizeRes)
    drawable?.setBounds(0, 0, size, size)
    this.setCompoundDrawables(drawable, null, null, null)
}

fun BaseGridView.setFocusedItemAtStart(
    windowAlignLowEdge: Int = BaseGridView.WINDOW_ALIGN_LOW_EDGE,
    paddingStart: Int = context.resources.getDimensionPixelSize(R.dimen.row_padding_start_home)
) {
    windowAlignment = windowAlignLowEdge
    windowAlignmentOffsetPercent = 0f
    windowAlignmentOffset = paddingStart
    itemAlignmentOffsetPercent = 0f
}

fun EditText.showPassword() {
    transformationMethod = HideReturnsTransformationMethod.getInstance()
}

fun EditText.hidePassword() {
    transformationMethod = PasswordTransformationMethod.getInstance()
}

fun FragmentActivity.addFragmentWithAddToBackStack(fragment: Fragment) {
    supportFragmentManager.beginTransaction().add(android.R.id.content, fragment)
        .addToBackStack(fragment.tag).commit()
}

fun FragmentActivity.replaceFragmentWithAddToBackStack(fragment: Fragment) {
    supportFragmentManager.beginTransaction().replace(android.R.id.content, fragment)
        .addToBackStack(fragment.tag).commit()
}

fun Fragment.popBackStackImmediate() {
    activity?.supportFragmentManager?.popBackStackImmediate()
}

fun View.animateProfileIconOnFocus(
    hasFocus: Boolean,
    focusScale: Float = Constants.VIEW_FOCUS_SCALE
) {
    if (hasFocus) {
        this.animate().scaleX(1f).scaleY(1f).duration =
            Constants.ANIMATE_FOCUS_DURATION_MS
    } else {
        this.animate().setListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
            }

            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                this@animateProfileIconOnFocus.isVisible = true
            }
        }).scaleX(focusScale)
            .scaleY(focusScale).duration = Constants.ANIMATE_FOCUS_DURATION_MS
    }
}

/**
 * animate card alpha
 * @param hasFocus true [alpha =0] else [alpha=1]
 */
fun View.animateCardViewAlpha(hasFocus: Boolean) {
    alpha = if (hasFocus) 1f else 0f
    animate().alpha(if (hasFocus) 0f else 1f).setDuration(CARD_ALPHA_DURATION).start()
}

const val CARD_ALPHA_DURATION = 50L
