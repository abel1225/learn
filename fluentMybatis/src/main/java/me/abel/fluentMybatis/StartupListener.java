package me.abel.fluentMybatis;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.abel.fluentMybatis.service.HelloService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

@Component
public class StartupListener implements ApplicationContextAware, ServletContextAware, InitializingBean, ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {

    }

    @Override
    public void setServletContext(ServletContext context) {
    }

    @Override
    public void afterPropertiesSet() {

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext applicationContext = event.getApplicationContext();
        HelloService contextBean = applicationContext.getBean(HelloService.class);
        try {
            contextBean.findAll();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}