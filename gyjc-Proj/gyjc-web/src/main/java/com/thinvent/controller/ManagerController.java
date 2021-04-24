package com.thinvent.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thinvent.entity.Notice;
import com.thinvent.entity.SToken;
import com.thinvent.entity.SUser;
import com.thinvent.model.response.GenericResponseModel;
import com.thinvent.security.Md5Utils;
import com.thinvent.service.NoticeService;
import com.thinvent.service.SSystemLogService;
import com.thinvent.service.STokenService;
import com.thinvent.service.SUserService;
import com.jayway.jsonpath.JsonPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping(value = "/gyProj")
public class ManagerController {

    @Autowired
    private SUserService sUserService;

    @Autowired
    private STokenService sTokenService;

    @Autowired
    private SSystemLogService systemLogService;

    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/getUserInfo")
    public GenericResponseModel getUserInfo(@RequestParam(value= "pageNum" ,required = false) Integer pageNum,
                                            @RequestParam(value = "searchName" ,required = false)String name,
                                            @RequestParam(value = "searchLoginName" ,required = false)String loginName,
                                            @RequestParam(value = "searchPhone" ,required = false)String phone){
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(sUserService.getUserInfo(pageNum,name,loginName,phone));
        return genericResponseModel;
    }

    @PostMapping("/deleteUser")
    public GenericResponseModel deleteUser(@RequestBody String requestStr){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization");
        SToken userInfo = sTokenService.getUserInfo(token);
        GenericResponseModel genericResponseModel=new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        String userId=JsonPath.read(requestStr, "$.userId");
        try{
            sUserService.deleteUser(userId,userInfo);
            genericResponseModel.setErrMsg("禁用成功");
        }catch (Exception e){
            genericResponseModel.setErrCode(504);
            genericResponseModel.setErrMsg("禁用失败");
            e.printStackTrace();
        }
        return genericResponseModel;
    }

    @PostMapping("/undeleteUser")
    public GenericResponseModel undeleteUser(@RequestBody String requestStr){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization");
        SToken userInfo = sTokenService.getUserInfo(token);
        GenericResponseModel genericResponseModel=new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        String userId=JsonPath.read(requestStr, "$.userId");
        try{
            sUserService.undeleteUser(userId,userInfo);
            genericResponseModel.setErrMsg("启用成功");
        }catch (Exception e){
            genericResponseModel.setErrCode(504);
            genericResponseModel.setErrMsg("启用失败");
            e.printStackTrace();
        }
        return genericResponseModel;
    }

    @PostMapping("/updateUser")
    public GenericResponseModel updateUser(@RequestBody String requestStr) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization");
        SToken sToken = sTokenService.getUserInfo(token);
        GenericResponseModel genericResponseModel=new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        String userName = JsonPath.read(requestStr, "$.userName");
        String loginName = JsonPath.read(requestStr, "$.loginName");
        Integer userSex=null;
        String userPhone=null;
        String userEmail=null;
        try{
            userSex = JsonPath.read(requestStr, "$.userSex");
        }catch (Exception e){
            System.out.println("性别为空");
        }
        try{
            userPhone = JsonPath.read(requestStr, "$.userPhone");
        }catch (Exception e){
            System.out.println("手机号为空");
        }
        try{
            userEmail = JsonPath.read(requestStr, "$.userEmail");
        }catch (Exception e){
            System.out.println("邮箱为空");
        }
        if("1".equals(JsonPath.read(requestStr, "$.create"))){
            SUser sUser = sUserService.getOne(new QueryWrapper<SUser>().eq("LOGIN_NAME", loginName));
            if (null != sUser) {
                genericResponseModel.setErrCode(201);
                genericResponseModel.setErrMsg("当前账号已被注册");
                return genericResponseModel;
            }
            String password = JsonPath.read(requestStr, "$.password");
            sUser = new SUser();
            sUser.setId(UUID.randomUUID().toString().replace("-", ""));
            sUser.setCreateTime(new Date());
            sUser.setCreateUserId(sToken.getLoginUserId());
            sUser.setCreateUserName(sToken.getLoginUserName());
            sUser.setPassword(Md5Utils.encodePassword(password));
            sUser.setEnablePassword(Md5Utils.base64Encode(password));
            sUser.setLoginName(loginName);
            sUser.setFullName(userName);
            sUser.setEmail(userEmail);
            sUser.setSex(userSex);
            sUser.setPhone(userPhone);
            sUser.setIsDelete(0);
            //普通用户-0
            sUser.setType(0);
            sUserService.save(sUser);
            genericResponseModel.setErrCode(200);
            genericResponseModel.setErrMsg("创建用户成功");
            return genericResponseModel;
        }
        try{
            String userId=JsonPath.read(requestStr, "$.userId");
            sUserService.updateUser(userId,userName,userSex,userPhone,userEmail,sToken);
            genericResponseModel.setErrMsg("更新成功");
        }catch (Exception e){
            genericResponseModel.setErrCode(201);
            genericResponseModel.setErrMsg("更新失败");
            e.printStackTrace();
        }
        return genericResponseModel;
    }

    @RequestMapping("/getLogInfo")
    public GenericResponseModel getLogInfo(@RequestParam(value= "pageNum" ,required = false) Integer pageNum,
                                           @RequestParam(value= "searchLoginName" ,required = false) String searchLoginName,
                                           @RequestParam(value= "searchUserName" ,required = false) String searchUserName,
                                           @RequestParam(value= "startTime" ,required = false) String startTime,
                                           @RequestParam(value= "endTime" ,required = false) String endTime){
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        genericResponseModel.setData(systemLogService.getLogInfo(pageNum,searchLoginName,searchUserName,startTime,endTime));
        return genericResponseModel;
    }

    @RequestMapping("/updatePassword")
    public GenericResponseModel updatePassword(@RequestParam("loginName")String loginName,
                                               @RequestParam("oldPassword")String oldPassword,
                                               @RequestParam("newPassword")String newPassword){
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        SUser user = sUserService.getUserByLoginName(loginName,2);
        try {
            String oldPasswordMd5 = Md5Utils.encodePassword(oldPassword);
            if(!user.getPassword().equals(oldPasswordMd5)){
                genericResponseModel.setErrMsg("初始密码错误");
            }else{
                user.setPassword(Md5Utils.encodePassword(newPassword));
                user.setEnablePassword(Md5Utils.base64Encode(newPassword));
                sUserService.updateById(user);
                genericResponseModel.setErrMsg("更新成功");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return genericResponseModel;
    }

    @GetMapping(value = "/getNoticeLists")
    public GenericResponseModel getSelectList(@RequestParam(value= "pageNum" ,required = false) Integer pageNum,
                                              @RequestParam(value= "title" ,required = false) String title,
                                              @RequestParam(value= "subName" ,required = false) String subName,
                                              @RequestParam(value= "status" ,required = false) String status,
                                              @RequestParam(value= "startTime" ,required = false) String startTime,
                                              @RequestParam(value= "endTime" ,required = false) String endTime) throws ParseException {
        GenericResponseModel genericResponseModel = new GenericResponseModel();
        //设置响应码
        genericResponseModel.setErrCode(200);
        HashMap<String, Object> map = new HashMap<>();
        map.put("noticeType",title);
        map.put("publishStatus",status);
        map.put("noticeSubject",subName);
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        genericResponseModel.setData(noticeService.getNoticeList(pageNum, map));
        return genericResponseModel;
    }

    @PostMapping("/saveNotice")
    public GenericResponseModel saveNotice(@RequestBody Notice notice) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization");
        SToken sToken = sTokenService.getUserInfo(token);
        GenericResponseModel genericResponseModel=new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        notice.setPublishUserId(sToken.getLoginUserId());
        notice.setIsDelete(0);
        if(StringUtils.isEmpty(notice.getId())){
            notice.setCreateTime(new Date());
            notice.setClickCount(Long.valueOf(0));
            notice.setPublishStatus("XZ");
            noticeService.save(notice);
        }else {
            noticeService.updateById(notice);
        }
        return genericResponseModel;
    }

    @GetMapping("/deleteNotice")
    public GenericResponseModel deleteNotice(@RequestParam("id") String id) {
        GenericResponseModel genericResponseModel=new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        noticeService.deleteNotice(id);
        return genericResponseModel;
    }

    /**
     * 编辑页面发布消息
     * @param notice
     * @return
     */
    @PostMapping("/saveAndPublishNotice")
    public GenericResponseModel saveAndPublishNotice(@RequestBody Notice notice) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization");
        SToken sToken = sTokenService.getUserInfo(token);
        GenericResponseModel genericResponseModel=new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        notice.setPublishUserId(sToken.getLoginUserId());
        notice.setPublishStatus("YFB");
        notice.setIsDelete(0);
        if(StringUtils.isEmpty(notice.getId())){
            notice.setCreateTime(new Date());
            notice.setClickCount(Long.valueOf(0));
            noticeService.save(notice);
        }else {
            noticeService.updateById(notice);
        }
        return genericResponseModel;
    }

    /**
     * 发布消息
     * @param id
     * @return
     */
    @GetMapping("/publishNotice")
    public GenericResponseModel publishNotice(@RequestParam("id") String id) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String token = request.getHeader("Authorization");
        SToken sToken = sTokenService.getUserInfo(token);
        GenericResponseModel genericResponseModel=new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        noticeService.publishNotice(id,sToken.getLoginUserId());
        return genericResponseModel;
    }

    /**
     * 取消发布消息
     * @param id
     * @return
     */
    @GetMapping("/unpublishNotice")
    public GenericResponseModel unpublishNotice(@RequestParam("id") String id) {
        GenericResponseModel genericResponseModel=new GenericResponseModel();
        genericResponseModel.setErrCode(200);
        noticeService.unpublishNotice(id);
        return genericResponseModel;
    }

}
