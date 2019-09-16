package com.neeyoo.util;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Create by NeeYoo.
 * Create on 2019/9/12.
 * Description: 工具类
 */
public class CommonUtils {

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: 处理参数
     */
    public static Map<String, Object> httpRequestParamasToMap(HttpServletRequest httpRequest) {
        Map<String, Object> params = new HashMap<>();
        Map<String, String[]> requestParams = httpRequest.getParameterMap();
        for (String name : requestParams.keySet()) {
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        return params;
    }

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: map转化为对象
     */
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;
        Object obj = beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }

            field.setAccessible(true);
            field.set(obj, map.get(field.getName()));
        }
        return obj;
    }

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: 将Map中的key由下划线转换为驼峰 user_name-->userName
     */
    public static Map<String, Object> formatHumpName(Map<String, Object> map) {
        Map<String, Object> newMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String newKey = toFormatCol(key);
            newMap.put(newKey, entry.getValue());
        }
        return newMap;
    }

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: 驼峰转下划线
     */
    private static String toFormatCol(String colName) {
        StringBuilder sb = new StringBuilder();
        String[] str = colName.toLowerCase().split("_");
        int i = 0;
        for (String s : str) {
            if (s.length() == 1) {
                s = s.toUpperCase();
            }
            i++;
            if (i == 1) {
                sb.append(s);
                continue;
            }
            if (s.length() > 0) {
                sb.append(s.substring(0, 1).toUpperCase());
                sb.append(s.substring(1));
            }
        }
        return sb.toString();
    }

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: 将Map中的key由驼峰转换为下划线 userName-->user_name
     */
    public static Map<String, Object> formatHumpToUnderLine(Map<String, Object> map) {
        Map<String, Object> newMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();
            String newKey = underscoreName(key);
            newMap.put(newKey, entry.getValue());
        }
        return newMap;
    }

    private static String underscoreName(String camelCaseName) {
        StringBuilder result = new StringBuilder();
        if (camelCaseName != null && camelCaseName.length() > 0) {
            result.append(camelCaseName.substring(0, 1).toLowerCase());
            for (int i = 1; i < camelCaseName.length(); i++) {
                char ch = camelCaseName.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: 判断对象为空/空串
     */
    public static boolean isEmptyString(Object obj) {
        if (obj == null) {
            return true;
        } else if ((obj instanceof List)) {
            return ((List) obj).size() == 0;
        } else {
            return (obj instanceof String) && ((String) obj).trim().equals("");
        }
    }

}
