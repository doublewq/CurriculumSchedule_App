package com.xjtu.curriculumschedule.curriculumschedule_app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/15.
 * 课程课表
 * 该页面 根据学期 课程 验证码  检索到相关内容
 */
public class Kc_mainActivity  extends Activity{

    URLData urlData=new URLData();
    private Handler handler;

    private String kc=null;

    private ImageView imgView=null;
    private Spinner kcname_spin;
    private Map myMap=new HashMap();
    private List<String> kcitems = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kc_layout);
        imgView=(ImageView) findViewById(R.id.imgv_yzm);
        //初始化学期下拉框 在xml里面配置过了

        EditText kcname_edit= (EditText) findViewById(R.id.edit_kcname);
        kc=kcname_edit.getText().toString();

        //课程下拉列
        kcname_spin= (Spinner) findViewById(R.id.spacer_kcname);

        //课程绑定
        new Thread(){
            @Override
            public void run() {
                urlData.GetCookie();
                String html_kc = urlData.GetXNXQKC("20161",kc);
                HtmlParseJson htm2Json=new HtmlParseJson();
                myMap = htm2Json.OptiontoList(html_kc);

                handler.sendEmptyMessage(1);
            }
        }.start();
        //验证码
        Thread t = new Thread(){
            @Override
            public void run() {
                //urlData.GetCookie();
                Bitmap bitmap_img = urlData.GetImage(0);
                Message msg = new Message();
                msg.what=2;
                msg.obj=bitmap_img;
                handler.sendMessage(msg);
                //handler.sendEmptyMessage(2);

            }
        };
        t.start();


        //更新页面
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1){
                    for(Object s:myMap.keySet()){
                        kcitems.add(myMap.get(s).toString());
                    }
                    ArrayAdapter<String> kcAdapter = new ArrayAdapter<String>(Kc_mainActivity.this, android.R.layout.simple_spinner_item, kcitems);
                    kcAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    kcname_spin.setAdapter(kcAdapter);
                }else if(msg.what==2){
                    imgView.setImageBitmap((Bitmap)msg.obj);
                }
            }
        };
    }
    //获得所有课程信息
    public void getAllkcInfo(View view) {
        EditText edit_yzm= (EditText) findViewById(R.id.edit_yzm);
        final String yzm=edit_yzm.getText().toString();
        String kcName=kcname_spin.getSelectedItem().toString();
        new Thread(yzm){
            @Override
            public void run() {
                String  html_tablekc=urlData.GetKBFBLessonSel("20161","000590","1",yzm);
                Log.i("html",html_tablekc);
            }
        }.start();

    }
}
