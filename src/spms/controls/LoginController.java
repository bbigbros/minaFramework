package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

public class LoginController implements Controller {
	MySqlMemberDao memberDao;
	
	public LoginController setMemberDao(MySqlMemberDao memberDao) {
		System.out.println("setMemberDao in LoginController()");
		this.memberDao = memberDao;
		return this;
	}
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if (model.get("loginMember") == null) {
			return "/auth/LogInForm.jsp";
		} else {
			Member loginMember = (Member)model.get("loginMember"); 
			Member member = memberDao.exist(loginMember.getEmail(), loginMember.getPassword());
			if (member != null) {		
				System.out.println("hello member");
				HttpSession session = (HttpSession)model.get("session");
				session.setAttribute("member", member);
				return "redirect:../member/list.do";
			} else {
				return "/auth/LogInFail.jsp";
			}
		}
	}
}
