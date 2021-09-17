package kr.donation.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import kr.controller.Action;
import kr.donation.dao.DonationDAO;

public class DeleteDonationAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//���۵� ������ ���ڵ� ó��
		request.setCharacterEncoding("utf-8");
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_grade = (Integer)session.getAttribute("user_grade");
		String comp = "fail";
		
		Map<String,String> mapAjax = new HashMap<String,String>();
		
		if(user_grade == 1) {
			
			int num = Integer.parseInt(request.getParameter("dnum"));
			
			DonationDAO dao = DonationDAO.getInstance();
			comp = dao.DeleteDonation(num);

		}
		
		if("complete".equals(comp)) mapAjax.put("result", "complete");
		else mapAjax.put("result", "fail");
		
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/donation/listDonation.jsp";
	}

}
