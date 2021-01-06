/*
 * Copyright 2012-2022
 */
package com.sermon.easyexcel.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 一句话说明描述功能
 *
 * @author longquan.huang
 * @version 1.0
 * @date 2021/1/5 9:50 下午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataVo {
    @ExcelProperty("double")
    private String doubleData;
    @ExcelProperty("字符串标题")
    private String string;
    @ExcelProperty(value = "日期标题", converter = CustomString2DateConverter.class)
    private Date date;
}

