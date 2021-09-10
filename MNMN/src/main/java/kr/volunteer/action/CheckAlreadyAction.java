package kr.volunteer.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.volunteer.dao.VolunteerDAO;

public class CheckAlreadyAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//�α��� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
		
		VolunteerDAO dao = VolunteerDAO.getInstance();
		int Count = dao.checkAlready(user_num);
		
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		if(Count > 0) {//�̹� ���� ��û
			mapAjax.put("result", "Already");
		}else {//���� ��û ��
			mapAjax.put("result", "Yet");
		}
		
		/*
		 * JSON�������� ��ȯ�ϱ⸦ ���ϴ� ���ڿ��� HashMap�� key�� value�� ������ ������ ��
		 * ObjectMapper�� writeValueAsString�� Map ��ü�� �����ؼ� �Ϲ� ���ڿ� �����͸�
		 * JSON������ ���ڿ� �����ͷ� ��ȯ �� ��ȯ
		 */
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
