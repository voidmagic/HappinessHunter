package com.example.wangqian.happinesshunter.tools;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class DateTool {
    public static String getCurrentTime(){
    	SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	return formatter.format(new Date());
    }

    //Date格式
    public static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final DateFormat DAY_FORMAT = new SimpleDateFormat("MM-dd");
    public static final String DateFormatStr = "yyyy-MM-dd\'T\'HH:mm:ss";

    public static Random random = new Random();

}
