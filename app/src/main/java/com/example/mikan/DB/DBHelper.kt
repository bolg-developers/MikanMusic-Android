package com.example.mikan.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.mikan.DB.TaskModel

class DBHelper (context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    @Throws(SQLiteException::class)

    /**
     * insertTask(task: TaskModel)
     * データの挿入
     * @param task
     * */
    fun insertTask(task: TaskModel): Boolean {
        //val db = writableDatabase

        val db = writableDatabase

        val values = ContentValues()
        values.put(DBContract.TaskEntry.TASK_NAME, task.name)
        values.put(DBContract.TaskEntry.STATUS, task.status)
        values.put(DBContract.TaskEntry.DISPLAYNAME, task.displayname)

        db.insert(DBContract.TaskEntry.TABLE_NAME, null, values)
        return true
    }

    /**
     * GetRecordTask( key :String)
     * 指定した項目のみ抽出しCursolを返す
     * @param key
     * @return Cusor
     * */
    fun GetRecordTask( key :String):Cursor{
        val db = readableDatabase

        return db.rawQuery("SELECT " + key + " FROM " + DBContract.TaskEntry.TABLE_NAME,null)
    }

    /**
     * GetRecordAllTask( key :Array<String>)
     * 指定した項目を持つすべてのレコードを抽出しCursolを返す
     * @param key 文字列配列
     * @return Cusor
     * */
    fun GetRecordAllTask(key :Array<String>):Cursor{
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM " +DBContract.TaskEntry.TABLE_NAME + " WHERE "+ DBContract.TaskEntry.DISPLAYNAME + " = ?" , key)
    }

    fun GetAll():Cursor{
        val db = readableDatabase
        return db.rawQuery("SELECT * FROM "+ DBContract.TaskEntry.TABLE_NAME,null)
    }
    /**
     * DeletePart(key:Array<String>)
     * 指定した項目を持つすべてのレコードを削除する
     * @param key 文字列配列
     * @return Cusor
     * */
     fun DeletePart(key:Array<String>){
        val db = writableDatabase
        db.delete(DBContract.TaskEntry.TABLE_NAME, DBContract.TaskEntry.DISPLAYNAME + "=?", key)
    }

    /**
     * DeleteAll(key:Array<String>)
     * 全レコード削除
     * */
    fun DeleteAll() {
        val db = writableDatabase

        db.delete(DBContract.TaskEntry.TABLE_NAME, null, null)
    }


    fun closeDB(){
        val db = writableDatabase
        db.close()
    }



    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "TASKS.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.TaskEntry.TABLE_NAME + " (" +
                    DBContract.TaskEntry.TASK_NAME + " TEXT ," +
                    DBContract.TaskEntry.STATUS + " TEXT," +
                    DBContract.TaskEntry.DISPLAYNAME + " TEXT)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.TaskEntry.TABLE_NAME
    }


}