package com.thinvent.service.secure;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.thinvent.entity.secure.BasicPropertyOnline;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xff
 * @since 2021-01-25
 */
public interface BasicPropertyOnlineService extends IService<BasicPropertyOnline> {

    /**
     * 根据查询条件查询在线监测数据
     * @param pageNum 页码
     * @param dataType 类型
     * @param dqmc 地区名称
     * @param company 所属企业
     * @param manufacturer 厂商
     * @return 分页信息
     */
    PageInfo<BasicPropertyOnline> getOnlineDataList(int pageNum, String dataType
            , String dqmc
            , String company
            , String manufacturer);


    /**
     * 在线监测数据文件上传
     * @param file
     * @return
     */
    String uploadBasicPorpertyOnline(MultipartFile file);

    /**
     * 在线监测数据文件上传日志
     * @param excelName excel名字
     * @param result    结果
     * @param reason    失败原因
     */
    void saveBasicPropertyRecord(String excelName,String result,String reason);

}
