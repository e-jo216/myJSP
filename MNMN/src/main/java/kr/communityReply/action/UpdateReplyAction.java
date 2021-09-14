package kr.communityReply.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.communityReply.dao.CommunityReplyDAO;
import kr.communityReply.vo.CommunityReplyVO;
import kr.controller.Action;

public class UpdateReplyAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setCharacterEncoding("utf-8");
	
		int writer_num = Integer.parseInt(request.getParameter("mem_num"));
		Map<String,String> mapAjax = new HashMap<String,String>();
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num==null) {//�α����� ���� ���� ���
			mapAjax.put("result", "logout");
		}else if(user_num!=null && user_num == writer_num) {//�α����� �Ǿ��ְ� �α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ ��ġ
			CommunityReplyVO reply = new CommunityReplyVO();
			reply.setRe_num(Integer.parseInt(request.getParameter("re_num")));
			reply.setRe_content(request.getParameter("re_content"));
			
			CommunityReplyDAO dao = CommunityReplyDAO.getInstance();
			dao.updateReply(reply);
			
			mapAjax.put("result", "success");
		}else {//�α����� �Ǿ��ְ� �α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ ����ġ
			mapAjax.put("result", "wrongAccess");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
