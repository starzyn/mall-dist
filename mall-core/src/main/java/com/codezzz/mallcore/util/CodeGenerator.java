package com.codezzz.mallcore.util;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @since 2020.07.08
 */
public class CodeGenerator {

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.load(new ClasspathResourceLoader().getResourceReader("generator.properties", "UTF-8"));
        String jdbcUrl = Objects.requireNonNull(properties.getProperty("jdbcUrl"));
        String driverName = Objects.requireNonNull(properties.getProperty("driverName"));
        String username = Objects.requireNonNull(properties.getProperty("username"));
        String password = Objects.requireNonNull(properties.getProperty("password"));
        Boolean serviceEnabled = Boolean.valueOf(Objects.requireNonNull(properties.getProperty("serviceEnabled")));
        //前缀
        String servicePrefix = Objects.requireNonNull(properties.getProperty("servicePrefix"));
        //后缀
        String serviceSuffix = Objects.requireNonNull(properties.getProperty("serviceSuffix"));
        String persistentPrefix = Objects.requireNonNull(properties.getProperty("persistentPrefix"));
        String basePackage = Objects.requireNonNull(properties.getProperty("basePackage"));
        String tablePrefix = properties.getProperty("tablePrefix");
        List<String> prefix = new ArrayList<>();
        if (StringUtils.isNotBlank(tablePrefix)) {
            for (String s : tablePrefix.split(",")) {
                prefix.add(s.trim());
            }
        }

        AutoGenerator generator = new AutoGenerator();
        String moduleName = scanner("模块名，例如：user。注意写简名，程序后面会自己拼接前后缀的！");
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + File.separator + servicePrefix + "-" + moduleName + serviceSuffix;
        // ？
        String persistentPath = System.getProperty("user.dir") + File.separator + persistentPrefix;
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor(System.getProperty("user.name"));
        gc.setOpen(false);
        gc.setSwagger2(true); //实体属性 Swagger2 注解
        gc.setServiceName("%sService");
        gc.setMapperName("%sMapper");
        generator.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(jdbcUrl);
        // dsc.setSchemaName("public");
        dsc.setDriverName(driverName);
        dsc.setUsername(username);
        dsc.setPassword(password);
        MySqlQuery mySqlQuery = new MySqlQuery() {
            @Override
            public String[] fieldCustom() {
                return new String[]{"Null"};
            }
        };
        dsc.setDbQuery(mySqlQuery);
        generator.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(moduleName);
        pc.setParent(basePackage);
        pc.setMapper("mapper");

        if (serviceEnabled) {
            pc.setServiceImpl("service");
        }
        generator.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 自定义输出配置，自定义配置会被优先输出
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return persistentPath + "/src/main/resources/mapper/"
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        String javaSource = persistentPath + "/src/main/java/" + basePackage.replace(".", File.separator);
        focList.add(new FileOutConfig("/templates/mapper.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return javaSource + "/mapper/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_JAVA;
            }
        });

        focList.add(new FileOutConfig("/templates/entity.java.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return javaSource + "/model/entity/" + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });


//        focList.add(new FileOutConfig("/templates/service.java.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return javaSource + "/core/service/" + tableInfo.getEntityName() + "Service" + StringPool.DOT_JAVA;
//            }
//        });
//        focList.add(new FileOutConfig("/templates/serviceImpl.java.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return javaSource + "/core/service/impl/" + tableInfo.getEntityName() + "ServiceImpl" + StringPool.DOT_JAVA;
//            }
//        });
//
//        focList.add(new FileOutConfig("/templates/controller.java.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return projectPath + "/src/main/java/" + basePackage.replace(".", File.separator) + "/" + moduleName + "/controller/" + tableInfo.getEntityName() + "Controller" + StringPool.DOT_JAVA;
//            }
//        });

        cfg.setFileOutConfigList(focList);
        generator.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity(null);
        templateConfig.setService(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setController(null);
        templateConfig.setXml(null);
        templateConfig.setMapper(null);
        generator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        strategy.setSuperEntityClass("com.isyscore.common.model.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setLogicDeleteFieldName("deleted");//逻辑删除
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
//        strategy.setSuperEntityColumns("id");
        strategy.setControllerMappingHyphenStyle(true);
        prefix.add(pc.getModuleName() + "_");
        strategy.setTablePrefix(prefix.toArray(new String[0]));
//        strategy.setTableFillList(Arrays.asList(new TableFill("updated_at", FieldFill.INSERT_UPDATE), new TableFill("created_at", FieldFill.INSERT)));
        generator.setStrategy(strategy);
        generator.setTemplateEngine(new VelocityTemplateEngine());
        generator.execute();
    }

    /**
     * 读取控制台内容
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
}