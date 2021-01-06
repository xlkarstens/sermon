/*
 * Copyright 2012-2022
 */
package com.sermon.easyexcel.easyexcel;

import com.alibaba.excel.EasyExcel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 一句话说明描述功能
 *
 * @author longquan.huang
 * @version 1.0
 * @date 2021/1/5 9:49 下午
 */
@Slf4j
public class Test {
    public static void main(String[] args) {
        // 写法1
        String fileName = "/Users/huanglongquan/tmp/simpleWrite" + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
//        EasyExcel.write(fileName, DataVo.class).sheet("模板").doWrite(data());

        EasyExcel.read(fileName, DataVo.class, new DateListener()).sheet().headRowNumber(1).doRead();
        log.error("------=================");



    }

    /**
     * 生成数据
     * @return
     */
    public static List<DataVo> data(){
        List<DataVo> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            DataVo dataVo = new DataVo();
            dataVo.setDate(new Date());
            dataVo.setDoubleData(String.valueOf(2*i));
            dataVo.setString("----" + i);
            list.add(dataVo);
        }
        return list;
    }
}
