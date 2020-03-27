package cn.isif.reviewandroid.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper
/**
 * 构造方法
 *
 * @param mContext
 * @param name 数据库名称
 * @param factory 可选游标工厂 一般为null
 * @param version 数据库的版本，值必须为正整数且递增
 */
@JvmOverloads constructor(mContext: Context, name: String, factory: SQLiteDatabase.CursorFactory? = null, version: Int = Version) : SQLiteOpenHelper(mContext, name, factory, version) {

    /**
     * 在数据库第一次创建时调用，该方法可以用以数据库表的创建和数据初始化等操作
     *
     * @param db
     */
    override fun onCreate(db: SQLiteDatabase) {
        val sql = "create table person(id integer primary key autoincrement,name varchar(64),address varchar(64))"
        db.execSQL(sql)
    }

    /**
     * 此方法在数据库版本升级时自动调用，用于数据库更新等
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val sql = "alter table person add sex varchar(8)"
        db.execSQL(sql)
    }

    companion object {
        private val Version = 1 //数据库版本号
    }
}
