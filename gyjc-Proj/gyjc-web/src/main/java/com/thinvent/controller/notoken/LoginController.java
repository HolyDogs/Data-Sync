package com.thinvent.controller.notoken;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thinvent.entity.SToken;
import com.thinvent.entity.SUser;
import com.thinvent.model.response.GenericResponseModel;
import com.thinvent.security.Md5Utils;
import com.thinvent.security.TokenCreater;
import com.thinvent.service.STokenService;
import com.thinvent.service.SUserService;
import com.thinvent.utils.GyjcConstant;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * 登录注册
 * @author xufeng
 * @version 1.0
 * @date 2020/11/11 10:52
 **/
@RestController
@RequestMapping(value = "/gyProj")
public class LoginController {

    @Autowired
    private SUserService sUserService;

    @Autowired
    private STokenService sTokenService;

    @PostMapping("/login")
    public GenericResponseModel login(@RequestParam("name") String name
            , @RequestParam("password") String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        SUser sUser = sUserService.getOne(new QueryWrapper<SUser>()
                .eq("LOGIN_NAME", name)
                .eq("PASSWORD", Md5Utils.encodePassword(password)));
        if (null == sUser) {
            genericResponseModel.setErrCode(201);
            genericResponseModel.setErrMsg("登录失败，帐号或密码错误");
            return genericResponseModel;
        }
        if (1 == sUser.getIsDelete()) {
            genericResponseModel.setErrCode(201);
            genericResponseModel.setErrMsg("登录失败，该帐号已被禁用");
            return genericResponseModel;
        }
        //创建token返回,过期时间30分钟
        String token = TokenCreater.createJWT(sUser.getId(), name, GyjcConstant.ENABLE_TIME);
        //token保存到数据库
        SToken sToken = new SToken();
        sToken.setId(UUID.randomUUID().toString().replace("-", ""));
        //数据插入时间
        sToken.setCreateTime(new Date());
        //认证用户id
        sToken.setLoginUserId(sUser.getId());
        //认证用户名
        sToken.setLoginUserName(name);
        //解析token
        HashMap tokenMap = TokenCreater.parseJWT(token);
        //token生效时间
        sToken.setLoginTime((Date) MapUtils.getObject(tokenMap, "iat"));
        //token失效时间
        sToken.setFailureTime((Date) MapUtils.getObject(tokenMap, "exp"));
        //token值
        sToken.setTokenNo(token);
        //保存
        sTokenService.save(sToken);
        genericResponseModel.setErrCode(200);
        //设置token返回
        genericResponseModel.setToken(token);
        HashMap<String, String> map = new HashMap<>();
        map.put("type", String.valueOf(sUser.getType()));
        map.put("name",sUser.getFullName());
        genericResponseModel.setData(map);

        return genericResponseModel;
    }

    @PostMapping("/register")
    public GenericResponseModel register(@RequestParam("name") String name
            , @RequestParam("password") String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        SUser sUser = sUserService.getOne(new QueryWrapper<SUser>().eq("LOGIN_NAME", name));
        if (null != sUser) {
            genericResponseModel.setErrCode(201);
            genericResponseModel.setErrMsg("当前账号已被注册");
            return genericResponseModel;
        }
        sUser = new SUser();
        sUser.setId(UUID.randomUUID().toString().replace("-", ""));
        sUser.setCreateTime(new Date());
        sUser.setPassword(Md5Utils.encodePassword(password));
        sUser.setEnablePassword(Md5Utils.base64Encode(password));
        sUser.setLoginName(name);
        sUser.setIsDelete(0);
        //普通用户-0
        sUser.setType(0);
        sUserService.save(sUser);
        genericResponseModel.setErrCode(200);
        return genericResponseModel;
    }

}
