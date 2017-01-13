package cloud.molddata.parser.cv.controller;

import cloud.molddata.parser.cv.config.AppConfig;
import cloud.molddata.parser.cv.config.RootConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        // Creates the dispatcher servlet context
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.setDisplayName("next hop");
        // Registers the servlet configuraton with the dispatcher servlet context
        ctx.register(AppConfig.class);
        ctx.register(RootConfig.class);
        ctx.register(SpringSecurityInitializer.class);
        servletContext.addListener(new ContextLoaderListener(ctx));
        //servletContext.setInitParameter("defaultHtmlEscape", "true");
        ctx.setServletContext(servletContext);

        // Further configures the servlet context
        ServletRegistration.Dynamic servlet
                = servletContext.addServlet("dispatcherServlet", new DispatcherServlet(ctx));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);
    }
}
