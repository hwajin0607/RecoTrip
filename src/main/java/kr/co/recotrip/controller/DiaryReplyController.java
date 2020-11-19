package kr.co.recotrip.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.co.recotrip.dto.DiaryReplyDTO;
import kr.co.recotrip.service.DiaryReplyService;
import kr.co.recotrip.service.ReviewService;

@Controller
public class DiaryReplyController {
	//내가 한 부분
	
	private static final Logger logger = LoggerFactory.getLogger(DiaryReplyController.class);
	@Autowired DiaryReplyService service;
	
	@RequestMapping(value = "/commentCount", method = RequestMethod.POST)
	public @ResponseBody int commentCount(@RequestParam String tdIdx) {
		logger.info("댓글 갯수 불러올 게시글 번호: "+tdIdx);
		int result = service.commentCount(tdIdx);
		return result;
	}
	
	
	@RequestMapping(value="/replylist", method = RequestMethod.POST)
	public @ResponseBody HashMap<String, Object> list(@RequestParam Map<String, String> params) {
		logger.info("param : "+params);
		String page = params.get("page");
		String pagePerCnt = params.get("ppn");
		String tdIdx = params.get("tdIdx");
		return service.pagingList(Integer.parseInt(page), Integer.parseInt(pagePerCnt),tdIdx);
	}
		
	@RequestMapping(value = "/commentWrite", method = RequestMethod.POST)
	public @ResponseBody int commentWrite(@RequestParam String loginId,@RequestParam String tdIdx,@RequestParam String comment) {
		logger.info("로그인 된 아이디 :  "+loginId+"/댓글 쓸 게시글 번호 : "+tdIdx+"/작성한 내용 : "+comment);
		return service.commentWrite(loginId,tdIdx,comment);
	}
	
	@RequestMapping(value = "/commentUpdate", method = RequestMethod.POST)
	public @ResponseBody int commentUpdate(@RequestParam String reNum,@RequestParam String upContent) {
		logger.info("댓글 번호 : "+reNum+"/ 수정할 내용 : "+upContent);
		return service.commentUpdate(reNum,upContent);
	}
	
	@RequestMapping(value = "/commentDelete", method = RequestMethod.POST)
	public @ResponseBody int commentDelete(@RequestParam String reNum) {
		logger.info("삭제할 댓글 번호 : "+reNum);
		return service.commentDelete(reNum);
	}

	

}
