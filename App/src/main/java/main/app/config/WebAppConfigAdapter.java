package main.app.config;

import main.app.interceptor.CustomInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Component
@Slf4j
public class WebAppConfigAdapter implements WebMvcConfigurer {

    @Autowired
    CustomInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        log.info("this method will get invoked by container while deployment");
        log.info("value of interceptor is " + interceptor);
        // adding custom interceptor
        interceptorRegistry.addInterceptor(interceptor);
    }
}