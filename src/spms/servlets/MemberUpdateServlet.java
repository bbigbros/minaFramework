package spms.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDao;
import spms.vo.Member;

// ServletContext에 보관된 MemberDao 사용하기   
@SuppressWarnings("serial")
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
  @Override
  protected void doGet(
      HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
    	System.out.println("Update doGet()");
	    ServletContext sc = this.getServletContext();
	    MemberDao memberDao = (MemberDao)sc.getAttribute("memberDao");
	    int memberNo = Integer.parseInt((String)request.getAttribute("memberNo"));
   
        request.setAttribute("member", memberDao.selectOne(memberNo));
        request.setAttribute("viewUrl", "/member/MemberUpdateForm.jsp");
	      
    } catch (Exception e) {
    	throw new ServletException(e);
    }
  }

  @Override
  protected void doPost(
      HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    try {
    	System.out.println("Update doPost()");
        ServletContext sc = this.getServletContext();
        MemberDao memberDao = (MemberDao) sc.getAttribute("memberDao");  

        memberDao.update(new Member()
    		  			  .setNo(Integer.parseInt(request.getParameter("no")))
					      .setName(request.getParameter("name"))
					      .setEmail(request.getParameter("email")));
        request.setAttribute("viewUrl", "redirect:list.do");
    } catch (Exception e) {
    	throw new ServletException(e);
    }
  }
}
