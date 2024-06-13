1. 只有漫画的作者能添加漫画参与者
2. 只有漫画作者能创建章节
3. 只有漫画的作者能从漫画参与者中添加用户到章节的参与中
4. 只有漫画的参与者才能被添加到章节的参与者中
5. 只有章节的参与者才能创建页
6. 我们需要统计每一个章节参与者的创作数量
7. 上传者上传的漫画需要漫画作者审核才能通过，只有通过的适合，才会累计到协作数量中

## 业务流程

1. 用户登录
2. 用户创建漫画
3. 用户上传封面
4. 用户创建章节
5. 用户点击章节，创建paper
   1. paper的状态是doing
6. 用户点击具体的paper
7. 绘画
   1. 绘画就算创建一个rawPad，一个paper能映射多个rawPad
8. 上传图片到中转中心
9. 管理员审核中转中心的数据,并根据中转中心的对象创建paper并设置状态为finished
10. 当整个章节绘制完成后，将章节里面的漫画，保存成pdf文件
11. 并把章节状态设置成 Finished 状态，普通用户就可以查看创建完成后的pdf文件了


升级 spring boot3遇到的问题
1. 无法使用
```xml
    <dependencies>
        <dependency>
            <groupId>io.github.openfeign.form</groupId>
            <artifactId>feign-form</artifactId>
            <version>3.8.0</version>
        </dependency>
        <dependency>
            <groupId>io.github.openfeign.form</groupId>
            <artifactId>feign-form-spring</artifactId>
            <version>3.8.0</version>
        </dependency>
    </dependencies>
```
文件服务不在使用  feign 了，直接调用file-service吧
2. 无法使用 老版本的 minio starter
有一个奇怪的bug，在main方法中扫描包
 @ComponentScan({"com.jlefebure.spring.boot.minio.*"})
就无法正常访问接口了
   https://github.com/jlefebure/spring-boot-starter-minio/issues/27#issuecomment-1554531277
解决方法
自己写一个 starter

3. 无法使用mybatis-plus
修改版本
将
<artifactId>mybatis-plus-boot-starter</artifactId>
修改成
<artifactId>mybatis-plus-spring-boot3-starter</artifactId>
执行版本 
```xml
    <properties>    
        <mybatisplus.boot.starter.version>3.5.5</mybatisplus.boot.starter.version>
        <mybatis.boot.starter.version>3.0.0</mybatis.boot.starter.version>
    </properties>
```
```xml
    <dependencies>
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-spring-boot3-starter</artifactId>
            <version>${mybatisplus.boot.starter.version}</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${mybatis.boot.starter.version}</version>
        </dependency>
    </dependencies>
```

4. gateway
spring cloud 高版本 弃用了 Robbon 需要在 gateway 服务中添加

```xml
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-loadbalancer</artifactId>
        </dependency>
```

5. jjwt

高版本的jdk，javax中的一些内容被删除了，所以必须更新jjwt版本
新版本的写法大不相同 ，自定义 hs265 版本的密钥 必须字符的个数达标才行





