/*
 *
 *  * Copyright (c) 2019 Author: Fancyears Milos Malvis @1243093366@qq.com
 *  * ALL COPYRIGHT REVERSED.
 *
 */

package cn.net.connor.hozon.common.util;

import java.util.List;
import java.util.Set;

/**
 * Created by haozt on 2018/5/22
 */
public class ListUtils {

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
