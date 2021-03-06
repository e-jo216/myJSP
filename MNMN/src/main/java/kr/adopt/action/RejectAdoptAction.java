package kr.adopt.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.adopt.dao.AdoptDAO;
import kr.controller.Action;

public class RejectAdoptAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_grade = (Integer)session.getAttribute("user_grade");
		
		if(user_grade == null) {//로그인 상태가 아닌 경우
			return "redirect:/member/loginForm.do";
		}
		
		request.setCharacterEncoding("UTF-8");
		
		int adopt_num = Integer.parseInt(request.getParameter("adopt"));
		String adopt_why = request.getParameter("reason");
		
		AdoptDAO dao = AdoptDAO.getInstance();
		boolean check = false;
		
		//사용자가 관리자인지 확인
		if(user_grade == 1) {
			check = dao.rejectAdopt(adopt_num, adopt_why);
		}
		
		request.setAttribute("check", check);
		
		return "/WEB-INF/views/adopt/rejectAdopt.jsp";
	}

}
