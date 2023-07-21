package com.github.shiayanga.xinchaung.dameng.controller;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.annotation.Resource;
import java.util.List;

/**
 * @author LiYang
 */
@RestController
@RequestMapping("/dm")
public class DmController {
    @Resource
    private JdbcTemplate jdbcTemplate;
    @GetMapping("/queryDbVersion")
    public Integer queryDbVersion(){
        return jdbcTemplate.queryForObject("select count(1) from DMHR.DEPARTMENT", Integer.class);
    }
}
