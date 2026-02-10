# 使用官方 Java 17 镜像 
FROM openjdk:17-jdk-slim 

# 设置工作目录 
WORKDIR /app 

# 将你的 jar 包考入镜像 
# 注意：这里假设打包后的 jar 名称为 order-food-0.0.1-SNAPSHOT.jar，请根据实际 pom.xml 配置调整
# 正确示范 (如果是多阶段)
COPY order-food.jar /app/app.jar

# 暴露端口（微信云托管默认 80） 
EXPOSE 80 

# 启动命令 
ENTRYPOINT ["java","-jar","app.jar"]