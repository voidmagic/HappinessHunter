package com.example.wangqian.happinesshunter.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangqian.happinesshunter.R;
import com.example.wangqian.happinesshunter.WordCloudActivity;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class IndexActivity extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);
        textView = (TextView) findViewById(R.id.tip) ;
        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim);
        textView.startAnimation(animation);

    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject add = new MenuObject("新的日常");
        add.setResource(R.drawable.icn_1);

        MenuObject cloud = new MenuObject("回忆云");
        cloud.setResource(R.drawable.icn_4);

        MenuObject list = new MenuObject("过往日常");
        list.setResource(R.drawable.icn_5);

        MenuObject setting = new MenuObject("设置");
        setting.setResource(R.drawable.setting);

        menuObjects.add(close);
        menuObjects.add(add);
        menuObjects.add(cloud);
        menuObjects.add(list);
        menuObjects.add(setting);
        return menuObjects;
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
//        mToolbar.setNavigationIcon(R.drawable.btn_back);
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        mToolBarTextView.setText(getResources().getString(R.string.app_name));
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            finish();
        }
    }
    Intent intent;
    @Override
    public void onMenuItemClick(View clickedView, int position) {
        //Toast.makeText(this, "Clicked on position: " + position, Toast.LENGTH_SHORT).show();
        switch (position){
            case 1:
                iniPopupWindowNoBack(IndexActivity.this,"          告诉大白，今天心情如何?");
                break;
            case 2:
                intent = new Intent();
                intent.setClass(IndexActivity.this, WordCloudActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent();
                intent.setClass(IndexActivity.this, ListActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent();
                intent.setClass(IndexActivity.this, AlarmActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    public  void iniPopupWindowNoBack(final Context context, String content) {
        final PopupWindow pwMyPopWindow;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_iknow, null);
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
                intent = new Intent();
                intent.setClass(IndexActivity.this, EditPositiveActivity.class);
                startActivity(intent);
            }
        });
        negitive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pwMyPopWindow.dismiss();
                intent = new Intent();
                intent.setClass(IndexActivity.this, EditNegativeActivity.class);
                startActivity(intent);
            }
        });

    }
}
