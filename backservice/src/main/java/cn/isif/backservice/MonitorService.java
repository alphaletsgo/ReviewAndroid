package cn.isif.backservice;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;

import androidx.annotation.RequiresApi;

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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Log.d(TAG, "onAccessibilityEvent");
        //获取Root
//        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
//        printChild(rootNode);
        List<AccessibilityWindowInfo> windowInfos = getWindows();
        for (AccessibilityWindowInfo awi : windowInfos) {
            AccessibilityNodeInfo nodeInfo = awi.getRoot();
            if (null != nodeInfo) {
                printChild(nodeInfo);
            }
        }
    }

    private void printChild(AccessibilityNodeInfo node) {
        int count = node.getChildCount();
        for (int i = 0; i < count; i++) {
            AccessibilityNodeInfo child = node.getChild(i);
            if (null != child) {
                //两种获取text的方式
                CharSequence text = child.getText();
                if (TextUtils.isEmpty(text)) {
                    text = child.getContentDescription();
                }
                if (!TextUtils.isEmpty(text)) {
                    Log.d(TAG, text.toString());
                }
                if (child.getChildCount() == 0) {
                    return;
                } else {
                    printChild(child);
                }
            }
        }
    }

    @Override
    public void onInterrupt() {

    }
}
