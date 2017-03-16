package com.ai.cwf.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created at 陈 on 2017/3/15.
 *
 * @author chenwanfeng
 * @email 237142681@qq.com
 */

public class SimpleSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "user_data.db"; //数据库名称
    private static final int version = 2; //数据库版本

    public SimpleSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user" +
                "(_id integer primary key autoincrement , username varchar(20) not null , school varchar(60) not null );";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertUserToDB(User user) {
        //实例化一个ContentValues用来装载待插入的数据cv.put("username","Jack Johnson");//添加用户名
        ContentValues cv = new ContentValues();
        //添加数据
        cv.put("username", user.name);
        cv.put("school", user.school);
        //执行插入操作
        long r = getWritableDatabase().insert("user", null, cv);
        close();

        //插入操作的SQL语句
//        String sql = "insert into user(username,school) values ('" + user.name + "','" + user.school + "')";
//        execSQL(sql);//执行SQL语句
    }

    public void deleteUserFormDB(User user) {
        //删除的条件
        String whereClause = "username=?";
        //删除的条件参数
        String[] whereArgs = {user.name + ""};
        //执行删除
        long r = getWritableDatabase().delete("user", whereClause, whereArgs);
        close();

        //删除操作的SQL语句
//        String sql = "delete from user where username='" + user.name + "'";
        //execSQL(sql);//执行删除操作
    }

    public void updateUserFormDB(User user) {
        //实例化ContentValues
        ContentValues cv = new ContentValues();
        //添加要更改的字段及内容
        cv.put("school", user.school);
        //删除的条件
        String whereClause = "username=?";
        //删除的条件参数
        String[] whereArgs = {user.name};
        //执行删除
        long r = getWritableDatabase().update("user", cv, whereClause, whereArgs);
        close();

        //修改的SQL语句
//        String sql = "update [user] set school = '" + user.school + "' where username='" + user.name+"'";//修改的SQL语句
//        execSQL(sql);//执行修改
    }

    public ArrayList<User> getAllDate() {
        ArrayList<User> users = new ArrayList<>();
        Cursor c = getReadableDatabase().query("user", null, null, null, null, null, null);//查询并获得游标
        if (c.moveToFirst()) {//判断游标是否为空
            do {//移动到指定记录
                User u = new User(c.getString(c.getColumnIndex("username"))
                        , c.getString(c.getColumnIndex("school")));
                users.add(u);
            } while (c.moveToNext());
        }
        c.close();
        close();
        return users;
    }

    public void clearDB() {
        String sql = "DELETE FROM user";//清空数据
//        String sql = "DROP TABLE user";//删除表
        SQLiteDatabase database = getWritableDatabase();
        database.execSQL(sql);
        sql = "update sqlite_sequence SET seq = 0 where name ='user'";//自增长ID为0
        database.execSQL(sql);
        database.close();
    }

}
