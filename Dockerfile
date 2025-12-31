#FROM openjdk:17-jdk
# 选择基础镜像，包含 JRE 17 用于运行 Java 程序
# 优势：镜像体积小，仅用于运行，不包含开发工具
FROM 101.132.60.25:5000/eclipse-temurin:17-jre

# 设置容器内的工作目录为 /services
# COPY、ENTRYPOINT 等命令都会基于这个目录
WORKDIR /services

# 将本地 target 目录下的 jar 文件拷贝到容器内，并命名为 app.jar
# target/*.jar 匹配当前 Maven 打包后的 Spring Boot jar
COPY target/*.jar springai-service.jar

# 告诉 Docker 这个容器运行时会监听的端口
# 只是声明，不会自动映射，需要 docker-compose 或 docker run 映射
EXPOSE 8080

# 容器启动时执行的命令
# java -jar app.jar 启动 Spring Boot 应用
ENTRYPOINT ["java", "-jar", "springai-service.jar"]

