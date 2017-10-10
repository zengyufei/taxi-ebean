package com.ys.common.utils;

import com.google.common.collect.Lists;
import com.ys.common.base.MarkId;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作枚举的工具类
 * @author yaya
 *
 */
public class EnumUtils {

    /**
     * 根据一个索引得到某个枚举类的枚举值
     * @param <I>
     * @param type
     * 	枚举类的Class
     * @param index
     * 	索引
     * @return
     * @throws AssertionError
     * 	若提供的索引在枚举中没有对映的映射,抛出此断言错误
     */
    public static <I extends MarkId> I getEnum(Class<I> type, int index) {
        I[] types = type.getEnumConstants();
        for (I t : types) {
            if (t.getIndex() == index) {
                return t;
            }
        }
        throw new AssertionError("不能够映射:" + index + "到枚举" + type.getSimpleName());
    }

    /**
     * 根据一个枚举描述得到某个枚举类的枚举值
     * @param <I>
     * @param type
     *  枚举类的Class
     * @param description
     *  枚举描述
     * @return
     * @throws AssertionError
     * 	若提供的枚举描述在枚举中没有对映的映射,抛出此断言错误
     */
    public static <I extends MarkId> I getEnum(Class<I> type, String description) {
        I[] types = type.getEnumConstants();
        for (I t : types) {
            if (t.getMark().equals(description)) {
                return t;
            }
        }
        throw new AssertionError("不能够映射:" + description + "到枚举" + type.getSimpleName());
    }

    /**
     * 得到某枚举类中所有的值的描述
     * @param <I>
     * @param type
     * 枚举类的Class
     * @return
     */
    public static <I extends MarkId> List<String> getDescriptions(Class<I> type) {
        I[] types = type.getEnumConstants();
        List<String> result = Lists.newArrayList();
        for (I t : types) {
            result.add(t.getMark());
        }
        return result;
    }

}
