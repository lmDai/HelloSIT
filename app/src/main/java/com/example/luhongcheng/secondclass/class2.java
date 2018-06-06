package com.example.luhongcheng.secondclass;

/**
 * Created by alex233 on 2018/6/2.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import com.example.luhongcheng.R;
import com.example.luhongcheng.item3;


public class class2 extends AppCompatActivity implements View.OnClickListener {

    private List<SecondClass> secondClassList;
    private SecondclassAdapter adapter;
    private Handler handler;
    private ListView lv;

    private Button sendpostdata;
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    List<String> cookies;
    private ProgressBar progressBar;


    String xuehao;
    String mima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item6);
        secondClassList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.news_lv);

        Button sendpostdata = (Button) findViewById(R.id.send_request);
        sendpostdata.setOnClickListener(this);
        builder = new OkHttpClient.Builder();
        okHttpClient = builder.build();
        progressBar = (ProgressBar) findViewById(R.id.progressBarNormal) ;



        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 1){
                    progressBar.setVisibility(View.GONE);
                    adapter = new SecondclassAdapter(class2.this, secondClassList);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            SecondClass secondClass = secondClassList.get(position);
                            Intent intent = new Intent(class2.this,SecondclassDisplayActvivity.class);
                            intent.putExtra("news_url", secondClass.getA2());
                            startActivity(intent);
                            //Intent intent = new Intent(MainActivity.this,NewsDisplayActvivity.class);
                            //intent.putExtra("news_url",news.getNewsUrl());
                            //startActivity(intent);

                            //Intent intent2 = new Intent(MainActivity.this,NewsDisplayActvivity.class);
                            //intent2.putExtra("COOKIE",str);
                            //startActivity(intent2);
                            //此处不能传递COOKIE，可能会混淆
                        }
                    });
                }
            }
        };
        getID();
        postdata();
    }

    private void getID() {
        SharedPreferences spCount = getSharedPreferences("userid", 0);
        xuehao= spCount.getString("username", "");
        mima= spCount.getString("password", "");

    }

    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            if(xuehao.length()==10&&mima.length()>=4){
                postdata();
            }
            else {
                Toast.makeText(class2.this,"你还没有输入账号", Toast.LENGTH_SHORT).show();
            }

        }
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


                    RequestBody requestBody1 = new FormBody.Builder()
                            .add("goto", "http://myportal.sit.edu.cn/loginSuccess.portal")
                            .add("gotoOnFail", "http://myportal.sit.edu.cn/loginFailure.portal")
                            .add("Login.Token1",xuehao)
                            .add("Login.Token2",mima)
                            .build();
                    Request request1 = new Request.Builder()
                            .url("http://myportal.sit.edu.cn/userPasswordValidate.portal")
                            .post(requestBody1)
                            .build();
                    Response response1 = client.newCall(request1).execute();

                    final Headers headers1 = response1.headers();
                    cookies = headers1.values("Set-Cookie");
                    Log.d("cookie信息A", "onResponse-size: " + cookies);
                    String[] iPlanetDirectoryPro = cookies.toArray(new String[cookies.size()]);
                    String str1 = null;
                    for (int i = 0; i < iPlanetDirectoryPro.length; ++i) {
                        str1 = iPlanetDirectoryPro[i];
                    }
                    System.out.println("iPlanetDirectoryPro:"+str1.toString());
                    //str1是iPlanetDirectoryPro的值


                    Request request2 = new Request.Builder()
                            .url("http://sc.sit.edu.cn/login.jsp")
                            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                            .header("Accept-Language", "zh-CN,zh;q=0.9")
                            .header("Connection", "Keep-Alive")
                            .header("Cookie", str1)
                            .header("Host", "sc.sit.edu.cn")
                            .header("Upgrade-Insecure-Requests","1")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3423.2 Safari/537.36")
                            .build();
                    Response response2 = client.newCall(request2).execute();

                    final Headers headers2 = response2.headers();
                    cookies = headers2.values("Set-Cookie");
                    String[] as = cookies.toArray(new String[cookies.size()]);
                    String str2 = null;
                    for (int i = 0; i < as.length; ++i) {
                        str2 = as[i];
                    }
                    System.out.println("假的JSESSIONID:"+str2.toString());
                    //str2是第二课堂返回的第一个session
                    String str = str1+";"+str2;
                    System.out.println("set_cookie:"+str.toString());
                    str = str.replaceAll("Path=/; HttpOnly","");
                    str = str.replaceAll("Path=/; Domain=.sit.edu.cn;","");
                    System.out.println("str:"+str.toString());


                    Request request3 = new Request.Builder()
                            .url("http://sc.sit.edu.cn/j_spring_security_check?j_username=1510400642&returnUrl=")
                            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                            .header("Accept-Language", "zh-CN,zh;q=0.9")
                            .header("Connection", "Keep-Alive")
                            .header("Cookie", str)
                            .header("Host", "sc.sit.edu.cn")
                            .header("Upgrade-Insecure-Requests","1")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3423.2 Safari/537.36")
                            .build();
                    Response response3 = client.newCall(request3).execute();
                    final Headers headers3 = response3.headers();
                    Log.d("头信息", "header " + headers3);
                    cookies = headers3.values("Set-Cookie");
                    String[] ad = cookies.toArray(new String[cookies.size()]);
                    String str3 = null;
                    for (int i = 0; i < ad.length; ++i) {
                        str3 = ad[i];
                    }
                    System.out.println("真的JSESSIONID:"+str3.toString());
                    str = str1+";"+str3;
                    System.out.println("str:"+str.toString());

                    Request request4 = new Request.Builder()
                            .url("http://sc.sit.edu.cn/public/activity/activityList.action?categoryId=ff8080814e241104014eb867e1481dc3")
                            .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                            .header("Accept-Language", "zh-CN,zh;q=0.9")
                            .header("Connection", "Keep-Alive")
                            .header("Cookie", str)
                            .header("Host", "sc.sit.edu.cn")
                            .header("Upgrade-Insecure-Requests","1")
                            // .header("Referer", "http://myportal.sit.edu.cn/userPasswordValidate.portal")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3423.2 Safari/537.36")
                            .build();
                    Response response4 = client.newCall(request4).execute();
                    String responseData4 = response4.body().string();
                    getNews(responseData4);




                    okHttpClient.newCall(request4).enqueue(new Callback() {
                        @Override
                        public void onFailure(okhttp3.Call call, IOException e) {
                        }
                        @Override
                        public void onResponse(okhttp3.Call call, Response response) throws IOException {
                            //Log.d("源代码", "onResponse: " + response.body().string().toString());
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void getNews(final String responseData4){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Document doc = Jsoup.parse(responseData4);
                    //Element url = doc.getElementById("pf8271");   //依据ID取值
                    Elements link =  doc.getElementsByTag("li");

                    for(int j = 2;j < link.size();j++){
                        String A1 = link.get(j).select("a").text();
                        System.out.println("A1"+A1.toString());

                        String A2 = link.get(j).select("a").attr("href");
                        A2 = "http://sc.sit.edu.cn"+A2;
                        System.out.println("A2"+A1.toString());

                        String A3 = link.get(j).select("span").text();
                        System.out.println("A3"+A1.toString());

                        SecondClass secondClass = new SecondClass(A1,A2,A3);
                        secondClassList.add(secondClass);
                    }


                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}





