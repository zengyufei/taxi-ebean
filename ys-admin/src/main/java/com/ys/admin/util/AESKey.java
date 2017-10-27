package com.ys.admin.util;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class AesKey {

    public static String secretKey(){
        return "ys!@#2017";
    }
    
    public static List<String> historyKeyList(){
        return ImmutableList.of("");
    }
}
