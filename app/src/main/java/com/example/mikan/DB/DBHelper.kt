package com.example.mikan.DB

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.mikan.DB.DBContract.TaskEntry.Companion.TABLE_NAME
import com.example.mikan.DB.TaskModel

class DBHelper (context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    @Throws(SQLiteException::class)
    /**
     * insertTask(task: TaskModel)
     * @param task
     * */
    fun insertTask(task: TaskModel): Boolean {
        val db = writableDatabase

        val values = ContentValues()
        values.put(DBContract.TaskEntry.TASK_NAME, task.name)
        values.put(DBContract.TaskEntry.EMAIL, task.email)
        values.put(DBContract.TaskEntry.PASSWORD, task.password)
        values.put(DBContract.TaskEntry.DISPLAYNAME, task.displayname)

        db.insert(DBContract.TaskEntry.TABLE_NAME, null, values)
        return true
    }

    fun ReadTask( key :String):Cursor{
        val db = readableDatabase
        return db.rawQuery("SELECT " + key + " FROM " + TABLE_NAME,null)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "SIGN.db"

        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.TaskEntry.TABLE_NAME + " (" +
                    DBContract.TaskEntry.TASK_NAME + " TEXT NOT NULL," +
                    DBContract.TaskEntry.EMAIL + " TEXT NOT NULL," +
                    DBContract.TaskEntry.PASSWORD + " TEXT NOT NULL," +
                    DBContract.TaskEntry.DISPLAYNAME + " TEXT NOT NULL)"

        private const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.TaskEntry.TABLE_NAME
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

}