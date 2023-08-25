package com.github.shiayanga.xinchaung.dameng.controller;


import com.github.shiayanga.xinchaung.dameng.entity.City;
import com.github.shiayanga.xinchaung.dameng.entity.CityExample;
import com.github.shiayanga.xinchaung.dameng.mapper.CityMapper;
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

    @Resource
    CityMapper cityMapper;

    @GetMapping("/queryDbVersion")
    public City queryDbVersion(){
        CityExample cityExample = new CityExample();
        CityExample.Criteria criteria = cityExample.createCriteria();
        criteria.andCityIdEqualTo("BJ");
        List<City> cities = cityMapper.selectByExample(cityExample);
        return cities.get(0);
    }
}
