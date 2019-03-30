package com.example.e321799.conecta4.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import TableroConecta4
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import com.example.e321799.conecta4.Activities.setColor
import com.example.e321799.conecta4.R
import es.uam.eps.multij.Tablero

class ERView(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {
    private val DEBUG = "ERView"
    private var numero: Int = 0
    private val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var heightOfTile: Float = 0.toFloat()
    private var widthOfTile: Float = 0.toFloat()
    private var radio: Float = 0.toFloat()
    private var board: TableroConecta4? = null
    private val ROWS = 6
    private val COLUMNS = 7
    private var onPlayListener: OnPlayListener? = null
    interface OnPlayListener {
        fun onPlay(column: Int)
    }

    init {

        backgroundPaint.strokeWidth = 16f
        linePaint.strokeWidth = 4f
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = 500
        val wMode: String
        val hMode: String
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        var widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        var heightSize = View.MeasureSpec.getSize(heightMeasureSpec)
        val width: Int
        val height: Int
        if (widthSize < heightSize) {
            heightSize = widthSize
            height = heightSize
            width = height
        } else {
            widthSize = heightSize
            height = widthSize
            width = height
        }
        setMeasuredDimension(height, width)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        widthOfTile = (w / COLUMNS).toFloat()
        heightOfTile = (h / ROWS).toFloat()
        if (widthOfTile < heightOfTile)
            radio = widthOfTile * 0.3f
        else
            radio = heightOfTile * 0.3f
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val boardWidth = width.toFloat()
        val boardHeight = height.toFloat()
        backgroundPaint.style = Paint.Style.STROKE
        backgroundPaint.color = ContextCompat.getColor(context, R.color.black)
        canvas.drawRect(0f, 0f, boardWidth, boardHeight, backgroundPaint)
        backgroundPaint.style = Paint.Style.FILL
        backgroundPaint.color = ContextCompat.getColor(context, R.color.color_board)
        canvas.drawRect(0f, 0f, boardWidth, boardHeight, backgroundPaint)
        drawCircles(canvas, linePaint)
    }

    private fun drawCircles(canvas: Canvas, paint: Paint) {
        var centerRaw: Float
        var centerColumn: Float
        for (i in 0 until ROWS) {
            val pos = i
            centerRaw = heightOfTile * (1 + 2 * pos) / 2f
            for (j in 0 until COLUMNS) {
                centerColumn = widthOfTile * (1 + 2 * j) / 2f
                paint.style = Paint.Style.STROKE
                paint.color = ContextCompat.getColor(context, R.color.black)
                canvas.drawCircle(centerColumn, centerRaw, radio, paint)
                paint.style = Paint.Style.FILL
                paint.setColor(board!!, i, j, context)
                canvas.drawCircle(centerColumn, centerRaw, radio, paint)
            }
        }
    }

    private fun fromEventToJ(event: MotionEvent): Int {
        return (event.x / widthOfTile).toInt()
    }

    fun setOnPlayListener(listener: OnPlayListener) {
        this.onPlayListener = listener
    }
    fun setBoard(board: TableroConecta4) {
        this.board = board
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (onPlayListener == null)
            return super.onTouchEvent(event)
        if (board!!.estado != Tablero.EN_CURSO) {
            return super.onTouchEvent(event)
        }
        if (event.action == MotionEvent.ACTION_DOWN) {
            try {
                onPlayListener?.onPlay(fromEventToJ(event))
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return true
    }

}