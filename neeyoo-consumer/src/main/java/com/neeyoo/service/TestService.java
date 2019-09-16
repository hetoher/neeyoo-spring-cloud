package com.neeyoo.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import java.util.Map;

/**
 * Created by NeeYoo.
 * Created on 2019/9/12.
 * Description: 测试service
 */
//@CacheConfig(cacheNames = "SMS_CODE")
public interface TestService {

    @CacheEvict(value = "SMS_CODE", key = "'consumer_'+#p0")
    String removeCode(String phone);

    @Cacheable(value = "SMS_CODE", key = "'consumer_'+#p0")
    String getCode(String phone);

    @Cacheable(value = "SMS_CODE1", key = "'consumerRegister_'+#p0")
    String getCode1(String phone);

    @Cacheable(value = "SMS_CODE2", key = "'consumerModify_'+#p0")
    String getCode2(String phone);
}
