package com.example.wangqian.happinesshunter.service;


import com.example.wangqian.happinesshunter.entity.Record;

import java.util.List;

public interface RecordService {
    List<Record> getRecords();

    List<Record> getRecords(String keyWord);

    Record getRecord(Long id);

    Record saveRecord(Record record);

}
