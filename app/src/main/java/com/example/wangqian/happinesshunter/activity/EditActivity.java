package com.example.wangqian.happinesshunter.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.wangqian.happinesshunter.R;
import com.example.wangqian.happinesshunter.dao.DiaryDao;
import com.example.wangqian.happinesshunter.entity.Diary;
import com.example.wangqian.happinesshunter.service.DemoNlpServiceImpl;
import com.example.wangqian.happinesshunter.service.NlpService;
import com.example.wangqian.happinesshunter.tools.DateTool;
import com.example.wangqian.happinesshunter.tools.HeartLayout;
import com.example.wangqian.happinesshunter.tools.Model;

import org.w3c.dom.Text;

import app.dinus.com.loadingdrawable.LoadingView;
import app.dinus.com.loadingdrawable.render.LoadingRenderer;
import app.dinus.com.loadingdrawable.render.goods.WaterBottleLoadingRenderer;

/**
 * Created by salto on 2017-12-18.
 */

public class EditActivity extends Activity implements TextWatcher {
    private HeartLayout mHeartLayout;

    private TextView tipText,titleText;
    private EditText editText;
    int emotionStrength;
    private LoadingView bottle_view;
    private WaterBottleLoadingRenderer bottle;

    private NlpService nlpService;
    int posSize = 0;
    int cogSize = 0;
    int negSize = 0;
    int totalSize = 0;

    private int number=0;
    private DiaryDao diaryDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        nlpService = new DemoNlpServiceImpl();


    }

    private void initView() {
        //取消标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.edit);

        editText = (EditText) findViewById(R.id.edit_text);
        editText.addTextChangedListener(this);

        tipText = (TextView) findViewById(R.id.tip);
        tipText = (TextView) findViewById(R.id.edit_title);
        bottle_view = (LoadingView) findViewById(R.id.water_bottle_view);
        bottle = (WaterBottleLoadingRenderer)bottle_view.getLoadingRender();
        bottle.updateWater(140,"#FF29E3F2");


        // 看日记
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int id=extras.getInt("id");
            if(id!=0){
                number=1;
                diaryDao=new DiaryDao(this);
                Diary diary=diaryDao.getDiaryById(id);
                titleText.setText(diary.getTitle());
                editText.setText(diary.getContent());

            }

        }



    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }


    @Override
    public void afterTextChanged( final Editable s) {
        //Log.e("xxxxxxxxxxx",s.toString());
        if (s == null || s.toString().equals("")) return;

        new Thread(new Runnable() {
            @Override
            public void run() {
                //每三个字更新一次
                // if (s.length()% 3 != 0) return;


                if (s.toString().length() > 11){
                    bottle.updateWater(15,"#2ed365");
                }else if (s.toString().length() > 9){
                    bottle.updateWater(50,"#d81e06");
                }else if (s.toString().length() > 7){
                    bottle.updateWater(90,"#96ed1b");
                }else if (s.toString().length() > 5){
                    bottle.updateWater(100,"#d7c637");
                }else if (s.toString().length() > 3){
                    bottle.updateWater(120,"#287d78");
                }else;




                    totalSize = nlpService.totalSize(s.toString());
                int currentPosSize = nlpService.positiveNum(s.toString());
                int currentCogSize = nlpService.cognitiveNum(s.toString());
                negSize = nlpService.negativeNum(s.toString());

                emotionStrength = nlpService.emotionStrength(s.toString());
                Log.e("xxx emtotionStrength",emotionStrength+"");

                if (currentPosSize>posSize){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mHeartLayout != null){

                                mHeartLayout.addFavor();
                            }
                        }
                    });
                }
                posSize = currentPosSize;
                if (currentCogSize >cogSize){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mHeartLayout != null){
                                mHeartLayout.addFavor();
                            }
                        }
                    });
                }
                cogSize = currentCogSize;


                Double positiveRate = posSize * 1.0 / totalSize;
                Double cognitiveRate = cogSize * 1.0 / totalSize;
                Double negativeRate = negSize * 1.0 / totalSize;

                final StringBuffer feedback = Model.getFeedback(s.length(),positiveRate,negativeRate,cognitiveRate);
                if (null == feedback) return;

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tipText.setText(feedback);
                    }
                });

            }
        }).start();
    }

    public  void iniPopupWindowNoBack(final Context context, String content) {
        final PopupWindow pwMyPopWindow;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_commit, null);
        TextView positive = (TextView) layout.findViewById(R.id.positive);
        TextView negitive = (TextView) layout.findViewById(R.id.negitive);
        TextView contentTextView=(TextView)layout.findViewById(R.id.content);
        contentTextView.setText(content);
        pwMyPopWindow = new PopupWindow(layout);
        pwMyPopWindow.setFocusable(true);// 加上这个,popupwindow中的ListView才可以接收点击事件


// 控制popupwindow的宽度和高度自适应
        layout.measure(View.MeasureSpec.UNSPECIFIED,
                View.MeasureSpec.UNSPECIFIED);
        pwMyPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pwMyPopWindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);

// 控制popupwindow点击屏幕其他地方消失
        //   pwMyPopWindow.setBackgroundDrawable(context.getResources().getDrawable(
        //         R.color.background));// 设置背景图片，不能在布局中设置，要通过代码来设置
        pwMyPopWindow.setOutsideTouchable(false);


        pwMyPopWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);


        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwMyPopWindow.dismiss();
                Diary diary = new Diary(titleText.getText().toString(),
                        editText.getText().toString(),
                        DateTool.getCurrentTime(),emotionStrength);
                diaryDao=new DiaryDao(EditActivity.this);
                if(number==1){
                    diary.setId(EditActivity.this.getIntent().getExtras().getInt("id"));
                    diaryDao.update(diary);
                    finish();
                }else{
                    diaryDao.save(diary);
                    finish();
                }

            }
        });
        negitive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwMyPopWindow.dismiss();

            }
        });

    }
}
