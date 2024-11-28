package cn.isif.reviewandroid.views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View

class TagsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    var tagsList: ArrayList<Tag>? = null
    val dm: DisplayMetrics by lazy {
        resources.displayMetrics
    }

    private val mTextPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            style = Paint.Style.FILL
            textSize = 15 * dm.scaledDensity
            textAlign = Paint.Align.LEFT
        }
    }

    fun setTags(tags: List<String>?) {
        tags?.let {
            tagsList = ArrayList(tags.size)
            it.forEach { text ->
                tagsList!!.add(Tag(text, calculateTextBounds(text)))
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        canvas?.let { ca ->
            tagsList?.forEach {
                val fm = mTextPaint.fontMetrics
                val baseline = it.bounds.height() / 2f + (fm.bottom - fm.top) / 2 - fm.bottom
                ca.drawText(it.text, it.bounds.left.toFloat(), baseline, mTextPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        Log.d(TAG, "UNSPECIFIED:${MeasureSpec.UNSPECIFIED}")
        Log.d(TAG, "EXACTLY:${MeasureSpec.EXACTLY}")
        Log.d(TAG, "AT_MOST:${MeasureSpec.AT_MOST}")
        Log.d(
            TAG,
            "mode:${MeasureSpec.getMode(widthMeasureSpec)},width:${
                MeasureSpec.getSize(widthMeasureSpec)
            }"
        )
        Log.d(
            TAG,
            "mode:${MeasureSpec.getMode(heightMeasureSpec)},height:${
                MeasureSpec.getSize(heightMeasureSpec)
            }"
        )
        val measureWidth = MeasureSpec.getSize(widthMeasureSpec)
        var lastTag: Tag? = null
        tagsList?.forEach {
            var bounds = it.bounds
            if (lastTag == null) {
                it.bounds = Rect(0, 0, bounds.width(), bounds.height())
            } else {
                var bs = lastTag!!.bounds
                it.bounds = if (bs.right + it.bounds.width() >= measureWidth) {
                    Rect(0, bs.bottom, bounds.width(), bs.bottom + bounds.height())
                } else {
                    Rect(
                        it.bounds.right,
                        bs.bottom,
                        bs.right + it.bounds.width(),
                        bs.bottom + it.bounds.height()
                    )
                }
            }
            lastTag = it
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun calculateTextBounds(text: String): Rect {
        return Rect().apply {
            mTextPaint.getTextBounds(text, 0, text.length, this)
            Log.d(TAG, "text width:${this.width()},height:${this.height()}")
        }
    }

    class Tag(val text: String, var bounds: Rect)
    companion object {
        const val TAG = "TagsView"
    }
}