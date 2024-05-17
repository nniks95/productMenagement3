package com.nikola.spring.configuration;

import jakarta.servlet.MultipartConfigElement;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletRegistration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;


public class WebAppInitializer implements WebApplicationInitializer {

    //private static final String TEMP_FOLDER = "C:\\Users\\Niks\\IdeaProjects\\productMenagement3\\src\\main\\resources\\static\\upload\\";
    private static final String TEMP_FOLDER = System.getProperty("java.io.tmpdir");
    private static final int MAX_UPLOAD_SIZE = 5*1024*1024;
    @Override
    public void onStartup(ServletContext container) {

        // Create the dispatcher servlet's Spring application context
        AnnotationConfigWebApplicationContext dispatcherContext =
                new AnnotationConfigWebApplicationContext();
        dispatcherContext.register(SpringMvcConfiguration.class);

        // Register and map the dispatcher servlet
        ServletRegistration.Dynamic dispatcher =
                container.addServlet("dispatcher", new DispatcherServlet(dispatcherContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");
        MultipartConfigElement multipartConfigElement = new MultipartConfigElement(TEMP_FOLDER,MAX_UPLOAD_SIZE,MAX_UPLOAD_SIZE*2L,MAX_UPLOAD_SIZE/2);
        dispatcher.setMultipartConfig(multipartConfigElement);
    }



}

