package com.example.wangqian.happinesshunter.service;

import com.example.wangqian.happinesshunter.entity.Record;

import java.util.ArrayList;
import java.util.List;


public class DemoRecordService implements RecordService {
    @Override
    public List<Record> getRecords() {

        List<Record> recordList = new ArrayList<>();

        Record record0 = new Record();
        record0.setId(0L);
        record0.setStrength(1);
        record0.setContent("贾跃亭尚无归期，停牌半年之久的乐视却再次深陷舆论危机。近日，乐视被爆出因涉嫌IPO造假，或被强制退市。");
        recordList.add(record0);

        Record record1 = new Record();
        record1.setId(1L);
        record1.setStrength(1);
        record1.setContent("而在资本市场的背后，除了已经在在风雨中摇摆一年多的乐视，还有18万乐视股民。这支曾经在2015年一路飙涨，一跃成为国内A股市场最瞩目的股票，如今负面消息接踵而至。");
        recordList.add(record1);

        Record record2 = new Record();
        record2.setId(2L);
        record2.setStrength(1);
        record2.setContent("被套的股民，如今怎么看待乐视和贾跃亭？有什么样的心路历程？对于不得不为贾跃亭“站岗”的现状，是否后悔过当初的投资决定？最近，创业家&i黑马与一位乐视被套股民聊了聊，TA炒股多年，买入了数百万元的乐视股票。在很多人看来，TA在乐视一事上有点执迷不悟，甚至滑稽可笑。TA认为，乐视模式没有问题，贾跃亭也不是骗子，而乐视危机是源于谣言所造成的挤兑。");
        recordList.add(record2);

        Record record3 = new Record();
        record3.setId(3L);
        record3.setStrength(1);
        record3.setContent("我认为，归根结底，乐视的模式没有问题。而且它之所以能起来，我觉得跟它做了很多创新性的事情有关。");
        recordList.add(record3);
        return recordList;
    }

    @Override
    public List<Record> getRecords(String keyWord) {
        return null;
    }

    @Override
    public Record getRecord(Long id) {
        return null;
    }

    @Override
    public Record saveRecord(Record record) {
        return null;
    }
}
