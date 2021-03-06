package kr.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;

public class ModifyPasswordAction implements Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		
		if(user_num == null) {//로그인 상태가 아닌 경우
			return "redirect:/member/loginForm.do";
		}
		
		request.setCharacterEncoding("UTF-8");
		
		//사용자 입력 id
		String id = request.getParameter("id");
		//로그인 한 id
		String user_id = (String)session.getAttribute("user_id");
		//현재 비밀번호
		String origin_passwd = request.getParameter("origin_passwd");
		//새 비밀번호
		String passwd = request.getParameter("passwd");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(id);
		boolean check = false;
		
		//사용자 입력한 아이디가 존재 여부, 로그인한 아이디와 일치 여부 확인
		if(member!=null && id.equals(user_id)) {
			//비밀번호 일치 여부 확인
			check = member.isCheckedPassword(origin_passwd);
		}
		
		if(check) {//인증 성공
			//비밀번호 변경
			dao.updatePassword(passwd, user_num);
		}
		
		request.setAttribute("check", check);
		
		return "/WEB-INF/views/member/modifyPassword.jsp";
	}

}
