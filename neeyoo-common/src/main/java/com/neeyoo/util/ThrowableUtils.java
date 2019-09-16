package com.neeyoo.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Create by NeeYoo.
 * Create on 2019/9/12.
 * Description: 异常堆栈信息
 */
public class ThrowableUtils {

    /**
     * 获取堆栈信息
     * @param throwable
     * @return
     */
    public static String getStackTrace(Throwable throwable){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        try {
            throwable.printStackTrace(pw);
            return sw.toString();
        } finally {
            pw.close();
        }
    }
}
