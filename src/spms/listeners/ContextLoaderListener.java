 package spms.listeners;

import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import spms.context.ApplicationContext;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			System.out.println("applicationContext: " + applicationContext);
			
			applicationContext = new ApplicationContext();
			String resource = "spms/dao/mybatis-config.xml";
			System.out.println("resource : " + resource);
			InputStream inputStream = Resources.getResourceAsStream(resource);
			System.out.println("inputStream: " + inputStream);
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
			applicationContext.addBean("sqlSessionFactory", sqlSessionFactory);
			
			System.out.println("applicationContext: " + applicationContext);
			
			ServletContext sc = event.getServletContext();
			String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));
			System.out.println("propertiesPath : " + propertiesPath);
			applicationContext.prepareObjectsByProperties(propertiesPath);
			applicationContext.prepareObjectsByAnnotation("");
			applicationContext.injectDependency();
	    } catch(Throwable e) {
	    		e.printStackTrace();
	    }
	}

  	@Override
  	public void contextDestroyed(ServletContextEvent event) {}
}
