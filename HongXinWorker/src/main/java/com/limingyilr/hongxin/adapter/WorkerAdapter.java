package com.limingyilr.hongxin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.limingyilr.hongxin.R;
import com.limingyilr.hongxin.bean.AcceptOrder;
import com.limingyilr.hongxin.bean.Worker;

import java.util.List;

/**
 * Created by lmy on 2014/12/6.
 */
public class WorkerAdapter extends BaseAdapter {
    private Context context;
    private List<Worker> list;

    public WorkerAdapter(Context context, List<Worker> list) {
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
                R.layout.item_worker, null);
        TextView name = (TextView) convertView.findViewById(R.id.item_worker_name);
        TextView phone = (TextView) convertView.findViewById(R.id.item_worker_phone);
        TextView id = (TextView) convertView.findViewById(R.id.item_worker_id);
        TextView status = (TextView) convertView.findViewById(R.id.item_worker_status);
        TextView amount = (TextView) convertView.findViewById(R.id.item_worker_amount);
        name.setText(list.get(position).getName());
        phone.setText(list.get(position).getPhoneNumber());
        id.setText(list.get(position).getWorkerId());
        status.setText(list.get(position).getOpenId());
        amount.setText(list.get(position).getCompletedAmount());
        return convertView;
    }

    public void synchronize(List<Worker> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
