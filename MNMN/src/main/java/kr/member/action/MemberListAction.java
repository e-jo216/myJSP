package kr.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.member.dao.MemberDAO;
import kr.member.vo.MemberVO;
import kr.util.PagingUtil;

public class MemberListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {
			return "redirect:/member/loginForm.do";
		}
		
		Integer user_grade = (Integer)session.getAttribute("user_grade");
		boolean check= false;
		
		if(user_grade == 1) {
			check = true;
			
			//�����ڷ� �α����� ���
			String pageNum = request.getParameter("pageNum");
			if(pageNum==null) pageNum = "1";
			
			String keyfield = request.getParameter("keyfield");
			String keyword = request.getParameter("keyword");
			if(keyfield == null) keyfield = "";
			if(keyword == null) keyword = "";
			
			MemberDAO dao = MemberDAO.getInstance();
			int count = dao.getMemberCountByAdmin(keyfield, keyword);
			
			//������ ó��
			//keyfield,keyword,currentPage,count,rowCount,pageCount,url
			PagingUtil page = new PagingUtil(keyfield,keyword,Integer.parseInt(pageNum),count,20,10,"memberList.do");
			
			List<MemberVO> list = null;
			if(count > 0) {
				list = dao.getListMemberByAdmin(page.getStartCount(), page.getEndCount(),keyfield, keyword);
			}
			
			request.setAttribute("count", count);
			request.setAttribute("list", list);
			request.setAttribute("pagingHtml", page.getPagingHtml());
		}
		
		request.setAttribute("check", check);
		
		return "/WEB-INF/views/member/memberList.jsp";
	}

}           





