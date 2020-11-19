package kr.co.recotrip.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.recotrip.dto.DiaryDTO;
import kr.co.recotrip.dto.PagingVO;
import kr.co.recotrip.service.DiaryService;


@Controller
public class DiaryController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired DiaryService service;

	//내가 한 부분 - 검색 기능
	@RequestMapping(value = "/tdList", method = RequestMethod.GET)
	public ModelAndView tdList(@RequestParam(defaultValue = "all") String search_option, @RequestParam(defaultValue = "") String keyword, Model model,PagingVO vo ,@RequestParam(value="nowPage", required=false)String nowPage
			, @RequestParam(value="cntPerPage", required=false)String cntPerPage) {
		ModelAndView mav = new ModelAndView();
		
		int total = service.countBoard();
		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "10";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) { 
			cntPerPage = "10";
		}
		vo = new PagingVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage));
		ArrayList<DiaryDTO> diaryList = service.tdList(vo, search_option, keyword);
		
		
		mav.addObject("paging", vo);
		mav.addObject("diaryList", diaryList);
		mav.setViewName("tdList");

		return mav;
	}

	//글쓰기 폼으로 보내기 
	@RequestMapping(value = "/tdWriteForm", method = RequestMethod.GET)
	public ModelAndView tdWriteForm(Model model, RedirectAttributes rAttr, HttpSession session) {
		logger.info("글쓰기 폼");
		ModelAndView mav = new ModelAndView();
		String msg = "";
		String page = "";
		String id = (String)session.getAttribute("loginId");
		logger.info("id : " + id);
		if(session.getAttribute("loginId")==null) {
			msg = "로그인이 필요한 서비스입니다.";
			page = "redirect:/tdList";
			
		}else {
			page = "tdWriteForm";
		}
		HashMap<String, String> fileList = new HashMap<String, String>();
		session.setAttribute("fileList", fileList);
		rAttr.addFlashAttribute("msg", msg);
		mav.addObject("id", id);
		mav.setViewName(page);
		return mav;
	}

	@RequestMapping(value = "/tdWrite", method = RequestMethod.POST)
	public ModelAndView tdWrite(HttpSession session, @RequestParam HashMap<String,String> params, RedirectAttributes rAttr) {
		logger.info("글쓰기" + params.size());
		logger.info("params: {}",params);

		return service.tdWrite(params,session, rAttr);
	}
	
	@RequestMapping(value = "/tdUploadForm", method = RequestMethod.GET)
	public ModelAndView tdUploadForm(@RequestParam String value) {
		logger.info("value : " + value);
		ModelAndView mav = new ModelAndView();
		mav.addObject("value", value);
		mav.setViewName("tdUploadForm");
		return mav;
	}
	
	@RequestMapping(value = "/tdFileUpload", method = RequestMethod.POST)
	public ModelAndView tdFileUpload(MultipartFile file, HttpSession session,@RequestParam String value) {
		logger.info("upload 요청");
		logger.info("value 들어오나? {}",value);
		logger.info("session : " + session);
		session.getAttribute("fileList");
		return service.tdFileUpload(file,session,value);
	}
	
	//파일 삭제
	@RequestMapping(value = "/tdFileDelete", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, Object> tdFileDelete(@RequestParam String fileName, HttpSession session) {
		logger.info("fileDelete 요청 : "+fileName);
		return service.tdFileDelete(fileName, session);
		
	}
	
	//del파일 삭제
	@RequestMapping(value = "/delFileDelete", method = RequestMethod.GET)
	public @ResponseBody HashMap<String, Object> delFileDelete(@RequestParam String delfileName, HttpSession session) {
		logger.info("delfileDelete 요청 : "+delfileName);

		return service.delFileDelete(delfileName, session);
		
	}
	
	
	@RequestMapping(value = "/tdDetail", method = RequestMethod.GET)
	public ModelAndView tdDetail(Model model, @RequestParam String idx,HttpSession session) {
		logger.info("디테일요청");
		logger.info("idx: {}",idx);
		//HashMap<String, Object> delFileName = session.setAttribute("delFileName", delFileName);
		
		ModelAndView mav = service.tdDetail(idx);
		mav.addObject("tdIdx",idx);
		mav.setViewName("tdDetail");
		
		return mav;
	}
	
	@RequestMapping(value = "/tdButton", method = RequestMethod.GET)
	public @ResponseBody int tdButton(@RequestParam String idx, HttpSession session) {
		logger.info("일기 삭제 버튼 확인");
		logger.info("삭제할 일기 idx: {}",idx);
		String loginId = (String) session.getAttribute("loginId");
		logger.info("로그인 된 id : {}", loginId);
		String id = service.tdButton(idx);
		logger.info("글쓴이 id : {}", id);
		
		int result = 0;
		
		if(id.equals(loginId) || loginId.equals("admin")) {
			result = 1;
		}
		
		return result;
	}
	
	@RequestMapping(value = "/tdDelete", method = RequestMethod.GET)
	public ModelAndView tdDelete(Model model, @RequestParam String idx, HttpSession session, RedirectAttributes rAttr) {
		logger.info("일기 삭제 요청");
		logger.info("삭제할 일기 idx: {}",idx);
		
		
	
		return service.tdDelete(idx, rAttr);
	}
	
	@RequestMapping(value = "/tdUpdateForm", method = RequestMethod.GET)
	public ModelAndView tdUpdateForm(@RequestParam String idx, HttpSession session, RedirectAttributes rAttr) {
		logger.info("업데이트폼 이동 : "+idx);
		
		HashMap<String, String> fileList = new HashMap<String, String>();
		session.setAttribute("fileList",fileList);
		// 위 2개의 줄은 사진 업로드
		
		HashMap<String, String> delFileList = new HashMap<String, String>();
		session.setAttribute("delFileList", delFileList);
		// 위 2개의 줄은 사진 지울 
		
		ModelAndView mav = service.tdDetail(idx);
		
		mav.setViewName("tdUpdateForm");
		return mav;
		//return service.tdUpdate(idx, rAttr, session);
	}
	
	
	
	@RequestMapping(value = "/tdUpdate", method = RequestMethod.GET)
	public ModelAndView tdUpdate(HttpSession session, RedirectAttributes rAttr, @RequestParam HashMap<String, String> params) {
		logger.info("업데이트할 idx : " +params.get("idx"));
		
		ModelAndView mav = service.tdUpdate(params, rAttr, session);
				
		return mav;
		//return service.tdUpdate(idx, rAttr, session);
	}
	
	
}
