package com.connor.hozon.bom.resources.util;

import com.google.common.base.Function;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created by haozt on 2018/5/22
 */
public class ListUtil {

    public static<V>  boolean isEmpty(List<V> list){
        if(null !=list){
            return list.isEmpty();
        }
        return true;
    }

    public static<V>  boolean isNotEmpty(List<V> list){
        return !isEmpty(list);
    }

    public static<V>  boolean isEmpty(Set<V> list){
        if(null !=list){
            return list.isEmpty();
        }
        return true;
    }

    public static<V>  boolean isNotEmpty(Set<V> list){
        return !isEmpty(list);
    }


}
