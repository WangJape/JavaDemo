package com.wjp.mybatisgenerator.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.TopLevelClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LombokPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        return true;
    }

    @Override
    public boolean modelBaseRecordClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        topLevelClass.addImportedType("lombok.Data");
        topLevelClass.addAnnotation("@Data");
        //topLevelClass.addImportedType("lombok.Getter");
        //topLevelClass.addImportedType("lombok.Setter");
        //topLevelClass.addImportedType("lombok.ToString");
        //topLevelClass.addAnnotation("@Getter");
        //topLevelClass.addAnnotation("@Setter");
        //topLevelClass.addAnnotation("@ToString");
        topLevelClass.addJavaDocLine("/**");
        topLevelClass.addJavaDocLine(" * " + introspectedTable.getRemarks() + " 实体");
        topLevelClass.addJavaDocLine(" *");
        topLevelClass.addJavaDocLine(" * @author Jape");
        topLevelClass.addJavaDocLine(" * @since " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")));
        topLevelClass.addJavaDocLine(" */");
        return true;
    }

    @Override
    public boolean clientGenerated(Interface interfaze, IntrospectedTable introspectedTable) {
        // Mapper文件的注释
        interfaze.addJavaDocLine("/**");
        interfaze.addJavaDocLine(" * " + introspectedTable.getRemarks() + " Mapper");
        interfaze.addJavaDocLine(" *");
        interfaze.addJavaDocLine(" * @author Jape");
        interfaze.addJavaDocLine(" * @since " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mm")));
        interfaze.addJavaDocLine(" */");
        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, Plugin.ModelClassType modelClassType) {
        // 不生成setter
        return false;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method, TopLevelClass topLevelClass, IntrospectedColumn introspectedColumn, IntrospectedTable introspectedTable, ModelClassType modelClassType) {
        // 不生成getter
        return false;
    }
}
