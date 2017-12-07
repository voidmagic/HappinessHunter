package com.example.wangqian.happinesshunter.service;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.wangqian.happinesshunter.HappinessHunterApplication;
import com.github.kevinsawicki.http.HttpRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DemoNlpServiceImpl implements NlpService {

    @Override
    public List<String> segmentSentence(String sentence) {
        Log.e("segmentSentence",sentence);
        HttpRequest request =  HttpRequest.get("http://115.159.180.45:9000/segment/" + sentence, true);
        String jsonResponse = request.body();
        return JSON.parseArray(jsonResponse, String.class);
    }

    @Override
    public int emotionStrength(String sentence) {

        Log.e("emotionStrength",sentence);
        HttpRequest request =  HttpRequest.get("http://115.159.180.45:9000/sentiment/" + sentence, true);
        String jsonResponse = request.body();
        float s = JSON.parseObject(jsonResponse, Float.class) * 10;
        int s1 = 0;
        if (s <=5 ) {
            s1 = 0;
        } else if (s <= 9 ) {
            s1 = (int) (s - 5);
        } else {
            s1 = 4;
        }
        return s1;
    }

    @Override
    public int integrity(String sentence) {
        Log.e("integrity",sentence);
        List<String> stringList = segmentSentence(sentence);
        if (stringList.size() < 10) {
            return 0;
        } else if (stringList.size() < 20) {
            return 1;
        } else if (stringList.size() < 30) {
            return 2;
        } else {
            return 3;
        }
    }

    @Override
    public List<String> containsPositiveWords(String sentence) {
        List<String> inputWords = segmentSentence(sentence);
        Set<String> inputWordsSet = new HashSet<>(inputWords);

        Set<String> positiveDict = HappinessHunterApplication.positiveWords;

        Set<String> result = new HashSet<>();
        result.addAll(inputWordsSet);
        result.retainAll(positiveDict);
        return new ArrayList<>(result);

    }

    @Override
    public List<String> containsNegativeWords(String sentence) {
        List<String> inputWords = segmentSentence(sentence);
        Set<String> inputWordsSet = new HashSet<>(inputWords);

        Set<String> negativeDict = HappinessHunterApplication.negativeWords;

        Set<String> result = new HashSet<>();
        result.addAll(inputWordsSet);
        result.retainAll(negativeDict);
        return new ArrayList<>(result);
    }

    /**
     * 包含的negative词
     *
     * @param sentence 输入句子
     * @return 包含的negative词
     */
    @Override
    public List<String> containsCognitiveWords(String sentence) {
        List<String> inputWords = segmentSentence(sentence);
        Set<String> inputWordsSet = new HashSet<>(inputWords);

        Set<String> cognitiveDict = HappinessHunterApplication.cognitiveWords;

        Set<String> result = new HashSet<>();
        result.addAll(inputWordsSet);
        result.retainAll(cognitiveDict);
        return new ArrayList<>(result);
    }

}
