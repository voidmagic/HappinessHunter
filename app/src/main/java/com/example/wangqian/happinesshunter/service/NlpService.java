package com.example.wangqian.happinesshunter.service;


import java.util.List;

public interface NlpService {

    /**
     * 分词
     * @param sentence 要分词的句子（整段输入）
     * @return 分词结果
     */
    List<String> segmentSentence(String sentence);

    /**
     * 句子的情感强度
     * @param sentence 句子（整段输入）
     * @return 情感强度，0-5, 0表示情感强度一般, 5表示非常happy.
     */
    int emotionStrength(String sentence);

    /**
     * 句子的完整度，根据这个来提醒用户再多写一点之类
     * @param sentence 句子（整段输入）
     * @return 0-5，0表示不完整，5表示很完整
     */
    int integrity(String sentence);
}
