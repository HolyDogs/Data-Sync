/*
package com.thinvent;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

public class MPGenerator {



  @Test
        public void testGenerator() {
            //1. 全局配置
            GlobalConfig config = new GlobalConfig();
            config.setActiveRecord(false) // 是否支持AR模式
                    .setAuthor("xff") // 作者a

         .setOutputDir("T:\\newfile\\scwork\\source\\trunk\\工业监测数据迁移及指标管理项目\\gyjc-Proj\\gyjc-web\\src\\main\\java") // 生成路径
                    .setFileOverride(false)  // 文件覆盖
                    //.setIdType(IdType.AUTO) // 主键策略
                    .setServiceName("%sService")  // 设置生成的service接口的名字的首字母是否为I
                    // IEmployeeService  .setBaseResultMap(true)
                    .setBaseColumnList(true);
            //2. 数据源配置
            DataSourceConfig dsConfig  = new DataSourceConfig();
            dsConfig.setDbType(DbType.ORACLE)  // 设置数据库类型
                    .setDriverName("oracle.jdbc.OracleDriver")
                    .setUrl("jdbc:oracle:thin:@192.168.2.16:1521:ORCL")
                    .setUsername("safe")
                    .setPassword("password");
            //3. 策略配置
            StrategyConfig stConfig = new StrategyConfig();
            stConfig.setCapitalMode(true) //全局大写命名
                    .setDbColumnUnderline(true)  // 指定表名 字段名是否使用下划线
                    .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                    .setInclude("NETWORK_KEY_NODES_LOG");
                    //.setTablePrefix("tbl_")
                    //.setInclude("tbl_employee");
            //4. 包名策略配置
            PackageConfig pkConfig = new PackageConfig();
            pkConfig.setParent("com.thinvent")
                    .setMapper("mapper")
                    .setService("service")
                    //.setController("controller")
                    .setEntity("entity")
                    .setXml("config");
            //5. 整合配置
            AutoGenerator ag = new AutoGenerator();
            ag.setGlobalConfig(config)
                    .setDataSource(dsConfig)
                    .setStrategy(stConfig)
                    .setPackageInfo(pkConfig);
            //6. 执行
            ag.execute();
        }




}

*/

