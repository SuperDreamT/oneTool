package com.superto.tnote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.superto.tnote.R;
import com.superto.tnote.model.Bank;
import com.superto.tnote.model.Birthday;

import java.util.List;

/**
 * Created by 87724 on 2017/12/26.
 */

public class BirthdayAdapter extends BaseAdapter {

    private Context ctx;
    private List<Birthday> birthday;

    public BirthdayAdapter(Context ctx, List<Birthday> birthday){
        this.ctx=ctx;
        this.birthday=birthday;
    }

    @Override
    public int getCount() {
        return birthday.size();
    }

    @Override
    public Object getItem(int position) {
        return birthday.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Birthday cos=birthday.get(position);//得到当前行的Picture对象
        ViewHolder holder;
        if (convertView==null){
            LayoutInflater inflater = LayoutInflater.from(ctx);
            convertView=inflater.inflate(R.layout.item_birthday,null);
            holder = new ViewHolder();
            holder.people = convertView.findViewById(R.id.iv_birthday_people);
            holder.name = convertView.findViewById(R.id.tv_birthday_name);
            holder.time = convertView.findViewById(R.id.tv_birthday_time);
            holder.countdown = convertView.findViewById(R.id.tv_birthday_countdown);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.name.setText(cos.getName());
        holder.time.setText(cos.getDate());
        holder.countdown.setText(cos.getCountdown());
        return convertView;
    }

    class ViewHolder{
        private ImageView people;
        private TextView name;
        private TextView time;
        private TextView countdown;

    }
}
