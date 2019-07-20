package com.example.mikan.Adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DBAdapter
 * */
public class DBAdapter {
	//** DBとTable **
    private final static String DB_NAME     = "mikan.db";  	          // DB名
    private final static String DB_TABLE 	= "myTable";                 // DBのテーブル名
    private final static int DB_VERSION  	= 1;         	            // DBのバージョン

    //** DBの項目(Column) **
    public final static String COL_ID	 	        = "_id";         	// id
    public final static String COL_EMAIL	        = "email";   	    // e-mail
    public final static String COL_PASSWORD 		= "password";      // パスワード
    public final static String COL_DISPLAYNAME   = "displayname";  	// 購入先


    private SQLiteDatabase db = null;           			// SQLiteDatabase
    private DBHelper dbHelper = null;           			// DBHepler
    protected Context context;                  			// Context

	//*************
    // Constructor
	//*************
    public DBAdapter(Context context) {
        this.context = context;
        dbHelper = new DBHelper(this.context);				// DB Helper呼出し(DB,Table作成)
    }

    //******************
	// Data Base Helper 
	//******************/
    private static class DBHelper extends SQLiteOpenHelper {
        //** Constructor **
        public DBHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);		// DB作成
        }

       	//** onCreate - DB中にTableが無いときCallされる **
        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTbl = "CREATE TABLE " + DB_TABLE + " ("
                    + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COL_DISPLAYNAME + " TEXT NOT NULL"
                    + ");";
            db.execSQL(createTbl);      					// Table作成
        }

        //** onUpgrade - DB Version更新時Callされる **
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);	// Table削除
            onCreate(db);									// Table再作成
        }
    }

 	//****************************
	// Data Base Adapter (Methods)
	//****************************/
	//** Open DB Read/Write_Mode **
    public DBAdapter openDB_RW() {
        db = dbHelper.getWritableDatabase();        		// Read/Write_ModeでDB_Open
        return this;
    }
    //** Open DB Read_only **
    public DBAdapter openDB_R() {
        db = dbHelper.getReadableDatabase();	 	       	// Read_OnlyでDB_Open
        return this;
    }
    //** Close DB **
    public void closeDB() {
        db.close();											// DB_Close
        db = null;
    }
	//** Tableに Record挿入 **
    public void insertRecord(String email, String pass, String disname) {
        ContentValues values = new ContentValues();     	// ContentValuesに項目格納
        values.put(COL_DISPLAYNAME, disname);
        db.insert(DB_TABLE, null, values);      			// TableにRecord挿入(商品登録)
    }
    //** Tableから 指定された項目(ここでは商品名)"のみ"抽出してCursolに格納→Cursolを返す
    public Cursor getRecord_Column(String key_columns) {
        return db.rawQuery("SELECT " + key_columns + " FROM " + DB_TABLE,null);
    }
    //** Tableから 指定された項目(ここでは商品名)を持つ全Recordを抽出してCursolに格納→Cursolを返す
    public Cursor getRecord_All(String[] key_columns) {
        return db.rawQuery("SELECT * FROM " + DB_TABLE + " WHERE "+ COL_DISPLAYNAME + " = ?" , key_columns);
    }
    //** Tableから 指定された項目(ここでは商品名)を持つ全Recordを削除
    public void deleteRecord(String[] key_columns) {
        db.delete(DB_TABLE, COL_EMAIL + "=?", key_columns);
    }
    //** Table から全Recordを削除
    public void deleteRecord_All() {
        db.delete(DB_TABLE, null, null);
    }
}