/*
 * Copyright 2012-2022
 */
package com.sermon.easyexcel.easyexcel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.sermon.easyexcel.util.StringToDateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 一句话说明描述功能
 *
 * @author longquan.huang
 * @version 1.0
 * @date 2021/1/5 10:12 下午
 */
public class CustomString2DateConverter implements Converter<Date> {
    @Override
    public Class supportJavaTypeKey() {
        return Date.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    /**
     * 这里读会用到
     * @param cellData
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public Date convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        Object data = cellData.getStringValue();
        if (data != null) {
            String time = String.valueOf(data);
            Date date = StringToDateUtil.stringToDate(time);
            if (date != null) {
                Calendar calendar = Calendar.getInstance();
                // 时
                calendar.set(Calendar.HOUR_OF_DAY, date.getHours());
                // 分
                calendar.set(Calendar.MINUTE, date.getMinutes());
                // 秒
                calendar.set(Calendar.SECOND, date.getSeconds());
                // 毫秒
                calendar.set(Calendar.MILLISECOND, 0);
                date = calendar.getTime();
            }
            return date;
        }
        return null;
    }

    /**
     * 这里写会用到
     * @param value
     * @param contentProperty
     * @param globalConfiguration
     * @return
     * @throws Exception
     */
    @Override
    public CellData convertToExcelData(Date value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (value != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            String dateString = formatter.format(value);
            return new CellData(dateString);
        }
        return null;
    }
}
