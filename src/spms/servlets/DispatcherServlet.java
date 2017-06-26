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

import spms.bind.DataBinding;
import spms.bind.ServletRequestDataBinder;
import spms.controls.Controller;

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

			if (pageController instanceof DataBinding) {
				System.out.println("pageController : " + servletPath);
				preparedRequestData(request, model, (DataBinding)pageController);
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
	
	private void preparedRequestData(HttpServletRequest request, 
						HashMap<String, Object> model, DataBinding dataBinding) throws Exception {
		System.out.println("preparedRequestData start()");
		Object[] dataBinders = dataBinding.getDataBinders();
		String dataName = null;
		Class<?> dataType = null;
		Object dataObj = null;
		
		System.out.println("preparedRequestData length: " + dataBinders.length);
		for (int i = 0; i < dataBinders.length; i+=2) {
			dataName = (String)dataBinders[i];
			dataType = (Class<?>)dataBinders[i+1];
			dataObj  = ServletRequestDataBinder.bind(request, dataType, dataName);
			System.out.println("dataName: " + dataName + " , " + "dataObj: " + dataObj);
			model.put(dataName, dataObj);
		}
	}
}
