package kr.co.recotrip.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kr.co.recotrip.dto.PagingVO;
import kr.co.recotrip.dto.ReviewDTO;

public interface ReviewDAO {
	//내가 한 부분
	
	List<HashMap<String, Object>> reviewlist(PagingVO vo, String dest_num);

	int reviewwrite(String userid,String review, String dest_num);

	int reviewupdate(String reNum, String upcontent);

	int reviewdelete(String reNum);
	
	void ratingDelete(String userid, String dest_num);

	void rating(String userid, String score, String dest_num);
	
	int countBoard(String dest_num);

	int over(String userid,String dest_num);


}
