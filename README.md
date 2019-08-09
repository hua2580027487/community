##码农社区

##文档地址
- [注册OAuth文档]{https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/}
- [spring文档]{https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-sql.html}
- [spring boot 整合 mybaties]{http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/}
- [thymeleaf模板]{https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html#using-theach}
- [lombok地址]{https://projectlombok.org/setup/maven}
##项目工具
- 数据库：H2（仅支持一次连接）
-  mvn flyway:migrate
-  lombok(网络问题，本地安装)
##快捷键
- （win）alt + insert : 快速Set、Get
- (win) ctrl + shift + n :快速查找文件
- （win）ctrl + y ：快速删除一行
- （win）shift + enter : 快速切换下一行
- (win) ctrl + shift + F12 : 代码窗口最大化
-  (win) ctrl + p : 方法参数提示
-  (win) ctrl + alt + v : 生成相应对象
##开启编码
- 用户名和密码问题（7.23）
- ideamaven依赖lombok还不行，得下载lombok插件
- thymeleaf模板使用
- mybatis驼峰映射默认关闭，修改mybatis.configuration.map-underscore-to-camel-case=true
- 在对象模型中不按照驼峰命名（类中用"_"）导致有个别字段值一直为空,因为数据库中会映射为驼峰命名到数据模型中