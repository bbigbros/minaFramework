package spms.controls;

import java.util.Map;

import spms.dao.MySqlMemberDao;
import spms.vo.Member;

public class MemberUpdateController implements Controller {
	MySqlMemberDao memberDao;
	
	public MemberUpdateController setMemberDao(MySqlMemberDao memberDao) {
		System.out.println("setMemberDao in MemberUpdateController()");
		this.memberDao = memberDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object[]{ "no", Integer.class, 
							 "member", spms.vo.Member.class };
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if (model.get("member") == null) {
			int no = (Integer)model.get("no");
			model.put("member", memberDao.selectOne(no));
			
			return "/member/MemberUpdateForm.jsp";
		} else {
			Member member = (Member)model.get("member");
			memberDao.update(member);
			
			return "redirect:list.do";
		}
	}

}
