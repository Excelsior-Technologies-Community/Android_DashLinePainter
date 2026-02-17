package com.ext.dashlinepainter

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PathEffect
import android.graphics.DashPathEffect
import android.util.AttributeSet
import android.view.View

class DashLineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    private var dashColor = 0xFF000000.toInt()
    private var dashWidth = 6f
    private var dashLength = 20f
    private var dashGap = 10f

    private val dashPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.DashLineView)

            dashColor = typedArray.getColor(
                R.styleable.DashLineView_dashColor,
                dashColor
            )

            dashWidth = typedArray.getDimension(
                R.styleable.DashLineView_dashWidth,
                dashWidth
            )

            dashLength = typedArray.getDimension(
                R.styleable.DashLineView_dashLength,
                dashLength
            )

            dashGap = typedArray.getDimension(
                R.styleable.DashLineView_dashGap,
                dashGap
            )

            typedArray.recycle()
        }

        setupPaint()
    }

    private fun setupPaint() {
        dashPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = dashWidth
            color = dashColor
            pathEffect = DashPathEffect(floatArrayOf(dashLength, dashGap), 0f)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawLine(
            0f,
            height / 2f,
            width.toFloat(),
            height / 2f,
            dashPaint
        )
    }
}

