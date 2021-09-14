package kr.communityReply.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.communityReply.dao.CommunityReplyDAO;
import kr.controller.Action;

public class DeleteReplyAction implements Action{
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		System.out.println("1");	
		int re_num = Integer.parseInt(request.getParameter("re_num"));
		System.out.println("2");
		int writer_num = Integer.parseInt(request.getParameter("mem_num"));
		System.out.println("3");	
		Map<String,String> mapAjax = new HashMap<String,String>();
		System.out.println("4");	
		//�α��� Ȯ��
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//�α��� �ȵ� ���
			mapAjax.put("result", "logout");
		}else if(user_num!=null && user_num == writer_num) {//�α����� �Ǿ��ְ� �α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ�� ��ġ
			CommunityReplyDAO dao = CommunityReplyDAO.getInstance();
			dao.deleteReply(re_num);
					
			mapAjax.put("result", "success");
		}else {//�α����� �Ǿ��ְ� �α����� ȸ����ȣ�� �ۼ��� ȸ����ȣ�� ����ġ
			mapAjax.put("result", "wrongAccess");
				}
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}

}
