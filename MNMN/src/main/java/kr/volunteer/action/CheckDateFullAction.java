package kr.volunteer.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.volunteer.dao.VolunteerDAO;

import java.util.logging.Logger;

public class CheckDateFullAction implements Action{
	
	private final static Logger LOG = Logger.getGlobal();

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		
		String date = request.getParameter("date");
		date = date.replace("-", "/");
		date = date.substring(2);
		
		int time = Integer.parseInt(request.getParameter("time"));
		
		VolunteerDAO dao = VolunteerDAO.getInstance();
		int volCount = dao.checkDateFull(date, time);
		
		
		LOG.info("�����ڼ� : " + volCount);
		
		
		
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		if(volCount < 5) {//������ �� ���ʰ�
			mapAjax.put("result", "LessThan");
			LOG.info("������ �� ���ʰ�");
		}else {//������ �� �ʰ�
			mapAjax.put("result", "MoreThan");
			LOG.info("������ �� �ʰ�");
		}
		
		/*
		 * JSON�������� ��ȯ�ϱ⸦ ���ϴ� ���ڿ��� HashMap�� key�� value�� ������ ������ ��
		 * ObjectMapper�� writeValueAsString�� Map ��ü�� �����ؼ� �Ϲ� ���ڿ� �����͸�
		 * JSON������ ���ڿ� �����ͷ� ��ȯ �� ��ȯ
		 */
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		
		
		LOG.info(ajaxData);
		
		
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
