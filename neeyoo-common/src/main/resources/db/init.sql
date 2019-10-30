-- ----------------------------
-- Table structure for `neeyoo_consumer`
-- Description: 用户表
-- ----------------------------
DROP TABLE IF EXISTS `neeyoo_consumer`;
CREATE TABLE `neeyoo_consumer` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_phone` varchar(15) NOT NULL COMMENT '账户/手机号',
  `nick_name` varchar(32) DEFAULT NULL COMMENT '用户昵称',
  `user_photo` varchar(128) DEFAULT NULL COMMENT '用户头像',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `salt` varchar(32) NOT NULL COMMENT '盐',
  `pay_password` varchar(100) DEFAULT NULL COMMENT '支付密码',
  `pay_salt` varchar(32) DEFAULT NULL COMMENT '支付密码盐',
  `useable_points` bigint DEFAULT '0' COMMENT '可使用积分',
  `accumulate_points` bigint DEFAULT '0' COMMENT '累计积分',
  `account_balances` decimal(12,2) DEFAULT '0.00' COMMENT '账户余额',
  `account_balances_total` decimal(12,2) DEFAULT '0.00' COMMENT '历史累计收益',
  `status_sign` char(1) NOT NULL DEFAULT '0' COMMENT '账号状态标志: 0正常 1锁定 2异常',
  `account_status_sign` char(1) NOT NULL DEFAULT '0' COMMENT '账户状态标志: 0正常 1锁定',
  `registration_id` varchar(64) DEFAULT NULL COMMENT '极光id',
  `effective_sign` char(1) NOT NULL DEFAULT 'Y' COMMENT '有效标志',
  `last_login_time` bigint DEFAULT NULL COMMENT '上一次登录时间',
  `create_time` bigint DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';
-- ----------------------------
-- Records of logistics_user
-- ----------------------------