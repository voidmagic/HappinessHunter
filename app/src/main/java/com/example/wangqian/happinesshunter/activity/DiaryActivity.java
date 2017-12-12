package com.example.wangqian.happinesshunter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangqian.happinesshunter.R;
import com.example.wangqian.happinesshunter.adapter.DiaryAdapter;
import com.example.wangqian.happinesshunter.dao.DiaryDao;
import com.example.wangqian.happinesshunter.entity.Diary;

import java.util.List;



public class DiaryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
	private ListView listView;
	private DiaryDao diaryDao;
	private Diary diary;
	private List<Diary> diaryList;

	public static final String TAG = "DiaryActivity";
	private static final int MENU_EDIT = 1;// 编辑菜单id
	private static final int MENU_DELETE = 2;// 删除菜单的id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diary);
		listView = (ListView) this.findViewById(R.id.listview);
		initToolbar();
		refreshList();
		// 当列表为空时，显示
		TextView emptyText = (TextView) this.findViewById(R.id.emptyShow);
		listView.setEmptyView(emptyText);
		// TODO 步骤1：给列表组件注册上下文菜单
		this.registerForContextMenu(listView);
		listView.setOnItemClickListener(this);

	}

	public void onCreateContextMenu(ContextMenu menu, View v,
									ContextMenuInfo menuInfo) {

          super.onCreateContextMenu(menu, v, menuInfo); 
          menu.setHeaderTitle("请选择");  

          menu.add(0,1,0,"编辑");  
          menu.add(0,2,0,"删除");  

    }  
	@Override
	protected void onResume() {
		refreshList();
		super.onResume();
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
		mToolbar.setNavigationIcon(R.drawable.btn_back);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		mToolbar.setTitleMarginStart(0);

	}

	/**
	 * 刷新列表的显示
	 */
	public void refreshList() {
		diaryDao = new DiaryDao(this);
		diaryList = diaryDao.getAllDiariesData();
		DiaryAdapter diaryAdapter = new DiaryAdapter(this,diaryList);
		listView.setAdapter(diaryAdapter);

	}

	// 编辑和删除
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		Log.v(TAG, "context item seleted ID=" + menuInfo.id);
		switch (item.getItemId()) {
		case MENU_EDIT:
			Intent intent = new Intent();
			intent.setClass(DiaryActivity.this, EditPositiveActivity.class);
			intent.putExtra("id",(int)menuInfo.id);
			startActivity(intent);
            //跳转到DiaryEditActivity，并将选中日记对应的id传过去
			break;
		case MENU_DELETE:
			diaryDao.delete((int)menuInfo.id);
            refreshList();
			break;
		default:
			break;
		}
		return super.onContextItemSelected(item);
	}



	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
		Toast.makeText(this,"click  "+diaryList.get(i).getId(), Toast.LENGTH_SHORT).show();
		Intent intent = new Intent();
		intent.setClass(DiaryActivity.this, DiaryReviewActivity.class);
		intent.putExtra("id",diaryList.get(i).getId());
		startActivity(intent);
	}
}
