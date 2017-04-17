package com.xjtu.curriculumschedule.curriculumschedule_app;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.audiofx.LoudnessEnhancer;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.DataOutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.Date;
import java.lang.StringBuilder;

public class URLData{
	public String m_head;
	public String m_cokie;
	private static URLData m_UrlData;

	public static URLData getInstance(){
		if(m_UrlData==null){
			m_UrlData=new URLData();
		}
		return m_UrlData;
	}
	
	public void GetCookie()
	{
		m_head = "http://jwgl.lnc.edu.cn";
		m_cokie = _GetCookie();
	}

	public Bitmap GetImage(int indexs)
	{
		return _GetImage(m_cokie, indexs);
	}
	//教室课表
	public String GetKBFBRoomSel(String Sel_XNXQ, String rad_gs, String txt_yzm, String Sel_XQ, String Sel_JXL, String Sel_ROOM)
	{
		String param = "Sel_XNXQ="+Sel_XNXQ+"&rad_gs="+rad_gs+"&txt_yzm="+txt_yzm+"&Sel_XQ="+Sel_XQ+"&Sel_JXL="+Sel_JXL+"&Sel_ROOM="+Sel_ROOM;
		//System.out.println(param);
		String url = m_head+"/ZNPK/KBFB_RoomSel_rpt.aspx";
		String r = m_head+"/ZNPK/KBFB_RoomSel.aspx";
		return _PostFunction(m_cokie, url, r, param);
	}
	//行政班级课表
	public String GetKBFBClassSel(String Sel_XNXQ, String txtxzbj, String Sel_XZBJ, String type, String txt_yzm)
	{
		String param = "Sel_XNXQ="+Sel_XNXQ+"&txtxzbj="+txtxzbj+"&Sel_XZBJ="+Sel_XZBJ+"&type="+type+"&txt_yzm="+txt_yzm;
		//System.out.println(param);
		String url = m_head+"/ZNPK/KBFB_ClassSel_rpt.aspx";
		String r = m_head+"/ZNPK/KBFB_ClassSel.aspx";
		return _PostFunction(m_cokie, url, r, param);
	}
	//教师课表
	public String GetTeacherKBFB(String Sel_XNXQ, String Sel_JS, String type, String txt_yzm)
	{
		String param = "Sel_XNXQ="+Sel_XNXQ+"&Sel_JS="+Sel_JS+"&type="+type+"&txt_yzm="+txt_yzm;
		//System.out.println(param);
		String url = m_head+"/ZNPK/TeacherKBFB_rpt.aspx";
		String r = m_head+"/ZNPK/TeacherKBFB.aspx";
		return _PostFunction(m_cokie, url, r, param);
	}

	//课程课表
	public String GetKBFBLessonSel(String Sel_XNXQ, String Sel_KC, String gs, String txt_yzm)
	{
		String param = "Sel_XNXQ="+Sel_XNXQ+"&Sel_KC="+Sel_KC+"&gs="+gs+"&txt_yzm="+txt_yzm;
		//Log.i("param",param);
		String url = m_head+"/ZNPK/KBFB_LessonSel_rpt.aspx";
		String r = m_head+"/ZNPK/KBFB_LessonSel.aspx";
		return _PostFunction(m_cokie, url, r, param);
	}


	//教室课表
	public String GetListJXL(String w, String id)	//defalut w=150
	{
		String param = "w="+w+"&id="+id;
		String url = m_head+"/ZNPK/Private/List_JXL.aspx?"+param;
		return _GetFunction(m_cokie, url);
	}

	public String GetListROOM(String w, String id)	//defalut w=150
	{
		String param = "w="+w+"&id="+id;
		String url = m_head+"/ZNPK/Private/List_ROOM.aspx?"+param;
		return _GetFunction(m_cokie, url);
	}

	//行政班级课表
	public String GetListXZBJClassName(String xnxq, String xzbj)
	{
		Date date = new Date();
		long times = date.getTime();
		String param = "xnxq="+xnxq+"&xzbj="+xzbj+"&t="+times;
		String url = m_head+"/ZNPK/Private/List_XZBJ.aspx?"+param;
		return _GetFunction(m_cokie, url);
	}
	public String GetListXZBJTerm(String flag, String xnxq, String xzbj)	//defalut flag=1
	{
		String param = "flag="+flag+"xnxq="+xnxq+"&xzbj="+xzbj;
		String url = m_head+"/ZNPK/Private/List_XZBJ.aspx?"+param;
		return _GetFunction(m_cokie, url);
	}

	//教师课表
	public String GetTeacherList(String xnxq, String s)
	{
		String param = "xnxq="+xnxq+"&js="+s;
		String url = m_head+"/ZNPK/Private/List_JS.aspx?"+param;
		return _GetFunction(m_cokie, url);
	}

	//课程课表
	public String GetXNXQKC(String xnxq, String kc)
	{
		String param = "xnxq="+xnxq+"&kc="+kc;
		String url = m_head+"/ZNPK/Private/List_XNXQKC.aspx?"+param;
		return _GetFunction(m_cokie, url);
	}
	
	

	private String _GetFunction(String cok, String u) 
	{
		StringBuilder tmp = null;
		try {
			URL url = new URL(u);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Cookie", cok);
			InputStream in = conn.getInputStream();

			InputStreamReader fo = new InputStreamReader(in, "gbk");
			BufferedReader reader = new BufferedReader(fo);
			String context;
			tmp = new StringBuilder();
			while((context =reader.readLine()) != null)
			{
				tmp.append(context);
			}

			in.close();
			fo.close();
			reader.close();
			conn.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(tmp);
	}

	private String _PostFunction(String cok, String u, String r, String param) 
	{
		StringBuilder tmp = null;
		try {
			URL url = new URL(u);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
            conn.setDoInput(true);
			conn.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.setRequestProperty("Referer", r);
            conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Host", m_head.substring(7,m_head.length()));
			conn.setRequestProperty("Origin", m_head);
			conn.setRequestProperty("Upgrade-Insecure-Requests", "1");
			conn.setRequestProperty("Cookie", cok);
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2914.3 Safari/537.36");
			conn.setRequestProperty("Cache-Control", "max-age=0");
			PrintWriter out = new PrintWriter(conn.getOutputStream());
			out.print(param);
			out.flush();
			out.close();

			InputStream in = conn.getInputStream();

			InputStreamReader fo = new InputStreamReader(in, "gbk");
			BufferedReader reader = new BufferedReader(fo);
			String context;
			tmp = new StringBuilder();
			while((context =reader.readLine()) != null)
			{
				tmp.append(context);
			}

			in.close();
			fo.close();
			reader.close();
			conn.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log.i("get string",String.valueOf(tmp));
		return String.valueOf(tmp);
	}

	private String _GetCookie() 
	{
		String co  = new String();
		try {
			URL url = new URL(m_head);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			//InputStream in = conn.getInputStream();
			//in.close();
			co = conn.getHeaderField("Set-Cookie");
			conn.disconnect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		co = co.substring(0,co.indexOf(";"));
		//System.out.println(co);
		return co;
	}
	private Bitmap _GetImage(String cok,int index) {

		String PostData[] = {"KBFB_LessonSel",	//课程查询 0
							"TeacherKBFB",		//教室查询 1
							"KBFB_ClassSel",	//班级查询 2
							"KBFB_RoomSel"		//教室查询 3
							};
		Date date = new Date();
		Bitmap bitmap = null;
		long times = date.getTime();
		try {
			URL url = new URL(m_head+"/sys/ValidateCode.aspx?t="+times);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Cookie", cok);
			conn.setRequestProperty("Referer", m_head+"/ZNPK/"+PostData[index]+".aspx");
			InputStream in = conn.getInputStream();
			//bitmap = BitmapFactory.decodeStream(in);

			//File file = new File("image.jpg");
			String filePath = "/sdcard/AA/";
			//存储到本地dirFile
			File dirFile = new File(Environment.getExternalStorageDirectory(), "test.jpg");
			if (!dirFile.exists()) {
				dirFile.createNewFile();
			}
			FileOutputStream fo = new FileOutputStream(dirFile);
			byte[] buf = new byte[1024];
			int length = 0;
			while ((length = in.read(buf, 0, buf.length)) != -1)
			{
				fo.write(buf, 0, length);

			}
//			byte[] buf = new byte[1024];
//			int length = 0;
//			while ((length = in.read(buf, 0, buf.length)) != -1) {
//				fo.write(buf, 0, length);
//			}
			fo.close();
			in.close();
			conn.disconnect();

			//加载
			File file2 = new File(dirFile.toString());
			if (file2.exists())
			{
				bitmap = BitmapFactory.decodeFile(dirFile.toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}


}
