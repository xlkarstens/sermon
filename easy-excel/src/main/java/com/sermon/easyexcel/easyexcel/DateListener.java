/*
 * Copyright 2012-2022
 */
package com.sermon.easyexcel.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelAnalysisStopException;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 一句话说明描述功能
 *
 * @author longquan.huang
 * @version 1.0
 * @date 2021/1/5 9:58 下午
 */
@Slf4j
public class DateListener extends AnalysisEventListener<DataVo> {
    /**
     * 解析出来的数据存储到list中
     */
    private final List<DataVo> list = new ArrayList<>();

    /**
     * 异常信息
     */
    private String errorMsg;

    /**
     * 头部信息
     */
    private Map<Integer, String> headMap = null;


    /**
     * 每100条处理数据，防止
     */
    private static final int BATCH_COUNT = 100;


    /**
     * 解析一条数据
     * @param dataVo 解析出来的数据
     * @param analysisContext 分析上下文
     */
    @Override
    public void invoke(DataVo dataVo, AnalysisContext analysisContext) {
        log.error("解析的数据：{}", JSON.toJSONString(dataVo));
        list.add(dataVo);
        if (list.size() == BATCH_COUNT) {
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.error("xiexi -------------");
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context){
        log.error("解析到：{}", JSON.toJSONString(headMap));
        this.headMap = headMap;
    }

    /**
     * 在转换异常 获取其他异常下会调用本接口。抛出异常则停止读取。如果这里不抛出异常则 继续读取下一行。
     *
     * @param exception 异常信息
     * @param context 上下问
     * @throws Exception 抛出异常
     */
    @Override
    public void onException(Exception exception, AnalysisContext context) {
        log.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        int col,row =0;
        String title = "";
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException)exception;
            col = excelDataConvertException.getColumnIndex();
            row = excelDataConvertException.getRowIndex();
            log.error("第{}行，第{}列解析异常，数据为:{}", row, col , excelDataConvertException.getCellData());
            title = this.headMap.get(col);
            log.info("出错标题列名称 -- "+ title);
        }
        this.errorMsg = "出错行：" + row + ", 出错列：" + title;
        log.error(errorMsg);
        throw new ExcelAnalysisStopException(errorMsg);
    }

    /**
     * 返回异常信息
     * @return 异常信息
     */
    public String getErrorMsg() {
        return this.errorMsg;
    }
}
