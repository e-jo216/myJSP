package kr.volunteer.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;


import kr.controller.Action;
import kr.volunteer.dao.VolunteerDAO;

public class CheckDateFullAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		
		String date = request.getParameter("vol_date");
		
		VolunteerDAO dao = VolunteerDAO.getInstance();
		int volCount = dao.checkDateFull(date);
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		
		
		
		if(volCount < 5) {//������ �� ���ʰ�
			mapAjax.put("result", "Less than");
		}else {//������ �� �ʰ�
			mapAjax.put("result", "More than");
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
