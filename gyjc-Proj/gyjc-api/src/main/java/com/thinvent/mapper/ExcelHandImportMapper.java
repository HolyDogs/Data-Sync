package com.thinvent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.thinvent.entity.ExcelHandImport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xff
 * @since 2020-09-24
 */
@Mapper
public interface ExcelHandImportMapper extends BaseMapper<ExcelHandImport> {

    /**
     * 插入指标数据到主题表
     * @param tableName 表名
     * @param zbmc 指标名称
     * @param zbfl 指标分类
     * @param jldw 计量单位
     * @param zbkey 指标编码
     * @return 新增条数
     */
    int insertIntoZbTableFromHandImport(@Param("tableName") String tableName
            , @Param("zbmc") String zbmc
            , @Param("zbfl") String zbfl
            , @Param("jldw") String jldw
            , @Param("zbkey") String zbkey
            , @Param("startTime") String startTime
            , @Param("endTime") String endTime);
}
