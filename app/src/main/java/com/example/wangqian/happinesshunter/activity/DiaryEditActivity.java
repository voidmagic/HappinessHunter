package com.example.wangqian.happinesshunter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.wangqian.happinesshunter.R;
import com.example.wangqian.happinesshunter.dao.DiaryDao;
import com.example.wangqian.happinesshunter.entity.Diary;
import com.example.wangqian.happinesshunter.tools.DateTool;


public class DiaryEditActivity extends Activity {
	
	private EditText titleText;
	private EditText contentText;
	private ImageView happy1,happy2,happy3,happy4;
	private Integer happy;
	private DiaryDao diaryDao;
	private int number=0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit);
		initViews();

		// 实现添加日记
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			int id=extras.getInt("id");
			if(id!=0){
				number=1;
				diaryDao=new DiaryDao(this);
				Diary diary=diaryDao.getDiaryById(id);
				titleText.setText(diary.getTitle());
				contentText.setText(diary.getContent());
			}

		}
		//步骤1：获取DiaryActivity传过来的数据，将选定日记的标题和内容显示在相应的文本框中 20分
		//此处插入若干行代码

	}
	private void initViews() {
		titleText=(EditText) findViewById(R.id.title);
		contentText=(EditText) findViewById(R.id.content);
		happy1 = (ImageView) findViewById(R.id.happy1);
		happy2 = (ImageView) findViewById(R.id.happy2);
		happy3 = (ImageView) findViewById(R.id.happy3);
		happy4 = (ImageView) findViewById(R.id.happy4);
	}



	//单击提交日记按钮时调用的方法
	public void addOrupdate(View view){
	    //步骤2：完成日记更新操作 20分
		Diary diary = new Diary(titleText.getText().toString(),
				contentText.getText().toString(),
				DateTool.getCurrentTime(),happy);
	    diaryDao=new DiaryDao(DiaryEditActivity.this);
	    if(number==1){
	    	diary.setId(this.getIntent().getExtras().getInt("id"));
			diaryDao.update(diary);
			finish();
	    }else{
		 diaryDao.save(diary);
				finish();
	    }
	}

	public void happySelect(View view) {
		switch (view.getId()){
			case R.id.ha1:
				Log.e("xxxxxxxxxxxx","click 1");
				happy = 3;
				happy1.setImageDrawable(getResources().getDrawable(R.mipmap.a23));
				happy2.setImageDrawable(getResources().getDrawable(R.mipmap.a11_default));
				happy3.setImageDrawable(getResources().getDrawable(R.mipmap.a12_default));
				happy4.setImageDrawable(getResources().getDrawable(R.mipmap.a13_default));
				break;
			case R.id.ha2:

				Log.e("xxxxxxxxxxxx","click 2");
				happy = 2;
				happy1.setImageDrawable(getResources().getDrawable(R.mipmap.a23_default));
				happy2.setImageDrawable(getResources().getDrawable(R.mipmap.a11));
				happy3.setImageDrawable(getResources().getDrawable(R.mipmap.a12_default));
				happy4.setImageDrawable(getResources().getDrawable(R.mipmap.a13_default));
				break;
			case R.id.ha3:

				Log.e("xxxxxxxxxxxx","click 3");
				happy = 1;
				happy1.setImageDrawable(getResources().getDrawable(R.mipmap.a23_default));
				happy2.setImageDrawable(getResources().getDrawable(R.mipmap.a11_default));
				happy3.setImageDrawable(getResources().getDrawable(R.mipmap.a12));
				happy4.setImageDrawable(getResources().getDrawable(R.mipmap.a13_default));
				break;
			case R.id.ha4:
				happy = 0;

				Log.e("xxxxxxxxxxxx","click 4");
				happy1.setImageDrawable(getResources().getDrawable(R.mipmap.a23_default));
				happy2.setImageDrawable(getResources().getDrawable(R.mipmap.a11_default));
				happy3.setImageDrawable(getResources().getDrawable(R.mipmap.a12_default));
				happy4.setImageDrawable(getResources().getDrawable(R.mipmap.a13));
				break;
			default:
				break;
		}

	}
}
