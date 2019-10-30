package com.neeyoo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by NeeYoo.
 * Created on 2019/10/29.
 * Description:
 */
@Data
@ApiModel(value = "LoginSuccessDTO")
public class LoginSuccessDTO  implements Serializable{

    @ApiModelProperty(value = "用户电话", dataType = "Integer")
    private String userPhone;

    @ApiModelProperty(value = "token", dataType = "Integer")
    private String authorization;

    @ApiModelProperty(value = "刷新token", dataType = "Integer")
    private String refreshToken;

}
