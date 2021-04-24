package com.thinvent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.thinvent.entity.SToken;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xff
 * @since 2020-11-12
 */
public interface STokenService extends IService<SToken> {

    public SToken getUserInfo(String id);

}
