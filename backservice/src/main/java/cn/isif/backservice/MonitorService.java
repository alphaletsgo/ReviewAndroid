package cn.isif.backservice;

import android.accessibilityservice.AccessibilityService;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.List;

/**
 * AccessibilityService 原本是用来帮助有障碍的人更好的使用手机
 * 其可以监听前台事件
 */
public class MonitorService extends AccessibilityService {
    public final String TAG = "MonitorService";


    @Override
    protected void onServiceConnected() {
        Log.d(TAG, "onServiceConnected");
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        switch (accessibilityEvent.getAction()) {
            case AccessibilityEvent.TYPE_VIEW_CLICKED:
            case AccessibilityEvent.TYPE_VIEW_LONG_CLICKED:
                Log.d(TAG, "CLICK EVENT");
                break;
            default:
                Log.d(TAG,"" + accessibilityEvent.getAction());
                break;
        }
//        Log.d(TAG, "onAccessibilityEvent: " + accessibilityEvent.toString());
//        if (!TextUtils.isEmpty(accessibilityEvent.getPackageName())) {
//            Log.d(TAG + "-" + "packageName", accessibilityEvent.getPackageName().toString());
//        }
//        if (!TextUtils.isEmpty(accessibilityEvent.getClassName())) {
//            Log.d(TAG + "-" + "className", accessibilityEvent.getClassName().toString());
//        }
//        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
//        if (null != rootNode) {
//
//        }
//        List<CharSequence> textTree = accessibilityEvent.getText();
//        for (CharSequence cs : textTree) {
//            Log.d(TAG, cs.toString());
//        }
    }

    @Override
    public void onInterrupt() {

    }
}
