package com.example.wangqian.happinesshunter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class WordRecordListActivity extends AppCompatActivity {

    public static String wordSelectedAsParams = "wordSelectedAsParams";

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_record_list);

        textView = (TextView) findViewById(R.id.selected_word_text_view);
        String word = this.getIntent().getStringExtra(wordSelectedAsParams);
        textView.setText(word);
    }

}
