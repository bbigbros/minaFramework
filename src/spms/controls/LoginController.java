package spms.controls;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.dao.MemberDao;
import spms.vo.Member;

public class LoginController implements Controller {
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if (model.get("loginMember") == null) {
			return "/auth/LogInForm.jsp";
		} else {
			MemberDao memberDao = (MemberDao)model.get("memberDao");
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
