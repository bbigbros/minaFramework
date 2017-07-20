package spms.controls;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlProjectDao;

@Component("/project/delete.do")
public class ProjectDeleteController implements Controller, DataBinding {
	MySqlProjectDao projectDao;

	public ProjectDeleteController setProjectDao(MySqlProjectDao projectDao) {
		this.projectDao = projectDao;
		return this;
	}
	
	public Object[] getDataBinders() {
		return new Object[]{ "no", Integer.class };
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		int no = (Integer)model.get("no");
		projectDao.delete(no);
		
		return "redirect:list.do";
	}
}