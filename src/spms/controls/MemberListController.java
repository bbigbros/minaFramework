package spms.controls;

import java.util.Map;

import spms.dao.MySqlMemberDao;

public class MemberListController implements Controller {
	MySqlMemberDao memberDao;
	
	public MemberListController setMemberDao(MySqlMemberDao memberDao) {
		System.out.println("setMemberDao in MemberListController()");
		this.memberDao = memberDao;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		model.put("members", memberDao.selectList());
		return "/member/MemberList.jsp";
	}
}
