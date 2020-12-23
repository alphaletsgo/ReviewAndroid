package cn.isif.reviewandroid.views

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView

class MyTextView : AppCompatTextView {
    constructor(context: Context?) : super(context!!) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {}

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> Log.d(TAG,"dispatchTouchEvent ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.d(TAG,"dispatchTouchEvent ACTION_MOVE")
            MotionEvent.ACTION_UP -> Log.d(TAG,"dispatchTouchEvent ACTION_UP")
            MotionEvent.ACTION_CANCEL -> Log.d(TAG,"dispatchTouchEvent ACTION_CANCEL")
        }
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> Log.d(TAG,"onTouchEvent ACTION_DOWN")
            MotionEvent.ACTION_MOVE -> Log.d(TAG,"onTouchEvent ACTION_MOVE")
            MotionEvent.ACTION_UP -> Log.d(TAG,"onTouchEvent ACTION_UP")
            MotionEvent.ACTION_CANCEL -> Log.d(TAG,"onTouchEvent ACTION_CANCEL")
        }
        return super.onTouchEvent(event)
    }
    companion object{
        const val TAG = "MyTextView"
    }
}