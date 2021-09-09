package kr.volunteer.action;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.volunteer.dao.VolunteerDAO;

public class CheckDateRequestAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		
		String date = request.getParameter("date");
		date = date.replace("-", "/");
		date = date.substring(2);
		
		int time = Integer.parseInt(request.getParameter("time"));
		
		VolunteerDAO dao = VolunteerDAO.getInstance();
		int Requested = dao.checkDateRequest(date, time);
		
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		if(Requested > 0) {//�ش� ��¥�� ��û�� ���� ����
			mapAjax.put("result", "Requested");
		}else {//�ش� ��¥�� ��û�� ���� ����
			mapAjax.put("result", "NotRequested");
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
