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
		
		if(user_num == null) {//�α��� ���°� �ƴ� ���
			return "redirect:/member/loginForm.do";
		}
		
		request.setCharacterEncoding("UTF-8");
		
		//����� �Է� id
		String id = request.getParameter("id");
		//�α��� �� id
		String user_id = (String)session.getAttribute("user_id");
		//���� ��й�ȣ
		String origin_passwd = request.getParameter("origin_passwd");
		//�� ��й�ȣ
		String passwd = request.getParameter("passwd");
		
		MemberDAO dao = MemberDAO.getInstance();
		MemberVO member = dao.checkMember(id);
		boolean check = false;
		
		//����� �Է��� ���̵� ���� ����, �α����� ���̵�� ��ġ ���� Ȯ��
		if(member!=null && id.equals(user_id)) {
			//��й�ȣ ��ġ ���� Ȯ��
			check = member.isCheckedPassword(origin_passwd);
		}
		
		if(check) {//���� ����
			//��й�ȣ ����
			dao.updatePassword(passwd, user_num);
		}
		
		request.setAttribute("check", check);
		
		return "/WEB-INF/views/member/modifyPassword.jsp";
	}

}
