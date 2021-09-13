package kr.pet.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.controller.Action;
import kr.pet.dao.PetDAO;
import kr.pet.vo.PetVO;
import kr.util.PagingUtil;

public class PetListAction implements Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		//�α��ο� ������� Ȯ�� ����
		
		String pageNum = request.getParameter("pageNum");
		if(pageNum==null)pageNum = "1";
		
		PetDAO dao = PetDAO.getInstance();
		int count = dao.getPetCount();
		
		//������ ó��
		//currentPage, count, rowCount, pageCount, url
		PagingUtil page = new PagingUtil(Integer.parseInt(pageNum),count,12,12,"petList.do");
		
		List<PetVO> list = null;
		if(count > 0) {
			list = dao.getListPet(page.getStartCount(), page.getEndCount());
		}
		
		request.setAttribute("count", count);
		request.setAttribute("list", list);
		request.setAttribute("pagingHtml", page.getPagingHtml());
		
		return "/WEB-INF/views/adopt/petList.jsp";
	}

}
