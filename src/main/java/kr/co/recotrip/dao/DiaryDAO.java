package kr.co.recotrip.dao;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.recotrip.dto.DiaryDTO;
import kr.co.recotrip.dto.PagingVO;

public interface DiaryDAO {

	int countBoard();
	
	//내가 한 부분 - 검색 기능
	ArrayList<DiaryDTO> tdList(PagingVO vo, String search_option,String keyword);

	DiaryDTO tdDetail(String idx);

	int tdWrite(DiaryDTO bean);

	int tdWrite2(DiaryDTO bean);

	int tdWrite3(DiaryDTO bean);

	int tdWrite4(DiaryDTO bean);

	int tdWrite5(DiaryDTO bean);

	void fileWrite(int idx, String string, String key);

	String tdButton(String idx);

	int tdPDelete(String idx);
	
	ArrayList<String> getDelFileName(String idx);

	int tdDelete(String idx);

	int tdUpdate(HashMap<String, String> params);

	int tdUpdate2(HashMap<String, String> params);

	int tdUpdate3(HashMap<String, String> params);

	int tdUpdate4(HashMap<String, String> params);

	int tdUpdate5(HashMap<String, String> params);

	void delPhoto(String string);

}
