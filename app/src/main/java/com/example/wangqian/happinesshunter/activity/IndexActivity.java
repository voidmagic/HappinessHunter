package com.example.wangqian.happinesshunter.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

import com.example.wangqian.happinesshunter.R;


/**
 * Created by salto on 2017-11-13.
 */

public class IndexActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM); //Enable自定义的View
            actionBar.setCustomView(R.layout.actionbar_custom);//设置自定义的布局：actionbar_custom
        }
    }
}
