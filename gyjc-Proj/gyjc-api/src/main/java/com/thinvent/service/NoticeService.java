package com.thinvent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.thinvent.entity.Notice;

import java.text.ParseException;
import java.util.HashMap;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yb
 * @since 2021-01-28
 */
public interface NoticeService extends IService<Notice> {

    /**
     * 获取公告列表
     * @param pageNum   页数
     * @param map   传值
     * @return
     */
    public PageInfo getNoticeList(Integer pageNum,HashMap<String, Object> map) throws ParseException;

    /**
     * 删除消息
     * @param id
     */
    public void deleteNotice(String id);

    /**
     * 发布消息
     * @param id
     * @param userId 发布人ID
     */
    public void publishNotice(String id,String userId);

    /**
     * 取消发布
     * @param id
     */
    public void unpublishNotice(String id);

    /**
     * 获取消息详情
     * @param id
     * @param flag clickCOunt是否+1
     */
    public Notice getNoticeInfo(String id,String flag);

}
