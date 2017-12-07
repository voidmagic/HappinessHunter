package com.example.wangqian.happinesshunter;

import android.app.Application;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;

public class HappinessHunterApplication extends Application {

    public static Set<String> positiveWords = new HashSet<>();
    public static Set<String> negativeWords = new HashSet<>();
    public static Set<String> cognitiveWords = new HashSet<>();

    @Override
    public void onCreate() {
        super.onCreate();

        initSentimentDict();
    }

    private void initSentimentDict() {
        try {
            initPositive();
            initNegative();
            initCognitive();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initPositive() throws IOException {

        InputStream is = this.getApplicationContext().getAssets().open("positive.txt");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        // Convert the buffer into a string.
        String text = new String(buffer, "utf-8");
        String[] wordList = text.split("\n");

        for (String aWordList : wordList) {
            if (StringUtils.isNotEmpty(aWordList)) {
                positiveWords.add(aWordList);
            }
        }
        is.close();
    }

    private void initNegative() throws IOException {

        InputStream is = this.getApplicationContext().getAssets().open("negative.txt");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        // Convert the buffer into a string.
        String text = new String(buffer, "utf-8");
        String[] wordList = text.split("\n");
        for (String aWordList : wordList) {
            if (StringUtils.isNotEmpty(aWordList)) {
                negativeWords.add(aWordList);
            }
        }
        is.close();
    }

    private void initCognitive() throws IOException {
        InputStream is = this.getApplicationContext().getAssets().open("cognitive.txt");
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        // Convert the buffer into a string.
        String text = new String(buffer, "utf-8");
        String[] wordList = text.split("\n");
        for (String aWordList : wordList) {
            if (StringUtils.isNotEmpty(aWordList)) {
                cognitiveWords.add(aWordList);
            }
        }
        is.close();
    }
}
