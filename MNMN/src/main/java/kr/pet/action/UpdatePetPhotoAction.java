package kr.pet.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;

import com.oreilly.servlet.MultipartRequest;

import kr.controller.Action;
import kr.pet.dao.PetDAO;
import kr.util.FileUtil;

public class UpdatePetPhotoAction implements Action {
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String,String> mapAjax = new HashMap<String,String>();
		
		HttpSession session = request.getSession();
		Integer user_num = (Integer)session.getAttribute("user_num");
		if(user_num==null) {//�α��� �ȵ� ���
			mapAjax.put("result", "logout");
		}else {//�α��� �� ���
			//���۵� ���� ����
			PetDAO dao = PetDAO.getInstance();
			
			//���� ���ε�
			MultipartRequest multi = FileUtil.createFile(request);
			dao.updatePetPhoto(multi.getFilesystemName("photo"),user_num);
			
			mapAjax.put("result", "success");
		}
		
		//JSON �����ͷ� ��ȯ
		ObjectMapper mapper = new ObjectMapper();
		String ajaxData = mapper.writeValueAsString(mapAjax);
		
		request.setAttribute("ajaxData", ajaxData);
		
		return "/WEB-INF/views/common/ajax_view.jsp";
	}
}
