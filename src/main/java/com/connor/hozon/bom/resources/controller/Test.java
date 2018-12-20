package com.connor.hozon.bom.resources.controller;

/**
 * @Author: haozt
 * @Date: 2018/12/20
 * @Description:
 */
public class Test {
    public static void main(String[] a){
        String s  ="100.20";
        System.out.println(Integer.valueOf(s.split("\\.")[1]));
    }
}
