package com.example.wangqian.happinesshunter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wangqian.happinesshunter.entity.Record;
import com.example.wangqian.happinesshunter.service.DemoRecordService;
import com.example.wangqian.happinesshunter.service.RecordService;
import com.flyco.dialog.entity.DialogMenuItem;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.huaban.analysis.jieba.JiebaSegmenter;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WordCloudActivity extends AppCompatActivity {

    private WebView wordCloudWebView;
    private CircularProgressView progressView;
    private TextView progressTextView;

    // use dependency injection if possible
    private RecordService recordService;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_cloud);

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
                recordService = new DemoRecordService();
                List<Record> recordList = recordService.getRecords();
                Map<String, Integer> wordValueMap = new HashMap<>();
                JiebaSegmenter segmenter = HappinessHunterApplication.jiebaSegmenter;

                while (segmenter == null) {
                    segmenter = HappinessHunterApplication.jiebaSegmenter;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                for (Record record: recordList) {
                    int strength = record.getStrength();
                    String stripedContent = record.getContent().replaceAll("\\p{P}" , " ");
                    List<String> wordList = segmenter.sentenceProcess(stripedContent);
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
                Log.i("tagsss", rawDataBuilder.toString());
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
                String[] itemList = buildDialogMenuItems(text);
                NormalListDialog dialog = new NormalListDialog(WordCloudActivity.this, itemList)
                        .title("\"" + text + "\"相关故事")
                        .dividerHeight(0.5f);


                dialog.setOnOperItemClickL(new OnOperItemClickL() {
                    @Override
                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(WordCloudActivity.this, id + "  " + position, Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });
    }

    public String[] buildDialogMenuItems(String word) {
        List<Record> recordList = recordService.getRecords(word);
        List<String> strings = new ArrayList<>();
        for (Record record: recordList) {
            strings.add(record.getContent());
        }
        return strings.toArray(new String[0]);
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

}
