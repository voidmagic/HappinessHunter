package com.example.wangqian.happinesshunter.tools;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTool {
    public static String getCurrentTime(){
    	SimpleDateFormat formatter=new SimpleDateFormat("yyyy年MM月dd日");
    	return formatter.format(new Date());
    }
}
