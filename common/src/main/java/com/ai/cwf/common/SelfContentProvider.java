package com.ai.cwf.common;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by maoyu on 2018/5/8.
 * 自定义的ContentProvider，实现进程/App间数据共享
 * 使用数据库保存数据
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class SelfContentProvider extends ContentProvider {

    private static final String TAG = SelfContentProvider.class.getClass().getSimpleName();

    public static final String AUTHORITY = "com.ai.cwf.common";
    public static final int User_URI_CODE = 0;
    public static final int Book_URI_CODE = 1;


    public static class UserTable {
        public static String TABLE_NAME = User.class.getSimpleName();

        public final static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

        public static String COl_ID = "_ID";
        public static String COL_USERNAME = "USERNAME";
        public static String COL_SCHOOL = "SCHOOL";
        public final static String[] Projection = new String[]{COl_ID, COL_USERNAME, COL_SCHOOL};
    }

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, UserTable.TABLE_NAME, User_URI_CODE);
        sUriMatcher.addURI(AUTHORITY, "book", Book_URI_CODE);
    }

    private Context context;

    private SQLiteDatabase mDB;

    @Override
    public boolean onCreate() {
        context = getContext();
        mDB = SQLiteUtils.getInstance(context).getDB().getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String table = getTableName(uri);
        checkTableName(table);
        return mDB.query(table, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case Book_URI_CODE:
                return "book";
            case User_URI_CODE:
                return "user";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String table = getTableName(uri);
        checkTableName(table);
        mDB.insert(table, null, values);
        // 通知外界 ContentProvider 中的数据发生变化
        context.getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = getTableName(uri);
        checkTableName(table);
        int count = mDB.delete(table, selection, selectionArgs);
        // 通知外界 ContentProvider 中的数据发生变化
        if (count > 0)
            context.getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = getTableName(uri);
        if (null == table)
            throw new NullPointerException("uri not config");
        int count = mDB.update(table, values, selection, selectionArgs);
        // 通知外界 ContentProvider 中的数据发生变化
        if (count > 0)
            context.getContentResolver().notifyChange(uri, null);
        return count;
    }

    private String getTableName(Uri uri) {
        switch (sUriMatcher.match(uri)) {
            case User_URI_CODE:
                return UserTable.TABLE_NAME;
            case Book_URI_CODE:
                return null;
        }
        return null;
    }

    private void checkTableName(String tableName) {
        if (null == tableName)
            throw new NullPointerException("uri not config");
    }
}
