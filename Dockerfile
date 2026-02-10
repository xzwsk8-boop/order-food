# 第一阶段：构建环境
# 使用官方 Maven 镜像进行编译，别名为 builder
FROM maven:3.9.6-eclipse-temurin-17 AS builder

# 设置构建工作目录
WORKDIR /build

# 1. 单独复制 pom.xml 并下载依赖
# 这样利用 Docker 缓存，如果 pom.xml 没变，就不需要重新下载依赖
COPY pom.xml .
# 下载依赖（go-offline 可以让后续构建更快，但有时不稳定，直接 package 也可以）
# RUN mvn dependency:go-offline

# 2. 复制源代码
COPY src ./src

# 3. 执行打包
# -DskipTests 跳过单元测试，加快构建速度（流水线中通常有单独的测试步骤）
RUN mvn clean package -DskipTests

# 第二阶段：运行环境
# 使用精简版 JRE 镜像，减小最终镜像体积
FROM openjdk:17-jdk-slim

# 设置运行工作目录
WORKDIR /app

# 从 builder 阶段复制构建好的 Jar 包
# 注意：这里会自动找到 target 目录下生成的 jar 包并重命名为 app.jar
COPY --from=builder /build/target/*.jar app.jar

# 暴露端口（微信云托管/常见的云容器服务通常默认 80）
EXPOSE 8080

# 启动命令
# 可以通过环境变量 JAVA_OPTS 传入 JVM 参数
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]
