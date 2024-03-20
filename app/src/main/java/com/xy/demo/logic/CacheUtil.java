package com.xy.demo.logic;

import java.util.Date;
import java.util.Random;

public class CacheUtil {

    private static final int RANDOM_LOWER_BOUND = 0; // 随机数下限
    private static final int RANDOM_UPPER_BOUND = 20; // 随机数上限
    private static final int MAX_VALUE = 50000; // 最大限制值  5G大小 最大虚假垃圾
    private Date lastAccessTime; // 上次访问时间
    private int currentValue; // 当前累计值
    private Random random; // 随机数生成器

    public CacheUtil() {
        this.random = new Random();
    }


    public long generateValue(long lastTime) {
        Date now = new Date(); // 当前时间
        long timeDiffInMinutes = (now.getTime() - lastTime) / (60 * 1000); // 时间差（分钟）

        // 如果时间差大于0分钟，则增加相应的随机次数
        if (timeDiffInMinutes > 0) {
            for (int i = 0; i < timeDiffInMinutes; i++) {
                int randomValue = random.nextInt(RANDOM_UPPER_BOUND - RANDOM_LOWER_BOUND + 1) + RANDOM_LOWER_BOUND; // 生成随机数
                currentValue += randomValue; // 累加到当前值

                // 检查是否超过最大值，如果超过则不再增加
                if (currentValue >= MAX_VALUE) {
                    int[] numbers = {51121, 58889, 55555,65654,35898,33586}; // 随机虚拟大小
                    int randomIndex = random.nextInt(numbers.length); // 生成一个随机索引
                    currentValue = numbers[randomIndex];
                    break;
                }
            }
        }

//        // 更新上次访问时间
//        lastAccessTime = now;

        // 返回当前值
        return currentValue;
    }

    // 初始化或重置上次访问时间和当前值
    public void reset(Date initialAccessTime, int initialValue) {
        lastAccessTime = initialAccessTime;
        currentValue = initialValue;
    }

    // 获取当前累计值
    public int getCurrentValue() {
        return currentValue;
    }

    // 示例使用
    public static void main(String[] args) {
        CacheUtil generator = new CacheUtil();
        Date initialAccessTime = new Date(); // 假设这是用户的首次访问时间
        generator.reset(initialAccessTime, 0); // 初始化时间和值

        // 假设用户首次访问页面
//        System.out.println("User accessed the page for the first time. Current value: " + generator.generateValue());

        // 等待一段时间（例如5分钟）
        try {
            Thread.sleep(5 * 60 * 1000); // 等待5分钟
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 用户再次访问页面，应该增加5次随机数值
//        System.out.println("User accessed the page again after 5 minutes. Current value: " + generator.generateValue());
    }

}
