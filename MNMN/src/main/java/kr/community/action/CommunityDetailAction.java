package kr.community.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.community.dao.CommunityDAO;
import kr.community.vo.CommunityVO;
import kr.controller.Action;

public class CommunityDetailAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//�۹�ȣ ��ȯ
		int com_num = Integer.parseInt(request.getParameter("com_num"));
		
		CommunityDAO dao = CommunityDAO.getInstance();
		//��ȸ�� ����
		dao.updateReadcount(com_num);
		
		CommunityVO com = dao.getCommunity(com_num);
		
		com.setCom_title(com.getCom_title());
		com.setCom_content(com.getCom_content());
		
		request.setAttribute("com", com);
		
		return "/WEB-INF/views/community/communityDetail.jsp";
	}

}
