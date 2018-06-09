package com.example.luhongcheng;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;



/*Gridview*/

/**
 * Created by Administrator on 2018/4/7.
 */

public class OneFragment extends Fragment {
    private String context;
    public OneFragment(String context){
        this.context = context;
    }
    /*以下是GridView定义的*/
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter1;

    /*注销APP*/
    private long mExitTime;

    /*轮换图片定义的*/
    //统计下载了几张图片
    int n=0;
    //统计当前viewpager轮播到第几页
    int p=0;
    private ViewPager vp;
    //准备好三张网络图片的地址
    private String imageUrl[]=new String[]
            {"http://www.sit.edu.cn/page/main297/images/1.jpg",
                    "http://www.sit.edu.cn/page/main297/images/2.jpg",
                    "http://www.sit.edu.cn/page/main297/images/3.jpg",
                    "http://www.sit.edu.cn/page/main297/images/4.jpg"};
    //装载下载图片的集合
    private List<ImageView> data;
    //控制图片是否开始轮播的开关,默认关的
    private boolean isStart=false;
    //开始图片轮播的线程
    private MyThread t;
    //存放代表viewpager播到第几张的小圆点
    private LinearLayout ll_tag;
    //存储小圆点的一维数组
    private ImageView tag[];
    private Handler mHandler=new Handler(){
        public void handleMessage(android.os.Message msg) {
            switch(msg.what){
                case 0:
                    n++;
                    Bitmap bitmap=(Bitmap) msg.obj;
                    ImageView iv=new ImageView(getActivity());
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    iv.setImageBitmap(bitmap);
                    //把图片添加到集合里
                    data.add(iv);
                    //当接收到第三张图片的时候，设置适配器,
                    if(n==imageUrl.length){
                        vp.setAdapter(new ImageLunhuanAdapter(data,getActivity()));
                        //创建小圆点
                        creatTag();
                        //把开关打开
                        isStart=true;
                        t= new MyThread();
                        //启动轮播图片线程
                        t.start();
                    }
                    break;
                case 1:
                    //接受到的线程发过来的p数字
                    int page=(Integer) msg.obj;
                    vp.setCurrentItem(page);
                    break;
            }
        };
    };


    String username;
    String password;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.a_fragment, container, false);
        return  view;
    }

    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*gridview.java*/
        gridView = (GridView) getView().findViewById(R.id.gridview);
        //初始化数据
        initData();

        String[] from={"ItemImage","ItemText"};

        int[] to={R.id.ItemImage,R.id.ItemText};

        adapter1=new SimpleAdapter(getContext(), dataList, R.layout.gridview_item, from, to);

        gridView.setAdapter(adapter1);


   /* 给item设置点击事件*/

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent=new Intent(getActivity(),item0.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1=new Intent(getActivity(),item1.class);
                        startActivity(intent1);
                        break;
                    case 2:
                        Intent intent2=new Intent(getActivity(),item2.class);
                        startActivity(intent2);
                        break;
                    case 3:
                        Intent intent3=new Intent(getActivity(),item3.class);
                        startActivity(intent3);
                        break;
                    case 4:
                        Intent intent4=new Intent(getActivity(),item4.class);
                        startActivity(intent4);
                        break;
                    case 5:
                        Intent intent5=new Intent(getActivity(),item5.class);
                        startActivity(intent5);
                        break;
                    case 6:
                        Intent intent6=new Intent(getActivity(),item6.class);
                        startActivity(intent6);
                        break;
                    case 7:
                        Intent intent7=new Intent(getActivity(),item7.class);
                        startActivity(intent7);
                        break;
                    case 8:
                        Intent intent8=new Intent(getActivity(),item8.class);
                        startActivity(intent8);
                        break;
                    case 9:
                        Intent intent9=new Intent(getActivity(),item9.class);
                        startActivity(intent9);
                        break;
                    case 10:
                        Intent intent10=new Intent(getActivity(),item10.class);
                        startActivity(intent10);
                        break;
                    case 11:
                        Intent intent11=new Intent(getActivity(),item11.class);
                        startActivity(intent11);
                        break;
                    case 12:
                        Intent intent12=new Intent(getActivity(),item12.class);
                        startActivity(intent12);
                        break;
                    case 13:
                        Intent intent13=new Intent(getActivity(),item13.class);
                        startActivity(intent13);
                        break;
                    case 14:
                        Intent intent14 = new Intent(getActivity(),item14.class);
                        startActivity(intent14);
                        break;
                    default:
                        break;
                }
            }
        });
        /*点击事件设置完毕*/
        init();


    }


    void initData() {
        //图标
        int icno[] = { R.mipmap.g12,R.mipmap.g1, R.mipmap.g2,R.mipmap.g3,R.mipmap.g4,R.mipmap.g5,R.mipmap.g6,R.mipmap.g7,R.mipmap.g8,
                R.mipmap.g9,R.mipmap.g10,R.mipmap.g11,R.mipmap.g0,R.mipmap.g13,R.mipmap.g15};
        //图标下的文字
        String name[]={"个人信息","第二课堂","我的课表","考试安排","成绩查看",
                "电费查询","学生事务","学习课堂","校园文化",
                "二级学院","文件下载","校园公告","我的账号","关于our","其他系统"};
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i <icno.length; i++) {
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("ItemImage", icno[i]);
            map.put("ItemText",name[i]);
            dataList.add(map);
        }
    }

    /*lunhuan.java*/



    /*轮换图片*/
    private void init() {
        // TODO Auto-generated method stub
        vp=(ViewPager) getView().findViewById(R.id.vp);
        ll_tag=(LinearLayout) getView().findViewById(R.id.ll_tag);
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                //把当前的页数赋值给P
                p=position;
                //得到当前图片的索引,如果图片只有三张，那么只有0，1，2这三种情况
                int currentIndex=(position%imageUrl.length);
                for(int i=0;i<tag.length;i++){
                    if(i==currentIndex){
                        tag[i].setBackgroundResource(R.drawable.dot_focused);
                    }else{
                        tag[i].setBackgroundResource(R.drawable.dot_normal);
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
        //构造一个存储照片的集合
        data=new ArrayList<ImageView>();
        //从网络上把图片下载下来
        for(int i=0;i<imageUrl.length;i++){
            getImageFromNet(imageUrl[i]);
        }
    }

    private void getImageFromNet(final String imagePath) {
        // TODO Auto-generated method stub
        new Thread(){
            public void run() {
                try {
                    URL url=new URL(imagePath);
                    HttpURLConnection con=(HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(10*1000);
                    InputStream is=con.getInputStream();
                    //把流转换为bitmap
                    Bitmap bitmap= BitmapFactory.decodeStream(is);
                    Message message=new Message();
                    message.what=0;
                    message.obj=bitmap;
                    //把这个bitmap发送到hanlder那里去处理
                    mHandler.sendMessage(message);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            };
        }.start();

    }
    //控制图片轮播
    class MyThread extends Thread{
        @Override
        public void run() {
            // TODO Auto-generated method stub
            super.run();
            while(isStart){
                Message message=new Message();
                message.what=1;
                message.obj=p;
                mHandler.sendMessage(message);
                try {
                    //睡眠3秒,在isStart为真的情况下，一直每隔三秒循环
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                p++;
            }
        }
    }
    protected void creatTag() {
        tag=new ImageView[imageUrl.length];
        for(int i=0;i<imageUrl.length;i++){

            tag[i]=new ImageView(getActivity());
            //第一张图片画的小圆点是白点
            if(i==0){
                tag[i].setBackgroundResource(R.drawable.dot_focused);
            }else{
                //其它的画灰点
                tag[i].setBackgroundResource(R.drawable.dot_normal);
            }
            //设置上下左右的间隔
            tag[i].setPadding(0,0,40,40);
            tag[i].setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            //添加到viewpager底部的线性布局里面
            ll_tag.addView(tag[i]);
        }
    }


}

