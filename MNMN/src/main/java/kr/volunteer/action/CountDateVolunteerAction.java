package kr.volunteer.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.volunteer.dao.VolunteerDAO;

public class CountDateVolunteerAction implements Action{
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		
		String date = request.getParameter("date");
		
		VolunteerDAO dao = VolunteerDAO.getInstance();
		int volCount[] = dao.countDateVolunteer(date);
		String strcnt = "";
		
		for(int i = 0; i < volCount.length; i++) {
			strcnt += volCount[i];
		}
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		if(strcnt == "")
			mapAjax.put("result", "0000000000");
		else
			mapAjax.put("result", strcnt);
		
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
