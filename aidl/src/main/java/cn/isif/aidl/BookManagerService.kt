package cn.isif.aidl

import android.app.Activity
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteCallbackList
import android.util.Log
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean

class BookManagerService : Service() {
    private val exitFlag:AtomicBoolean = AtomicBoolean().apply { this.set(false) }//退出标志
    val bookList:MutableList<Book> = CopyOnWriteArrayList<Book>()
    /**
     * val listeners:MutableList<IOnNewBookArrivedListener> = CopyOnWriteArrayList()
     *  此类只能纪录listener对象但无法通过里面纪录的值判断是否可以解绑，
     *  if (listener!=null&&!listeners.contains(listener))；//aidl无法判断里面的对象是否相等，listener对象跟我们传入的对象不是同一个对象
     *  其他类似
     */
    val listeners:RemoteCallbackList<IOnNewBookArrivedListener> = RemoteCallbackList()

    override fun onCreate() {
        super.onCreate()
        bookList.add(Book("Android",10.00))
        bookList.add(Book("IOS",10.00))
        /**
         * 自动生成一本书并加入到booklist中
         */
        Thread(Runnable {
            while (!exitFlag.get()){
                Thread.sleep(5_000)
                //生成一本书
                var book = Book("new book#${bookList.size + 1}",12.00)
                onNewBookArrived(book)
            }
        }).start()
    }
    private fun onNewBookArrived(book:Book){
        bookList.add(book)
        var i = listeners.beginBroadcast()
        while (i>0){
            i--
            listeners.getBroadcastItem(i)?.onNewBookArrived(book)
        }
        listeners.finishBroadcast() //beginBroadcast和finishBroadcast必须配对使用
    }

    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    override fun onDestroy() {
        exitFlag.set(true)//服务退出时结束任务
        super.onDestroy()
    }

    companion object {
        const val TAG = "BookManagerService"
        fun getActionIntent(activity: Activity): Intent {
            return Intent(activity, BookManagerService::class.java)
        }
    }

    private val mBinder = object : IBookManagerInterface.Stub(){
        override fun registerListener(listener: IOnNewBookArrivedListener?) {
            listeners.register(listener)
        }

        override fun unregisterListener(listener: IOnNewBookArrivedListener?) {
            listeners.unregister(listener)
        }

        override fun bookList(): MutableList<Book> {
//            Thread.sleep(5_000)//测试客户端ANR
            return bookList
        }

        override fun addBook(book: Book?) {
            if (book!=null){
                Log.d(TAG,book.toString())
                bookList.add(book)
            }
        }
    }

}
