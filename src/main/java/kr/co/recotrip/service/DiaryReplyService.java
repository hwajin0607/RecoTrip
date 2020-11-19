package kr.co.recotrip.service;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.recotrip.dao.DiaryReplyDAO;
import kr.co.recotrip.dto.DiaryReplyDTO;


@Service
public class DiaryReplyService {
	//내가 한 부분

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired DiaryReplyDAO dao;
	
	public HashMap<String, Object> pagingList(int page, int pagePerCnt, String tdIdx) {
		
		HashMap<String, Object> json = new HashMap<String, Object>();
		
		logger.info("현재 페이지 : {}",page);
		logger.info("페이지당 보여줄 수  : {}",pagePerCnt);		

		int allCnt = dao.commentCount(tdIdx);//해당 게시글 댓글 수
	
		 int range = allCnt%pagePerCnt>0? Math.round(allCnt/pagePerCnt)+1:Math.round(allCnt/pagePerCnt);
		 
		 if(page>range) { page = range; }
		 
		int end = page*pagePerCnt;
		int start = end - pagePerCnt+1;
		
		logger.info("??"+page+"/"+range);
		
		json.put("currPage",page);
		json.put("range", range);
		json.put("list", dao.commentList(tdIdx,start,end));		

		return json;
	}
	
	public int commentWrite(String loginId, String tdIdx, String comment) {
		return dao.commentWrite(loginId,tdIdx,comment);
	}
	public int commentCount(String tdIdx) {
		return dao.commentCount(tdIdx);
	}
	public int commentUpdate(String reNum, String upContent) {
		return dao.commentUpdate(reNum,upContent);
	}
	public int commentDelete(String reNum) {
		return dao.commentDelete(reNum);
	}



	
}
