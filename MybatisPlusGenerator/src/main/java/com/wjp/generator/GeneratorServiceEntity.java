package com.wjp.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

public class GeneratorServiceEntity {

    @Test
    public void generateCode() {
        boolean serviceNameStartWithI = false;//user -> UserService, 设置成true: user -> IUserService
        GlobalConfig config = new GlobalConfig();
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUrl("jdbc:mysql://127.0.0.1:3306/jtsys?characterEncoding=utf8&useSSL=false&serverTimezone=Hongkong")
                .setUsername("root")
                .setPassword("123456");

        StrategyConfig strategyConfig = new StrategyConfig();
        /* "account","account_role","permission","role","role_permission","role_vuerouter" ,"vuerouter"*/
        /*  "store","activity","activity_buy_product","activity_give_product",
            "combo","combo_product","customer_activity","customer_combo",
            "customer_combo_remainder","customer_member","member","member_product","merchant",
            "product","store","store_product",
            "records_product_transaction","records_member_transaction","records_combo_transaction","records_activity_transaction"*/

        String OutputDir = "G:\\Develop\\临时\\high-rail\\src\\main\\java";//修改成自己的目录
        String packageName = "com.wei.highrail";//要生成到的包，结合下边的输出目录，就是最终的输出目录
        //修改替换成你需要的表名，多个表名传数组
        String[] tablesNamee = new String[]{
                "sys_user_roles","sys_roles","sys_depts","sys_logs","sys_menus","sys_role_menus","sys_users"
        };
        strategyConfig
                .setCapitalMode(true) //全局大写命名
                .setEntityLombokModel(true)
                .setNaming(NamingStrategy.underline_to_camel)//下划线转成驼峰式命名
                .setRestControllerStyle(false)//@RestController
                .setInclude(tablesNamee);
        config.setActiveRecord(false)
                .setAuthor("赵伟")//修改注释上的@Author
                .setOutputDir(OutputDir)
                .setFileOverride(true)
                .setEnableCache(false);//xml关闭二级缓存
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(
                        new PackageConfig()
                                .setParent(packageName)
                                .setController("controller")
                                .setEntity("entity")
                ).execute();
    }

}
