package com.example.egor.notebook.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.example.egor.notebook.Managers.FileManager;

import java.io.File;
import java.util.Calendar;
import java.util.Date;


public class SQLiteFileListHandler extends SQLiteOpenHelper {
//    TODO:: Add fields in db
//    TODO:: Add methods

    public static final String TAG = SQLiteFileListHandler.class.getSimpleName();
    private static final String NAME = "file_db";
    private static final String TABLE_NAME = "files_list";
    private static final String ID_FIELD = "_id";
    private static final String FILE_NAME_FIELD = "name";
    private static final String CREATION_DATE_FIELD = "create_date";
    private static final String PATH_FIELD = "path";
    private static final String SD_CARD_EXIST_FIELD = "sd_card";
    private static final String FILE_SIZE_FIELD = "size";
    private static final String HASH_FIELD = "hash";
    private static final int VERSION = 1;
    private Cursor mCursor;
    private ContentValues mContentValues;

    private Date mDate;
    public SQLiteFileListHandler(Context context) {
        super(context, NAME, null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + "`"+TABLE_NAME + "`" + "(" + ID_FIELD + " integer primary key AUTOINCREMENT," + FILE_NAME_FIELD + " text not null ," + CREATION_DATE_FIELD + " date not null, " + PATH_FIELD + " text not null, " + SD_CARD_EXIST_FIELD + " boolean not null, " + FILE_SIZE_FIELD + " int, " + HASH_FIELD + " text not null);";
        Log.d(TAG, sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void addFileOnDB(File file)
    {
        String fileName = file.getName();
        mDate = new Date();
        String fileDate = mDate.toString();
        String filePath = file.getAbsolutePath();
        boolean isFileSdCard = file.getAbsolutePath().contains("sd_card");
        long fileSize = file.length();
        int fileHash = file.hashCode();
        Log.d(TAG, "Имя: " + fileName + ", Дата: " + fileDate + ", Путь:" + filePath + ", Размер: " + fileSize + ", Хеш: " + fileHash);
        mContentValues= new ContentValues();
        mContentValues.put(FILE_NAME_FIELD, fileName);
        mContentValues.put(CREATION_DATE_FIELD, fileDate);
        mContentValues.put(PATH_FIELD, filePath);
        mContentValues.put(SD_CARD_EXIST_FIELD, isFileSdCard);
        mContentValues.put(FILE_SIZE_FIELD, fileSize);
        mContentValues.put(HASH_FIELD, fileHash);

        SQLiteDatabase db = this.getWritableDatabase();
        long id = db.insert(TABLE_NAME, null, mContentValues);
        Log.i(TAG, "Inset row - " + id);
        db.close();


    }
    public void getTable()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        while(cursor.moveToNext())
        {
            String id = cursor.getString(cursor.getColumnIndex(ID_FIELD));
            String name = cursor.getString(cursor.getColumnIndex(FILE_NAME_FIELD));
            String date = cursor.getString(cursor.getColumnIndex(CREATION_DATE_FIELD));
            String path = cursor.getString(cursor.getColumnIndex(SD_CARD_EXIST_FIELD));
            String size = cursor.getString(cursor.getColumnIndex(FILE_SIZE_FIELD));
            String hash = cursor.getString(cursor.getColumnIndex(HASH_FIELD));
            Log.d(TAG, "Номер строки: " + id +", Имя: " + name + ", Дата: " + date + ", Путь:" + path + ", Размер: " + size + ", Хеш: " + hash);

        }
    }
    public void updateFileInDB(File file)
    {
        String query = "UPDATE " + TABLE_NAME + " SET " + FILE_SIZE_FIELD + " = " + file.length() + ", " + HASH_FIELD + " = " + file.hashCode() + " WHERE " + FILE_NAME_FIELD + " = " + "'" + file.getName() + "';";
        SQLiteDatabase db = this.getWritableDatabase();
        db.rawQuery(query, null);
        db.close();
    }


}
