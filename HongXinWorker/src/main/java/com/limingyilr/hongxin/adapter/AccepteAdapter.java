package com.limingyilr.hongxin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.limingyilr.hongxin.R;
import com.limingyilr.hongxin.bean.AcceptOrder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lmy on 2014/12/3.
 */
public class AccepteAdapter extends BaseAdapter {
    private Context context;
    private boolean viewTip;
    private List<AcceptOrder> list;

    public AccepteAdapter(Context context, List<AcceptOrder> list) {
        this.context = context;
        this.list = list;
        this.viewTip = true;
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
                R.layout.item_accept, null);
        TextView school = (TextView) convertView.findViewById(R.id.item_accept_school);
        TextView room = (TextView) convertView.findViewById(R.id.item_accept_room);
        TextView orderId = (TextView) convertView.findViewById(R.id.item_accept_order);
        TextView tip = (TextView) convertView.findViewById(R.id.item_accept__enter);
        if (!viewTip) tip.setVisibility(TextView.INVISIBLE);
        school.setText(list.get(position).getSchool());
        room.setText(list.get(position).getRoomName());
        orderId.setText(list.get(position).getOrderId());
        return convertView;
    }

    public void synchronize(List<AcceptOrder> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public boolean isViewTip() {
        return viewTip;
    }

    public void setViewTip(boolean viewTip) {
        this.viewTip = viewTip;
    }
}
