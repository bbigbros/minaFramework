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
import spms.controls.MemberAddController;
import spms.controls.MemberDeleteController;
import spms.controls.MemberListController;
import spms.controls.MemberUpdateController;
import spms.vo.Member;

@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		String servletPath = request.getServletPath();
		System.out.println("req.getServletPath() Chk: " + servletPath);
		try {
			ServletContext sc = this.getServletContext();
			HashMap<String, Object> model = new HashMap<String, Object>();
			System.out.println("moel list >> " + model.keySet());
			model.put("memberDao", sc.getAttribute("memberDao"));
			
			String pageControllerPath = null;
			Controller pageController = null;
			if ("/member/list.do".equals(servletPath)) {
				pageController = new MemberListController();
			} else if ("/member/add.do".equals(servletPath)) {
				pageController = new MemberAddController();
				if (request.getParameter("email") != null) {
					model.put("member", new Member()
										.setEmail(request.getParameter("email"))
										.setPassword(request.getParameter("password"))
										.setName(request.getParameter("name")));
				}
				
			} else if ("/member/update.do".equals(servletPath)) {
				pageController = new MemberUpdateController();
				if (request.getParameter("email") != null) {
					model.put("member", new Member()
										.setNo(Integer.parseInt(request.getParameter("no")))
										.setName(request.getParameter("name"))
										.setEmail(request.getParameter("email")));
				} else {
					model.put("memberNo", request.getParameter("no"));
				}
				
			} else if ("/member/delete.do".equals(servletPath)) {
				pageController = new MemberDeleteController();
				if (request.getParameter("no") != null) {
					model.put("memberNo", request.getParameter("no"));
				}
			} else if ("/auth/login.do".equals(servletPath)) {
				pageController = new LoginController();
				if (request.getParameter("email") != null) {
					System.out.println("is email");
					model.put("session", request.getSession());
					model.put("loginMember", new Member()
									    .setEmail(request.getParameter("email"))
									    .setPassword(request.getParameter("password")));
				}
				
			} else if ("/auth/logout.do".equals(servletPath)) {
				pageControllerPath = "/auth/logout";
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
