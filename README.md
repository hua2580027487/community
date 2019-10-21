##码农社区

##文档地址
- [注册OAuth文档]{https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/}
- [spring文档]{https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-sql.html}
- [spring boot 整合 mybaties]{http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/}
- [thymeleaf模板]{https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#using-theach}
- [lombok地址]{https://projectlombok.org/setup/maven}
- [mybatis逆向配置]{http://www.mybatis.org/generator/running/running.html}
- [springMVC拦截器]{https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor}
- [MarkDown富文本]{http://editor.md.ipandao.com/}
##项目工具
- 数据库：H2（仅支持一次连接）
-  mvn flyway:migrate
-  mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate(mybatis逆向命令)
-  lombok(网络问题，本地安装)
-  BeanUtils.copyProperties(复制对象工具类)
##项目依赖
- git
- jdk
- maven
- mysql
##部署
- yum update
- yum install git
- mkdir App  cd /App
- git clone https://github.com/hua2580027487/community.git
- yum install maven(会安装jdk)
- mvn -v
- mvn clean compile package
- cp src/main/resources/application.properties src/main/resources/application-production.properties
- mvn package
- java -jar -Dspring.profiles.active=production target/community-0.0.1-SNAPSHOT.jar
- kill -s -9 sid(杀掉进程)
-  ps -aux | grep java
- nohup java -jar -Dspring.profiles.active=production target/community-0.0.1-SNAPSHOT.jar &

##服务器安装mysql
- wget https://dev.mysql.com/get/mysql80-community-release-el7-3.noarch.rpm
- rpm -ivh mysql80-community-release-el7-3.noarch.rpm
- yum makecache
- yum list | grep mysql-com
- yum install mysql-community-server.x86_64（时间比较长）
- systemctl start mysqld(初始化)
- grep 'password' /var/log/mysqld.log（默认密码）
- mysql -uroot -p（输入密码登录）
- ALTER USER 'root'@'localhost' IDENTIFIED BY '新密码';（更换新密码）
- set global validate_password_policy=0;（密码简单请更换密码策略）
- set global validate_password_length=1;
- 云服务器请配置防火墙端口（特别是3306）
- 配置外网访问mysql
- 步骤：配置权限和防火墙
- [配置权限方法]{http://www.luyixian.cn/news_show_72990.aspx}
- mvn clean compile flyway:baseline（当数据库中已经有结构数据时使用migrate报错使用）
- mvn clean compile flyway:repair(报错后修复)
- mvn clean compile flyway:migrate（重新生成）
- ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '123';
##快捷键
- （win）alt + insert : 快速Set、Get
- (win) ctrl + shift + n :快速查找文件
- （win）ctrl + y ：快速删除一行
- （win）shift + enter : 快速切换下一行
- (win) ctrl + shift + F12 : 代码窗口最大化
-  (win) ctrl + p : 方法参数提示
-  (win) ctrl + alt + v : 生成相应对象
-  (win) ctrl + alt + m : 抽方法
-  (win) ctrl + alt + p : 抽参数
-  (win) ctrl + alt + f : 抽变量
-  (win) shift + f6 : 关联修改对象
- （win）ctrl + f6 : 修改变量和类型
-  (win) ctrl + alt + o: 删除无用import
-  (win) alt + f7: 查看依赖
-  (win) ctrl + alt + o: 删除无用import
##编码故障
- 用户名和密码问题（7.23）
- ideamaven依赖lombok还不行，得下载lombok插件
- thymeleaf模板使用
- mybatis驼峰映射默认关闭，修改mybatis.configuration.map-underscore-to-camel-case=true
- 在对象模型中不按照驼峰命名（类中用"_"）导致有个别字段值一直为空,因为数据库中会映射为驼峰命名到数据模型中
- 分页优化
- 拦截器注解拦截了样式，去掉@EnableWebMvc注解
- 拦截器原因（默认不配置资源会自动去加载项目静态资源，如果使用@EnableWebMvc必须配置静态资源,addResourceHandlers(引用里面默认禁用静态资源)）
- 异常（springBoot自动识别error.html,使用枚举自定义异常，拦截不到的异常使用ErrorController拦截）
- 使用枚举定义异常（优雅手段）
- 使用事务@Transactional控制提交问题回复的脏数据
- 使用PriorityQueue实现top n 步骤：（1.实例化PriorityQueue并设置类型和max  2.定义队列规则（DTO必须实现compareto方法）  3.使用list实现排序）