package com.example.wangqian.happinesshunter.service;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.wangqian.happinesshunter.HappinessHunterApplication;
import com.github.kevinsawicki.http.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class DemoNlpServiceImpl implements NlpService {

    public List<String> segmentSentenceCN(String sentence) {
        Log.e("segmentSentence",sentence);
        HttpRequest request =  HttpRequest.get("http://115.159.180.45:9000/segment/" + sentence, true);
        String jsonResponse = request.body();
        return JSON.parseArray(jsonResponse, String.class);
    }
    @Override
    public List<String> segmentSentence(String sentence) {
        Log.e("segmentSentence",sentence);
        HttpRequest request =  HttpRequest.get("http://115.159.180.45:9000/segment-en/" + sentence, true);
        String jsonResponse = request.body();
        return JSON.parseArray(jsonResponse, String.class);
    }


    @Override
    public int emotionStrength(String sentence) {

        Log.e("emotionStrength",sentence);
        //HttpRequest request =  HttpRequest.get("http://115.159.180.45:9000/sentiment/" + sentence, true);
        HttpRequest request =  HttpRequest.get("http://115.159.180.45:9000/sentiment-en/" + sentence, true);
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
    public int totalSize(String sentence) {
        List<String> inputWords = segmentSentence(sentence);
        return inputWords.size();
    }

    @Override
    public List<String> containsPositiveWords(String sentence) {
        List<String> inputWords = segmentSentence(sentence);



        Set<String> positiveDict = HappinessHunterApplication.positiveWords;

        inputWords.retainAll(positiveDict);

        return new ArrayList<>(new HashSet<>(inputWords));

    }

    @Override
    public double positivePercentage(String sentence) {
        List<String> inputWords = segmentSentence(sentence);
        int total = inputWords.size();
        Set<String> positiveDict = HappinessHunterApplication.positiveWords;
        inputWords.retainAll(positiveDict);
        return inputWords.size() * 1.0 / total;
    }


    @Override
    public int positiveNum(String sentence) {
        List<String> inputWords = segmentSentence(sentence);
        Log.e("xxxxxxxx positive",inputWords.size()+"" );
        int total = inputWords.size();
        Set<String> positiveDict = HappinessHunterApplication.positiveWords;
        inputWords.retainAll(positiveDict);
        return inputWords.size();
    }

    @Override
    public List<String> containsNegativeWords(String sentence) {
        List<String> inputWords = segmentSentence(sentence);

        Set<String> negativeDict = HappinessHunterApplication.negativeWords;

        inputWords.retainAll(negativeDict);
        return new ArrayList<>(new HashSet<>(inputWords));
    }


    @Override
    public double negativePercentage(String sentence) {
        List<String> inputWords = segmentSentence(sentence);
        int total = inputWords.size();

        Set<String> negativeDict = HappinessHunterApplication.negativeWords;

        inputWords.retainAll(negativeDict);
        return inputWords.size() * 1.0 / total;
    }

    @Override
    public int negativeNum(String sentence) {
        List<String> inputWords = segmentSentence(sentence);
        int total = inputWords.size();
        Set<String> negativeDict = HappinessHunterApplication.negativeWords;

        inputWords.retainAll(negativeDict);
        return inputWords.size();
    }

    @Override
    public List<String> containsCognitiveWords(String sentence) {
        List<String> inputWords = segmentSentence(sentence);
        List<String> cognitiveDict = HappinessHunterApplication.cognitiveWords;

        inputWords.retainAll(cognitiveDict);
        return new ArrayList<>(new HashSet<>(inputWords));
    }

    @Override
    public double cognitivePercentage(String sentence) {
        List<String> inputWords = segmentSentence(sentence);
        int total = inputWords.size();
        List<String> cognitiveDict = HappinessHunterApplication.cognitiveWords;
       inputWords.retainAll(cognitiveDict);
        return inputWords.size() * 1.0 / total;
    }

    @Override
    public int cognitiveNum(String sentence) {
        List<String> inputWords = segmentSentence(sentence);
        int total = inputWords.size();
        List<String> cognitiveDict = HappinessHunterApplication.cognitiveWords;
        inputWords.retainAll(cognitiveDict);
        return inputWords.size();
    }

}
