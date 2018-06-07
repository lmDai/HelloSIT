package com.example.luhongcheng;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.luhongcheng.secondclass.class0;
import com.example.luhongcheng.secondclass.class1;
import com.example.luhongcheng.secondclass.class2;
import com.example.luhongcheng.secondclass.class3;
import com.example.luhongcheng.secondclass.class4;
import com.example.luhongcheng.secondclass.class5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by alex233 on 2018/4/21.
 */



public class item1 extends AppCompatActivity {

    /*以下是GridView定义的*/
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter1;
    String str;
    String xuehao;
    String mima;
    List<String> cookies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item1);

        /*gridview.java*/

        gridView = (GridView) findViewById(R.id.gridview);
        //初始化数据
        initData();
        testCookie();

        String[] from={"ItemImage","ItemText"};

        int[] to={R.id.ItemImage,R.id.ItemText};

        adapter1=new SimpleAdapter(this, dataList, R.layout.gridview_item, from, to);

        gridView.setAdapter(adapter1);




   /* 给item设置点击事件*/

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent=new Intent(item1.this, class0.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1=new Intent(item1.this,class1.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2=new Intent(item1.this, class2.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Toast.makeText(item1.this,"未开放", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Intent intent4=new Intent(item1.this, class3.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5=new Intent(item1.this, class4.class);
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6=new Intent(item1.this, class5.class);
                        startActivity(intent6);
                        break;
                    case 7:
                        Toast.makeText(item1.this,"未开放", Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        Toast.makeText(item1.this,"未开放", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        });
        /*点击事件设置完毕*/
    }



    void initData() {
        //图标
        int icno[] = { R.mipmap.list23,R.mipmap.list24,R.mipmap.list23,R.mipmap.list24,R.mipmap.list23,
                R.mipmap.list1,R.mipmap.list3,R.mipmap.list1,R.mipmap.list3};
        //图标下的文字
        String name[]={
                "讲座报告",
                "公益志愿",
                "创新创业创意",
                "安全教育",
                "校园文化",
                "社团活动",
                "社会实践",
                "论文专利",
                "会议（无2学分）"

        };
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <icno.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("ItemImage", icno[i]);
            map.put("ItemText",name[i]);
            dataList.add(map);
        }
    }

    private  void  testCookie(){
        SharedPreferences spCount = getSharedPreferences("userid", 0);
        xuehao= spCount.getString("username", "");
        mima= spCount.getString("password", "");

        SharedPreferences spCount1 = getSharedPreferences("SecondClassCookie", 0);
        str = spCount1.getString("SecondClassCookie", "");
        if(xuehao.length()==10&&mima.length()>=4){
            if (str.length()== 0){
                postdata();
            }
        }

    }

    private void postdata() {
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

                    save(str);
                    //统一保存第二课堂cookie

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void save(String str) {
        SharedPreferences.Editor editor = getSharedPreferences("SecondClassCookie",0).edit();
        editor.putString("SecondClassCookie",str);
        editor.commit();
    }


}