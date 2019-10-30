package com.neeyoo.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by NeeYoo.
 * Created on 2019/10/29.
 * Description:
 */
@Entity
@Data
@Table(name = "neeyoo_user")
@ApiModel(value = "NeeYooConsumer实体类")
public class NeeYooConsumer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @ApiModelProperty(value = "id", dataType = "Long")
    private Long id;

    @Column(name = "user_phone", nullable = false)
    @ApiModelProperty(value = "账户/手机号", dataType = "String")
    private String userPhone;

    @Column(name = "nick_name")
    @ApiModelProperty(value = "昵称", dataType = "String")
    private String nickName;

    @Column(name = "user_photo")
    @ApiModelProperty(value = "用户头像", dataType = "String")
    private String userPhoto;

    @Column(name = "password", nullable = false)
    @ApiModelProperty(value = "密码", dataType = "String")
    private String password;

    @Column(name = "salt", nullable = false)
    @ApiModelProperty(value = "盐", dataType = "String")
    private String salt;

    @Column(name = "pay_password")
    @ApiModelProperty(value = "支付密码", dataType = "String")
    private String payPassword;

    @Column(name = "pay_salt")
    @ApiModelProperty(value = "支付密码盐", dataType = "String")
    private String paySalt;

    @Column(name = "useable_points")
    @ApiModelProperty(value = "可使用积分", dataType = "Long")
    private Long useablePoints;

    @Column(name = "accumulate_points")
    @ApiModelProperty(value = "累计积分", dataType = "Long")
    private Long accumulatePoints;

    @Column(name = "account_balances")
    @ApiModelProperty(value = "账户余额", dataType = "BigDecimal")
    private BigDecimal accountBalances;

    @Column(name = "account_balances_total")
    @ApiModelProperty(value = "历史累计余额", dataType = "BigDecimal")
    private BigDecimal accountBalancesTotal;

    @Column(name = "status_sign", nullable = false)
    @ApiModelProperty(value = "账号状态标志: 0正常 1锁定 2异常", dataType = "String")
    private String statusSign;

    @ApiModelProperty(value = "极光id", dataType = "String")
    private String registrationId;

    @Column(name = "effective_sign", nullable = false)
    @ApiModelProperty(value = "有效标志", dataType = "String")
    private String effectiveSign;

    @Column(name = "last_login_time")
    @ApiModelProperty(value = "上一次登录时间", dataType = "Long")
    private Long lastLoginTime;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间", dataType = "Long")
    private Long createTime;
}
