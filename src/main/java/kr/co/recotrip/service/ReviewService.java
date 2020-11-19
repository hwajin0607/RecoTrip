package kr.co.recotrip.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import kr.co.recotrip.dao.ReviewDAO;
import kr.co.recotrip.dto.PagingVO;
import kr.co.recotrip.dto.ReviewDTO;

@Service
public class ReviewService {
	//내가 한 부분
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired ReviewDAO dao;

	public List<HashMap<String, Object>> reviewlist(PagingVO vo, String dest_num) {
		return dao.reviewlist(vo, dest_num);
	}
	
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public int reviewwrite(String userid,String review,String score, String dest_num) {
		int success=0;
		int result = dao.over(userid,dest_num);
		
		if(result >= 1) {
			success = 0;
		}else {
			success = dao.reviewwrite(userid,review, dest_num);
			dao.rating(userid,score,dest_num);
		}
		logger.info("성공? : "+success);
		return success;
	}
	public int reviewupdate(String reNum, String upcontent) {
		int success = dao.reviewupdate(reNum,upcontent);
		logger.info("수정 성공? :"+success);
		return success;
	}
	@Transactional(isolation = Isolation.READ_COMMITTED)
	public int reviewdelete(String reNum, String userid, String dest_num) {
		dao.ratingDelete(userid,dest_num);
		int success = dao.reviewdelete(reNum);
		return success;
	}

	public int countBoard(String dest_num) {
		return dao.countBoard(dest_num);
	}


}
