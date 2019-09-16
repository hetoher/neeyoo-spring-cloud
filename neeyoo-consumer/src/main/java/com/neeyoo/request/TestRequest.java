package com.neeyoo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Created by NeeYoo.
 * Created on 2019/9/12.
 * Description:
 */
@Data
@ApiModel(value = "测试请求类")
public class TestRequest implements Serializable {

    @NotBlank(message = "name不能为空")
    @ApiModelProperty(value = "名字", dataType = "String")
    private String name;

    @ApiModelProperty(value = "电话", dataType = "String")
    private String phone;
}
