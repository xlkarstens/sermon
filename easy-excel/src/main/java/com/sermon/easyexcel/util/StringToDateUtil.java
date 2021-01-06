/*
 * Copyright 2012-2022
 */
package com.sermon.easyexcel.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串转时间，支持多种格式
 *
 * @author longquan.huang
 * @version 1.0
 * @date 2021/1/5 6:28 下午
 */
public class StringToDateUtil {
    public static final Map<Integer, Character> DATE_CHAR_MAP = new HashMap<>();
    public static final Map<Integer, Character> TIME_CHAR_MAP = new HashMap<>();
    public static final Pattern PATTERN = Pattern.compile("^(\\d+)\\D*(\\d*)\\D*(\\d*)\\D*(\\d*)\\D*(\\d*)\\D*(\\d*)");
    public static final Pattern TIME_PATTERN = Pattern.compile("^(\\d+)\\D*(\\d*)\\D*(\\d*)");

    static {
        DATE_CHAR_MAP.put(1, 'y');
        DATE_CHAR_MAP.put(2, 'M');
        DATE_CHAR_MAP.put(3, 'd');
        DATE_CHAR_MAP.put(4, 'H');
        DATE_CHAR_MAP.put(5, 'm');
        DATE_CHAR_MAP.put(6, 's');

        TIME_CHAR_MAP.put(1, 'H');
        TIME_CHAR_MAP.put(2, 'm');
        TIME_CHAR_MAP.put(3, 's');
    }

    /**
     * 任意日期字符串转换为Date，不包括无分割的纯数字（13位时间戳除外） ，日期时间为数字，年月日时分秒，但没有毫秒
     *
     * @param dateString 日期字符串
     * @return Date
     */
    public static Date stringToDate(String dateString) {
        dateString = dateString.trim().replaceAll("[a-zA-Z]"," ");
        int num = getNum(dateString);
        if (num == 0) {
            return null;
        }
        if (num == 13) {
            //支持13位时间戳
            if(Pattern.matches("^[-+]?\\d{13}$",dateString)) {
                return new Date(Long.parseLong(dateString));
            }
        }
        Matcher m = null;
        Map<Integer, Character> charMap = null;
        if (num < 7) {
            m = TIME_PATTERN.matcher(dateString);
            charMap = TIME_CHAR_MAP;
        } else {
            m = PATTERN.matcher(dateString);
            charMap = DATE_CHAR_MAP;
        }
        StringBuilder sb = new StringBuilder(dateString);
        if (m.find(0)) {
            // 从被匹配的字符串中，充index = 0的下表开始查找能够匹配pattern的子字符串。
            // m.matches()的意思是尝试将整个区域与模式匹配，不一样。
            int count = m.groupCount();
            int end = m.end();
            for (int i = 1; i <= count; i++) {
                for (Map.Entry<Integer, Character> entry : charMap.entrySet()) {
                    if (entry.getKey() == i) {
                        sb.replace(m.start(i), m.end(i), replaceEachChar(m.group(i), entry.getValue()));
                        break;
                    }
                }
            }
        } else {
            System.out.println("错误的日期格式");
            return null;
        }
        String format = sb.toString();
        SimpleDateFormat sf = new SimpleDateFormat(format);
        try {
            return sf.parse(dateString);
        } catch (ParseException e) {
            System.out.println("日期字符串转Date出错");
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将指定字符串的所有字符替换成指定字符，跳过空白字符
     *
     * @param s 被替换字符串
     * @param c 字符
     * @return 新字符串
     */
    public static String replaceEachChar(String s, Character c) {
        StringBuilder sb = new StringBuilder("");
        for (Character c1 : s.toCharArray()) {
            if (c1 != ' ') {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 提取纯数字个数
     * @param text
     * @return
     */
    public static int getNum(String text) {
        int num = 0;
        if (null != text && !"".equals(text)) {
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) >= 48 && text.charAt(i) <= 57) {
                    num++;
                }
            }
        }
        return num;
    }

    public static void main(String[] args) {
        String test = "2020-12-23 1-12:23";
//        String test = "10-12:23";
//        String test = "10-12";
        Date date = StringToDateUtil.stringToDate(test);
        System.out.println(date);
    }
}
