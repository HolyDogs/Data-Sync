package com.thinvent.mapper;

import com.thinvent.entity.BasestationCoordination;
import com.thinvent.entity.BasestationUseSchedule;
import com.thinvent.entity.SatelliteEarthStation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yb
 * @version 1.0
 * @date 2020/11/12
 **/
@Mapper
public interface BasestationMapper {

    public List<SatelliteEarthStation> getEarthStations(@Param("bhqdsbh") String bhqdsbh,
                                                        @Param("yhdwmc") String yhdwmc,
                                                        @Param("lxr") String lxr,
                                                        @Param("startDate") String startDate,
                                                        @Param("endDate") String endDate);

    public List<BasestationUseSchedule> getUseSchedules(@Param("jzsyh") String jzsyh,
                                                        @Param("tzszsyr") String tzszsyr,
                                                        @Param("lxr") String lxr,
                                                        @Param("jzlx") String jzlx,
                                                        @Param("jzzt") String jzzt,
                                                        @Param("startDate") String startTime,
                                                        @Param("endDate") String endTime);

    public List<BasestationCoordination> getCoordinations(@Param("bhqdsbh") String bhqdsbh,
                                                          @Param("yhdwmc") String yhdwmc,
                                                          @Param("sfxyxt") String sfxyxt,
                                                          @Param("sfyfqxtqq") String sfyfqxtqq,
                                                          @Param("sfyqrxtqq") String sfyqrxtqq,
                                                          @Param("sfywcxt") String sfywcxt);

}
