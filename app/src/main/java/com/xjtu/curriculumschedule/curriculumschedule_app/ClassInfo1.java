package com.xjtu.curriculumschedule.curriculumschedule_app;

/**
 * Created by Administrator on 2017/4/14.
 */
public class ClassInfo1 implements Comparable<ClassInfo1> {
    private String week;
    private String lesson;
    private String info;


    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getLesson() {
        return lesson;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }


    @Override
    public int compareTo(ClassInfo1 o) {
        int w1=Integer.parseInt(this.week);
        int w2=Integer.parseInt(o.getWeek());
        //先按星期进行排列；
        if(w1>w2){
            return (w1-w2);
        }
        if (w1<w2){
            return (w1-w2);
        }
        //按第几节课进行排序
        int class1=Integer.parseInt(this.lesson);
        int class2=Integer.parseInt(o.getLesson());
        if (class1>class2){
            return (class1-class2);
        }
        if (class1<class2){
            return (class1-class2);
        }
        return  0;
    }
}
