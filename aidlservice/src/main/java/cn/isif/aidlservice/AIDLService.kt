package cn.isif.aidlservice

import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.util.Log
import java.util.concurrent.CopyOnWriteArrayList


class AIDLService : Service() {
    //    var books = mutableListOf<Book>() //不支持并发
    var books = CopyOnWriteArrayList<Book>()//支持并发,aidl本身是不支持copyonwritearraylist，在传输中转为arraylist

    override fun onBind(intent: Intent): IBinder? {
//        Log.d(TAG, "onBind")
//        //权限验证
//        var check = checkCallingPermission("cn.isif.aidlservice.permission.TEST")
//        if (check==PackageManager.PERMISSION_DENIED){//权限拒绝
//            Log.d(TAG,"Permission denied!!!")
//            return null
//        }
        return mBinder
    }

    companion object {
        private const val TAG = "AIDLService"

        fun startService(context: Context) {
            context.startService(Intent(context, AIDLService::class.java))
        }

        fun stopService(context: Context) {
            context.stopService(Intent(context, AIDLService::class.java))
        }
    }

    /**
     * 测试定向tag语法：in out inout
     * 所有非基本类型的参数都需要一个定向tag来表明数据是如何走向的，要不是in，out或者inout。
     * 基本数据类型默认是in，而且不能是其他tag。
     */
    private val mBinder = object : IManager.Stub() {
        override fun addBookIn(book: Book): Book {
            Log.d(TAG,"======== in begin =========")
            if (book!=null){
                book.price = 200.0
                Log.d(TAG,book.toString())
                books.add(book)
                Log.d(TAG,books.joinToString())
            }
            Log.d(TAG,"======== in end =========")
            return book;
        }

        override fun getBookList(): MutableList<Book> {
            Log.d(TAG,books.joinToString())
            return books
        }

        override fun addBookOut(book: Book): Book {
            Log.d(TAG,"======== out begin =========")
            if (book!=null){
                book.price = 200.0
                Log.d(TAG,book.toString())
                books.add(book)
                Log.d(TAG,books.joinToString())
            }
            Log.d(TAG,"======== out end =========")
            return book;
        }

        override fun addBookInOut(book: Book): Book {
            Log.d(TAG,"======== inout begin =========")
            if (book!=null){
                book.price = 200.0
                Log.d(TAG,book.toString())
                books.add(book)
                Log.d(TAG,books.joinToString())
            }
            Log.d(TAG,"======== inout end =========")
            return book;
        }
    }
}
