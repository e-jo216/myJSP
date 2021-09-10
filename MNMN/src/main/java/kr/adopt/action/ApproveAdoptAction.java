package kr.adopt.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.adopt.dao.AdoptDAO;
import kr.controller.Action;

public class ApproveAdoptAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_grade = (Integer)session.getAttribute("user_grade");
		
		if(user_grade == null) {//�α��� ���°� �ƴ� ���
			return "redirect:/member/loginForm.do";
		}
		
		request.setCharacterEncoding("UTF-8");
		
		int adopt_num = Integer.parseInt(request.getParameter("adopt"));
		int pet_num = Integer.parseInt(request.getParameter("pet"));
		
		AdoptDAO dao = AdoptDAO.getInstance();
		boolean check = false;
		
		//����ڰ� ���������� Ȯ��
		if(user_grade == 1) {
			check = dao.approveAdopt(adopt_num, pet_num);
		}
		
		request.setAttribute("check", check);
		
		return "/WEB-INF/views/adopt/approveAdopt.jsp";
	}

}
