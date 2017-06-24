package spms.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.controls.Controller;
import spms.controls.LoginController;
import spms.controls.LogoutController;
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.vo.Member;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// 프론트 컨트롤러 각 요청에 따라 페이지 컨트롤러를 불러오는 기능을 담당한다.
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath();
		try {
			ServletContext sc = this.getServletContext();
			HashMap<String, Object> model = new HashMap<String, Object>();
			model.put("session", request.getSession());
	
			Controller pageController = (Controller)sc.getAttribute(servletPath);
			
			if ("/member/add.do".equals(servletPath)) {
				if (request.getParameter("email") != null) {
					model.put("member", new Member()
										.setEmail(request.getParameter("email"))
										.setPassword(request.getParameter("password"))
										.setName(request.getParameter("name")));
				}
				
			} else if ("/member/update.do".equals(servletPath)) {
				if (request.getParameter("email") != null) {
					model.put("member", new Member()
										.setNo(Integer.parseInt(request.getParameter("no")))
										.setName(request.getParameter("name"))
										.setEmail(request.getParameter("email")));
				} else {
					model.put("memberNo", request.getParameter("no"));
				}
			} else if ("/member/delete.do".equals(servletPath)) {
				if (request.getParameter("no") != null) {
					model.put("memberNo", request.getParameter("no"));
				}
			} else if ("/auth/login.do".equals(servletPath)) {
				if (request.getParameter("email") != null) {
					model.put("loginMember", new Member()
									    .setEmail(request.getParameter("email"))
									    .setPassword(request.getParameter("password")));
				}
			} else if ("/auth/logout.do".equals(servletPath)) {
				model.put("session", request.getSession());		
			}
		
			String viewUrl = pageController.execute(model);
		
			for (String key : model.keySet()) {
				System.out.println("key: " + key);
				request.setAttribute(key, model.get(key));
			}
			
			if(viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			} else {
				RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
}
