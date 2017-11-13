package com.example.wangqian.happinesshunter.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.wangqian.happinesshunter.R;
import com.example.wangqian.happinesshunter.dao.DiaryDao;
import com.example.wangqian.happinesshunter.entity.Diary;


public class DiaryReviewActivity extends Activity {

	private EditText titleText;
	private EditText contentText;
	private ImageView happy1,happy2,happy3,happy4;
	private DiaryDao diaryDao;
	private int number=0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_review);
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
				show(diary.getHappy());
			}

		}

	}
	private void initViews() {
		titleText=(EditText) findViewById(R.id.title);
		contentText=(EditText) findViewById(R.id.content);
		happy1 = (ImageView) findViewById(R.id.happy1);
		happy2 = (ImageView) findViewById(R.id.happy2);
		happy3 = (ImageView) findViewById(R.id.happy3);
		happy4 = (ImageView) findViewById(R.id.happy4);
	}




	public void show(Integer i) {
		switch (i){
			case 3:
				happy1.setImageDrawable(getResources().getDrawable(R.mipmap.a23));
				happy2.setImageDrawable(getResources().getDrawable(R.mipmap.a11_default));
				happy3.setImageDrawable(getResources().getDrawable(R.mipmap.a12_default));
				happy4.setImageDrawable(getResources().getDrawable(R.mipmap.a13_default));
				break;
			case 2:
				happy1.setImageResource(R.mipmap.a23_default);
				happy2.setImageDrawable(getResources().getDrawable(R.mipmap.a11));
				happy3.setImageDrawable(getResources().getDrawable(R.mipmap.a12_default));
				happy4.setImageDrawable(getResources().getDrawable(R.mipmap.a13_default));
				break;
			case 1:
				happy1.setImageDrawable(getResources().getDrawable(R.mipmap.a23_default));
				happy2.setImageDrawable(getResources().getDrawable(R.mipmap.a11_default));
				happy3.setImageDrawable(getResources().getDrawable(R.mipmap.a12));
				happy4.setImageDrawable(getResources().getDrawable(R.mipmap.a13_default));
				break;
			case 0:
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
