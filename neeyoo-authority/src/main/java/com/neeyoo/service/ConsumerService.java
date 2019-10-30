package com.neeyoo.service;

import com.neeyoo.dto.LoginSuccessDTO;
import com.neeyoo.exception.AuthorityException;
import com.neeyoo.request.LoginRequest;

/**
 * Created by NeeYoo.
 * Created on 2019/10/29.
 * Description:
 */
public interface ConsumerService {

    /**
     * 根据手机号查询用户信息
     *
     * @param request
     * @return
     */
    LoginSuccessDTO login(LoginRequest request) throws AuthorityException;
}
