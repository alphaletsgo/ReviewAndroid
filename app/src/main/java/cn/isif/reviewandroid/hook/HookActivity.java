package cn.isif.reviewandroid.hook;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import cn.isif.reviewandroid.R;
import cn.isif.reviewandroid.TestActivity;

/**
 * 我们对startActivity方法进行hook
 * 我们知道调用activity的startActivity会调用到Instrumentation的execStartActivity方法
 * 那么我们就可以从此入手
 */
public class HookActivity extends AppCompatActivity {
    Button button;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
        button = findViewById(R.id.bt_start);
        hook();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HookActivity.this,TestActivity.class));
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hook(){
        //获取Instrumentation对象
        try {
            Field f = this.getClass().getDeclaredField("mInstrumentation");
            f.setAccessible(true);
            //构造待替换的对象
            EVilInstrumentation instrumentation = new EVilInstrumentation((Instrumentation)f.get(this));
            f.set(this,instrumentation);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
}



class EVilInstrumentation extends Instrumentation {
    private final String TAG = "EVilInstrumentation";
    Instrumentation mBase;

    public EVilInstrumentation(Instrumentation mBase) {
        this.mBase = mBase;

    }

    /**
     *  use hook method
     * @param who
     * @param contextThread
     * @param token
     * @param target
     * @param intent
     * @param requestCode
     * @param options
     * @return
     */
    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, String target,
            Intent intent, int requestCode, Bundle options) {
        //打印日志
        Log.d(TAG, "到此一游");
        //利用反射调用 mBase 的execStartActivity
        Class[] p1 = new Class[]{Context.class, IBinder.class, IBinder.class, String.class, Intent.class, Integer.class, Bundle.class};
        Object[] p2 = new Object[]{who, contextThread, token, target, intent, requestCode, options};
        try {
            Method exec = Instrumentation.class.getMethod("execStartActivity", p1);
            return (ActivityResult) exec.invoke(mBase, p2);
        } catch (NoSuchMethodException e){
            e.printStackTrace();
        }catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }
}