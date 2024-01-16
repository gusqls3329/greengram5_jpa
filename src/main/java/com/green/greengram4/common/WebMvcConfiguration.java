package com.green.greengram4.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Configuration
//@Configuration : 스프링에 설정파일로 등록되며 (WebMvcConfiguration이)빈등록이 되어 설정할때 거기있는 필요할때 메소드를 실행해줌
public class WebMvcConfiguration implements WebMvcConfigurer { //새로고침할때 오류 안생기도록 하는 코드 > 새로고침을 해도 원래있는 주소로 머물수있도록 해줌

    private final String imgFolder;
    public WebMvcConfiguration(@Value("${file.dir}")String imgFolder) {
        this.imgFolder = imgFolder;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       registry.addResourceHandler("/pic/**")
               .addResourceLocations("file:" + imgFolder);
    //static, controller에 연결된url이있는지 확인하지 말고 여기를 살펴봐라

        registry
                .addResourceHandler("/**")
                .addResourceLocations("classpath:/static/**")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);
                        // If we actually hit a file, serve that. This is stuff like .js and .css files.
                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }
                        // Anything else returns the index.
                        return new ClassPathResource("/static/index.html");
                    }
                });
    }
}