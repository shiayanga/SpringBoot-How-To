package com.github.shiayanga.collection;

import java.util.TreeMap;

/**
 *
 * @author LiYang
 * @version 1.0
 * 2022/10/27 15:24
 **/
public class Map {

    public static void main(String[] args) {
        TreeMap<String, Object> treeMap = new TreeMap<String, Object>(String.CASE_INSENSITIVE_ORDER);

        treeMap.put("content-type","application/json");


        System.out.println(treeMap.get("ContEnt-Type"));




    }
}




















































































































































