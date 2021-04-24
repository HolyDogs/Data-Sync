package com.thinvent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thinvent.entity.SToken;
import com.thinvent.entity.SUser;
import com.github.pagehelper.PageInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xff
 * @since 2020-11-11
 */
public interface SUserService extends IService<SUser> {

    /**
     * 获取用户信息
     * @param loginName
     * @param isDelete 是否删除 0-获取未删除，1-获取删除，2所有
     * @return
     */
    SUser getUserByLoginName(String loginName, Integer isDelete);

    /**
     * 获取用户信息
     * @param pageNum
     * @param name  姓名
     * @param loginName 登录名
     * @param phone 手机号
     * @return
     */
    PageInfo getUserInfo(Integer pageNum, String name, String loginName, String phone);

    /**
     * 修改is_delete
     * 获取修改人的信息
     * @param userId 用户id
     */
    void deleteUser(String userId, SToken sToken);


    /**
     * 修改is_delete
     * 获取修改人的信息
     * @param userId 用户id
     */
    void undeleteUser(String userId, SToken sToken);

    /**
     * 更新用户信息
     * @param userId 用户id
     * @param userName 姓名
     * @param userSex 性别
     * @param userPhone 手机号
     * @param userEmail 邮箱
     * @param sToken 更新人相关信息
     */
    void updateUser(String userId, String userName, Integer userSex, String userPhone, String userEmail, SToken sToken);
}
