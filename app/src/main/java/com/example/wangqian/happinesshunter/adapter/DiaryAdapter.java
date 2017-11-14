package com.example.wangqian.happinesshunter.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangqian.happinesshunter.R;
import com.example.wangqian.happinesshunter.entity.Diary;
import com.example.wangqian.happinesshunter.tools.DateTool;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * Created by salto on 2017-11-13.
 */

public class DiaryAdapter extends BaseAdapter {

    private Context context;
    private List<Diary> diaryList = null;

    public DiaryAdapter(Context context, List<Diary> diaryList) {
        this.context = context;
        this.diaryList = diaryList;
    }

    @Override
    public int getCount() {
        return diaryList.size();
    }

    @Override
    public Object getItem(int position) {
        return diaryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return diaryList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.happy = (ImageView) convertView.findViewById(R.id.happy);
            holder.time = (TextView) convertView.findViewById(R.id.createtime);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder)convertView.getTag();
        }
        Diary diary = diaryList.get(position);
        holder.title.setText(diary.getTitle());
        holder.content.setText(diary.getContent());
        holder.time.setText(diary.getCreatetime());

//        holder.time.setText(DateTool.DATE_FORMAT.format(new Date(diary.getCreatetime())));


        holder.happy.setImageResource(R.mipmap.a11);

            switch (diary.getHappy()){
                case 3:
                    holder.happy.setImageResource(R.mipmap.a23);
                    break;
                case 2:
                    holder.happy.setImageResource(R.mipmap.a11);
                    break;
                case 1:
                    holder.happy.setImageResource(R.mipmap.a12);
                    break;
                case 0:
                    holder.happy.setImageResource(R.mipmap.a13);
                    break;
                default:
                    break;
            }



        return convertView;
    }


    class ViewHolder {
        TextView title;
        TextView content;
        ImageView happy;
        TextView time;

    }
}

