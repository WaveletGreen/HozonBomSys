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
    /**
     * list 中有一个为主键key
     * @param list
     * @param function
     * @return
     */
    public static <K,V> Map<K, V> listToMap(List<V> list, Function<V, K> function){
        Map<K, V> map = new HashMap<K, V>();
        for(V value:list){
            K k = function.apply(value);
            map.put(k, value);
        }
        return map;
    }

    public static<V>  boolean isEmpty(List<V> list){
        if(null !=list){
            return list.isEmpty();
        }
        return true;
    }

    public static<V>  boolean isNotEmpty(List<V> list){
        if(null !=list){
            return !list.isEmpty();
        }
        return false;
    }

    public static<V>  boolean isEmpty(Set<V> list){
        if(null !=list){
            return list.isEmpty();
        }
        return true;
    }

    public static<V>  boolean isNotEmpty(Set<V> list){
        if(null !=list){
            return !list.isEmpty();
        }
        return false;
    }

    /**
     * 对list 记录进行分类
     */
    public static <K,V> Map<K, List<V>> listToMapWithList(List<V> list,Function<V, K> function){
        Map<K, List<V>> map = new HashMap<K, List<V>>();
        for(V value:list){
            K k = function.apply(value);

            List<V> ele = map.get(k);
            if(ele != null){
                ele.add(value);
            }else{
                ele = new ArrayList<V>();
                ele.add(value);
                map.put(k, ele);
            }

        }
        return map;
    }

    /**
     * 对list 的每个元素转为HashMap
     */
    public static <V> List<HashMap> listConverMap(List<V> list,Class<V> clazz){
        List<HashMap> listMap = new ArrayList<HashMap>();
        Field[] fileds = clazz.getDeclaredFields();
        try {
            for(V value:list){
                HashMap map = new HashMap();
                for(Field f:fileds){
                    f.setAccessible(true);
                    map.put(f.getName(), f.get(value));
                }
                listMap.add(map);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }

        return listMap;
    }

    /**
     * list 元素转为另一个元素的list
     */
    public static <F,R> List<R> listConvert(List<F> list,Function<F, R> function){
        List<R> listN = new ArrayList<R>();
        for(F value:list){
            R r = function.apply(value);
            if(r != null)
                listN.add(r);
        }
        return listN;
    }

    /**
     * list 元素过滤另一个元素的list
     */
    public static <F> List<F> filter(List<F> list,Function<F, Boolean> function){
        List<F> listN = new ArrayList<F>();
        for(F value:list){
            Boolean r = function.apply(value);
            if(r)
                listN.add(value);
        }
        return listN;
    }


    public static <F> List<F> minus(List<F> origin,List<F> target){
        Map<Integer, F> mapTarget = toMapByHashCode(target);
        List<F> result = new ArrayList<F>();
        for(F value:origin){
            if(mapTarget.get(value.hashCode()) == null){
                result.add(value);
            }
        }
        return result ;
    }


    /**
     * list 元素转为另一个元素的list并且去重,适合list数据量较小
     */
    public static <F,R> List<R> listConvertAndUnique(List<F> list,Function<F, R> function){
        List<R> listN = new ArrayList<R>();
        for(F value:list){
            R r = function.apply(value);
            if(!listN.contains(r))
                listN.add(r);
        }
        return listN;
    }

    /**
     * list 元素转为以seg分割的字符串
     */
    public static String split(List list,String seg){
        StringBuilder sb = new StringBuilder();
        for(Object value:list){
            sb.append(value.toString()+seg);
        }
        String t = sb.toString();
        if(t.endsWith(seg)){
            int end = t.length()-seg.length();
            t = t.substring(0, end);
        }
        return t;
    }


    public static <V> Map<Integer, V> toMapByHashCode(List<V> list){
        Map<Integer, V> map = new HashMap<Integer, V>();
        for(V value:list){
            map.put(Integer.valueOf(value.hashCode()), value);
        }
        return map;
    }

    // list to  ListMultimap
    public static <K,V> ListMultimap<K, V> listToListMultiMap(List<V> list, Function<V, K> function){
        ListMultimap listMultimap = ArrayListMultimap.<K,V>create();
        for(V value : list){
            listMultimap.put(function.apply(value), value);
        }
        return listMultimap;
    }


    public static <V> String listToString(List<V> list,String join){
        if(list == null && list.size() ==0) return  "";
        if(join == null) join = "";
        StringBuilder st = new StringBuilder();
        for(V v:list){
            st.append(v.toString());
            st.append(join);
        }

        return st.toString();
    }

}
