package com.github.shiayanga.xinchaung.dameng.mapper;

import com.github.shiayanga.xinchaung.dameng.entity.City;
import com.github.shiayanga.xinchaung.dameng.entity.CityExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CITY
     *
     * @mbg.generated Fri Jul 21 19:06:17 CST 2023
     */
    long countByExample(CityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CITY
     *
     * @mbg.generated Fri Jul 21 19:06:17 CST 2023
     */
    int deleteByExample(CityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CITY
     *
     * @mbg.generated Fri Jul 21 19:06:17 CST 2023
     */
    int insert(City row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CITY
     *
     * @mbg.generated Fri Jul 21 19:06:17 CST 2023
     */
    int insertSelective(City row);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CITY
     *
     * @mbg.generated Fri Jul 21 19:06:17 CST 2023
     */
    List<City> selectByExample(CityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CITY
     *
     * @mbg.generated Fri Jul 21 19:06:17 CST 2023
     */
    int updateByExampleSelective(@Param("row") City row, @Param("example") CityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table CITY
     *
     * @mbg.generated Fri Jul 21 19:06:17 CST 2023
     */
    int updateByExample(@Param("row") City row, @Param("example") CityExample example);
}