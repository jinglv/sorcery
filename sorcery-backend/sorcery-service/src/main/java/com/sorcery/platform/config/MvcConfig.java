package com.sorcery.platform.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @author jinglv
 * @date 2022/9/16 16:32
 */
@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 图片物理存储路径
     */
    @Value("${SavePath.ProfilePhoto}")
    private String profilePhotoPath;
    /**
     * 图片映射路径
     */
    @Value("${SavePath.ProfilePhotoMapper}")
    private String profilePhotoMapperPath;

    /**
     * 配置静态资源路径
     *
     * @param registry registry
     */
    @Override

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        File fileDir = new File(profilePhotoPath);
        registry.addResourceHandler(profilePhotoMapperPath + "**")
                .addResourceLocations("file:" + fileDir.getAbsolutePath() + "/");
    }
}
