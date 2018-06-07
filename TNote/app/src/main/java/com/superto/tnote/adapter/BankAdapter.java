package com.superto.tnote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.superto.tnote.R;
import com.superto.tnote.model.Bank;

import java.util.List;

/**
 * Created by 87724 on 2017/12/26.
 */

public class BankAdapter extends BaseAdapter {

    private Context ctx;
    private List<Bank> bank;

    public BankAdapter(Context ctx, List<Bank> bank){
        this.ctx=ctx;
        this.bank=bank;
    }

    @Override
    public int getCount() {
        return bank.size();
    }

    @Override
    public Object getItem(int position) {
        return bank.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Bank cos=bank.get(position);//得到当前行的Picture对象
        ViewHolder holder;
        if (convertView==null){
            LayoutInflater inflater = LayoutInflater.from(ctx);
            convertView=inflater.inflate(R.layout.item_bank,null);
            holder = new ViewHolder();
            //为的是listview滚动的时候快速设置值
            holder.cardNumber = convertView.findViewById(R.id.tv_bank_bankNumber);
            holder.cardName = convertView.findViewById(R.id.tv_bank_bankName);
            holder.cardHolder = convertView.findViewById(R.id.tv_bank_cardholder);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.cardNumber.setText(cos.getCardNumber());
        holder.cardName.setText(cos.getCardName());
        holder.cardHolder.setText(cos.getCardHolder());
        return convertView;
    }

    class ViewHolder{
        private TextView cardNumber;
        private TextView cardName;
        private TextView cardHolder;

    }
}
