package cn.isif.reviewandroid.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.util.Log
import java.util.concurrent.CopyOnWriteArrayList

class BookProvider : ContentProvider() {
    //这里我们采用模拟数据库的方式
    private val AUTHORITY = "cn.isif.reviewandroid.book.provider"
    private val BOOK_CONTENT_URI = Uri.parse("content://${AUTHORITY}/book")
    private val USER_CONTENT_URI = Uri.parse("content://${AUTHORITY}/user")
    private val BOOK_URI_CODE = 0
    private val USER_URI_CODE = 1
    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY,"book",BOOK_URI_CODE)
        addURI(AUTHORITY,"user",USER_URI_CODE)
    }

    val books:CopyOnWriteArrayList<Book> = CopyOnWriteArrayList()
    val users:CopyOnWriteArrayList<User> = CopyOnWriteArrayList()

    override fun onCreate(): Boolean {
        Log.d(TAG,"onCreate")
        initProviderData()
        return true
    }

    private fun initProviderData(){
        var u1 = User(1,"LIS")
        var u2 = User(2,"ADMIN")
        users.apply {
            add(u1)
            add(u2)
        }
        var b1 = Book("Android",10.0,1)
        var b2 = Book("IOS",10.0,2)
        var b3 = Book("KOTLIN",20.0,2)
        books.apply {
            add(b1)
            add(b2)
            add(b3)
        }
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        Log.d(TAG,"query")
        when(getDataType(uri)){
            "book" -> {
                //返回全部
                var strs = arrayOf("name","price","uId")
                var cursor = MatrixCursor(strs)
                for (b in books){
                    cursor.addRow(arrayOf(b.name,b.price,b.uId))
                }
                return cursor
            }
            "user" -> {
                //处理同上
            }
        }
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        Log.d(TAG,"delete")
        return 0
    }

    override fun getType(uri: Uri): String? {
        Log.d(TAG,"getType")
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(TAG,"insert")
        return null
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        Log.d(TAG,"update")
        return 0
    }

    private fun getDataType(uri: Uri):String{
        when(sUriMatcher.match(uri)){
            BOOK_URI_CODE -> return "book"
            USER_URI_CODE -> return "user"
            else -> return ""
        }
    }

    companion object{
        const val TAG = "BookProvider"
    }
}
