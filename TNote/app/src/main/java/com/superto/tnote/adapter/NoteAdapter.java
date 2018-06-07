package com.superto.tnote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.superto.tnote.model.Note;
import com.superto.tnote.R;

import java.util.List;

/**
 * Created by 87724 on 2017/12/26.
 */

public class NoteAdapter extends BaseAdapter {

    private Context ctx;
    private List<Note> note;

    public NoteAdapter(Context ctx, List<Note> note){
        this.ctx=ctx;
        this.note=note;
    }

    @Override
    public int getCount() {
        return note.size();
    }

    @Override
    public Object getItem(int position) {
        return note.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Note cos=note.get(position);//得到当前行的Picture对象
        ViewHolder holder;
        if (convertView==null){
            LayoutInflater inflater = LayoutInflater.from(ctx);
            convertView=inflater.inflate(R.layout.item_note,null);
            holder = new ViewHolder();
            holder.tvTitle = convertView.findViewById(R.id.tv_lvTitle);
            holder.tvContent = convertView.findViewById(R.id.tv_lvContent);
            holder.tvDate = convertView.findViewById(R.id.tv_lvData);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvTitle.setText(cos.getTitle());
        holder.tvContent.setText(cos.getContent());
        holder.tvDate.setText(cos.getDate());
        return convertView;
    }

    class ViewHolder{
        private TextView tvTitle;
        private TextView tvContent;
        private TextView tvDate;

    }
}
