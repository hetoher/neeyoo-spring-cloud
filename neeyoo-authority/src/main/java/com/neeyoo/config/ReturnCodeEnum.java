package com.neeyoo.config;

import lombok.Getter;

/**
 * Created by NeeYoo.
 * Created on 2019/10/29.
 * Description:
 */
@Getter
public enum  ReturnCodeEnum {

    PARAMS_CHECK_FAIL(-1, "参数错误"),
    SERVICE_INVALID(-99, "访问服务失败"),

    AUTHORITY_INVALID(-199, "凭据无效"),
    LOGIN_OUT_FAIL(-200, "退出登录失败"),
    LOGIN_FAIL_EXPIRE(-201, "身份过期,刷新token"),
    LOGIN_FAIL_INVALID(-202, "已在其它设备登录/无效/refreshToken失效等,需要重新登录"),
    LOGIN_FAIL(-203, "自动登录失败,请重试"),

    USER_EXISTS(-9921, "用户已存在"),
    USER_NOT_EXISTS(-9922, "用户不存在"),
    USER_ACCOUNT_ERROR(-9923, "账号或密码错误"),
    USER_ADD_FAIL(-9924, "注册失败"),
    USER_MODIFY_FAIL(-9925, "修改密码失败"),
    ;

    ReturnCodeEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;
}
