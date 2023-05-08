package com.github.shiayanga.collection;

import java.util.ArrayList;

public class ListUtil {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>() {{
            add("http://127.0.0.1");
        }};
        boolean match = list.stream().anyMatch(node -> node.contains("127.0.0.1"));
        System.out.println(match);
    }
}
