package kr.volunteer.action;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.controller.Action;
import kr.volunteer.dao.VolunteerDAO;
import kr.volunteer.vo.VolunteerVO;

public class VolunteerUserAction implements Action{
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num == null) {//�α��� ���� ���� ���
			return "redirect:/member/loginForm.do";
		}
		
		//�α��� �� ���
		VolunteerVO vol = new VolunteerVO();
		vol.setVol_m_num(user_num);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date d = sdf.parse(request.getParameter("date"));
		
		vol.setVol_date(new Date(d.getTime()));
		vol.setVol_time(Integer.parseInt(request.getParameter("time")));
		VolunteerDAO dao = VolunteerDAO.getInstance(); 
		dao.insertVolunteer(vol);
		
		return "/WEB-INF/views/volunteer/VolunteerUser.jsp";
	}

}
