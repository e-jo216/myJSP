package kr.adopt.action;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.adopt.dao.AdoptDAO;
import kr.adopt.vo.AdoptVO;
import kr.controller.Action;


public class AdoptRegisterAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		
		request.setCharacterEncoding("utf-8");
		
		//�α��� üũ
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//�α��� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
		
		AdoptDAO dao = AdoptDAO.getInstance();
		
		AdoptVO adoptVO = new AdoptVO();
		adoptVO.setAdopt_member_num(Integer.parseInt(request.getParameter("mem_num")));
		adoptVO.setAdopt_pet_num(Integer.parseInt(request.getParameter("pet_num")));
		adoptVO.setAdopt_intro(request.getParameter("adopt_intro"));
		adoptVO.setAdopt_married(Integer.parseInt(request.getParameter("checkboxVal")));
		adoptVO.setAdopt_house(request.getParameter("adopt_house"));
		
		boolean check = dao.insertAdoptRegister(adoptVO);
		
		request.setAttribute("check", check);
		
		
		return "/WEB-INF/views/adopt/adoptRegister.jsp";
	}

}
