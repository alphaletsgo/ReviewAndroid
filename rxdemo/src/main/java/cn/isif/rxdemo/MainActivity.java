package cn.isif.rxdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity {
    Context mContext;
    final String TAG = "MainActivity";
    Button bt;
    Button btRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        bt = findViewById(R.id.bt_get_repo);
        btRequest = findViewById(R.id.bt_request);
        bt.setOnClickListener(v -> {
            GetRepoActivity.Companion.startActivity(this);
        } );
//        testSyncZip();
//        testAsyncZip();
//        testBackPress();
        btRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSubscription.request(95);
            }
        });
        demo();
    }

    /**
     * 同步zip
     */
    private void testSyncZip(){
        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG,"emit A");
                emitter.onNext("A");
                Thread.sleep(1_000);
                Log.d(TAG,"emit B");
                emitter.onNext("B");
                Thread.sleep(1_000);
                Log.d(TAG,"emit C");
                emitter.onNext("C");
                Thread.sleep(1_000);
            }
        });
        Observable<Integer> observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG,"emit 1");
                emitter.onNext(1);
                Thread.sleep(1_000);
                Log.d(TAG,"emit 2");
                emitter.onNext(2);
                Thread.sleep(1_000);
                Log.d(TAG,"emit 3");
                emitter.onNext(3);
                Thread.sleep(1_000);
                Log.d(TAG,"emit 4");
                emitter.onNext(4);
                Thread.sleep(1_000);
            }
        });
        Observable.zip(observable1, observable2, new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String str, Integer integer) throws Exception {
                return "zip " + str + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG,s);
            }
        });

    }

    /**
     * 异步zip
     */
    private void testAsyncZip(){
        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.d(TAG,"emit A");
                emitter.onNext("A");
                Thread.sleep(1_000);
                Log.d(TAG,"emit B");
                emitter.onNext("B");
                Thread.sleep(1_000);
                Log.d(TAG,"emit C");
                emitter.onNext("C");
                Thread.sleep(1_000);
            }
        }).subscribeOn(Schedulers.io());
        Observable<Integer> observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG,"emit 1");
                emitter.onNext(1);
                Thread.sleep(1_000);
                Log.d(TAG,"emit 2");
                emitter.onNext(2);
                Thread.sleep(1_000);
                Log.d(TAG,"emit 3");
                emitter.onNext(3);
                Thread.sleep(1_000);
                Log.d(TAG,"emit 4");
                emitter.onNext(4);
                Thread.sleep(1_000);
            }
        }).subscribeOn(Schedulers.io());
        Observable.zip(observable1, observable2, new BiFunction<String, Integer, String>() {
            @Override
            public String apply(String str, Integer integer) throws Exception {
                return "zip " + str + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG,s);
            }
        });

    }

    private void testBackPress(){
        Observable<Integer> observable1 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG,"emit");
               for (int i = 0;;i++){
                   emitter.onNext(i);
                   Log.d(TAG,"emit " + i);
                }
            }
        }).subscribeOn(Schedulers.io()).sample(10, TimeUnit.SECONDS);
        Observable<Integer> observable2 = Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
            }
        }).subscribeOn(Schedulers.io());
        Observable.zip(observable1, observable2, new BiFunction<Integer, Integer, String>() {
            @Override
            public String apply(Integer str, Integer integer) throws Exception {
                return "zip " + str + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG,s);
            }
        });
    }
    Subscription mSubscription;

    /**
     * 测试异步背压
     */
    private void demo(){
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                Log.d(TAG,"First requested = " + emitter.requested());
//                boolean flag;
//                for (int i = 0;;i++){
//                    flag = false;
//                    while (emitter.requested()==0){
//                        if (!flag){
//                            Log.d(TAG,"oh on! I can't emit value!");
//                            flag = true;
//                        }
//                    }
//                    emitter.onNext(i);
//                    Log.d(TAG,"emit " + i + " , requested = " + emitter.requested());
//                }
                int i = 0;
                while (true){
                    if (emitter.requested()!=0){
                        emitter.onNext(i);
                        Log.d(TAG,"emit " + i + " , requested = " + emitter.requested());
                        i++;
                    }
                }
            }
        }, BackpressureStrategy.ERROR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        Log.d(TAG,"onSubscribe");
                        mSubscription = s;
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG,"onNext");
                        Log.d(TAG,integer.toString());
                    }

                    @Override
                    public void onError(Throwable t) {
                        t.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG,"onComplete");
                    }
                });
    }

}
