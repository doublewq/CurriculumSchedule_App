package com.xjtu.curriculumschedule.curriculumschedule_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 */
public class Kc_Adapter extends ArrayAdapter<ClassInfo1> {
    private Context context;
    private int resource;
    private List<ClassInfo1> info1;

    public Kc_Adapter(Context context, int resource, List<ClassInfo1> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.info1=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ClassInfo1 info1_class=info1.get(position);
        convertView= LayoutInflater.from(context).inflate(resource,parent,false);
        TextView tvWeek= (TextView) convertView.findViewById(R.id.textView_week);
        TextView tvLesson= (TextView) convertView.findViewById(R.id.textView_lesson);
        TextView tvInfo= (TextView) convertView.findViewById(R.id.textView_info);

        //赋值
        tvWeek.setText("星期"+info1_class.getWeek());
        tvLesson.setText("|  "+"第"+info1_class.getLesson()+"节");
        tvInfo.setText("|  "+info1_class.getInfo());

        //点击查看详细 事件
        Button btn_detail= (Button) convertView.findViewById(R.id.btn_detail);
        btn_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return convertView;
    }
}
