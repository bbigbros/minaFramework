package spms.controls;

import java.util.Map;

import spms.bind.DataBinding;
import spms.dao.MySqlMemberDao;
import spms.vo.Member;

public class MemberAddController implements Controller, DataBinding {
	MySqlMemberDao memberDao;
	
	public MemberAddController setMemberDao(MySqlMemberDao memberDao) {
		System.out.println("setMemberDao in MemberAddController()");
		this.memberDao = memberDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object[]{ "member", spms.vo.Member.class };
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member)model.get("member");
		if (member.getEmail() == null) {
			System.out.println("Add controller form");
			return "/member/MemberForm.jsp";
		} else {
			System.out.println("Add controller insert");
			memberDao.insert(member);
			return "redirect:list.do";
		}
		
	}
}
