package com.xjtu.curriculumschedule.curriculumschedule_app;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Administrator on 2017/4/17.
 * 教师课表
 * 根据 学期 教室姓名 验证码 获取教师的课表
 */
public class Teacher_mainActivity extends Activity {
    //全局变量
    private URLData urlData=null;
    private HtmlParseJson htmlParseJson=null; //加载完桢哥 梁哥的两个类


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_layout);


    }
}
