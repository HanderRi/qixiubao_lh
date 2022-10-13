package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.demo.config.Result;
import com.example.demo.entity.People;
import com.example.demo.mapper.Peoplemapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/")
public class PeopleController {
    @Resource
    Peoplemapper peoplemapper;

    @PostMapping("/login")
    public Result<?> login(@RequestBody People people) {
        People res = peoplemapper.selectOne(Wrappers.<People>lambdaQuery().eq(People::getUsername, people.getUsername()).eq(People::getPassword, people.getPassword()));
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }
        return Result.success(res);
    }
}
