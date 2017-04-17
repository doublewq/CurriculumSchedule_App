package com.xjtu.curriculumschedule.curriculumschedule_app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
public class Kc_mainActivity  extends Activity{

    URLData urlData=null;
    private Handler handler;

    private String kc="";

    Bitmap bitmap_img=null;
    private ImageView imgView=null;
    private Spinner kcname_spin;
    private Map myMap=new HashMap();
    private List<String> kcitems = new ArrayList<String>();
    private ArrayList<ClassInfo1> Info1List=new ArrayList<>();
    private ListView listV_kc=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kc_layout);

        imgView=(ImageView) findViewById(R.id.imgv_yzm);
        //初始化学期下拉框 在xml里面配置过了

        EditText kcname_edit= (EditText) findViewById(R.id.edit_kcname);
        kc=kcname_edit.getText().toString();
        kc="";
        //课程下拉列
        kcname_spin= (Spinner) findViewById(R.id.spacer_kcname);
        getListCourse("1");
        kcname_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getListCourse("1");

            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        //更新页面
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1){
                    imgView.setImageBitmap(bitmap_img);//验证码显示
//                    for(Object s:myMap.keySet()){
//                        kcitems.add(myMap.get(s).toString());
//                    }
//                    ArrayAdapter<String> kcAdapter = new ArrayAdapter<String>(Kc_mainActivity.this, android.R.layout.simple_spinner_item, kcitems);
//                    kcAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    kcname_spin.setAdapter(kcAdapter);//下拉列表显示
                }else if(msg.what==2){
                    Kc_Adapter kc_adapter=new Kc_Adapter(Kc_mainActivity.this,R.layout.list_kc_layout,Info1List);
                    listV_kc.setAdapter(kc_adapter);
                }
            }
        };
    }
    /**
     *get the list of course
     * @param courseId
     */
    public void getListCourse(String courseId){
        new Thread(){
            @Override
            public void run() {
                Log.e("kc",kc);
                String html_kc = urlData.GetXNXQKC("20161",kc);
                HtmlParseJson htm2Json=new HtmlParseJson();
                myMap = htm2Json.OptiontoList(html_kc);
                handler.sendEmptyMessage(1);
            }
        }.start();
    }
    //获得所有课程信息
    public void getAllkcInfo(View view) {
        listV_kc= (ListView) findViewById(R.id.kclist_listView);
        EditText edit_yzm= (EditText) findViewById(R.id.edit_yzm);
        final String yzm=edit_yzm.getText().toString();
        //String kcName=kcname_spin.getSelectedItem().toString();
        new Thread(yzm){
            @Override
            public void run() {
                String  html_tablekc=urlData.GetKBFBLessonSel("20161","000590","1",yzm);
                HtmlParseJson htmlJson=new HtmlParseJson();
                try {
                    JSONObject jsonObject=htmlJson.getClassInfo2(html_tablekc);
                    Log.i("json",jsonObject.toString());
                    Iterator<String> keysArr=jsonObject.keys();
                    for (Iterator it=keysArr;it.hasNext();) {
                        String thiskey=(String) it.next();
                        Info1List=(ArrayList<ClassInfo1>) jsonObject.get(thiskey);
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
}
