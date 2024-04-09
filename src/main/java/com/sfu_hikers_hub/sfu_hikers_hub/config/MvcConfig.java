package com.sfu_hikers_hub.sfu_hikers_hub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

  @Value("${app.upload.dir}")
  private String uploadDir;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/uploads/**")
        .addResourceLocations("file:" + uploadDir);
  }
}

// @Configuration
// public class MvcConfig implements WebMvcConfigurer {

// @Override
// public void addResourceHandlers(ResourceHandlerRegistry registry) {
// registry.addResourceHandler("/uploads/**")
// .addResourceLocations("file:uploads/");
// }
// }

// public class MvcConfig {

// }
