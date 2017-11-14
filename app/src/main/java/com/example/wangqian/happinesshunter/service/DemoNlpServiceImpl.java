package com.example.wangqian.happinesshunter.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.kevinsawicki.http.HttpRequest;

import java.util.List;


public class DemoNlpServiceImpl implements NlpService {

    @Override
    public List<String> segmentSentence(String sentence) {
        HttpRequest request =  HttpRequest.get("http://115.159.180.45:9000/segment/" + sentence, true);
        String jsonResponse = request.body();
        return JSON.parseArray(jsonResponse, String.class);
    }

    @Override
    public int emotionStrength(String sentence) {
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
}
