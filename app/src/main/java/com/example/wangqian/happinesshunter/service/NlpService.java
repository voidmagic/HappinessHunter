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

    /**
     * 包含的positive词
     * @param sentence 输入句子
     * @return 包含的positive词
     */
    List<String> containsPositiveWords(String sentence);

    /**
     * 包含的positive词的比例
     * @param sentence 输入句子
     * @return 包含的positive词的比例
     */
    double positivePercentage(String sentence);

    /**
     * 包含的negative词
     * @param sentence 输入句子
     * @return 包含的negative词
     */
    List<String> containsNegativeWords(String sentence);

    /**
     * 包含的negative词的比例
     * @param sentence 输入句子
     * @return 包含的negative词的比例
     */
    double negativePercentage(String sentence);

    /**
     * 包含的cognitive词
     * @param sentence 输入句子
     * @return 包含的cognitive词
     */
    List<String> containsCognitiveWords(String sentence);

    /**
     * 包含的cognitive词的比例
     * @param sentence 输入句子
     * @return 包含的cognitive词的比例
     */
    double cognitivePercentage(String sentence);




}
