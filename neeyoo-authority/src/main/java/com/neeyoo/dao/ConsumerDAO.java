package com.neeyoo.dao;

import com.neeyoo.domain.NeeYooConsumer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by NeeYoo.
 * Created on 2019/10/29.
 * Description:
 */
@Mapper
public interface ConsumerDAO {

    NeeYooConsumer findByUserPhone(@Param("userPhone") String userPhone);

    void updateRegistrationId(@Param("userPhone") String userPhone,
                              @Param("registrationId") String registrationId,
                              @Param("loginTime") Long loginTime);

    NeeYooConsumer findByUserId(@Param("userId") Long userId);
}
