package cn.isif.backservice;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

/**
 * AccessibilityService 原本是用来帮助有障碍的人更好的使用手机
 * 其可以监听前台事件
 */
public class MonitorService extends AccessibilityService {
    public final String TAG = "MonitorService";



    @Override
    protected void onServiceConnected() {
        Log.d(TAG,"onServiceConnected");
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.d(TAG, "onAccessibilityEvent: " + accessibilityEvent.toString());
        Log.d(TAG,accessibilityEvent.getPackageName().toString());
        Log.d(TAG,accessibilityEvent.getClassName().toString());
    }

    @Override
    public void onInterrupt() {

    }
}
