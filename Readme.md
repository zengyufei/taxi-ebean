# idea 工具
安装 IntelliJ Lombok plugin 插件
安装 Ebean enhancement for 10.x 插件

## 启用 IntelliJ Lombok plugin 插件
```
打开 File -> Settings -> Build,Execution,Deployment -> Compiler -> Annotation Processors
  选择 Default 或选择项目
    勾选 Enable annotation processing
```


## 启用 Ebean enhancement for 10.x 插件
```
右键项目弹出菜单，在菜单最下方
  勾选 Ebean 10.x Enhancement
  清除项目  mvn clean
```

## 进入到 jar 目录，安装 jar 包，必须

### 安装 jar 到 maven（推荐）
mvn install:install-file -DgroupId=com.zyf -DartifactId=lombok -Dversion=0.0.1 -Dpackaging=jar -Dfile=lombok-0.0.1.jar


### 发布 jar 到 nexus
mvn deploy:deploy-file -DgroupId=com.zyf -DartifactId=lombok -Dversion=0.0.1 -Dpackaging=jar -DrepositoryId=nexus -Dfile=lombok-0.0.1.jar -Durl=http://192.168.150.200:3000/repository/maven-releases/ -DgeneratePom=false


## 测试连接数据库
```
ebean.connect.ConnectTest -Dspring.profiles.active=ebean
```

## 数据库建表
```
ebean.connect.ConnectTest -Dspring.profiles.active=create
```


## 建表之后，执行新增数据 
sql/init.sql