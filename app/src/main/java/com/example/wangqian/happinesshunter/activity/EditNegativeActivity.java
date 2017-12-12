package com.example.wangqian.happinesshunter.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wangqian.happinesshunter.R;
import com.example.wangqian.happinesshunter.dao.DiaryDao;
import com.example.wangqian.happinesshunter.entity.Diary;
import com.example.wangqian.happinesshunter.service.DemoNlpServiceImpl;
import com.example.wangqian.happinesshunter.service.NlpService;
import com.example.wangqian.happinesshunter.tools.DateTool;
import com.example.wangqian.happinesshunter.tools.Model;


public class EditNegativeActivity extends AppCompatActivity implements TextWatcher{

	private EditText titleText;
	private EditText contentText;
	private TextView tipText;
	private ImageView happy1,happy2,happy3,happy4;
	private Integer happy;
	private DiaryDao diaryDao;
	// private TextView full,emotionStength;

	private NlpService nlpService;
	private int number=0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_edit_negative);
		initViews();
		initToolbar();

		nlpService = new DemoNlpServiceImpl();
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
	private void initViews() {
		titleText=(EditText) findViewById(R.id.title);
		contentText=(EditText) findViewById(R.id.content);
		tipText = (TextView) findViewById(R.id.tips);

		happy1 = (ImageView) findViewById(R.id.happy1);
		happy2 = (ImageView) findViewById(R.id.happy2);
		happy3 = (ImageView) findViewById(R.id.happy3);
		happy4 = (ImageView) findViewById(R.id.happy4);

//        full = (TextView) findViewById(R.id.full);
//        emotionStength = (TextView) findViewById(R.id.emotion);

		contentText.addTextChangedListener(this);
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

		mToolbar.setTitle("New Diary");
	}


	//单击提交日记按钮时调用的方法
	public void addOrupdate(View view){
		//步骤2：完成日记更新操作
		Diary diary = new Diary(titleText.getText().toString(),
				contentText.getText().toString(),
				DateTool.getCurrentTime(),happy);
		diaryDao=new DiaryDao(EditNegativeActivity.this);
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
				happy1.setImageResource(R.mipmap.a23);
				happy2.setImageResource(R.mipmap.a11_default);
				happy3.setImageResource(R.mipmap.a12_default);
				happy4.setImageResource(R.mipmap.a13_default);
				break;
			case R.id.ha2:

				Log.e("xxxxxxxxxxxx","click 2");
				happy = 2;
				happy1.setImageResource(R.mipmap.a23_default);
				happy2.setImageResource(R.mipmap.a11);
				happy3.setImageResource(R.mipmap.a12_default);
				happy4.setImageResource(R.mipmap.a13_default);
				break;
			case R.id.ha3:

				Log.e("xxxxxxxxxxxx","click 3");
				happy = 1;
				happy1.setImageResource(R.mipmap.a23_default);
				happy2.setImageResource(R.mipmap.a11_default);
				happy3.setImageResource(R.mipmap.a12);
				happy4.setImageResource(R.mipmap.a13_default);
				break;
			case R.id.ha4:
				happy = 0;

				Log.e("xxxxxxxxxxxx","click 4");
				happy1.setImageResource(R.mipmap.a23_default);
				happy2.setImageResource(R.mipmap.a11_default);
				happy3.setImageResource(R.mipmap.a12_default);
				happy4.setImageResource(R.mipmap.a13);
				break;
			default:
				break;
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {



	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		//tipText.setText(" 哼哧哼哧~~~");
	}

	int posSize = 0;
	int cogSize = 0;
	int negSize = 0;
	int totalSize = 0;

	@Override
	public void afterTextChanged( final Editable s) {
		//Log.e("xxxxxxxxxxx",s.toString());
		if (s == null || s.equals("")) return;

		new Thread(new Runnable() {
			@Override
			public void run() {
				//每三个字更新一次
				// if (s.length()% 3 != 0) return;


				totalSize = nlpService.totalSize(s.toString());
				int currentPosSize = nlpService.positiveNum(s.toString());
				int currentCogSize = nlpService.cognitiveNum(s.toString());
				negSize = nlpService.negativeNum(s.toString());

				if (currentPosSize>posSize){
					Log.e("xxxxxxx","+1+1+1");
				}
				posSize = currentPosSize;
				if (currentCogSize >cogSize){
					Log.e("xxxxxxx","+1+1+1");
				}
				cogSize = currentCogSize;


				//Double negativeRate = nlpService.negativePercentage(s.toString());
				// Double positiveRate = nlpService.positivePercentage(s.toString());
				//Double cognitiveRate = nlpService.cognitivePercentage(s.toString());
				Double positiveRate = posSize * 1.0 / totalSize;
				Double cognitiveRate = cogSize * 1.0 / totalSize;
				Double negativeRate = negSize * 1.0 / totalSize;

				Log.e("xxxxxxxxxxxxx",positiveRate.toString()+"  "+negativeRate+"  "+cognitiveRate);
				Log.e("xxxxxwangliuping",posSize+"  "+negSize+"  "+cogSize);

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


}
