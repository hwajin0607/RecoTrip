package kr.co.recotrip.dao;

import java.util.List;

import kr.co.recotrip.dto.DiaryReplyDTO;

public interface DiaryReplyDAO {
	//내가 한 부분

	List<DiaryReplyDTO> commentList(String tdIdx,int start,int end);

	int commentWrite(String loginId, String tdIdx, String comment);

	int commentCount(String tdIdx);

	int commentUpdate(String reNum, String upContent);

	int commentDelete(String reNum);


}
