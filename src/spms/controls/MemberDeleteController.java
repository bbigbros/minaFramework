package spms.controls;

import java.util.Map;

import spms.dao.MySqlMemberDao;

public class MemberDeleteController implements Controller {
	MySqlMemberDao memberDao;
	
	public MemberDeleteController setMemberDao(MySqlMemberDao memberDao) {
		System.out.println("setMemberDao in MemberDeleteController()");
		this.memberDao = memberDao;
		return this;
	}
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		String no = (String)model.get("memberNo");
		memberDao.delete(Integer.parseInt(no));
		
		return "redirect:list.do";
	}
}
