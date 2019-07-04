package com.sermon.idempotent.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class IBaseRedisServiceTest {
    @Autowired
    private IBaseRedisService baseRedisService;
    final static private String KEY = "hello";
    final static private String VALUE = "longquan";
    final static private String VALUE1 = "xl";

    @Test
    public void get() {
        String value = baseRedisService.get(KEY);
        log.info("获取{}的值为:{}", KEY, value);
    }

    @Test
    public void set() {
        String value = VALUE1;
        Boolean bSu = baseRedisService.setex(KEY, 100L, value);
        if (bSu) {
            log.info("添加{}的值{}成功", KEY, value);
        } else {
            log.error("添加{}的值{}失败", KEY, value);
        }
    }

    @Test
    public void setnx() {
        String value = VALUE1;
        Boolean bSu = baseRedisService.setnx(KEY, value);
        if (bSu) {
            log.info("添加{}的值{}成功", KEY, value);
        } else {
            log.error("添加{}的值{}失败", KEY, value);
        }
    }
    @Test
    public void delete() {
        Long bDelNum = baseRedisService.delete(KEY);
        if (bDelNum <= 0) {
            log.error("删除{}失败", KEY);
        } else {
            log.info("删除{}成功", KEY);
        }
    }

    @Test
    public void exists() {
        Boolean bExists = baseRedisService.exists(KEY);
        if (bExists) {
            log.info("{}存在", KEY);
        } else {
            log.error("{}不存在", KEY);
        }
    }
}