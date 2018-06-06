package com.example.luhongcheng;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.luhongcheng.about.about0;
import com.example.luhongcheng.about.about1;
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

/**
 * Created by alex233 on 2018/4/21.
 */

public class item1 extends AppCompatActivity {

    /*以下是GridView定义的*/
    private GridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item1);

        /*gridview.java*/

        gridView = (GridView) findViewById(R.id.gridview);
        //初始化数据
        initData();

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



}