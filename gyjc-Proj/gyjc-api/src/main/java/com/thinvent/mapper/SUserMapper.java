package com.thinvent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinvent.entity.SUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xff
 * @since 2020-11-11
 */
@Mapper
public interface SUserMapper extends BaseMapper<SUser> {

    /**
     * 获取用户信息
     * @param name 用户名
     * @param loginName 登录名
     * @param phone 电话
     * @return 用户列表
     */
    List<SUser> getUserInfo(@Param("fullName") String name,
                                   @Param("loginName") String loginName,
                                   @Param("phone") String phone);

}
