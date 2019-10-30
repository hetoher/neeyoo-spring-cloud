package com.neeyoo.exception;

import lombok.Data;

/**
 * Create by NeeYoo.
 * Create on 2019/10/29.
 * Description: 自定义异常
 */
@Data
public class AuthorityException extends Exception {

    private Integer code;
    private String message;

    public AuthorityException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

}
