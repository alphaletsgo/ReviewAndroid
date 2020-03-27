package cn.isif.reviewandroid.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseDemo {

    fun createDatabse(mContext: Context) {
        //创建数据库
        val dbHepler = DatabaseHelper(mContext, "")
        val sdb = dbHepler.writableDatabase//创建或打开"可读写"的数据库
        //        SQLiteDatabase sdb = dbHepler.getReadableDatabase();//创建或打开"只读"的数据库
    }


    fun openDatabaseByPath(mContext: Context, path: String) {
        //打开一个外部的数据库
        val sdb = mContext.openOrCreateDatabase(path, Context.MODE_ENABLE_WRITE_AHEAD_LOGGING, null)
    }

    fun insert(sdb: SQLiteDatabase) {
        val values = ContentValues()
        values.put("id", 1)
        values.put("name", "luc")

        sdb.insert(
                "person", null, values
        )

    }

}
