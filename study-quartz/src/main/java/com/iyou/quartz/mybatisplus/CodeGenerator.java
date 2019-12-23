package com.iyou.quartz.mybatisplus;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * <p>
 * mysql 代码生成器演示例子
 * </p>
 *
 * @author jobob
 * @since 2018-09-12
 */
public class CodeGenerator {
    private static final String projectPath = System.getProperty("user.dir");
    private static final String parentPackage = "com.iyou.quartz";

    private static final String javaSrcPath = "/src/main/java";
    private static final String mapperXmlPath = "/src/main/resources/mapper/";
    private static final String templateMapperXmlPath = "/templates/mapper.xml.ftl";
    // 数据库
    private static final String dbDriveClassName = "com.mysql.cj.jdbc.Driver";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/iyou_springboot_demo";
    private static final String dbUsername = "root";
    private static final String dbPassword = "999999999";

    // 生成代码
    public static void main(String[] args) {
        generatorCode();
    }

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }

        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    private static void generatorCode() {
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig()
                .setOutputDir(projectPath + javaSrcPath)
                .setBaseColumnList(true)
                .setBaseResultMap(true)
                .setIdType(IdType.NONE)
                .setServiceName("%sService")  //去 掉接口上的I
                .setServiceImplName("%sServiceImpl")
                .setAuthor("weiguohua")
                .setSwagger2(true)
                .setOpen(false);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                // .setSchemaName("public")
                .setUrl(dbUrl)
                .setDriverName(dbDriveClassName)
                .setUsername(dbUsername)
                .setPassword(dbPassword);

        // 包配置
        PackageConfig packageConfig = new PackageConfig()
                .setModuleName(getModuleNameFromScanner())
                .setParent(parentPackage);

        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        }.setFileOutConfigList(Collections.singletonList(
                new FileOutConfig(templateMapperXmlPath) {
                    @Override
                    public String outputFile(TableInfo tableInfo) {
                        // 自定义输入文件名称
                        return projectPath
                                + mapperXmlPath
                                // + packageConfig.getModuleName()
                                + "/"
                                + tableInfo.getEntityName()
                                + "Mapper"
                                + StringPool.DOT_XML;
                    }
                })
        );

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntityLombokModel(true)
                .setEntityBuilderModel(true)
                .setTableFillList(
                        Arrays.asList(
                                new TableFill("createTime", FieldFill.INSERT),
                                new TableFill("createDate", FieldFill.INSERT),
                                new TableFill("createDateTime", FieldFill.INSERT),
                                new TableFill("updateTime", FieldFill.INSERT_UPDATE),
                                new TableFill("updateDate", FieldFill.INSERT_UPDATE),
                                new TableFill("updateDateTime", FieldFill.INSERT_UPDATE)
                        )
                )
                .setInclude(getTableNamesFromScanner())
                .setRestControllerStyle(true)
                .setControllerMappingHyphenStyle(true)
                .setTablePrefix("t_"); // packageConfig.getModuleName() + "_"

        // 代码生成器
        AutoGenerator generator = new AutoGenerator()
                .setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .setCfg(injectionConfig)
                .setTemplate(
                        new TemplateConfig().setXml(null)
                )
                .setTemplateEngine(new FreemarkerTemplateEngine());

        generator.execute();
    }

    private static String getModuleNameFromScanner() {
        return null;
        // String module = scanner("模块名");
        // return "null".equals(module) ? null : module;
    }

    private static String[] getTableNamesFromScanner() {
        return scanner("表名").replace(" +", "").split(",");
    }
}