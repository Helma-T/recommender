package cn.hesheng.recommender.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.*;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.ant;


/**
 * swagger auth
 * https://swagger.io/docs/specification/authentication/
 * https://swagger.io/docs/specification/2-0/authentication/api-keys/
 */
@EnableSwagger2
@Configuration
@Slf4j
public class Swagger2Config {

    /**
     * 通过 createRestApi函数来构建一个DocketBean
     * 函数名,可以随意命名,喜欢什么命名就什么命名
     */
    @Bean
    @Profile(value = "test")
    public Docket createRestApi() {
        log.info("测试环境swagger 不需要验证");
        return new Docket(DocumentationType.SWAGGER_2)

                //调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
                .apiInfo(apiInfo())
                .select()
                //控制暴露出去的路径下的实例
                //如果某个接口不想暴露,可以使用以下注解
                //@ApiIgnore 这样,该接口就不会暴露在 swagger2 的页面下
                .apis(RequestHandlerSelectors.basePackage("cn.hesheng"))
                .paths(PathSelectors.any())
                //安全定义
                .build();
    }

    /**
     * TODO Profile 生效了,但是 验证没有生效还是可以直接访问
     * @return
     */
    @Bean
    @Profile(value = "prod")
    public Docket createRestApiSecurity() {
        log.info("生产环境swagger 需要验证");
        return new Docket(DocumentationType.SWAGGER_2)

                //调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
                .apiInfo(apiInfo())
                .select()
                //控制暴露出去的路径下的实例
                //如果某个接口不想暴露,可以使用以下注解
                //@ApiIgnore 这样,该接口就不会暴露在 swagger2 的页面下
                .apis(RequestHandlerSelectors.basePackage("cn.hesheng"))
                .paths(PathSelectors.any())
                //安全定义
                .build()
                .securitySchemes(Arrays.asList(oauth()))
                .securityContexts(Arrays.asList(securityContext()));
    }
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("Spring Boot 测试使用 Swagger2 构建RESTful API")
                //创建人
                .contact(new Contact("orange","demo.com","1191066931@qq.com"))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }

    @Bean
    SecurityContext securityContext() {
        AuthorizationScope readScope = new AuthorizationScope("read:pets", "read your pets");
        AuthorizationScope[] scopes = new AuthorizationScope[1];
        scopes[0] = readScope;
        SecurityReference securityReference = SecurityReference.builder()
                .reference("petstore_auth")
                .scopes(scopes)
                .build();

        return SecurityContext.builder()
                .securityReferences(Arrays.asList(securityReference))
                .forPaths(ant("/api/pet.*"))
                .build();
    }

    @Bean
    SecurityScheme oauth() {
        return new OAuthBuilder()
                .name("petstore_auth")
                .grantTypes(grantTypes())
                .scopes(scopes())
                .build();
    }

    @Bean
    SecurityScheme apiKey() {
        return new ApiKey("api_key", "api_key", "header");
    }

    List<AuthorizationScope> scopes() {
        List<AuthorizationScope> list = new ArrayList<>(2);
        list.add(new AuthorizationScope("write:pets", "modify pets in your account"));
        list.add(new AuthorizationScope("read:pets", "read your pets"));
        return list;
    }

    /**
     * TODO loginEndpoint是认证的地址
     * @return
     */
    List<GrantType> grantTypes() {
        GrantType grantType = new ImplicitGrantBuilder()
                .loginEndpoint(new LoginEndpoint("http://petstore.swagger.io/api/oauth/dialog"))
                .build();
        return Arrays.asList(grantType);
    }

    @Bean
    public SecurityConfiguration securityInfo() {
        return new SecurityConfiguration("abc", "123", "pets", "petstore", "123", ApiKeyVehicle.HEADER, "", ",");
    }
}
