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

# 注解详谈

## 请求类

- @Api:用在请求的类上，表示对类的说明
    - tags="说明该类的作用，可以在UI界面上看到的注解"
    - value="该参数没什么意义，在UI界面上也看到，所以不需要配置"
- @Api其他属性配置

| 注解           | 说明                                    |
| -------------- | --------------------------------------- |
| value          | url的路径值                             |
| tags           | 如果设置这个值、value的值会被覆盖       |
| description    | 对api资源的描述                         |
| basePath       | 基本路径                                |
| position       | 如果配置多个Api 想改变显示的顺序位置    |
| produces       | 如, “application/json, application/xml” |
| consumes       | 如, “application/json, application/xml” |
| protocols      | 协议类型，如: http, https, ws, wss      |
| authorizations | 高级特性认证时配置                      |
| hidden         | 配置为true ，将在文档中隐藏             |

```java

@Api(tags = "接口说明")
@RestController
public class HelloController {
}
```

## 方法和方法参数

- @ApiOperation：用在请求的方法上，说明方法的用途、作用
    - value="说明方法的用途、作用"
    - notes="方法的备注说明"
- @ApiImplicitParams：用在请求的方法上，表示一组参数说明
    - name：参数名
    - value：参数的汉字说明、解释
    - required：参数是否必须传
    - paramType：参数放在哪个地方
        - header --> 请求参数的获取：@RequestHeader
        - query --> 请求参数的获取：@RequestParam
        - path（用于restful接口）--> 请求参数的获取：@PathVariable
        - body（不常用）
        - form（不常用）
    - dataType：参数类型，默认String，其它值dataType="Integer"
    - defaultValue：参数的默认值

```java
@ApiOperation(value = "Hello 测试接口", notes = "访问此接口，返回hello语句，测试接口")
@PostMapping("hello")
public Results<UserVO> hello(@RequestBody UserVO userVO){

        Results<UserVO> results=new Results<>(200,"SUCCESS",userVO);
        return results;
        }
```

```java
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "接口返回成功状态"),
        @ApiResponse(code = 500, message = "接口返回未知错误，请联系开发人员调试")
})
@ApiOperation(value = "获取用户信息", notes = "访问此接口，返回用户信息")
@PostMapping("/getUser/{id}")
public String getUser(@PathVariable String id)throws InterruptedException{
        // 业务...
        return"";
        }
```

```java
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "接口返回成功状态"),
        @ApiResponse(code = 500, message = "接口返回未知错误，请联系开发人员调试")
})
@ApiOperation(value = "Hello 测试接口", notes = "访问此接口，返回hello语句，测试接口")
@PostMapping("hello")
public Results<UserVO> hello(@RequestBody UserVO userVO){
        Results<UserVO> results=new Results<>(200,"SUCCESS",userVO);
        return results;
        }
```

## 方法的响应

- @ApiResponses:响应状态的说明。是个数组，可包含多个 @ApiResponse
    - @ApiResponse：每个参数的说明

```java
@ApiResponses(value = {
        @ApiResponse(code = 200, message = "接口返回成功状态"),
        @ApiResponse(code = 500, message = "接口返回未知错误，请联系开发人员调试")
})
@PostMapping("hello")
public Results<UserVO> hello(@RequestBody UserVO userVO){
        Results<UserVO> results=new Results<>(200,"SUCCESS",userVO);
        return results;
        }
```

## 对象描述

- @ApiModel：经常用于请求的入参对象和 响应返回值对象的描述
    - 入参是对象，即 @RequestBody 时， 用于封装请求（包括数据的各种校验）数据；
    - 返回值是对象，即 @ResponseBody 时，用于返回值对象的描述。
- @ApiModelProperty 用于每个属性上面，说明属生的含义

```java

@Data
@ApiModel(value = "HelloVO", description = "前端請求实体类参数")
public class HelloVO extends BaseClass {

    @ApiModelProperty(value = "Plane", required = true)
    String plant;

    @ApiModelProperty(value = "Hello")
    List<HelloBO> hlist;

}
```

```java

@ApiModel(value = "ResultVo", description = "响应参数说明")
public class ResultVO {

    /**
     * 响应码
     */
    @ApiModelProperty(value = "响应码(0:失败,1:成功)", required = true)
    private Integer code;

    /**
     * 提示信息
     */
    @ApiModelProperty(value = "提示信息", required = true)
    private String message;

    /**
     * 结果数据
     */
    @ApiModelProperty(value = "结果数据", required = true)
    private Object data;

    public Integer getCode() {
        return code;
    }
    ...
}
```

## ALL注解代码示例说明
