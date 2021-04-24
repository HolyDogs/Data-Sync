package com.thinvent.aspect;

import com.jayway.jsonpath.JsonPath;
import com.thinvent.entity.PoolZb;
import com.thinvent.entity.SToken;
import com.thinvent.entity.SUser;
import com.thinvent.model.response.GenericResponseModel;
import com.thinvent.service.*;
import com.thinvent.utils.GyjcConstant;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * 日志切面
 * @author yb
 * @version 1.0
 * @date 2020/11/12
 **/
@Aspect
@Component
@Slf4j
public class LogAspect {

    @Autowired
    private SSystemLogService systemLogService;

    @Autowired
    private PoolZbNewTableService poolZbNewTableService;

    @Autowired
    private PoolZbNewTableZsService poolZbNewTableZsService;

    @Autowired
    private PoolZbService poolZbService;

    @Autowired
    private STokenService sTokenService;

    @Autowired
    private SUserService sUserService;

    /**
     * 登录切面
     */
    @Pointcut("execution(* com.thinvent.controller.notoken.LoginController.login(..))")
    private void loginAop(){}

    /**
     * 注册切面
     */
    @Pointcut("execution(* com.thinvent.controller.notoken.LoginController.register(..))")
    private void registerAop(){}

    /**
     * 绑定指标切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbController.updateBindZb(..))")
    private void updateBindZbAop(){}

    /**
     * 解绑切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbController.unBundZb(..))")
    private void unBundZbAop(){}

    /**
     * 设置无匹配项切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbController.setPoolZbState(..))")
    private void setPoolZbStateAop(){}

    /**
     * 指标别名更新切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbController.commentUpdate(..))")
    private void commentUpdateAop(){}

    /**
     * 计量单位更新切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbController.jldwUpdate(..))")
    private void jldwUpdateAop(){}

    /**
     * 生成展示表切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbNewTableController.displayCreate(..))")
    private void displayCreateAop(){}

    /**
     * 主题表删除指标切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbNewTableController.deleteTableZb(..))")
    private void deleteTableZbAop(){}

    /**
     * 主题表批量删除指标切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbNewTableController.deleteTableZbList(..))")
    private void deleteTableZbListAop(){}

    /**
     * 主题表增加指标切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbNewTableController.zbDataAddToTable(..))")
    private void zbDataAddToTableAop(){}

    /**
     * 展示表批量删除指标切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbNewTableZsController.deleteZsTableZbList(..))")
    private void deleteZsTableZbList(){}

    /**
     * 创建主题表切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbController.tableCreate(..))")
    private void tableCreateAop(){}

    /**
     * 删除主题表切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbNewTableController.deleteTable(..))")
    private void deleteTableAop(){}

    /**
     * 展示表增加指标切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbNewTableZsController.addZbToZsTable(..))")
    private void addZbToZsTableAop(){}

    /**
     * 删除展示表切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbNewTableZsController.deleteZsTable(..))")
    private void deleteZsTableAop(){}

    /**
     * 展示表删除指标切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbNewTableZsController.deleteZsTableZb(..))")
    private void deleteZsTableZbAop(){}

    /**
     * 展示表生成统计数据切面
     */
    @Pointcut("execution(* com.thinvent.controller.PoolZbNewTableZsController.countToTjTable(..))")
    private void countToTjTableAop(){}

    /**
     * 管理系统切面
     */
    @Pointcut("execution(* com.thinvent.controller.ManagerController.*(..))")
    private void managerAop(){}

    @Around("loginAop()")
    public Object loginAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        Object result = null;
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        String userName = (String)arg;
        try {
            result = proceedingJoinPoint.proceed();
            GenericResponseModel genericResponseModel = (GenericResponseModel)result;
            if(genericResponseModel.getErrCode()==200){
                systemLogService.saveLog(ipAddr,null,userName,"账户'"+userName+"'登录成功",0);
            }else{
                systemLogService.saveLog(ipAddr,null,userName,"账户'"+userName+"'"+genericResponseModel.getErrMsg(),0);
            }
        } catch (Exception e) {
            GenericResponseModel genericResponseModel = new GenericResponseModel();
            genericResponseModel.setErrCode(201);
            genericResponseModel.setErrMsg("系统繁忙，请重试");
            systemLogService.saveLog(ipAddr,null,userName,"账户'"+userName+"'登录失败",0);
            systemLogService.saveLog(ipAddr,null,userName,e.getMessage(),1);
            return genericResponseModel;
        }
        return result;
    }


    @Around("registerAop()")
    public Object registerAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        Object result = null;
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        String userName = (String)arg;
        try {
            result = proceedingJoinPoint.proceed();
            GenericResponseModel genericResponseModel = (GenericResponseModel)result;
            if(genericResponseModel.getErrCode()==200){
                systemLogService.saveLog(ipAddr,null,userName,"账户'"+userName+"'注册成功",0);
            }else{
                systemLogService.saveLog(ipAddr,null,userName,"账户'"+userName+"'注册失败,"+genericResponseModel.getErrMsg(),0);
            }
        } catch (Exception e) {
            GenericResponseModel genericResponseModel = new GenericResponseModel();
            genericResponseModel.setErrCode(201);
            genericResponseModel.setErrMsg("系统繁忙，请重试");
            systemLogService.saveLog(null,null,userName,"账户'"+userName+"'注册失败",0);
            systemLogService.saveLog(null,null,userName,e.getMessage(),1);
            return genericResponseModel;
        }
        return result;
    }


    @Around("updateBindZbAop()")
    public Object updateBindZbAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        Object arg1=args[1];
        String bindZb=(String)arg1;
        bindZb = JsonPath.read(bindZb, "$.bindzbval");
        String zbId = (String)arg;
        List<PoolZb> zbs = poolZbService.getZbListByIdList(zbId);
        List<PoolZb> bindZbs = null;
        //修复bug，sqlin语句参数超过1000
        if (bindZb.length() < 2000) {
            //绑定指标
            bindZbs = poolZbService.getZbListByIdList(bindZb);
        }
        String zbmc = null;
        if(zbs!=null&&zbs.size()>0){
            zbmc = zbs.get(0).getZbmc();
        }
        StringBuffer bindZbmc = new StringBuffer();
        if (null != bindZbs) {
            for(int i=0;i<bindZbs.size();i++){
                if(i<bindZbs.size()-1){
                    bindZbmc.append(bindZbs.get(i).getZbmc()+",");
                }else{
                    bindZbmc.append(bindZbs.get(i).getZbmc());
                }
            }
        }

        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            GenericResponseModel genericResponseModel = (GenericResponseModel)result;
            if(genericResponseModel.getErrMsg().contains("保存")){
                systemLogService.saveLog(ipAddr,userId,null,"'"+zbmc+"'绑定'"+ bindZbmc.toString() +"'指标"+genericResponseModel.getErrMsg(),0);
            }else{
                systemLogService.saveLog(ipAddr,userId,null,genericResponseModel.getErrMsg(),0);
            }
        } catch (Exception e) {
            return getResult(ipAddr, userId, "'"+zbmc+"'绑定'" + bindZbmc.toString() +"'指标失败", e);
        }
        return result;
    }


    @Around("unBundZbAop()")
    public Object unBundZbAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        String zbId = (String)arg;
        List<PoolZb> zbs = poolZbService.getZbListByIdList(zbId);
        String zbmc=null;
        if(zbs!=null&&zbs.size()>0){
            zbmc=zbs.get(0).getZbmc();
        }
        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            GenericResponseModel genericResponseModel = (GenericResponseModel)result;
            systemLogService.saveLog(ipAddr,userId,null,"'"+zbmc+"'"+genericResponseModel.getErrMsg(),0);
        } catch (Exception e) {
            return getResult(ipAddr, userId, "'"+zbmc+"'"+"解绑失败", e);
        }
        return result;
    }


    @Around("setPoolZbStateAop()")
    public Object setPoolZbStateAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        String ids[]=(String[])arg;
        StringBuffer idstr=new StringBuffer();
        for(String zbid:ids){
            idstr.append(zbid+",");
        }
        List<PoolZb> zbList = poolZbService.getZbListByIdList(idstr.toString());
        StringBuffer zbmcs=new StringBuffer();
        for(PoolZb zb:zbList){
            zbmcs.append(zb.getZbmc()+",");
        }
        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            GenericResponseModel genericResponseModel = (GenericResponseModel)result;
            systemLogService.saveLog(ipAddr,userId,null,zbmcs.toString()+"置为无匹配项"+genericResponseModel.getErrMsg(),0);
        } catch (Exception e) {
            return getResult(ipAddr, userId, zbmcs.toString()+"置为无匹配项失败", e);
        }
        return result;
    }


    @Around("commentUpdateAop()")
    public Object commentUpdateAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg0 = args[0];
        String zbId=(String)arg0;
        List<PoolZb> zbs = poolZbService.getZbListByIdList(zbId);
        String zb=null;
        if(zbs!=null && zbs.size()>0){
            zb=zbs.get(0).getZbmc();
        }
        Object arg = args[1];
        String comment = (String)arg;
        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            GenericResponseModel genericResponseModel = (GenericResponseModel)result;
            systemLogService.saveLog(ipAddr,userId,null,zb+"修改为指标别名'"+comment+"'"+genericResponseModel.getErrMsg(),0);
        } catch (Exception e) {
            return getResult(ipAddr, userId, zb+"修改为指标别名'"+comment+"'更新失败", e);
        }
        return result;
    }


    @Around("jldwUpdateAop()")
    public Object jldwUpdateAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        String zbId = (String)arg;
        List<PoolZb> zbs = poolZbService.getZbListByIdList(zbId);
        String zbmc=null;
        if(zbs!=null&&zbs.size()>0){
            zbmc=zbs.get(0).getZbmc();
        }
        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            GenericResponseModel genericResponseModel = (GenericResponseModel)result;
            systemLogService.saveLog(ipAddr,userId,null,"'"+zbmc+"'计量单位"+genericResponseModel.getErrMsg(),0);
        } catch (Exception e) {
            return getResult(ipAddr, userId, "'" + zbmc + "'计量单位更新失败", e);
        }
        return result;
    }


    @Around("tableCreateAop()")
    public Object tableCreateAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        String tableName = (String)arg;
        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            GenericResponseModel genericResponseModel = (GenericResponseModel)result;
            systemLogService.saveLog(ipAddr,userId,null,"主题表'ZB_"+tableName+"'"+genericResponseModel.getErrMsg(),0);
        } catch (Exception e) {
            poolZbNewTableService.deleteTable(GyjcConstant.ZB_TABLE_PREFIX + tableName);
            return getResult(ipAddr, userId, "主题表'ZB_"+tableName+"'"+"建表失败", e);
        }
        return result;
    }


    @Around("zbDataAddToTableAop()")
    public Object zbDataAddToTableAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        Object arg1 = args[1];
        String zbList=(String)arg1;
        String tableId = (String)arg;
        String tableName=poolZbNewTableService.getPoolZbNewTable(tableId).getTableName();
        List<PoolZb> zbLists = poolZbService.getZbListByIdList(zbList);
        StringBuffer zbmcs=new StringBuffer();
        for(int i=0;i<zbLists.size();i++){
            if(i<zbLists.size()-1){
                zbmcs.append(zbLists.get(i).getZbmc()+",");
            }else{
                zbmcs.append(zbLists.get(i).getZbmc());
            }
        }
        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            systemLogService.saveLog(ipAddr,userId,null,"主题表'"+tableName+"'增加'"+zbmcs+"'指标成功",0);
        } catch (Exception e) {
            return getResult(ipAddr, userId, "主题表'"+tableName+"'"+"增加'"+zbmcs+"'指标失败", e);
        }
        return result;
    }


    @Around("deleteTableAop()")
    public Object deleteTableAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        String tableName = (String)arg;
        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            GenericResponseModel genericResponseModel = (GenericResponseModel)result;
            if("1".equals(genericResponseModel.getData().toString())){
                systemLogService.saveLog(ipAddr,userId,null,"主题表'"+tableName+"'删除失败！请先删除该主题表对应的展示表",0);
            }else {
                systemLogService.saveLog(ipAddr,userId,null,"主题表'"+tableName+"'删除成功",0);
            }
        } catch (Exception e) {
            return getResult(ipAddr, userId, "主题表'"+tableName+"'"+"删除失败", e);
        }
        return result;
    }


    @Around("deleteTableZbAop()")
    public Object deleteTableZbAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        Object arg1 = args[1];
        String tableId = (String)arg;
        String zbmc=(String)arg1;
        String tableName = poolZbNewTableService.getPoolZbNewTable(tableId).getTableName();
        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            systemLogService.saveLog(ipAddr,userId,null,"主题表'"+tableName+"'删除'"+zbmc+"'指标成功",0);
        } catch (Exception e) {
            return getResult(ipAddr, userId, "主题表'"+tableName+"'删除'"+zbmc+"'指标失败", e);
        }
        return result;
    }


    @Around("deleteTableZbListAop()")
    public Object deleteTableZbListAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        Object arg1 = args[1];
        String tableId = (String)arg;
        String param = JsonPath.read((String) arg1, "$.param");
        List<String> zbNameList = JsonPath.read(param, "$.[*].zbmc");
        String tableName = poolZbNewTableService.getPoolZbNewTable(tableId).getTableName();
        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            systemLogService.saveLog(ipAddr,userId,null,"主题表'"+tableName+"'删除'"+zbNameList.toString()+"'指标成功",0);
        } catch (Exception e) {
            return getResult(ipAddr, userId, "主题表'"+tableName+"'删除'"+zbNameList.toString()+"'指标失败", e);
        }
        return result;
    }


    @Around("displayCreateAop()")
    public Object displayCreateAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[1];
        String tableName = (String)arg+"_ZS";
        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            GenericResponseModel genericResponseModel = (GenericResponseModel)result;
            systemLogService.saveLog(ipAddr,userId,null,"展示表'"+tableName+"'，"+genericResponseModel.getErrMsg(),0);
        } catch (Exception e) {
            return getResult(ipAddr, userId, "展示表'"+tableName+"'创建失败", e);
        }
        return result;
    }


    @Around("deleteZsTableAop()")
    public Object deleteZsTableAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        String tableName = (String)arg;
        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            GenericResponseModel genericResponseModel = (GenericResponseModel)result;
            systemLogService.saveLog(ipAddr,userId,null,"展示表'"+tableName+"'删除成功",0);
        } catch (Exception e) {
            return getResult(ipAddr, userId, "展示表'"+tableName+"'删除失败", e);
        }
        return result;
    }


    @Around("addZbToZsTableAop()")
    public Object addZbToZsTableAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        String id = (String)arg;
        String tableName=poolZbNewTableZsService.getPoolZbNewTableZs(id).getTableName();
        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            GenericResponseModel genericResponseModel = (GenericResponseModel)result;
            systemLogService.saveLog(ipAddr,userId,null,"展示表'"+tableName+"'"+genericResponseModel.getErrMsg(),0);
        } catch (Exception e) {
            return getResult(ipAddr, userId, "展示表'" + tableName + "'指标删除失败", e);
        }
        return result;
    }


    @Around("deleteZsTableZbAop()")
    public Object deleteZsTableZbAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        Object arg1 = args[1];
        String tableId = (String)arg;
        String zbid = (String)arg1;
        String tableName=poolZbNewTableZsService.getPoolZbNewTableZs(tableId).getTableName();
        List<PoolZb> zbs = poolZbService.getZbListByIdList(zbid);
        String zbmc=null;
        if(zbs!=null&&zbs.size()>0){
            zbmc=zbs.get(0).getZbmc();
        }
        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            GenericResponseModel genericResponseModel = (GenericResponseModel)result;
            systemLogService.saveLog(ipAddr,userId,null,"展示表'"+tableName+"'删除指标'"+zbmc+"'成功",0);
        } catch (Exception e) {
            return getResult(ipAddr, userId, "展示表'"+tableName+"'删除指标'"+zbmc+"'失败", e);
        }
        return result;
    }


    @Around("deleteZsTableZbList()")
    public Object deleteZsTableZbListAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        Object arg1 = args[1];
        String tableId = (String)arg;
        String param = JsonPath.read((String) arg1, "$.param");
        List<String> zbNameList = JsonPath.read(param, "$.[*].zbmc");
        String tableName = poolZbNewTableZsService.getPoolZbNewTableZs(tableId).getTableName();
        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            GenericResponseModel genericResponseModel = (GenericResponseModel)result;
            systemLogService.saveLog(ipAddr,userId,null,"展示表'"+tableName+"'删除指标'"+zbNameList+"'成功",0);
        } catch (Exception e) {
            return getResult(ipAddr, userId, "展示表'"+tableName+"'删除指标'"+zbNameList+"'失败", e);
        }
        return result;
    }


    @Around("countToTjTableAop()")
    public Object countToTjTableAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userId=null;
        if(userInfo!=null){
            userId=userInfo.getLoginUserId();
        }
        Object[] args = proceedingJoinPoint.getArgs();
        Object arg = args[0];
        Object arg1 = args[1];
        String requestStr = (String)arg;
        String action = (String)arg1;
        if("add".equals(action)){
            action="增加";
        }else if("delete".equals(action)){
            action="删除";
        }
        //获取表名
        String tableName = JsonPath.read(requestStr, "$.tableName")+"_TJ";
        //解析维度
        String dimension = JsonPath.read(requestStr, "$.dimension");
        try {
            result = proceedingJoinPoint.proceed();
            if(StringUtils.isEmpty(userId)){
                return result;
            }
            GenericResponseModel genericResponseModel = (GenericResponseModel)result;
            if((Integer)genericResponseModel.getData()>0){
                systemLogService.saveLog(ipAddr,userId,null,"统计表'"+tableName+"'"+action+dimension+"数据"+genericResponseModel.getData()+"条",0);
            }else if("增加".equals(action)){
                systemLogService.saveLog(ipAddr,userId,null,"统计表'"+tableName+"'"+action+dimension+"数据失败",0);
            }else{
                systemLogService.saveLog(ipAddr,userId,null,"统计表'"+tableName+"'"+action+dimension+"数据"+genericResponseModel.getData()+"条",0);
            }
        } catch (Exception e) {
            return getResult(ipAddr, userId, "统计表'"+tableName+"'"+action+dimension+"数据失败", e);
        }
        return result;
    }


    @Around("managerAop()")
    public Object managerAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String ipAddr = request.getRemoteAddr();
        String token = request.getHeader("Authorization");
        Object result = null;
        SToken userInfo = sTokenService.getUserInfo(token);
        String userName=null;
        if(userInfo!=null){
            userName=userInfo.getLoginUserName();
        }
        try {
            if(StringUtils.isEmpty(userName)){
                result = proceedingJoinPoint.proceed();
                return result;
            }
            SUser user = sUserService.getUserByLoginName(userName,0);
            if(user==null){
                GenericResponseModel genericResponseModel = new GenericResponseModel();
                genericResponseModel.setErrCode(401);
                return genericResponseModel;
            }
            if(user.getType()!=1){
                systemLogService.saveLog(ipAddr,null,userName,"登录名为'"+userName+"'的用户试图进入系统管理",0);
                GenericResponseModel genericResponseModel = new GenericResponseModel();
                genericResponseModel.setErrCode(200);
                genericResponseModel.setErrMsg("权限不足！");
                return genericResponseModel;
            }
            result = proceedingJoinPoint.proceed();
        } catch (Exception e) {
            GenericResponseModel genericResponseModel = new GenericResponseModel();
            genericResponseModel.setErrCode(200);
            genericResponseModel.setErrMsg("当前网络繁忙，请稍后再试！");
            systemLogService.saveLog(ipAddr,null,userName,e.getMessage(),1);
            return genericResponseModel;
        }
        return result;
    }

    /**
     * 设置日志并返回
     * @param ipAddr ip地址
     * @param userId 用户ID
     * @param log 日志
     * @param e 异常
     * @return 返回结果
     */
    private Object getResult(String ipAddr, String userId, String log, Exception e) {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setErrMsg("当前网络繁忙，请稍后再试！");
        systemLogService.saveLog(ipAddr,userId,null, log,0);
        systemLogService.saveLog(ipAddr,userId,null,e.getMessage(),1);
        return genericResponseModel;
    }

}
