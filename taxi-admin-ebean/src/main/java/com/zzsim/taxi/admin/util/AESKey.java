package com.zzsim.taxi.admin.util;

import com.google.common.collect.ImmutableList;

import java.util.List;

public class AESKey {

    public static String secretKey(){
        return "taxi!@#2017";
    }
    
    public static List<String> historyKeyList(){
        return ImmutableList.of("");
    }
}
