package com.fankun.pureRipe.config;

import org.springframework.boot.autoconfigure.velocity.VelocityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

@Configuration
public class VelocityConfig {

//http://blog.csdn.net/lileronglilerong/article/details/50615199#comments
    @Bean
// 现在这个class不再是layout的，所以要把对应的 ViewResolver改成对应的，由于没有
// toolboxviewresolver，就用父类 veclotiyviewresoler
    public VelocityViewResolver velocityViewResolver(VelocityProperties properties) {
        VelocityViewResolver viewResolver = new VelocityViewResolver();
        viewResolver.setViewClass(VelocityLayoutToolboxView.class);
        properties.applyToViewResolver(viewResolver);// 设置默认属性，比如前缀和后缀
        return viewResolver;
    }


}
