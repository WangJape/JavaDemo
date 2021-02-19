   把插件 用idea工具编译成.class字节码，把编译好的class文件复制粘贴到pom文件的依赖mybatis-generator-core的plugins文件下
   在xml的<context>标签下第一位添加
   <plugin type="org.mybatis.generator.plugins.LombokPlugin"/>