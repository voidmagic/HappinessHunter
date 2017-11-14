package com.example.wangqian.happinesshunter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangqian.happinesshunter.activity.DiaryReviewActivity;
import com.example.wangqian.happinesshunter.dao.DiaryDao;
import com.example.wangqian.happinesshunter.entity.Diary;
import com.example.wangqian.happinesshunter.service.DemoNlpServiceImpl;
import com.example.wangqian.happinesshunter.service.NlpService;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.github.rahatarmanahmed.cpv.CircularProgressView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WordCloudActivity extends AppCompatActivity {

    private WebView wordCloudWebView;
    private CircularProgressView progressView;
    private TextView progressTextView;
    private NlpService nlpService;

    // use dependency injection if possible
    private DiaryDao diaryDao;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_cloud);
        initToolbar();
        diaryDao = new DiaryDao(this);
        nlpService = new DemoNlpServiceImpl();

        initWebView();
        initProjressView();
    }

    private void initProjressView() {
        progressView = (CircularProgressView) findViewById(R.id.loading_word_cloud_progress_view);
        progressView.startAnimation();
        progressTextView = (TextView) findViewById(R.id.loading_word_cloud_text_view);
    }


    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView(){
        wordCloudWebView = (WebView) findViewById(R.id.wordCloudWebView);

        WebSettings settings = wordCloudWebView.getSettings();
        // 启用javascript
        settings.setJavaScriptEnabled(true);
        // 从assets目录下面的加载html
        wordCloudWebView.loadUrl("file:///android_asset/main.html");
        wordCloudWebView.addJavascriptInterface(WordCloudActivity.this,"android");
    }

    private void loadData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Diary> recordList = diaryDao.getAllDiariesData();
                Map<String, Integer> wordValueMap = new HashMap<>();



                for (Diary diary: recordList) {
                    int strength = diary.getHappy();
                    String stripedContent = diary.getContent().replaceAll("\\p{P}" , " ");
                    List<String> wordList = nlpService.segmentSentence(stripedContent);
                    for (String word: wordList) {
                        if (wordValueMap.containsKey(word)) {
                            int value = wordValueMap.get(word);
                            wordValueMap.put(word, value + strength);
                        } else {
                            wordValueMap.put(word, strength);
                        }
                    }
                }

                final StringBuilder rawDataBuilder = new StringBuilder();

                for (Map.Entry<String, Integer> entry: wordValueMap.entrySet()) {
                    rawDataBuilder.append(entry.getKey());
                    rawDataBuilder.append(":");
                    rawDataBuilder.append(entry.getValue()*10000);
                    rawDataBuilder.append(",");
                }
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        progressView.setVisibility(View.INVISIBLE);
                        progressTextView.setVisibility(View.INVISIBLE);
                        wordCloudWebView.loadUrl("javascript:drawWordCloud(" + "'" + rawDataBuilder.toString() + "'" + ")");
                    }
                });
            }
        }).start();
    }



    @JavascriptInterface
    public void wordClicked(final String text){
        runOnUiThread(new Runnable() {

            @Override
            public void run() {

                final List<Diary> diaries = diaryDao.getDiaryByWord(text);
                List<String> strings = new ArrayList<>();
                for (Diary diary: diaries) {
                    strings.add(diary.getTitle());
                }
                String[] listString = strings.toArray(new String[0]);

                NormalListDialog dialog = new NormalListDialog(WordCloudActivity.this, listString)
                        .title("\"" + text + "\"相关日常")
                        .dividerHeight(0.5f);


                dialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(WordCloudActivity.this, id + "  " + position, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();

                        intent.setClass(WordCloudActivity.this, DiaryReviewActivity.class);
                        intent.putExtra("id",(int) diaries.get(position).getId());
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        });
    }


    @JavascriptInterface
    public void pageLoadFinish() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadData();
            }
        });
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
        mToolbar.setTitle("Word Cloud");


    }

}
