# 集成Knife4j接口文档

本工程使用Knife4j的3.0.3版本(Swagger3.0.0版本)

1. maven pom.xml导入依赖

```xml

<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>${knife4j.version}</version>
</dependency>
```

2. 添加Config配置

```java
package com.sorcery.platform.common;

import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * Swagger接口文档生成配置
 *
 * @author jinglv
 * @date 2022/7/18 17:12
 */
@Configuration
@EnableOpenApi
public class Knife4jConfig {

    private final OpenApiExtensionResolver openApiExtensionResolver;

    @Autowired
    public Knife4jConfig(OpenApiExtensionResolver openApiExtensionResolver) {
        this.openApiExtensionResolver = openApiExtensionResolver;
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .groupName("1.0版本")
                .select()
                .paths(PathSelectors.any())
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(RequestHandlerSelectors.basePackage("com.sorcery.platform.controller"))
                .build()
                .extensions(openApiExtensionResolver.buildSettingExtensions());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Sorcery接口工具平台API接口文档")
                .description("Sorcery接口工具平台API接口文档")
                .version("1.0.0")
                .termsOfServiceUrl("http://ip:port")
                .contact(new Contact("", "", ""))
                .build();
    }
}
```

3. 修改配置文件

```yml
knife4j:
  # 是否开启加强模式 true开启  false关闭
  enable: true
  setting:
    # 是否开启调试功能  true开启  false关闭
    enableDebug: true
  basic:
    # 是否开启认证功能  true开启  false关闭
    enable: false
    username: test
    password: 123456
```

4. 配置完成启动项目，访问接口文档：http://ip:port/doc.html

报错信息：‘Failed to start bean ‘documentationPluginsBootstrapper‘； nested exception is java.lang.NullPointe

问题分析：因为Springfox 使用的路径匹配是基于AntPathMatcher的，而Spring Boot 2.6.X使用的是PathPatternMatcher。

修复方法一：降低Spring Boot版本到2.6.x以下版本，例如 SpringBoot 2.5.6 兼容Swagger版本2.9.2

修复方法二：Spring Boot不降级的解决方案（例如：本工程使用的是SpringBoot 2.7.1版本），在application.yml文件中加入以下内容

```yaml
spring:
  mvc:
    pathmatch:
      matching-strategy: ant_path_matcher
```