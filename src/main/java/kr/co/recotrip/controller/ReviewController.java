package kr.co.recotrip.controller;



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
import org.springframework.web.servlet.ModelAndView;

import kr.co.recotrip.dto.PagingVO;
import kr.co.recotrip.service.ReviewService;

@Controller
public class ReviewController {
	//내가 한 부분
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	@Autowired ReviewService service;

	@RequestMapping(value = "/reviewlist", method = RequestMethod.GET)
	public ModelAndView reviewlist(PagingVO vo,Model model, @RequestParam(value="nowPage", required=false)String nowPage
			, @RequestParam(value="cntPerPage", required=false)String cntPerPage,@RequestParam String dest_num,HttpSession session) {
		logger.info("댓글 리스트 불러오기");
		ModelAndView mav = new ModelAndView();
		int total = service.countBoard(dest_num);
		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "5";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) { 
			cntPerPage = "5";
		}
		vo = new PagingVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage));
		mav.addObject("paging", vo);
		mav.addObject("info", service.reviewlist(vo,dest_num));
		mav.addObject("dest_num", dest_num);
		mav.setViewName("destReview");
		return mav;
	}
	
	@RequestMapping(value = "/reviewwrite", method = RequestMethod.GET)
	public @ResponseBody int reviewwrite(@RequestParam String userid, @RequestParam String review, @RequestParam String score, @RequestParam String dest_num) {
		logger.info("아이디 : "+userid+",후기 작성 : "+review);
		return service.reviewwrite(userid,review,score,dest_num);
	}
	
	@RequestMapping(value = "/reviewupdate", method = RequestMethod.POST)
	public @ResponseBody int reviewupdate(@RequestParam String reNum, @RequestParam String upcontent) {
		logger.info("댓글 번호 : "+reNum+",수정할 내용 : "+upcontent);
		return service.reviewupdate(reNum,upcontent);
	}
	
	@RequestMapping(value = "/reviewdelete", method = RequestMethod.POST)
	public @ResponseBody int reviewdelete(@RequestParam String reNum,@RequestParam String userid,@RequestParam String dest_num) {
		logger.info("삭제할 댓글 번호 : "+reNum);
		logger.info("삭제할 평점 아이디, 여행지 번호 : {}",userid+" / "+dest_num);
		return service.reviewdelete(reNum,userid,dest_num);
	}
	
}