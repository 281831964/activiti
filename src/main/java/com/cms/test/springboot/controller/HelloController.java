package com.cms.test.springboot.controller;

import com.cms.test.springboot.service.SettingService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @Value("${com.didispace.blog.name}")
    private String name;
    @Value("${com.didispace.blog.title}")
    private String title;

    @Autowired
    private SettingService settingService;


    @ApiOperation(value = "hello", notes = "hello springboot")
    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        return "Hello, SpringBoot!" + "name=" + name + ",title=" + title;
    }

    @RequestMapping("/setting")
    @ResponseBody
    public Object getSettingById() {
        return settingService.findSettingById(1);
    }
}
