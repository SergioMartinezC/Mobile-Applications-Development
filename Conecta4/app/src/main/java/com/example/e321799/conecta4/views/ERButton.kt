package com.example.e321799.conecta4.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.widget.Button
import com.example.e321799.conecta4.R

class ERButton(context: Context, attributeSet: AttributeSet) :
Button(context, attributeSet) {
    private var radious: Float = 0.toFloat()
    private var currentColor = R.color.colorPrimary
    private val paint: Paint
    init {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val x = 0.5f * width
        val y = 0.5f * height
        width = if (width < height) width else height
        radious = 0.15f * width
        paint.color = ContextCompat.getColor(context, currentColor)
        canvas?.drawCircle(x, y, radious, paint)
    }
    fun human() {
        currentColor = R.color.blue_token
        invalidate()
    }
    fun randomPlayer() {
        currentColor = R.color.red_token
        invalidate()
    }
    fun notClicked() {
        currentColor = R.color.colorPrimary
        invalidate()
    }
}