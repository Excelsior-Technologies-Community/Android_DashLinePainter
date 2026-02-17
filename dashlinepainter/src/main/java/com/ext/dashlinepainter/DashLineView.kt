package com.ext.dashlinepainter

import android.content.Context
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class DashLineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {

    companion object {
        const val HORIZONTAL = 0
        const val VERTICAL = 1
    }

    private var dashColor = 0xFF000000.toInt()
    private var dashWidth = 6f
    private var dashLength = 20f
    private var dashGap = 10f

    // NEW: Orientation variable
    private var orientation = HORIZONTAL

    private val dashPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        attrs?.let {
            val typedArray =
                context.obtainStyledAttributes(it, R.styleable.DashLineView)

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

            // NEW: Read orientation from XML
            orientation = typedArray.getInt(
                R.styleable.DashLineView_orientation,
                HORIZONTAL
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
            pathEffect = DashPathEffect(
                floatArrayOf(dashLength, dashGap),
                0f
            )
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (orientation == HORIZONTAL) {
            drawHorizontalLine(canvas)
        } else {
            drawVerticalLine(canvas)
        }
    }

    private fun drawHorizontalLine(canvas: Canvas) {
        val centerY = height / 2f

        canvas.drawLine(
            0f,
            centerY,
            width.toFloat(),
            centerY,
            dashPaint
        )
    }

    private fun drawVerticalLine(canvas: Canvas) {
        val centerX = width / 2f

        canvas.drawLine(
            centerX,
            0f,
            centerX,
            height.toFloat(),
            dashPaint
        )
    }
}
