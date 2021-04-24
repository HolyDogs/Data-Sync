package com.thinvent.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thinvent.entity.Notice;
import com.thinvent.mapper.NoticeMapper;
import com.thinvent.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yb
 * @since 2021-01-28
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {

    @Autowired
    @SuppressWarnings("all")
    private NoticeMapper noticeMapper;

    @Override
    public PageInfo getNoticeList(Integer pageNum, HashMap<String, Object> map) throws ParseException {
        PageHelper.startPage(pageNum, 10);
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        wrapper.eq("IS_DELETE",0);
        if(!StringUtils.isEmpty(map.get("noticeType"))){
            wrapper.eq("NOTICE_TYPE",map.get("noticeType"));
        }
        //判断是否为首页，首页不显示未到时间的消息
        if(!StringUtils.isEmpty(map.get("index"))){
            Date nowTime = new Date();
            String now = formatter.format(nowTime);
            wrapper.apply("PUBLISH_TIME <= to_date('" + now + "','YYYY-MM-DD')");
        }
        if(!StringUtils.isEmpty(map.get("publishStatus"))){
            wrapper.eq("PUBLISH_STATUS",map.get("publishStatus"));
        }
        if(!StringUtils.isEmpty(map.get("noticeSubject"))){
            wrapper.like("NOTICE_SUBJECT",map.get("noticeSubject"));
        }
        if(!StringUtils.isEmpty(map.get("startTime"))){
            wrapper.apply("PUBLISH_TIME >= to_date('" + map.get("startTime") + "','YYYY-MM-DD')");
        }
        if(!StringUtils.isEmpty(map.get("endTime"))){
            wrapper.apply("PUBLISH_TIME <= to_date('" + map.get("endTime") + "','YYYY-MM-DD')");
        }
        wrapper.last("ORDER BY SORTED DESC NULLS LAST,PUBLISH_TIME DESC");
        return (new PageInfo(noticeMapper.selectList(wrapper)));
    }

    @Override
    public void deleteNotice(String id) {
        UpdateWrapper<Notice> wrapper = new UpdateWrapper<>();
        wrapper.eq("ID",id);
        wrapper.set("IS_DELETE",1);
        noticeMapper.update(null,wrapper);
    }

    @Override
    public void publishNotice(String id,String userId) {
        UpdateWrapper<Notice> wrapper = new UpdateWrapper<>();
        wrapper.eq("ID",id);
        wrapper.set("PUBLISH_STATUS","YFB");
        wrapper.set("PUBLISH_USER_ID",userId);
        noticeMapper.update(null,wrapper);
    }

    @Override
    public void unpublishNotice(String id) {
        UpdateWrapper<Notice> wrapper = new UpdateWrapper<>();
        wrapper.eq("ID",id);
        wrapper.set("PUBLISH_STATUS","DFB");
        noticeMapper.update(null,wrapper);
    }

    @Override
    public Notice getNoticeInfo(String id, String flag) {
        QueryWrapper<Notice> wrapper = new QueryWrapper<>();
        wrapper.eq("ID",id);
        wrapper.eq("IS_DELETE",0);
        Notice notice = noticeMapper.selectOne(wrapper);
        if("1".equals(flag)){
            notice.setClickCount(notice.getClickCount()+1);
            noticeMapper.updateById(notice);
        }
        return notice;
    }

}
