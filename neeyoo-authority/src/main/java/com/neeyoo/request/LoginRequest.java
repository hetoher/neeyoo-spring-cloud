package com.neeyoo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by NeeYoo.
 * Created on 2019/10/29.
 * Description:
 */
@Data
@ApiModel(value = "登录请求参数", description = "登录请求实体类")
public class LoginRequest implements Serializable {

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "(13|14|15|17|18|19)[0-9]{9}", message = "手机号格式错误")
    @ApiModelProperty(value = "账户/手机号", dataType = "String")
    private String userPhone;

    @NotBlank(message = "密码不能为空")
    @Length(min = 6, max = 16, message = "密码由6个到20个字符组成,包含数字、字母、下划线等字符")
    @ApiModelProperty(value = "密码", dataType = "String")
    private String password;

    @NotBlank(message = "设备号不能为空")
    @ApiModelProperty(value = "设备号", dataType = "String")
    private String deviceId;

    @ApiModelProperty(value = "极光id", dataType = "String")
    private String registrationId;
}
