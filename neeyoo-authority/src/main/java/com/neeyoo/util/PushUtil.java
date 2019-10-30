package com.neeyoo.util;

import com.neeyoo.push.JiguangPush;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NeeYoo.
 * Created on 2019/10/29.
 * Description:
 */
public class PushUtil {

    // 极光推送状态码
    public static final String PUSH_STATUS_REMOTE_LOGIN = "201"; // 极光推送账户异地登录

    public static void accountRemoteLogin(String registrationId){
        Map<String, String> extrasParams = new HashMap<>();
        extrasParams.put("status", PUSH_STATUS_REMOTE_LOGIN);
        JiguangPush.sendToRegistrationId(registrationId,
                "您的账号已在别处登录, 如非本人操作, 请及时更改密码", "", "", extrasParams); // 给用户别处登录消息
    }
}
