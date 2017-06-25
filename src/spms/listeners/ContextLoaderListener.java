package spms.listeners;

// 서버에서 제공하는 DataSource 사용하기
import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import spms.controls.LoginController;
import spms.controls.LogoutController;
import spms.controls.MemberAddController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.dao.MySqlMemberDao;

@WebListener
public class ContextLoaderListener implements ServletContextListener {
  @Override
  public void contextInitialized(ServletContextEvent event) {
    try {
    	ServletContext sc = event.getServletContext();
      
        InitialContext initialContext = new InitialContext();
        DataSource ds = (DataSource)initialContext.lookup("java:comp/env/jdbc/java_db");
      
        MySqlMemberDao memberDao = new MySqlMemberDao();
        memberDao.setDataSource(ds);
      
        sc.setAttribute("/auth/login.do",    new LoginController().setMemberDao(memberDao));
        sc.setAttribute("/auth/logout.do",   new LogoutController());
        sc.setAttribute("/member/list.do",   new MemberListController().setMemberDao(memberDao));
        sc.setAttribute("/member/add.do",    new MemberAddController().setMemberDao(memberDao));
        sc.setAttribute("/member/update.do", new MemberUpdateController().setMemberDao(memberDao));
        sc.setAttribute("/member/delete.do", new MemberListController().setMemberDao(memberDao));
        
    } catch(Throwable e) {
    	e.printStackTrace();
    }
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {}
}
