package cn.isif.rxdemo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.isif.rxdemo.data.GitHubApi
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GetRepoActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    lateinit var mDisposable: Disposable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_repo)
        textView = findViewById(R.id.text)
        requestRepo();
    }

    private fun requestRepo() {
        val api = getRetrofit().create(GitHubApi::class.java)
//        mDisposable = api.repoList("uncle404")
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({
//                    var sb = StringBuilder()
//                    sb.append("[ ")
//                    var i = 0
//                    for (r in it) {
//                        i++;
//                        if (i != it.size - 1)
//                            sb.append(" , ")
//                    }
//                    sb.append(" ]")
//                    textView.text = sb.toString()
//                }, { it.printStackTrace() })
        //假设我们有一个需求：将获取的结果保存到数据库中，我们知道数据库操作也是耗时操作，应该在子线程中完成
        //针对这个需求，我们可以使用flatMap来实现。关于flatMap的基本使用方式可以看java包中的TransformingObservables
        mDisposable = api.repoList("uncle404")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { repos ->
                    val sb = StringBuilder()
                    sb.append("[ ")
                    for (i in repos.indices) {
                        sb.append(repos[i].name)
                        if (i != repos.size - 1) {
                            sb.append(" , ")
                        }
                    }
                    sb.append(" ]")
                    textView.text = "saving"
                }
                .doOnError { error: Throwable -> error.printStackTrace() }
                .observeOn(Schedulers.io())
                .flatMap<String> {
                    Thread.sleep(5000) //睡5秒，模拟数据保存
                    Observable.create { emitter: ObservableEmitter<String?> -> emitter.onNext("finish") } //创建一个新的Observable
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { info: String? -> textView.setText(info) }
    }

    /**
     * 获取Retrofit
     *
     * @return
     */
    private fun getRetrofit(): Retrofit {
        val httpClient = OkHttpClient().newBuilder()
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(9, TimeUnit.SECONDS)
                .build()
        return Retrofit.Builder().baseUrl("https://api.github.com/").client(httpClient).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    /**
     * 线程切换：
     * 子线程 到 主线程
     * subscribeOn 设置上游发送事件的线程。多次指定，只有第一次会生效
     * observeOn 设置下游接受事件的线程。多次指定，下游的线程就会多次切换
     * --------------------
     * RxJava中内置了很多线程选项
     * Schedulers.io();// io操作的线程，通常用于网络，读写文件等io密集型的操作
     * Schedulers.computation();//cpu计算密集型的操作
     * Schedulers.newThread();//一个常规的新线程
     * AndroidSchedulers.mainThread();//Android的主线程
     */
    private fun threadTransTest() {
        val observable = Observable.create<Int> { emitter ->
            Log.d(TAG, "Observable thread is :${Thread.currentThread().name}")
            Log.d(TAG, "emit 1")
            emitter.onNext(1)
        }
        val consumer = Consumer<Int> { integer ->
            Log.d(TAG, "Observer thread is :${Thread.currentThread().name}")
            Log.d(TAG, "onNext: $integer")
        }
//        observable.subscribeOn(Schedulers.newThread())  //设置被监听对象执行的线程
//                .observeOn(AndroidSchedulers.mainThread())  //设置监听对象执行的线程
//                .subscribe(consumer);
        //测试多次切换线程
//        observable.subscribeOn(Schedulers.newThread())  //只有这一次生效
//                .subscribeOn(Schedulers.io())   //不会生效
//                .observeOn(AndroidSchedulers.mainThread()) //虽然这里设置为再主线程执行，但是下面又将线程切换只io线程
//                .observeOn(Schedulers.io()) //切换到io线程，最终下游事件执行
//                .subscribe(consumer);
        //跟踪线程切换细节
        observable.subscribeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { Log.d(TAG, "After observeOn(mainThread),current thread is: ${Thread.currentThread().name}") }
                .observeOn(Schedulers.io())
                .doOnNext { Log.d(TAG, "After observeOn(io),current thread is: ${Thread.currentThread().name}") }
                .subscribe(consumer)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposable.dispose()
    }


    companion object {
        const val TAG = "GetRepoActivity"
        fun startActivity(activity: Activity){
            activity.startActivity(Intent(activity,GetRepoActivity::class.java))
        }
    }
}
