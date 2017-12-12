package com.example.wangqian.happinesshunter.tools;

import android.util.Log;

import static com.example.wangqian.happinesshunter.tools.DateTool.random;


/**
 * Created by salto on 2017-12-11.
 */

public class Model {
    private static final String TAG = "Model";
    private static final String WORD_LENGTH = "wordLength";
    private static final String POSITIVE_RATE = "positiveRate";
    private static final String NEGATIVE_RATE = "negativeRate";
    private static final String COGNITIVE_RATE = "cognitiveRate";
    private static final String PERFECT = "perfect";

    public static int result = 0;

    private static String LastState = WORD_LENGTH;

    // TODO: 2017-12-12 cause and insight
    public static StringBuffer getFeedback(int wordLength,double positiveRate, double negativeRate, double cognitiveRate){

        StringBuffer str = null;
        double grade;


        int lengthScore = 100;
        if (wordLength < 100) lengthScore = wordLength;
        double emotionScore = 3.5;
        if ((positiveRate + cognitiveRate)*17.5<3.5)
            emotionScore = (positiveRate + cognitiveRate)*17.5;

        grade = (lengthScore/100)*1.5 + emotionScore- negativeRate*1.5;

        if (grade > 4.0){
            result = 4;
        }else if (grade > 3.0){
            result = 3;
        }else if (grade > 2.0){
            result = 2;
        }else if (grade > 1.0){
            result = 1;
        }else {
            result = 0;
        }
        Log.d(TAG,"grade="+grade);

        String currentState = whoLow(wordLength,positiveRate,negativeRate,cognitiveRate);
       // if (currentState.equals(LastState)) return null;//取消注释，意味着，只有状态改变时，才更新tip
        LastState = currentState;



        //coginitvie的时候，是随机发生的，其他维度，是按照result等级区分tip；
        int whichTip = random.nextInt(5);
        switch (currentState){
            case WORD_LENGTH:
                Log.d(TAG,WORD_LENGTH);
                switch (result) {
                    case 0:
                        str = new StringBuffer("要不要多写一点呢");
                        break;
                    case 1:
                        str = new StringBuffer("可以再具体地和我说说么");
                        break;
                    case 2:
                        str = new StringBuffer("要不要多写一点呢");
                        break;
                    case 3:
                        str = new StringBuffer("说得越多，也许能想得更清楚哦");
                        break;
                    case 4:
                        str = new StringBuffer("有没有什么小细节没有发现？");
                        break;
                    default:
                        str = new StringBuffer("你一定遗漏了什么重点！");
                        break;
                }
                break;
            case POSITIVE_RATE:
                Log.d(TAG,POSITIVE_RATE);
                switch (result) {
                    case 0:
                        str = new StringBuffer("要不要多写点开心的事呀");
                        break;
                    case 1:
                        str = new StringBuffer("用你漂亮的眼睛发现生活中的美呀");
                        break;
                    case 2:
                        str = new StringBuffer("一定有你没有发现的小美好哦");
                        break;
                    case 3:
                        str = new StringBuffer("用你漂亮的眼睛发现生活中的美呀");
                        break;
                    case 4:
                        str = new StringBuffer("还有很多温暖的事情等着你发现");
                        break;
                    default:
                        str = new StringBuffer("有没有人帮助了你？");
                        break;
                }
                break;
            case NEGATIVE_RATE:
                Log.d(TAG,NEGATIVE_RATE);
                switch (result) {
                    case 0:
                        str = new StringBuffer("还有很多温暖的小细节等着你发现呢");
                        break;
                    case 1:
                        str = new StringBuffer("如果你是别人，你会怎么想？");
                        break;
                    case 2:
                        str = new StringBuffer("反过来想想会不会不一样？");
                        break;
                    case 3:
                        str = new StringBuffer("一定有你没有发现的小美好哦");
                        break;
                    case 4:
                        str = new StringBuffer("深深吸一口气，事情是不是没有想的那么复杂？");
                        break;
                    default:
                        str = new StringBuffer("有没有人帮助了你？");
                        break;
                }
                break;
            case COGNITIVE_RATE:
                Log.d(TAG,COGNITIVE_RATE);
                switch (result) {
                    case 0:
                        str = new StringBuffer("你的感受是什么样的呢");
                        break;
                    case 1:
                        str = new StringBuffer("事情发生的真正原因是什么？");
                        break;
                    case 2:
                        str = new StringBuffer("大胆说出你的想法");
                        break;
                    case 3:
                        str = new StringBuffer("事情发生的真正原因是什么");
                        break;
                    case 4:
                        str = new StringBuffer("你的感受是什么样的呢");
                        break;
                    default:
                        str = new StringBuffer("事情发生的真正原因是什么");
                        break;
                }
                break;
            case PERFECT:
                str = new StringBuffer("完美！");
                break;
        }

        return str;
    }

    private static String whoLow(int wordLength,double positiveRate, double negativeRate, double cognitiveRate){
        if (wordLength < 10) return WORD_LENGTH;
        if (negativeRate > 0.4) return NEGATIVE_RATE;
        if (positiveRate < 0.03) return POSITIVE_RATE;
        if (cognitiveRate  < 0.03) return COGNITIVE_RATE;

        if (wordLength < 35) return WORD_LENGTH;
        if (negativeRate > 0.2) return NEGATIVE_RATE;
        if (positiveRate < 0.08) return POSITIVE_RATE;
        if (cognitiveRate  < 0.08) return COGNITIVE_RATE;

        if (wordLength < 75) return WORD_LENGTH;
        if (negativeRate > 0.1) return NEGATIVE_RATE;
        if (positiveRate < 0.14) return POSITIVE_RATE;
        if (cognitiveRate  < 0.14) return COGNITIVE_RATE;

        return PERFECT;

    }



}
