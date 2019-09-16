package com.neeyoo.service.impl;

import com.neeyoo.service.TestService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NeeYoo.
 * Created on 2019/9/12.
 * Description:
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    public String removeCode(String phone) {
        return null;
    }

    @Override
    public String getCode(String phone) {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
    }

    @Override
    public String getCode1(String phone) {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
    }

    @Override
    public String getCode2(String phone) {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
    }
}
