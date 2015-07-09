package com.limingyilr.hongxin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.limingyilr.hongxin.R;
import com.limingyilr.hongxin.bean.Ticket;
import com.limingyilr.hongxin.bean.Water;

import java.util.List;

/**
 * Created by lmy on 2014/12/7.
 */
public class TicketAdapter  extends BaseAdapter {
    private Context context;
    private List<Ticket> list;

    public TicketAdapter(Context context, List<Ticket> list) {
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
                R.layout.item_ticket, null);
        TextView school = (TextView) convertView.findViewById(R.id.item_ticket_school);
        TextView room = (TextView) convertView.findViewById(R.id.item_ticket_room);
        TextView amount = (TextView) convertView.findViewById(R.id.item_ticket_amount);
        TextView id = (TextView) convertView.findViewById(R.id.item_ticket_id);
        TextView time = (TextView) convertView.findViewById(R.id.item_ticket_time);

        school.setText(list.get(position).getSchool());
        room.setText(list.get(position).getRoom());
        amount.setText(list.get(position).getAmount());
        id.setText(list.get(position).getOrderId());
        time.setText(list.get(position).getTime());
        return convertView;
    }

    public void synchronize(List<Ticket> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
