package com.assignment.WebMvc.AppConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class ApplicationInitializer implements WebApplicationInitializer {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        //bootStrap dispatcherServlet
        logger.info("Boot strapping application");
        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(SpringConfig.class);
        registerServlet(servletContext, context);
    }

    private void registerServlet(ServletContext servletContext, AnnotationConfigWebApplicationContext context) {
        logger.info("In method for registering servlet");
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("mvc",
                new DispatcherServlet(context));

        servletRegistration.setLoadOnStartup(1);
        servletRegistration.addMapping("/app/*");
    }
}
