package com.neeyoo.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by NeeYoo.
 * Created on 2019/9/12.
 * Description:
 */
@Data
@NoArgsConstructor
public class ConsumerException extends Exception {

    private Integer code;
    private String message;

    public ConsumerException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
}
