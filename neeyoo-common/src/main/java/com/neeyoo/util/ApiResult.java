package com.neeyoo.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * Created by NeeYoo.
 * Created on 2019/9/12.
 * Description: 统一返回报文格式
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ApiResult-统一返回报文格式", description = "统一返回报文格式")
public class ApiResult<T> implements Serializable {

    @ApiModelProperty(value = "错误代码(0-成功/其它-失败)")
    private Integer code = 0;
    @ApiModelProperty(value = "信息描述")
    private String msg;
    @ApiModelProperty(value = "数据对象")
    private T data;

    public ApiResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: 失败
     */
    public static ApiResult fail(Integer code, String msg){
        ApiResult apiResult = new ApiResult();
        apiResult.code = code;
        if (StringUtils.isEmpty(msg)){
            apiResult.msg = "失败";
        } else {
            apiResult.msg = msg;
        }
        return apiResult;
    }

    /**
     * Create by NeeYoo.
     * Create on 2019/9/12.
     * Description: 成功
     */
    public static<T> ApiResult ok(T data, String msg){
        ApiResult apiResult = new ApiResult();
        apiResult.code = 0;
        apiResult.data = data;
        if (StringUtils.isEmpty(msg)){
            apiResult.msg = "成功";
        } else {
            apiResult.msg = msg;
        }
        return apiResult;
    }
}
