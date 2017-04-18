package com.xjtu.curriculumschedule.curriculumschedule_app;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/4/17.
 */
public class MyKcSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String CREATE_KC = "create table KC_table ("
            + "kc_xq text, "
            + "kc_kcName text, "
            + "kc_week text, "
            + "kc_lesson text,"
            + "kc_info text)";

    public static final String CREATE_Teacher="create table Teacher_table ("
            + "kc_xq text, "
            + "kc_kcName text, "
            + "kc_week text, "
            + "kc_lesson text,"
            + "kc_info text)";
    private Context mContext;
    private String tableName="";

    public MyKcSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version,String tableOfName) {
        super(context, name, factory, version);
        this.mContext = context;
        this.tableName=tableOfName;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        if(tableName=="kc")
            sqLiteDatabase.execSQL(CREATE_KC);//执行sql语句创建数据库
        else if(tableName=="teacher")
            sqLiteDatabase.execSQL(CREATE_Teacher);//执行sql语句创建数据库
        //Toast.makeText(mContext,"创建成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
