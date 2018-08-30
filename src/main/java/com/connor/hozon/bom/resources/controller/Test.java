package com.connor.hozon.bom.resources.controller;

/**
 * @Author: haozt
 * @Date: 2018/8/30
 * @Description:
 */
public class Test {
    public static void main(String[] a){
        String value = "10.70.70.30.40";
        String[] ss = value.split("\\.");
        int i = 3;
        StringBuffer stringBuffer = new StringBuffer();
        for(int j = 0;j<i;j++){
            if(j==i-1){
                stringBuffer.append(ss[j]);
            }else
            stringBuffer.append(ss[j]+".");
        }
        System.out.println(stringBuffer.toString());
    }
}
