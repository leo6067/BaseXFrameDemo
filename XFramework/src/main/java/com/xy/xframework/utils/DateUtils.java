
package com.xy.xframework.utils;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static android.text.TextUtils.isEmpty;


/**
 * 日期工具类
 */
public class DateUtils {


    /**
     * 格式化传入的时间戳，将时间戳转化为指定格式字符串
     * @param timestamp
     * @param format 时间格式，如：yyyy-MM-dd HH:mm:ss SSS 或 yyyy年MM月dd日 HH:mm:ss     *
     * @param timestampType 时间戳格式 0毫秒 1秒
     * @return
     */
    public static String getTimeStampString(long timestamp,String format ,int timestampType)
    {
        if (timestamp==0){
            return  "";
        }
        if (format == null || format.length() <=0)
        {
            return  "";
        }
        if (timestampType == 1)
        {
            //如果时间戳格式是秒，需要江时间戳变为毫秒
            timestamp = timestamp * 1000L;
        }
        Date dateTime = new Date(timestamp);
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String timeString = sdf.format(dateTime);
        return  timeString;
    }



    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String time,String pattern) {
        String stamp=dateToStamp(time);
        if ( isEmpty(stamp)){
            return "";
        }else {
            return TimeStamp2Date2(stamp,pattern);
        }
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
            long ts = date.getTime();
            return String.valueOf(ts);
        } catch (Exception e) {

        }
        return "";
    }

    /*
     * 将时间转换为时间戳
     */
    public static long dateToStamp2(String time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(time);
            long ts = date.getTime();
            return ts;
        } catch (Exception e) {

        }
        return 0;
    }

    //获取现在时间 事例pattern:"yyyy-MM-dd hh:mm:ss"
    public static String getStringDate(String pattern) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    //获取明天时间 事例pattern:"yyyy-MM-dd"
    public static String getStringTomorrowDate(String pattern) {
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    //获取明天时间 事例pattern:"yyyy-MM-dd"
    public static String getStringDayDate(String pattern,int dayDe) {
        Date date=new Date();//取时间
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE,dayDe);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        return formatter.format(date);
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     *
     * @param timestampString 时间戳 如："1473048265";秒级
     * @param formats         要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     *
     * @param timestampString 时间戳 如："1473048265000";毫秒级
     * @param formats         要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date2(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString);
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    //获取系统时间的10位的时间戳
    public static String getCurrentTime(long currentTimeMillis){
        long time=currentTimeMillis/1000;
        String  str=String.valueOf(time);
        return str;
    }

    //  String pTime = "2012-03-12";
    public static String getWeek(String pTime) {
        String Week = "";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week = "天";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week = "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week = "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week = "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week = "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week = "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week = "六";
        }
        return "周" + Week;
    }

    //获取指定日期的年龄
    public static int getAge(String date) {
        Date birthDay = StrToDate(date,"yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            ToastUtils.showShort("当前日期有误!");
            return 0;
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth){
                    age--;
                }
            } else {
                age--;
            }
        }
        return age;
    }

    /**
     * string转成Date类型
     *
     * @param @param  dateString
     * @param @param  format
     * @param @return
     * @return Date
     * @throws
     * @Description:
     */
    public static Date StrToDate(String dateString, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date date = sdf.parse(dateString);
            return date;
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    //计算是否是婴儿
    public static boolean isBaby(String date) {
        int ageNum= getAge(date);
        if (ageNum>=0&&ageNum<2){
            return true;
        }else {
            return false;
        }
    }

    //计算是否是儿童
    public static boolean isChild(String date) {
        int ageNum= getAge(date);
        if (ageNum>=2&&ageNum<=12){
            return true;
        }else {
            return false;
        }
    }

    //计算是否是成人
    public static boolean isAdult(String date) {
        int ageNum= getAge(date);
        if (ageNum>12){
            return true;
        }else {
            return false;
        }
    }

    //比较两个时间的大小dateFirst：2018-10-12 1:dateFirst时间大 0：一样大 -1：dateSecond大
    public static int compareDate(String dateFirst,String dateSecond){
        String time1=dateFirst.replaceAll("-","");
        String time2=dateSecond.replaceAll("-","");
        if (new BigDecimal(time1).subtract(new BigDecimal(time2)).intValue()>0){
            return 1;
        }else if (new BigDecimal(time1).subtract(new BigDecimal(time2)).intValue()==0){
            return 0;
        }else {
            return -1;
        }
    }

    /*
     *计算time2减去time1的差值 差值只设置 几天 几个小时 或 几分钟
     * 根据差值返回多长之间前或多长时间后
     * */

    public static String getDistanceTime(String time1,String time2,int tag) {
        if (isEmpty(time1)||isEmpty(time2)){
            return "时间有误";
        }else {
            return getDistanceTime(dateToStamp2(time1),dateToStamp2(time2),tag);
        }
    }

    public static String getDistanceTime(long time1,long time2,int tag) {
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        long diff=0;
        String time="";
        if(time1<time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
        sec = (diff/1000-day*24*60*60-hour*60*60-min*60);

        if(tag>=1&&day!=0) {
            time+=day+"天";
        }
        if(tag>=2&&hour!=0)  {
            time+=hour+"小时";
        }
        if(tag>=3&&min!=0){
            time+=min+"分钟";
        }
        if(tag>=4&&sec!=0){
            time+=sec+"秒";
        }
        return time;
    }


    public static String getDistanceTime2(String time1,String time2) {
        if (isEmpty(time1)||isEmpty(time2)){
            return "时间有误";
        }else {
            return getDistanceTime2(dateToStamp2(time1),dateToStamp2(time2));
        }
    }

    public static String getDistanceTime2(long time1,long time2) {
        String hourStr,minStr;
        long hour = 0;
        long min = 0;
        long diff=0;
        String time="";
        if(time1<time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }

        hour = diff / (60 * 60 * 1000);
        min = diff / (60 * 1000)- hour * 60;

        hourStr=(hour<10)?("0"+hour):String.valueOf(hour);
        minStr=(min<10)?("0"+min):String.valueOf(min);

        return hourStr+":"+minStr;
    }


    //----------------------------根据时间差 算出当前的时间------------------------------------
    private static long currDistantTime;//与系统时间的时间差
    public static void setCurrDistantTime(long time) {
        if (time > 0) {
            currDistantTime = System.currentTimeMillis() - time;
        }
    }

    public static long getCurrDistantTime() {
        long time = System.currentTimeMillis() - currDistantTime;
        if (time <= 0) {
            time = System.currentTimeMillis();
        }
        return time;
    }
    //----------------------------根据时间差 算出当前的时间------------------------------------

    //将时间戳转成 分秒格式
    public static String longToString(long data) {
        String str="";

        long D = data/(24*60*60);
        long H = (data%(24*60*60))/(60*60);
        long M = (data%(24*60*60)%(60*60))/(60);
        long S = (data%(24*60*60)%(60*60)%(60));
        if (D>0){
            str +=D+"天";
        }

        if (H>0){
            str +=(H>9?H:"0"+H)+"时";
        }

        str +=(M>9?M:"0"+M)+"分"+(S>9?S:"0"+S)+"秒";

        return str;
    }
}
