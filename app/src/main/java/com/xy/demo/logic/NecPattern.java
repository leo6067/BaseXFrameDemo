package com.xy.demo.logic;

import android.util.Log;

/**
 * Created by huangr on 2019/8/8.
 * ClassName  : NecPattern
 * Description  : 构造NEC协议的pattern数组编码
 *
 * https://blog.csdn.net/u010127332/article/details/100151996?spm=1001.2014.3001.5501
 */
public class NecPattern {
    private static final String TAG = "NecPattern";

    //电平信号总时长
    private static final int TOTAL_TIME = 108000;

    //引导码
    private static final int START_H = 9000;
    private static final int START_L = 4500;

    //结束码
    private static final int END_L = 560;
    private static final int END_H = 2000;

    //重复码
    private static final int LOOP_H = 9000;
    private static final int LOOP_L = 2250;

    //高电平
    private static final int HIGH = 560;
    //低电平0：1125
    private static final int LOW_0 = 565;
    //低电平1：2250
    private static final int LOW_1 = 1690;

    private static int[] pattern;
    private static PatternList patternList = new PatternList();


    /**
     * 正常发码：引导码（9ms+4.5ms）+用户编码（高八位）+用户编码（低八位）+键数据码+键数据反码+结束码
     */
    public static int[] buildPattern(int userCodeH, int userCodeL, int keyCode) {
        //用户编码高八位00
        String userH = constructBinary(userCodeH);
        //用户编码低八位DF
        String userL = constructBinary(userCodeL);
        //数字码
        String key = constructBinaryCode(keyCode);
        //数字反码
        String keyReverse = constructBinaryCode(~keyCode);
        Log.d(TAG, " 键值 = [" + keyCode + "]， 逆向编码 = [" + userH +userL+key+keyReverse+ "]");
        patternList.clear();
        //引导码
        patternList.add(START_H);
        patternList.add(START_L);
        //用户编码
        changeAdd(userH);
        changeAdd(userL);
        //键数据码
        changeAdd(key);
        //键数据反码
        changeAdd(keyReverse);
        //结束码
        patternList.add(END_L);
        patternList.add(END_H);

        int size = patternList.size();
        pattern = new int[size];
        Log.d(TAG, " 键值 = [" + keyCode + "]， 脉冲信号编码 = " + patternList.toString());
        for (int i = 0; i < size; i++) {
            pattern[i] = patternList.get(i);
        }
        return pattern;
    }

    /**
     * 连续发码：引导码（9ms+4.5ms）+用户编码（高八位）+用户编码（低八位）+键数据码+键数据反码+延时码+重复码
     */
    public static int[] buildPattern(int userCodeH, int userCodeL, int keyCode, boolean longPress) {
        //用户编码高八位00
        String userH = constructBinary(userCodeH);
        //用户编码低八位DF
        String userL = constructBinary(userCodeL);
        //数字码
        String key = constructBinaryCode(keyCode);
        //数字反码
        String keyReverse = constructBinaryCode(~keyCode);
        Log.d(TAG, " 键值 = [" + keyCode + "]， 逆向编码 = [" + userH +userL+key+keyReverse+ "]");
        patternList.clear();
        //引导码
        patternList.add(START_H);
        patternList.add(START_L);
        //用户编码
        changeAdd(userH);
        changeAdd(userL);
        //键数据码
        changeAdd(key);
        //键数据反码
        changeAdd(keyReverse);
        //延时码
        patternList.add(HIGH);
        int gapTime = TOTAL_TIME - HIGH - START_H - START_L - patternList.getTotalTime();//108000-9000-4500-32位command-560
        patternList.add(gapTime);
        //重复码
        patternList.add(LOOP_H);
        patternList.add(LOOP_L);

        if(longPress){
            //如果长按则添加重复码，重复码需要3次以上设备才能响应
            for (int i = 0; i < 6; i++) {
                //延时码
                patternList.add(HIGH);
                patternList.add(TOTAL_TIME-HIGH-LOOP_H-LOOP_L);//108000-560-9000-2250
                //重复码
                patternList.add(LOOP_H);
                patternList.add(LOOP_L);
            }
        }

        int size = patternList.size();
        pattern = new int[size];
        Log.d(TAG, " 键值 = [" + keyCode + "]， 长按脉冲信号编码 = " + patternList.toString());
        for (int i = 0; i < size; i++) {
            pattern[i] = patternList.get(i);
        }
        return pattern;
    }

    /**
     * 十六进制键值转化为二进制串，并逆转编码
     * @param keyCode
     * @return
     */
    private static String constructBinary(int keyCode) {
        String binaryStr = convertToBinary(keyCode);
        char[] chars = binaryStr.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 7; i >= 4; i--) {
            sb.append(chars[i]);
        }

        for (int i = 3; i >= 0; i--) {
            sb.append(chars[i]);
        }
        return sb.toString();
    }

    /**
     * 十六进制键值转化为二进制串，并逆转编码
     * @param keyCode
     * @return
     */
    private static String constructBinaryCode(int keyCode) {
        String binaryStr = convertToBinary(keyCode);
        Log.d(TAG, " 键值 = [" + keyCode + "]， 数据码 = [" + binaryStr + "]");
        char[] chars = binaryStr.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 7; i >= 4; i--) {
            sb.append(chars[i]);
        }

        for (int i = 3; i >= 0; i--) {
            sb.append(chars[i]);
        }
        return sb.toString();
    }

    /**
     * 数字转换为长度为8位的二进制字符串
     * @return
     */
    private static String convertToBinary(int num) {
        String binary = Integer.toBinaryString(num);
        StringBuffer sb8 = new StringBuffer();
        //每个元素长度为8位，不够前面补充0
        if (binary.length() < 8) {
            for (int i = 0; i < 8 - binary.length(); i++) {
                sb8.append("0");
            }
            String binaryStr8 = sb8.append(binary).toString();
            return binaryStr8;
        }else{
            String binaryStr8 = binary.substring(binary.length() - 8);
            return binaryStr8;
        }
    }

    /**
     * 二进制转成电平信号
     *
     * @param code
     */
    public static void changeAdd(String code) {
        int len = code.length();
        String part;
        for (int i = 0; i < len; i++) {
            patternList.add(HIGH);
            part = code.substring(i, i + 1);
            if (part.equals("0"))
                patternList.add(LOW_0);
            else
                patternList.add(LOW_1);
        }
    }
}

