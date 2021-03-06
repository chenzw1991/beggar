package com.chenzhiwu.beggar.component.fileUpload.config;

import com.chenzhiwu.beggar.common.utils.SpringContextUtil;
import com.chenzhiwu.beggar.component.fileUpload.FileUpload;
import com.chenzhiwu.beggar.component.fileUpload.config.properties.UploadProjectProperties;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 小懒虫
 * @date 2018/11/3
 */
@Configuration
public class UploadFileConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        UploadProjectProperties properties = SpringContextUtil.getBean(UploadProjectProperties.class);
        registry.addResourceHandler(properties.getStaticPath()).addResourceLocations("file:" + FileUpload.getUploadPath());
    }
}
