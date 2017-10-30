package com.zzqfsy.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zzqfsy on 2017/6/12.
 */
@RestController
public class TestController {

    @ApiOperation(value = "测试服务启动")
    @RequestMapping(value = "/test/bootstarp", method = RequestMethod.GET)
    public String testbootstarp() {
        return "success";
    }

    @ApiOperation(value = "测试服务启动")
    @RequestMapping(value = "/test/bootstarp",method = RequestMethod.HEAD)
    public String testbootstarp2() {
        return "success";
    }
}
