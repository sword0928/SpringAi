package com.asksword.ai.common;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class CodeGenerator {
    public static void main(String[] args) {
        // 1️⃣ 控制台读取表名
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入表名（多个用逗号分割）：");
        String tables = scanner.nextLine();

        // 去空格 + 拆分
        String[] tableArray = Arrays.stream(tables.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);

        if (tableArray.length == 0) {
            System.err.println("❌ 未输入任何表名，程序结束");
            return;
        }

        // 2️⃣ 数据库配置
        String url = "jdbc:mysql://192.168.31.50:3306/demo?useSSL=false&serverTimezone=Asia/Shanghai";
        String username = "demo";
        String password = "demo123";
        String projectPath = System.getProperty("user.dir");
        // 3️⃣ 代码生成
        FastAutoGenerator.create(url, username, password)

                // 全局配置
                .globalConfig(builder -> {
                    builder.author("asksword")
                            .outputDir(projectPath + "/src/main/java")
                            .disableOpenDir();
                })

                // 包配置（重点：实体目录）
                .packageConfig(builder -> {
                    builder.parent("com.asksword.ai")
                            .entity("biz.model.entity")   // ✅ 你的实体目录
                            .mapper("biz.mappers")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/src/main/resources/mappers"));
                })

                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(tableArray)
                            .entityBuilder()
                            .enableLombok()
                            .enableTableFieldAnnotation()   // ⭐ 字段 @TableField
                            .formatFileName("%sEntity")     // 实体命名
                            .mapperBuilder()
                            .enableBaseResultMap()
                            .enableBaseColumnList()
                            .serviceBuilder().disable()
                            .controllerBuilder().disable();
                })
                .templateConfig(builder -> {
                    builder.entity("templates/entity.java.ftl");
                })
                // 模板引擎
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
