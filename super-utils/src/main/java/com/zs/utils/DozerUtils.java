package com.zs.utils;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: zs
 * @Date: 2019/8/28 10:11
 * @Description:
 */
public class DozerUtils {

    private static DozerBeanMapper dozer = null;

    static {
        dozer = new DozerBeanMapper();
    }

    /**
     * 将src对象中的属性值复制到desc对象的同名属性中
     * @param src 源
     * @param desc 目标
     */
    public static void dozer(Object src, Object desc){
        if(desc != null){
            dozer.map(src, desc);
        }
    }

    /**
     * 将src对象中的属性值复制到新建的desc对象的同名属性中
     * @param src 源
     * @param descClass 目标class
     * @return
     */
    public static <T> T dozer(Object src, Class<T> descClass){
        if(src == null ){
            return null;
        }
        return (T)dozer.map(src, descClass);
    }

    /**
     * 将srcList中的元素复制到元素类型为descClass的List集合中
     * @param srcList  源list
     * @param descClass 目标list
     * @return
     */
    public static <T> List<T> dozer(List srcList, Class<T> descClass){
        List<T> descList = new ArrayList<T>();
        if (srcList != null){
            for (Object obj : srcList){
                T t = DozerUtils.dozer(obj, descClass);
                descList.add(t);
            }
            return descList;
        }
        return null;
    }

}
