package com.thinvent.aspect;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thinvent.entity.SToken;
import com.thinvent.model.response.GenericResponseModel;
import com.thinvent.security.TokenCreater;
import com.thinvent.service.STokenService;
import com.thinvent.utils.GyjcConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 配置token切面，验证token是否有效
 * @author xufeng
 * @version 1.0
 * @date 2020/11/11 11:05
 **/
@Aspect
@Component
@Slf4j
public class TokenCheckAspect {

    @Pointcut("execution(* com.thinvent.controller.*.*(..))")
    private void aop1(){}

    @Autowired
    private STokenService sTokenService;

    @Around("aop1()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization");

        GenericResponseModel result = null;
        HashMap map = null;
        try {
            map = TokenCreater.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            log.warn("====当前TOKEN已失效");
            GenericResponseModel genericResponseModel = new GenericResponseModel();
            genericResponseModel.setErrCode(401);
            genericResponseModel.setErrMsg("登录身份失效,请重新登录！");
            return genericResponseModel;
        }
        //如果token成功解析，去与数据库比对
        QueryWrapper<SToken> queryWrapper = new QueryWrapper<>();
        //用户id
        queryWrapper.eq("LOGIN_USER_ID", MapUtils.getString(map, "id"));
        //查询token
        queryWrapper.select("TOKEN_NO");
        List<Object> tokenList = sTokenService.listObjs(queryWrapper);
        if (null != tokenList) {
            //查询是否包含该token
            if (!tokenList.contains(token)) {
                //不含该token的话则为异常token,身份失效
                log.warn("====当前TOKEN为异常TOKEN！！！");
                GenericResponseModel genericResponseModel = new GenericResponseModel();
                genericResponseModel.setErrCode(401);
                genericResponseModel.setErrMsg("登录身份失效,请重新登录！");
                return genericResponseModel;
            }
            //包含该TOKEN，检测生效时间与当前时间对比，如果过去十分钟则刷新TOKEN
            Date iat = (Date) MapUtils.getObject(map, "iat");
            //当前时间
            Date now = new Date();
            long timeValue = now.getTime() - iat.getTime();
            //截面运行结果
            result = (GenericResponseModel) joinPoint.proceed();
            if (timeValue > GyjcConstant.FLASH_TIME) {
                //超过十分钟则给用户一个新的token，避免登录身份突然失效
                String newToken = TokenCreater.createJWT(MapUtils.getString(map, "id")
                        , MapUtils.getString(map, "name")
                        , GyjcConstant.ENABLE_TIME);
                SToken sToken = new SToken();
                sToken.setId(UUID.randomUUID().toString().replace("-", ""));
                //数据插入时间
                sToken.setCreateTime(new Date());
                //认证用户id
                sToken.setLoginUserId(MapUtils.getString(map, "id"));
                //认证用户名
                sToken.setLoginUserName(MapUtils.getString(map, "name"));
                //解析token
                HashMap tokenMap = TokenCreater.parseJWT(newToken);
                //token生效时间
                sToken.setLoginTime((Date) MapUtils.getObject(tokenMap, "iat"));
                //token失效时间
                sToken.setFailureTime((Date) MapUtils.getObject(tokenMap, "exp"));
                //token值
                sToken.setTokenNo(newToken);
                //保存
                sTokenService.save(sToken);
                //设置新token返回给用户
                result.setToken(newToken);
            }

        }
        //token有效
        log.info("======TOKEN验证成功======");
        return result;
    }

}
