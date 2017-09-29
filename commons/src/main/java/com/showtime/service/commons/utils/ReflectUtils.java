package com.showtime.service.commons.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 反射构建对象的 util
 */
public class ReflectUtils {

    // String 类型
    private static final String CLASS_TYPE_STRING = "class java.lang.String";// 如果type是类类型，则前面包含"class "，后面跟类名

    // Integer 类型
    private static final String CLASS_TYPE_INTEGER = "class java.lang.Integer";// 如果type是类类型，则前面包含"class "，后面跟类名

    // Boolean 类型
    private static final String CLASS_TYPE_BOOLEAN = "class java.lang.Boolean";// 如果type是类类型，则前面包含"class "，后面跟类名

    // Date 类型
    private static final String CLASS_TYPE_DATE = "class java.util.Date";// 如果type是类类型，则前面包含"class "，后面跟类名

    // Long 类型
    private static final String CLASS_TYPE_LONG = "class java.lang.Long";// 如果type是类类型，则前面包含"class "，后面跟类名

    // Double 类型
    private static final String CLASS_TYPE_DOUBLE = "class java.lang.Double";// 如果type是类类型，则前面包含"class "，后面跟类名

    /**
     * @param modelForView 从前端传来的对象，最终要放入db
     * @param modelForDB   从db来的对象，用于覆盖null值
     * @return
     */
    public static void flushModel(Object modelForView, Object modelForDB) {
        Field[] fieldSubs = modelForView.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        Field[] fieldSupers = modelForView.getClass().getSuperclass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
            for (Field fieldSub : fieldSubs) { // 遍历所有属性
                String name = fieldSub.getName(); // 获取属性的名字
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = fieldSub.getGenericType().toString(); // 获取属性的类型
                if (type.equals(CLASS_TYPE_STRING)) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method mForView = modelForView.getClass().getMethod("get" + name);
                    String valueForView = (String) mForView.invoke(modelForView); // 调用getter方法获取属性值
                    Method mForDB = modelForDB.getClass().getMethod("get" + name);
                    String valueForDB = (String) mForDB.invoke(modelForDB); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = modelForView.getClass().getMethod("set" + name, String.class);
                        mForView.invoke(modelForView, valueForDB);
                    }
                }
                if (type.equals(CLASS_TYPE_INTEGER)) {
                    Method mForView = modelForView.getClass().getMethod("get" + name);
                    Integer valueForView = (Integer) mForView.invoke(modelForView);
                    Method mForDB = modelForDB.getClass().getMethod("get" + name);
                    Integer valueForDB = (Integer) mForDB.invoke(modelForDB); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = modelForView.getClass().getMethod("set" + name, Integer.class);
                        mForView.invoke(modelForView, valueForDB);
                    }
                }
                if (type.equals(CLASS_TYPE_BOOLEAN)) {
                    Method mForView = modelForView.getClass().getMethod("get" + name);
                    Boolean valueForView = (Boolean) mForView.invoke(modelForView);
                    Method mForDB = modelForDB.getClass().getMethod("get" + name);
                    Boolean valueForDB = (Boolean) mForDB.invoke(modelForDB); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = modelForView.getClass().getMethod("set" + name, Boolean.class);
                        mForView.invoke(modelForView, valueForDB);
                    }
                }
                if (type.equals(CLASS_TYPE_DATE)) {
                    Method mForView = modelForView.getClass().getMethod("get" + name);
                    Date valueForView = (Date) mForView.invoke(modelForView);
                    Method mForDB = modelForDB.getClass().getMethod("get" + name);
                    Date valueForDB = (Date) mForDB.invoke(modelForDB); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = modelForView.getClass().getMethod("set" + name, Date.class);
                        mForView.invoke(modelForView, valueForDB);
                    }
                }
                if (type.equals(CLASS_TYPE_LONG)) {
                    Method mForView = modelForView.getClass().getMethod("get" + name);
                    Long valueForView = (Long) mForView.invoke(modelForView);
                    Method mForDB = modelForDB.getClass().getMethod("get" + name);
                    Long valueForDB = (Long) mForDB.invoke(modelForDB); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = modelForView.getClass().getMethod("set" + name, Long.class);
                        mForView.invoke(modelForView, valueForDB);
                    }
                }
                if (type.equals(CLASS_TYPE_DOUBLE)) {
                    Method mForView = modelForView.getClass().getMethod("get" + name);
                    Double valueForView = (Double) mForView.invoke(modelForView);
                    Method mForDB = modelForDB.getClass().getMethod("get" + name);
                    Double valueForDB = (Double) mForDB.invoke(modelForDB); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = modelForView.getClass().getMethod("set" + name, Double.class);
                        mForView.invoke(modelForView, valueForDB);
                    }
                }
                // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
            }

            for (Field fieldSuper : fieldSupers) { // 遍历所有属性
                String name = fieldSuper.getName(); // 获取属性的名字
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = fieldSuper.getGenericType().toString(); // 获取属性的类型
                if (type.equals(CLASS_TYPE_STRING)) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method mForView = modelForView.getClass().getMethod("get" + name);
                    String valueForView = (String) mForView.invoke(modelForView); // 调用getter方法获取属性值
                    Method mForDB = modelForDB.getClass().getMethod("get" + name);
                    String valueForDB = (String) mForDB.invoke(modelForDB); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = modelForView.getClass().getMethod("set" + name, String.class);
                        mForView.invoke(modelForView, valueForDB);
                    }
                }
                if (type.equals(CLASS_TYPE_INTEGER)) {
                    Method mForView = modelForView.getClass().getMethod("get" + name);
                    Integer valueForView = (Integer) mForView.invoke(modelForView);
                    Method mForDB = modelForDB.getClass().getMethod("get" + name);
                    Integer valueForDB = (Integer) mForDB.invoke(modelForDB); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = modelForView.getClass().getMethod("set" + name, Integer.class);
                        mForView.invoke(modelForView, valueForDB);
                    }
                }
                if (type.equals(CLASS_TYPE_BOOLEAN)) {
                    Method mForView = modelForView.getClass().getMethod("get" + name);
                    Boolean valueForView = (Boolean) mForView.invoke(modelForView);
                    Method mForDB = modelForDB.getClass().getMethod("get" + name);
                    Boolean valueForDB = (Boolean) mForDB.invoke(modelForDB); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = modelForView.getClass().getMethod("set" + name, Boolean.class);
                        mForView.invoke(modelForView, valueForDB);
                    }
                }
                if (type.equals(CLASS_TYPE_DATE)) {
                    Method mForView = modelForView.getClass().getMethod("get" + name);
                    Date valueForView = (Date) mForView.invoke(modelForView);
                    Method mForDB = modelForDB.getClass().getMethod("get" + name);
                    Date valueForDB = (Date) mForDB.invoke(modelForDB); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = modelForView.getClass().getMethod("set" + name, Date.class);
                        mForView.invoke(modelForView, valueForDB);
                    }
                }
                if (type.equals(CLASS_TYPE_LONG)) {
                    Method mForView = modelForView.getClass().getMethod("get" + name);
                    Long valueForView = (Long) mForView.invoke(modelForView);
                    Method mForDB = modelForDB.getClass().getMethod("get" + name);
                    Long valueForDB = (Long) mForDB.invoke(modelForDB); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = modelForView.getClass().getMethod("set" + name, Long.class);
                        mForView.invoke(modelForView, valueForDB);
                    }
                }
                if (type.equals(CLASS_TYPE_DOUBLE)) {
                    Method mForView = modelForView.getClass().getMethod("get" + name);
                    Double valueForView = (Double) mForView.invoke(modelForView);
                    Method mForDB = modelForDB.getClass().getMethod("get" + name);
                    Double valueForDB = (Double) mForDB.invoke(modelForDB); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = modelForView.getClass().getMethod("set" + name, Double.class);
                        mForView.invoke(modelForView, valueForDB);
                    }
                }
                // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param testModel 填充传入的对象
     */
    public static void fillModel(Object testModel) {
        Field[] fieldSubs = testModel.getClass().getSuperclass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        Field[] fieldSupers = testModel.getClass().getSuperclass().getSuperclass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
            for (Field fieldSub : fieldSubs) { // 遍历所有属性
                String name = fieldSub.getName(); // 获取属性的名字
                if (name.equals("id")) {
                    continue;
                }
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = fieldSub.getGenericType().toString(); // 获取属性的类型
                if (type.equals(CLASS_TYPE_STRING)) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    String valueForView = (String) mForView.invoke(testModel); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, String.class);
                        mForView.invoke(testModel, "test");
                    }
                }
                if (type.equals(CLASS_TYPE_INTEGER)) {
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Integer valueForView = (Integer) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Integer.class);
                        mForView.invoke(testModel, 1);
                    }
                }
                if (type.equals(CLASS_TYPE_BOOLEAN)) {
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Boolean valueForView = (Boolean) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Boolean.class);
                        mForView.invoke(testModel, false);
                    }
                }
                if (type.equals(CLASS_TYPE_DATE)) {
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Date valueForView = (Date) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Date.class);
                        mForView.invoke(testModel, new Date());
                    }
                }
                if (type.equals(CLASS_TYPE_LONG)) {
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Long valueForView = (Long) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Long.class);
                        mForView.invoke(testModel, 1L);
                    }
                }
                if (type.equals(CLASS_TYPE_DOUBLE)) {
                    Double num = 1.0;
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Double valueForView = (Double) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Double.class);
                        mForView.invoke(testModel, num);
                    }
                }
                // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
            }

            for (Field fieldSuper : fieldSupers) { // 遍历所有属性
                String name = fieldSuper.getName(); // 获取属性的名字
                if (name.equals("id")) {
                    continue;
                }
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = fieldSuper.getGenericType().toString(); // 获取属性的类型
                if (type.equals(CLASS_TYPE_STRING)) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    String valueForView = (String) mForView.invoke(testModel); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, String.class);
                        mForView.invoke(testModel, "test");
                    }
                }
                if (type.equals(CLASS_TYPE_INTEGER)) {
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Integer valueForView = (Integer) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Integer.class);
                        mForView.invoke(testModel, 1);
                    }
                }
                if (type.equals(CLASS_TYPE_BOOLEAN)) {
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Boolean valueForView = (Boolean) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Boolean.class);
                        mForView.invoke(testModel, false);
                    }
                }
                if (type.equals(CLASS_TYPE_DATE)) {
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Date valueForView = (Date) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Date.class);
                        mForView.invoke(testModel, new Date());
                    }
                }
                if (type.equals(CLASS_TYPE_LONG)) {
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Long valueForView = (Long) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Long.class);
                        mForView.invoke(testModel, 1L);
                    }
                }
                if (type.equals(CLASS_TYPE_DOUBLE)) {
                    Double num = 1.0;
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Double valueForView = (Double) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Double.class);
                        mForView.invoke(testModel, num);
                    }
                }
                // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param testModel 填充传入的对象
     */
    public static void fillModelByDefault(Object testModel) {
        Field[] fieldSubs = testModel.getClass().getSuperclass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        Field[] fieldSupers = testModel.getClass().getSuperclass().getSuperclass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        try {
            for (Field fieldSub : fieldSubs) { // 遍历所有属性
                String name = fieldSub.getName(); // 获取属性的名字
                if (name.equals("id")) {
                    continue;
                }
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = fieldSub.getGenericType().toString(); // 获取属性的类型
                if (type.equals(CLASS_TYPE_STRING)) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    String valueForView = (String) mForView.invoke(testModel); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, String.class);
                        mForView.invoke(testModel, "");
                    }
                }
                if (type.equals(CLASS_TYPE_INTEGER)) {
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Integer valueForView = (Integer) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Integer.class);
                        mForView.invoke(testModel, Integer.MIN_VALUE);
                    }
                }
                if (type.equals(CLASS_TYPE_BOOLEAN)) {
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Boolean valueForView = (Boolean) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Boolean.class);
                        mForView.invoke(testModel, false);
                    }
                }
                if (type.equals(CLASS_TYPE_DATE)) {
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Date valueForView = (Date) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Date.class);
                        mForView.invoke(testModel, new Date());
                    }
                }
                if (type.equals(CLASS_TYPE_LONG)) {
                    Integer intNum = Integer.MIN_VALUE;
                    Long num = intNum.longValue();
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Long valueForView = (Long) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Long.class);
                        mForView.invoke(testModel, num);
                    }
                }
                if (type.equals(CLASS_TYPE_DOUBLE)) {
                    Double num = 0.0;
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Double valueForView = (Double) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Double.class);
                        mForView.invoke(testModel, num);
                    }
                }
                // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
            }

            for (Field fieldSuper : fieldSupers) { // 遍历所有属性
                String name = fieldSuper.getName(); // 获取属性的名字
                if (name.equals("id")) {
                    continue;
                }
                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
                String type = fieldSuper.getGenericType().toString(); // 获取属性的类型
                if (type.equals(CLASS_TYPE_STRING)) { // 如果type是类类型，则前面包含"class "，后面跟类名
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    String valueForView = (String) mForView.invoke(testModel); // 调用getter方法获取属性值
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, String.class);
                        mForView.invoke(testModel, "");
                    }
                }
                if (type.equals(CLASS_TYPE_INTEGER)) {
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Integer valueForView = (Integer) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Integer.class);
                        mForView.invoke(testModel, Integer.MIN_VALUE);
                    }
                }
                if (type.equals(CLASS_TYPE_BOOLEAN)) {
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Boolean valueForView = (Boolean) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Boolean.class);
                        mForView.invoke(testModel, false);
                    }
                }
                if (type.equals(CLASS_TYPE_DATE)) {
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Date valueForView = (Date) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Date.class);
                        mForView.invoke(testModel, new Date());
                    }
                }
                if (type.equals(CLASS_TYPE_LONG)) {
                    Integer intNum = Integer.MIN_VALUE;
                    Long num = intNum.longValue();
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Long valueForView = (Long) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Long.class);
                        mForView.invoke(testModel, num);
                    }
                }
                if (type.equals(CLASS_TYPE_DOUBLE)) {
                    Double num = 0.0;
                    Method mForView = testModel.getClass().getMethod("get" + name);
                    Double valueForView = (Double) mForView.invoke(testModel);
                    if (valueForView == null) {
                        mForView = testModel.getClass().getMethod("set" + name, Double.class);
                        mForView.invoke(testModel, num);
                    }
                }
                // 如果有需要,可以仿照上面继续进行扩充,再增加对其它类型的判断
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
