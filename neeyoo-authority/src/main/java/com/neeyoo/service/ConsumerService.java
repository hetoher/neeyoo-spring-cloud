package com.neeyoo.service;

import com.neeyoo.dto.LoginSuccessDTO;
import com.neeyoo.exception.AuthorityException;
import com.neeyoo.request.LoginRequest;
import com.neeyoo.request.RefreshTokenRequest;

import java.util.Map;

/**
 * Created by NeeYoo.
 * Created on 2019/10/29.
 * Description:
 */
public interface ConsumerService {

    /**
     * 根据手机号查询用户信息
     * @param request
     * @return
     * @throws AuthorityException
     */
    LoginSuccessDTO login(LoginRequest request) throws AuthorityException;

    /**
     * 刷新token
     * @param request
     * @return
     * @throws AuthorityException
     */
    Map<String, Object> refreshToken(RefreshTokenRequest request) throws AuthorityException;
}
