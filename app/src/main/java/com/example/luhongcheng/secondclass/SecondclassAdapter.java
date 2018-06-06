package com.example.luhongcheng.secondclass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.luhongcheng.R;

import java.util.List;

/**
 * Created by Administrator on 2017/1/21.
 */

public class SecondclassAdapter extends BaseAdapter {

    private List<SecondClass> secondClassList;
    private View view;
    private Context mContext;
    private ViewHolder viewHolder;

    public SecondclassAdapter(Context mContext, List<SecondClass> secondClassList) {
        this.secondClassList = secondClassList;
        this.mContext= mContext;
    }

    @Override
    public int getCount() {
        return secondClassList.size();
    }

    @Override
    public Object getItem(int position) {
        return secondClassList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.secondclass_item,
                    null);
            viewHolder = new ViewHolder();
            viewHolder.A1 = (TextView) view.findViewById(R.id.A1);
            //viewHolder.A2 = (TextView) view.findViewById(R.id.A2);
            viewHolder.A3 = (TextView) view.findViewById(R.id.A3);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.A1.setText(secondClassList.get(position).getA1());
        //viewHolder.A2.setText(" "+newsList.get(position).getA2());
        viewHolder.A3.setText(secondClassList.get(position).getA3());
        return view;
    }

    class ViewHolder{
        TextView A1;
        //TextView A2;
        TextView A3;

    }

}
