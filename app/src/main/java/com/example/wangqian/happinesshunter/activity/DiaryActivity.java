package com.example.wangqian.happinesshunter.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangqian.happinesshunter.R;
import com.example.wangqian.happinesshunter.WordCloudActivity;
import com.example.wangqian.happinesshunter.adapter.DiaryAdapter;
import com.example.wangqian.happinesshunter.dao.DiaryDao;
import com.example.wangqian.happinesshunter.entity.Diary;

import java.util.List;



public class DiaryActivity extends Activity implements AdapterView.OnItemClickListener{
	private ListView listView;
	private DiaryDao diaryDao;
	private Diary diary;
	private List<Diary> diaryList;
	private Cursor diaries;
	public static final String TAG = "DiaryActivity";
	private static final int MENU_EDIT = 1;// 编辑菜单id
	private static final int MENU_DELETE = 2;// 删除菜单的id

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diary);
		listView = (ListView) this.findViewById(R.id.listview);
		
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

	/**
	 * 刷新列表的显示
	 */
	public void refreshList() {
		diaryDao = new DiaryDao(this);
		//diaries = diaryDao.getAllDiaries();
		diaryList = diaryDao.getAllDiariesData();
		// TODO: 2017-11-13 改成 自定义adapter

		//startManagingCursor(diaries);
//		SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(this,
//				R.layout.item, diaries, new String[] { "title", "createtime" },
//				new int[] { R.id.title, R.id.createtime });
//
//		listView.setAdapter(simpleCursorAdapter);

		DiaryAdapter diaryAdapter = new DiaryAdapter(this,diaryList);
		listView.setAdapter(diaryAdapter);

	}
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0,0,0,"添加一篇新日记");
		menu.add(0,1,1,"回忆");
		
		return true;
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case 0:
				Intent intent = new Intent();
				intent.setClass(DiaryActivity.this, DiaryEditActivity.class);
				startActivity(intent);
				break;
			case 1:
			Intent intent2 = new Intent();
			intent2.setClass(DiaryActivity.this, WordCloudActivity.class);
			startActivity(intent2);
			break;
			default:
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	// 编辑和删除
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item.getMenuInfo();
		Log.v(TAG, "context item seleted ID=" + menuInfo.id);
		switch (item.getItemId()) {
		case MENU_EDIT:
			Intent intent = new Intent();
			intent.setClass(DiaryActivity.this, DiaryEditActivity.class);
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