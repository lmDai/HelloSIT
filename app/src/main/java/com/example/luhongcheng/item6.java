package com.example.luhongcheng;
/**
 * Created by alex233 on 2018/4/21.
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

import com.example.luhongcheng.NEWS.News;
import com.example.luhongcheng.NEWS.NewsAdapter;
import com.example.luhongcheng.NEWS.NewsDisplayActvivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.luhongcheng.R.id.username;


public class item6 extends AppCompatActivity implements View.OnClickListener {

    private List<News> newsList;
    private NewsAdapter adapter;
    private Handler handler;
    private ListView lv;
    private OkHttpClient okHttpClient;
    private OkHttpClient.Builder builder;
    List<String> cookies;
    String str;

    String xuehao;
    String mima;

    private ProgressBar progressBar;

    String b = "http://myportal.sit.edu.cn/";
    String LOGINURL1 = "http://myportal.sit.edu.cn/userPasswordValidate.portal";
    String LOGINURL2 = "http://myportal.sit.edu.cn/index.portal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item6);
        newsList = new ArrayList<>();
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
                    adapter = new NewsAdapter(item6.this,newsList);
                    lv.setAdapter(adapter);
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            News news = newsList.get(position);
                            Intent intent = new Intent(item6.this,NewsDisplayActvivity.class);
                            intent.putExtra("news_url",news.getNewsUrl());
                            startActivity(intent);

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
        testcookie(str);
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
                Toast.makeText(item6.this,"你还没有输入账号", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void postdata() {
        // 开启线程来发起网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();


                    Request request0 = new Request.Builder()
                            .url(b)
                            .build();
                    Response response0= client.newCall(request0).execute();
                    final Headers headers0 = response0.headers();
                    Log.d("头信息b", "header " + headers0);
                    cookies = headers0.values("Set-Cookie"); //这是另一种获取cookie的方法
                    Log.d("JSESSIONID", "onResponse-size: " + cookies);
                    String[] session = cookies.toArray(new String[cookies.size()]);
                    String str1 = null;
                    String str2 = null;
                    String str3 = null;
                    for (int i = 0; i < session.length; ++i) {
                        str1 = session[i=0];
                        str2 = session[i=1];
                        str3 = session[i=2];
                    }
                    System.out.println("1:"+str1.toString());
                    System.out.println("2:"+str2.toString());
                    System.out.println("3:"+str3.toString());
                    //str1是有用的SESSIONID


                    RequestBody requestBody = new FormBody.Builder()
                            .add("goto", "http://myportal.sit.edu.cn/loginSuccess.portal")
                            .add("gotoOnFail", "http://myportal.sit.edu.cn/loginFailure.portal")
                            .add("Login.Token1",xuehao)
                            .add("Login.Token2",mima)
                            .build();
                    Request request1 = new Request.Builder()
                            .url(LOGINURL1)
                            .post(requestBody)
                            .build();

                    Response response1 = client.newCall(request1).execute();
                    final Headers headers = response1.headers();
                    HttpUrl loginUrl = request1.url();

                    cookies = headers.values("Set-Cookie");
                    Log.d("cookie信息", "onResponse-size: " + cookies);

                    String[] strs = cookies.toArray(new String[cookies.size()]);
                    for (int i = 0; i < strs.length; ++i) {
                        str = strs[i];
                        str = str1+";"+str;
                        testcookie(str);
                    }



                    Request request = new Request.Builder()
                            .url(LOGINURL2)
                            .header("Accept", "text/html, application/xhtml+xml, image/jxr, */*")
                            .header("Accept-Language", "zh-Hans-CN,zh-Hans;q=0.5")
                            .header("Connection", "Keep-Alive")
                            .header("Cookie", str)
                            .header("Host", "myportal.sit.edu.cn")
                            .header("Referer", "http://myportal.sit.edu.cn/userPasswordValidate.portal")
                            .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; rv:11.0) like Gecko")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    getNews(responseData);


                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            //   Log.d("源代码", "onResponse: " + response.body().string().toString());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void getNews(final String responseData){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Document doc = Jsoup.parse(responseData);
                    Element url = doc.getElementById("pf8271");   //依据ID取值
                    Elements link =  url.getElementsByTag("li");

                    for(int j = 0;j < link.size();j++){
                        String uri = link.get(j).select("a.rss-title").attr("href");
                        uri = "http://myportal.sit.edu.cn/"+uri;
                        //System.out.println(uri.toString());

                        String title = link.get(j).select("a").attr("title");
                        //System.out.println(title.toString());

                        String time = link.get(j).select("span").text();
                        //System.out.println(time.toString());

                        News news = new News(title,uri,null,time);
                        newsList.add(news);
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


    private void testcookie(String str) {
        SharedPreferences.Editor editor=getSharedPreferences("cookies",0).edit();
        editor.clear();//清除消息
        editor.putString("iPlanetDirectoryPro",str); //保存iPlanetDirectoryPro
        editor.commit();

    }



}




