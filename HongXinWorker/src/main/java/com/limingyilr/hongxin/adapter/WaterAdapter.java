package com.limingyilr.hongxin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.limingyilr.hongxin.R;
import com.limingyilr.hongxin.bean.Water;
import com.limingyilr.hongxin.bean.Worker;

import java.util.List;

/**
 * Created by lmy on 2014/12/7.
 */
public class WaterAdapter extends BaseAdapter {
    private Context context;
    private List<Water> list;

    public WaterAdapter(Context context, List<Water> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(
                R.layout.item_water, null);
        TextView school = (TextView) convertView.findViewById(R.id.item_water_school);
        TextView room = (TextView) convertView.findViewById(R.id.item_water_room);
        TextView name = (TextView) convertView.findViewById(R.id.item_water_name);
        TextView id = (TextView) convertView.findViewById(R.id.item_water_id);
        TextView time = (TextView) convertView.findViewById(R.id.item_water_time);

        school.setText(list.get(position).getSchool());
        room.setText(list.get(position).getRoomName());
        name.setText(list.get(position).getWorkerName());
        id.setText(list.get(position).getOrderId());
        time.setText(list.get(position).getTime());
        return convertView;
    }

    public void synchronize(List<Water> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
