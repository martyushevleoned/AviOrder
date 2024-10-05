package org.example.config;

import org.example.model.constant.Page;
import org.example.model.constant.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController(Page.ROOT.getUrl()).setViewName(Page.ROOT.getTemplate());
        registry.addViewController(Page.LOGIN.getUrl()).setViewName(Page.LOGIN.getTemplate());
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler(Resource.STATIC.getAnyParamUrl())
                .addResourceLocations("classpath:" + Resource.STATIC.getUrl() + "/");
    }
}