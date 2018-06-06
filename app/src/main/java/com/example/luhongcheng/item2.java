package com.example.luhongcheng;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class item2 extends Activity {
    private WebView webView;
    Map<String,String> extraHeaders = new HashMap<String, String>();


    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    List<String> cookies;
    String str;
    String b = "http://ems.sit.edu.cn/";
    String d = "http://ems.sit.edu.cn:85/student/selCourse/syllabuslist.jsp"; //总课表
    String c = "http://ems.sit.edu.cn:85/student/selCourse/syllabuslist2.jsp?yearTerm=2018%B4%BA&yearTerm2=2017-2018%B5%DA2%D1%A7%C6%DA&cType=1";//周课表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.item2);
        webView = (WebView) this.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new WebPlugin(), "WebPlugin");

        postdata();

    }

    public void postdata() {
        // 开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final OkHttpClient client = new OkHttpClient().newBuilder()
                            .followRedirects(false)//禁止重定向
                            .followSslRedirects(false)//哈哈哈哈哈哈哈好开心啊
                            .build();

                    Request request1 = new Request.Builder()
                            .url(b)
                            .addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                            .addHeader("Accept-Language","zh-CN,zh;q=0.9")
                            .addHeader("Connection","Keep-Alive")
                            .addHeader("Host","ems.sit.edu.cn:85")
                            .addHeader("Upgrade-Insecure-Requests","1")
                            .addHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                            .build();
                    Response response1 = client.newCall(request1).execute();
                    String responseData1 = response1.body().string();
                    final Headers headers1 = response1.headers();
                    //Log.d("头信息11", "header " + headers1);
                    cookies = headers1.values("Set-Cookie"); //这是另一种获取cookie的方法
                    //Log.d("JSESSIONID11", "onResponse-size: " + cookies);

                    String[] aa = cookies.toArray(new String[cookies.size()]);
                    String str1 = null;
                    String str2 = null;
                    for (int i = 0; i < aa.length; ++i) {
                        str1 = aa[i=0];
                        str2 = aa[i=1];
                    }
                    //System.out.println("1:"+str1.toString());
                    //System.out.println("2:"+str2.toString());

                    RequestBody requestBody1 = new FormBody.Builder()
                            .add("loginName","1510400642")
                            .add("password","199709")
                            .add("authtype","2")
                            .add("loginYzm","")
                            .add("Login.Token1","")
                            .add("Login.Token2","")
                            .build();
                    Request request2 = new Request.Builder()
                            .url("http://ems.sit.edu.cn:85/login.jsp")
                            .post(requestBody1)
                            .header("Accept","text/html, application/xhtml+xml, image/jxr, */*")
                            .header("Accept-Language","zh-CN,zh;q=0.9")
                            .header("Cache-Control","max-age=0")
                            .header("Connection","Keep-Alive")
                            .header("Content-Length","85")
                            .header("Content-Type","application/x-www-form-urlencoded")
                            .header("Cookie",str1)
                            .header("Host","ems.sit.edu.cn:85")
                            .header("Origin","http://ems.sit.edu.cn:85")
                            .header("Referer","http://ems.sit.edu.cn:85/")
                            .addHeader("Upgrade-Insecure-Requests","1")
                            .header("User-Agent","Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                            .build();

                    Response response2 = client.newCall(request2).execute();
                    String responseData2 = response2.body().string();
                    final Headers headers2 = response2.headers();
                    //Log.d("头信息22", "header " + headers2);
                    cookies = headers2.values("Set-Cookie"); //这是另一种获取cookie的方法
                    //Log.d("cookie22", "onResponse-size: " + cookies);

                    String[] bb = cookies.toArray(new String[cookies.size()]);
                    String str3 = null;
                    String str4 = null;
                    String str5 = null;
                    for (int i = 0; i < bb.length; ++i) {
                        str3 = bb[i=0];
                        str4 = bb[i=1];
                        str5 = bb[i=2];
                    }
                    //System.out.println("3:"+str3.toString());
                    //System.out.println("4:"+str4.toString());
                    //System.out.println("5:"+str5.toString());

                    str = str1+";"+str2+";"+str3+";"+str4;
                    //System.out.println("总共的cookies:"+str.toString());

                    Request request3 = new Request.Builder()
                            .url("http://ems.sit.edu.cn:85/student/selCourse/syllabuslist2.jsp?yearTerm=2018%B4%BA&yearTerm2=2017-2018%B5%DA2%D1%A7%C6%DA&cType=1")
                            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                            // .header("Accept-Encoding", "gzip, deflate")
                            .header("Accept-Language", "zh-CN,zh;q=0.9")
                            .header("Cache-Control","max-age=0")
                            .header("Connection", "Keep-Alive")
                            .header("Cookie",str)
                            .header("Host", "ems.sit.edu.cn:85")
                            .header("Referer", "http://ems.sit.edu.cn:85/")
                            .header("Upgrade-Insecure-Requests","1")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                            .build();
                    Response response3 = client.newCall(request3).execute();
                    String responseData3 = response3.body().string();
                    save(responseData3);
                    webview(responseData3);



                    okHttpClient.newCall(request3).enqueue(new Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {
                        }
                        @Override
                        public void onResponse(okhttp3.Call call, Response response) throws IOException {
                            Log.d("源代码", "onResponse: " + response.body().string().toString());
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void save(String responseData3) {
        SharedPreferences.Editor  editor1 = getSharedPreferences("1zhou",0).edit();
        editor1.putString("1zhou",responseData3);
        editor1.commit();

    }


    private void webview(String responseData3) {

        webView.loadData(responseData3,"xml","UTF-8");

    }


    private class WebPlugin {

        @JavascriptInterface
        public void test() {
            Log.e("miquan", "kkkkkk");
            Toast.makeText(item2.this, "test toast ", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public String test2() {
            return "something";
        }
    }

}