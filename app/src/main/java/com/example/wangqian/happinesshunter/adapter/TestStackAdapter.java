package com.example.wangqian.happinesshunter.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangqian.happinesshunter.R;
import com.example.wangqian.happinesshunter.activity.DiaryActivity;
import com.example.wangqian.happinesshunter.activity.DiaryEditActivity;
import com.example.wangqian.happinesshunter.entity.Diary;
import com.loopeer.cardstack.CardStackView;
import com.loopeer.cardstack.StackAdapter;

import java.util.List;



public class TestStackAdapter extends StackAdapter<Diary> {
    public static Integer[] TEST_DATAS = new Integer[]{
            R.color.color_1,
            R.color.color_2,
            R.color.color_3,
            R.color.color_4,
            R.color.color_5,
            R.color.color_6,
            R.color.color_7,
            R.color.color_8,
            R.color.color_9,
            R.color.color_10,
            R.color.color_11,
            R.color.color_12,
            R.color.color_13,
            R.color.color_14,
            R.color.color_15,
            R.color.color_16,
            R.color.color_17,
            R.color.color_18,
            R.color.color_19,
            R.color.color_20,
            R.color.color_21,
            R.color.color_22,
            R.color.color_23,
            R.color.color_24,
            R.color.color_25,
            R.color.color_26
    };
    private List<Diary> diaryList;
    public TestStackAdapter(Context context, List<Diary> list) {
        super(context);
        this.diaryList = list;
    }

    @Override
    public void bindView(int position, CardStackView.ViewHolder holder) {
        if (holder instanceof ColorItemLargeHeaderViewHolder) {
            ColorItemLargeHeaderViewHolder h = (ColorItemLargeHeaderViewHolder) holder;
            h.onBind(position);
        }
        if (holder instanceof ColorItemWithNoHeaderViewHolder) {
            ColorItemWithNoHeaderViewHolder h = (ColorItemWithNoHeaderViewHolder) holder;
            h.onBind(position);
        }
        if (holder instanceof ColorItemViewHolder) {
            ColorItemViewHolder h = (ColorItemViewHolder) holder;
            if (diaryList.size()>position){
                Log.e("xxxxxx",diaryList.get(position).getContent());

            }
            if (diaryList.size()>position){

                String str = diaryList.get(position).getContent();
                int happy = diaryList.get(position).getHappy();
                String title = diaryList.get(position).getTitle();
                h.onBind(diaryList.get(position).getId(),"         "+title,happy,str,position);
            }else{
                h.onBind(-1,"         no title",1,"nothing",position);
            }

        }
    }

    @Override
    protected CardStackView.ViewHolder onCreateView(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case R.layout.list_card_item_larger_header:
                view = getLayoutInflater().inflate(R.layout.list_card_item_larger_header, parent, false);
                return new ColorItemLargeHeaderViewHolder(view);
            case R.layout.list_card_item_with_no_header:
                view = getLayoutInflater().inflate(R.layout.list_card_item_with_no_header, parent, false);
                return new ColorItemWithNoHeaderViewHolder(view);
            default:
                view = getLayoutInflater().inflate(R.layout.list_card_item, parent, false);
                return new ColorItemViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
    //    if (position == 6) {//TODO TEST LARGER ITEM
     //       return R.layout.list_card_item_larger_header;
    //    } else if (position == 10) {
   //         return R.layout.list_card_item_with_no_header;
    //    }else {
            return R.layout.list_card_item;
     //   }
    }

    static class ColorItemViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;
        TextView content;
        ImageView face;

        public ColorItemViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
            content = (TextView) view.findViewById(R.id.text_view);
            face = (ImageView) view.findViewById(R.id.face);

        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        public void onBind(final int id,String title,int happy,String str, int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), TEST_DATAS[position%25]), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(title);
            switch (happy){
                case 3:
                    face.setImageResource(R.mipmap.a23);
                    break;
                case 2:
                    face.setImageResource(R.mipmap.a11);
                    break;
                case 1:
                    face.setImageResource(R.mipmap.a12);
                    break;
                case 0:
                    face.setImageResource(R.mipmap.a13);
                    break;
                default:
                    break;
            }

            content.setText(str);
            content.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getContext(), DiaryEditActivity.class);
                    intent.putExtra("id",id);
                    getContext().startActivity(intent);
                    //跳转到DiaryEditActivity，并将选中日记对应的id传过去
                    return false;
                }
            });
        }

    }

    static class ColorItemWithNoHeaderViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        TextView mTextTitle;

        public ColorItemWithNoHeaderViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);

        }

        @Override
        public void onItemExpand(boolean b) {
        }

        public void onBind(int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(), TEST_DATAS[position%25]), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(String.valueOf(position));
        }

    }

    static class ColorItemLargeHeaderViewHolder extends CardStackView.ViewHolder {
        View mLayout;
        View mContainerContent;
        TextView mTextTitle;

        public ColorItemLargeHeaderViewHolder(View view) {
            super(view);
            mLayout = view.findViewById(R.id.frame_list_card_item);
            mContainerContent = view.findViewById(R.id.container_list_content);
            mTextTitle = (TextView) view.findViewById(R.id.text_list_card_title);
        }

        @Override
        public void onItemExpand(boolean b) {
            mContainerContent.setVisibility(b ? View.VISIBLE : View.GONE);
        }

        @Override
        protected void onAnimationStateChange(int state, boolean willBeSelect) {
            super.onAnimationStateChange(state, willBeSelect);
            if (state == CardStackView.ANIMATION_STATE_START && willBeSelect) {
                onItemExpand(true);
            }
            if (state == CardStackView.ANIMATION_STATE_END && !willBeSelect) {
                onItemExpand(false);
            }
        }

        public void onBind(int position) {
            mLayout.getBackground().setColorFilter(ContextCompat.getColor(getContext(),TEST_DATAS[position%25]), PorterDuff.Mode.SRC_IN);
            mTextTitle.setText(String.valueOf(position));

            itemView.findViewById(R.id.text_view).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((CardStackView)itemView.getParent()).performItemClick(ColorItemLargeHeaderViewHolder.this);
                }
            });
        }

    }

}
