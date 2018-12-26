package com.gcm.haoye.goodclassmate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by kancheng on 2017/12/7.
 */

public class MyAdapter extends BaseAdapter {
    //private int  saveYear, saveMonth, saveDay, saveHour, saveMinute;
    Context context;
    public List<Comment> list;
    private CommentsDataSource datasource;

    public MyAdapter(Context context,List<Comment> values) {
        this.list = values;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Comment getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getList_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null) {
            holder = new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.test,parent,false);
            TextView view_id = convertView.findViewById(R.id.list_id);
            TextView view_name = convertView.findViewById(R.id.name);
            TextView view_money = convertView.findViewById(R.id.money);
            Button view_deletebutton = convertView.findViewById(R.id.deletebutton);
            holder.view_id = view_id;
            holder.view_name = view_name;
            holder.view_money = view_money;
            holder.view_deletebutton = view_deletebutton;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Comment comment = list.get(position);
        //DateFormat.getDateInstance(DateFormat.SHORT).format(comment.getDatestart());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        long l = Long.parseLong(comment.getDatestart());
        //String formattedDate = sdf.format(l);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(l);
        calendar.add(Calendar.MONTH, 1);
        Date tdt=calendar.getTime();
        String time=sdf.format(tdt);

        holder.view_id.setText( time+ "");
        holder.view_money.setText(comment.getMoney() + "");
        holder.view_name.setText(comment.getName());
        holder.view_deletebutton
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                datasource = new CommentsDataSource(context);
                                datasource.open();
                                datasource.deleteComment(comment.getList_id());
                                list = datasource.getAllComments();
                                /* 更新畫面 */
                                notifyDataSetChanged();
                            }
                        }
                );
        return convertView;
    }


    class ViewHolder{
        TextView view_id;
        TextView view_name ;
        TextView view_money ;
        Button view_deletebutton;
    }
}
