package com.example.wangqian.happinesshunter;

import android.app.Application;

import com.huaban.analysis.jieba.JiebaSegmenter;

public class HappinessHunterApplication extends Application {

    public static JiebaSegmenter jiebaSegmenter;

    @Override
    public void onCreate() {
        super.onCreate();

        initSegmenter();
    }


    private void initSegmenter() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                jiebaSegmenter = new JiebaSegmenter();
            }
        }).start();
    }
}
