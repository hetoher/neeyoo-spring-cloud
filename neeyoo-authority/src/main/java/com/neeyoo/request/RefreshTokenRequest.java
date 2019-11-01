package com.neeyoo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by NeeYoo.
 * Created on 2019/10/31.
 * Description:
 */
@Data
@ApiModel(value = "RefreshTokenRequest-刷新token请求参数", description = "刷新token请求实体类")
public class RefreshTokenRequest {

    @NotNull(message = "参数错误")
    @ApiModelProperty(value = "userId", dataType = "Long")
    private Long userId;

    @NotBlank(message = "refreshToken不能为空")
    @ApiModelProperty(value = "刷新token值", dataType = "String")
    private String refreshToken;

    @NotBlank(message = "设备号不能为空")
    @ApiModelProperty(value = "设备号", dataType = "String")
    private String deviceId;

}
