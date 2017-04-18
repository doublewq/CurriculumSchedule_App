package com.xjtu.curriculumschedule.curriculumschedule_app;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2017/4/15.
 * 课程课表
 * 该页面 根据学期 课程 验证码  检索到相关内容
 */
public class Kc_mainActivity extends Activity {

    private MyKcSQLiteOpenHelper mHelper;
    private SQLiteDatabase mDb;

    URLData urlData = null;
    private Handler handler;

    private String xq = "";
    private String xqValue = "";
    private String kc = "";
    private String kcValue = "";
    Bitmap bitmap_img = null;
    private ImageView imgView = null;
    private EditText kcname_edit = null;
    private Spinner kcname_spin;
    private Map myMap = new HashMap();
    private List<String> kcitems = new ArrayList<String>();
    private ArrayList<ClassInfo1> Info1List = new ArrayList<>();

    private ListView listV_kc = null;
    private String[][] getxq = new String[][]{
            {"2016-2017学年第二学期", "20161"},
            {"2016-2017学年第一学期", "20160"},
            {"2015-2016学年第二学期", "20151"},
            {"2015-2016学年第一学期", "20150"},
            {"2014-2015学年第二学期", "20141"},
            {"2014-2015学年第一学期", "20140"},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kc_layout);
        imgView = (ImageView) findViewById(R.id.imgv_yzm);
        //初始化学期下拉框 在xml里面配置过了
        Spinner spacer_xq = (Spinner) findViewById(R.id.spacer_xq);
        xq = spacer_xq.getSelectedItem().toString();

        kcname_edit = (EditText) findViewById(R.id.edit_kcname);
        //课程下拉列
        kcname_spin = (Spinner) findViewById(R.id.spacer_kcname);


        //测试


        getListCourse("1");
        kcname_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getListCourse("2");
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //数据库
        mHelper = new MyKcSQLiteOpenHelper(this, "course_table.db", null, 1, "kc");

        //更新页面
        handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 1) {
                    imgView.setImageBitmap(bitmap_img);//验证码显示

                    for (Object s : myMap.keySet()) {
                        kcitems.add(myMap.get(s).toString());
                    }
                    ArrayAdapter<String> kcAdapter = new ArrayAdapter<String>(Kc_mainActivity.this, android.R.layout.simple_spinner_item, kcitems);
                    kcAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    kcname_spin.setAdapter(kcAdapter);//下拉列表显示
                } else if (msg.what == 2) {
                    Kc_Adapter kc_adapter = new Kc_Adapter(Kc_mainActivity.this, R.layout.list_kc_layout, Info1List);
                    listV_kc.setAdapter(kc_adapter);
                    //界面显示之后存入数据库
                    //addInfoToSqlite();
                }
            }
        };
    }

    //添加数据进入数据库
    private void addInfoToSqlite() {
        //实例化ContentValues对象并进行数据组装
        ContentValues values = new ContentValues();
        for (int i = 0; i < Info1List.size(); i++) {
            values.put("kc_xq", xqValue);
            values.put("kc_kcName", kcValue);
            values.put("kc_week", Info1List.get(i).getWeek());
            values.put("kc_lesson", Info1List.get(i).getLesson());
            values.put("kc_info", Info1List.get(i).getInfo());
            //插入数据
            mDb.insert("KC_table", null, values);
            values.clear();
        }
    }

    /**
     * get the list of course
     *
     * @param courseId
     */
    public void getListCourse(final String courseId) {
        new Thread() {
            @Override
            public void run() {
                urlData = urlData.getInstance();
                urlData.GetCookie();
                if (courseId == "1")
                    kc = "";
                else
                    kc = kcname_edit.getText().toString();
                for (int i = 0; i < getxq.length; i++) {
                    if (xq.equals(getxq[i][0]))
                        xqValue = getxq[i][1];
                }
                String html_kc = urlData.GetXNXQKC(xqValue, kc);
                HtmlParseJson htm2Json = new HtmlParseJson();
                myMap = htm2Json.OptiontoList(html_kc);
                //验证码
                bitmap_img = urlData.GetImage(0);//课程查询
                handler.sendEmptyMessage(1);
            }
        }.start();
    }

    //获得所有课程信息
    /*public void getAllkcInfo(View view) {
        listV_kc = (ListView) findViewById(R.id.kclist_listView);
        EditText edit_yzm = (EditText) findViewById(R.id.edit_yzm);
        final String yzm = edit_yzm.getText().toString();
        String kcName = kcname_spin.getSelectedItem().toString();

        //获得课程编号value
        for (Object s : myMap.keySet()) {
            if (myMap.get(s).toString() == kcName)
                kcValue = s.toString();
        }

        if (fetchDataFromSqlite()) {//说明数据库中有数据
            handler.sendEmptyMessage(2);
        } else {//否则，开启线程从网络上获取
            new Thread() {
                @Override
                public void run() {
                    String html_tablekc = urlData.GetKBFBLessonSel(xqValue, kcValue, "1", yzm);
                    HtmlParseJson htmlJson = new HtmlParseJson();
                    try {
                        JSONObject jsonObject = htmlJson.getClassInfo2(html_tablekc);
                        //Log.i("json",jsonObject.toString());
                        Iterator<String> keysArr = jsonObject.keys();
                        for (Iterator it = keysArr; it.hasNext(); ) {
                            String thiskey = (String) it.next();
                            Info1List = (ArrayList<ClassInfo1>) jsonObject.get(thiskey);
                        }
                        handler.sendEmptyMessage(2);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

    }*/

   /* public boolean fetchDataFromSqlite() {
        boolean flag = false;
        mDb = mHelper.getReadableDatabase();
        Cursor cursor = mDb.query("KC_table", null, "kc_xq=? and kc_kcName=?", new String[]{xqValue, kcValue}, null, null, null);
        if (cursor.moveToFirst()) { //从数据库中取到数据
            flag = true;
            do {
                String kc_week = cursor.getString(cursor.getColumnIndex("kc_week"));
                String kc_lesson = cursor.getString(cursor.getColumnIndex("kc_lesson"));
                String kc_info = cursor.getString(cursor.getColumnIndex("kc_info"));
                //取出了一条数据
                ClassInfo1 cou = new ClassInfo1();
                cou.setWeek(kc_week);
                cou.setLesson(kc_lesson);
                cou.setInfo(kc_info);
                Info1List.add(cou);
            } while (cursor.moveToNext());
            cursor.close();
            mDb.close();
        }
        return flag;
    }*/
}
