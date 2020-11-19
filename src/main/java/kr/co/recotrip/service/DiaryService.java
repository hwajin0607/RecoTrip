package kr.co.recotrip.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.co.recotrip.dao.DiaryDAO;
import kr.co.recotrip.dto.DiaryDTO;
import kr.co.recotrip.dto.PagingVO;

@Service
public class DiaryService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired DiaryDAO dao;

	@Value("#{config['Globals.root']}") String root;
	private String fullpath = null;
	
	//내가 한 부분 - 검색 기능
	public ArrayList<DiaryDTO> tdList(PagingVO vo, String search_option,String keyword) {
		logger.info("search_option:"+search_option+"keyword:"+keyword);
		return dao.tdList(vo,search_option,keyword);
	}

	public ModelAndView tdDetail(String idx) {
		
		ModelAndView mav =new ModelAndView();
		
		DiaryDTO list = dao.tdDetail(idx);
		mav.addObject("list",list);
		mav.addObject("idx",idx);
		logger.info("diaryList : {}",list);
		
		return mav;
	}

	@Transactional
	public ModelAndView tdWrite(HashMap<String, String> params, HttpSession session, RedirectAttributes rAttr) {
		ModelAndView mav = new ModelAndView();
		String msg = "글쓰기 실패";
		String page = "redirect:/tdWriteForm";
		DiaryDTO bean = new DiaryDTO();
		
		//1.bbs 테이블에 작성자, 제목, 내용 넣기
		bean.setId(params.get("id"));
		bean.setDiary_subject(params.get("title"));
		bean.setDiary_main(params.get("content0"));
		bean.setDiary_public(params.get("diary_public"));
		bean.setDiary_reg_date(params.get("date"));
		bean.setDiary_content1(params.get("content1"));
		
		if(params.get("content2") != null) {
			bean.setDiary_content2(params.get("content2"));
		}
		if(params.get("content3") != null) {
			bean.setDiary_content3(params.get("content3"));
		}
		if(params.get("content4") != null) {
			bean.setDiary_content4(params.get("content4"));
		}
		if(params.get("content5") != null) {
			bean.setDiary_content5(params.get("content5"));
		}
		
		
		HashMap<String, Object> fileList = (HashMap<String, Object>) session.getAttribute("fileList");
		logger.info("fileList : " + fileList);
		int count = params.size();
		logger.info("혼좀나자 : " +count);
		logger.info("fileList : " +fileList.size());
		
		int suc = 0;
		switch(count) {
		case 6:
			suc = dao.tdWrite(bean);
			logger.info("여기 들어와1?");
			break;
		case 7:
			suc = dao.tdWrite2(bean);
			logger.info("여기 들어와2?");
			break;
		case 8:
			suc = dao.tdWrite3(bean);
			logger.info("여기 들어와3?");
			break;
		case 9:
			suc = dao.tdWrite4(bean);
			logger.info("여기 들어와4?");
			break;
		case 10:
			suc = dao.tdWrite5(bean);
			logger.info("여기 들어와5?");
			break;
		}
		
		
		/*
		if(count == 6) {
			if(dao.tdWrite(bean) == 1) {//글 등록 성공
				success = true;
				logger.info("여기 들어와?");
		}else if(count ==7) {
			bean.setDiary_content1(params.get("content2"));
			if(dao.tdWrite2(bean) == 1) {//글 등록 성공
				success = true;
			}
			//dao.tdWrite(params);
		}else if(count == 8) {
			bean.setDiary_content1(params.get("content3"));
			if(dao.tdWrite3(bean) == 1) {//글 등록 성공
				success = true;
			}
		}else if(count == 9) {
			bean.setDiary_content1(params.get("content4"));
			if(dao.tdWrite4(bean) == 1) {//글 등록 성공
				success = true;
			}
		}else if(count == 10) {
			bean.setDiary_content1(params.get("content5"));
			if(dao.tdWrite5(bean) == 1) {//글 등록 성공
				success = true;
			}
		}
		*/
		if(suc>0) {
			//generateKey를 사용하였기 때문에 쿼리 실행 완료 후 빈(DTO)에 idx값이 생긴다.
			//2.photo 테이블에파일이 소속된 idx, 원본파일명, 새 파일명 넣기
			int size=fileList.size();
			logger.info("저장할 파일 수 : "+size);
			int idx = bean.getDiary_number();
			if(size>0) {//업로드한 파일이 있다면
				//idx, oriFileName, newFileName
				logger.info(idx+"번 게시물에 소속된 파일 등록");
				
				for (String key : fileList.keySet()) {
					//idx, oriFileName, newFileName
					dao.fileWrite(idx, (String)fileList.get(key), key);
				}
			}
			page = "redirect:/tdList";//등록된 상세 페이지로 이동
			msg = "글쓰기 성공";
		}else {
			//세션의 fileList에 저장된 모든 파일 삭제
			for (String fileName : fileList.keySet()) {
				File file = new File(root+"/upload/"+fileName);
				boolean sucdel = file.delete();
				logger.info(fileName+" 삭제 결과 : "+sucdel);
			}
			
		}
		session.removeAttribute("fileList");
		rAttr.addFlashAttribute("msg", msg);
		mav.setViewName(page);
			
		return mav;
		
	}

	public ModelAndView tdFileUpload(MultipartFile file, HttpSession session, String value) {
		
		ModelAndView mav = new ModelAndView();
		
		fullpath = root+"/upload/";
		
		//파일명 추출
		String fileName = file.getOriginalFilename();
		logger.info("파일명?"+fileName);
		
		//새로운 파일명 만들기
		String newFileName = System.currentTimeMillis()+fileName.substring(fileName.lastIndexOf("."));
		logger.info("새파일명?"+newFileName);
		
		//파일명 바꾸기+저장하기(java.nio <- 자바 7부터 적용)
		try {
			byte[] bytes = file.getBytes();//바이너리 추출 
			Path path = Paths.get(fullpath+newFileName);//저장 경로를 지정 
			Files.write(path, bytes);//파일 저장
			//업로드 성공한 파일을 session에 저장			
			HashMap<String, String> fileList = (HashMap<String, String>) session.getAttribute("fileList");
			fileList.put(newFileName, fileName);
			logger.info("업로드한 파일 갯수 : {}",fileList.size());
			session.setAttribute("fileList", fileList);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		mav.addObject("path","photo/"+newFileName);
		mav.addObject("value", value);
		mav.setViewName("tdUploadForm");
		//mav.setViewName("tdWriteForm");
		
		return mav;
	}

	public HashMap<String, Object> tdFileDelete(String fileName, HttpSession session) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int success = 0;
		
		//1. session에서 fileList가져오기
		HashMap<String, String> fileList = (HashMap<String, String>) session.getAttribute("fileList");
		
		//2. 실제 파일 삭제하기
		String delFileName = root+"/upload/"+fileName;
		logger.info("지울 파일 경로 : {}",delFileName);
		File file = new File(delFileName);
		if(file.exists()) {//파일이 존재할 경우
			if(file.delete()) {//삭제 처리 후 성공하면
				success = 1;//1로 변경
			}
		}else {
			logger.info("이미 삭제된 상황");
			success = 1;
		}
		//3. fileList에서 삭제한 파일명 지우기
		if(fileList.get(fileName) != null) {//파일명이 리스트에 존재하면
			fileList.remove(fileName);//지운다.
			logger.info("삭제 후 남은 파일 갯수 : "+fileList.size());
		}
		
		//4. session에 fileList넣기
		session.setAttribute("fileList", fileList);
		
		//5. 성공 여부 변경하기
		
		result.put("success", success);
		
		return result;
	}

	public String tdButton(String idx) {
		
		return dao.tdButton(idx);
	}

	public ModelAndView tdDelete(String idx, RedirectAttributes rAttr) {
		
		ModelAndView mav = new ModelAndView();
		String msg = "삭제 실패";
		String page = "redirect:/tdDetail?diary_number="+idx;
		
		ArrayList<String> delFileNames = dao.getDelFileName(idx);
		for (String fileName : delFileNames) {
			String delFileName = root+"/upload/"+fileName;
			logger.info("지울 파일 경로 : {}",delFileName);
			File file = new File(delFileName);
			if(file.exists()) {//파일이 존재할 경우
				if(file.delete()) {
					dao.tdPDelete(idx);
				}
			}
		}
		
		int result = dao.tdDelete(idx);
		
		if(result>0) {
			msg = "삭제 성공";
			page = "redirect:/tdList";
		}
		rAttr.addFlashAttribute("msg", msg);
		mav.setViewName(page);
		
		return mav;
	}

	@Transactional
	public ModelAndView tdUpdate(HashMap<String, String> params, RedirectAttributes rAttr, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		// 만약 -- 글 수정이 성공했다면 
		// 진행 ->
		// 업로드 파일이 있다면 -> insert
		// 지울 파일이 있다면 -> delete
		// transaction
		// 2개의 if? if 안에 if
		// ㅇ
		//dao.tdUpdate(params,rAttr,session);
		
		String msg = "글쓰기 실패";
		String page = "redirect:/tdWriteForm";

		HashMap<String, Object> fileList = (HashMap<String, Object>) session.getAttribute("fileList");
		logger.info("fileList : " + fileList);
		int count = params.size();
		logger.info("혼좀나자 : " +count);
		logger.info("fileList : " +fileList.size());
		logger.info("params : " + params);
		logger.info("idx : " + params.get("idx"));
		// params.get("idx") 
		//int diary_number = Integer.parseInt(params.get("idx"));
		
		
		
		int suc = 0;
		
		switch(count) {
		case 7:
			suc = dao.tdUpdate(params);
			logger.info("여기 들어와1?");
			break;
		case 8:
			suc = dao.tdUpdate2(params);
			logger.info("여기 들어와2?");
			break;
		case 9:
			suc = dao.tdUpdate3(params);
			logger.info("여기 들어와3?");
			break;
		case 10:
			suc = dao.tdUpdate4(params);
			logger.info("여기 들어와4?");
			break;
		case 11:
			suc = dao.tdUpdate5(params);
			logger.info("여기 들어와5?");
			break;
		}
		
		if(suc>0) {
			//generateKey를 사용하였기 때문에 쿼리 실행 완료 후 빈(DTO)에 idx값이 생긴다.
			//2.photo 테이블에파일이 소속된 idx, 원본파일명, 새 파일명 넣기
			int size=fileList.size();
			logger.info("저장할 파일 수 : "+size);
			if(size>0) {//업로드한 파일이 있다면
				int idx = Integer.parseInt(params.get("idx"));
				for (String key : fileList.keySet()) {
					//idx, oriFileName, newFileName
					dao.fileWrite(idx, (String)fileList.get(key), key);
				}
			}
			page = "redirect:/tdList";//등록된 상세 페이지로 이동
			msg = "글 수정 성공";
		}else {
			//세션의 fileList에 저장된 모든 파일 삭제
			for (String fileName : fileList.keySet()) {
				File file = new File(root+"/upload/"+fileName);
				boolean sucdel = file.delete();
				logger.info(fileName+" 삭제 결과 : "+sucdel);
			}
			
		}
		HashMap<String, Object> delFileList = (HashMap<String, Object>) session.getAttribute("delFileList");
		logger.info("delFileList : "+ delFileList);
		if(delFileList != null) {
			for (String key : delFileList.keySet()) {
				logger.info("삭제할 newf 값 :" + delFileList.get(key));
				dao.delPhoto((String)delFileList.get(key));
			}
			
		}
		session.removeAttribute("fileList");
		session.removeAttribute("delFileList");
		rAttr.addFlashAttribute("msg", msg);
		mav.setViewName(page);
			
		return mav;
		
	}

	public HashMap<String, Object> delFileDelete(String delfileName, HttpSession session) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		int success = 0;
		
		//1. session에서 fileList가져오기
		HashMap<String, String> delfileList = (HashMap<String, String>) session.getAttribute("delFileList");
		
		//2. 실제 파일 삭제하기
		String delPath = root+"/upload/"+delfileName;
		logger.info("수정 지울 파일 경로 : {}",delPath);
		File file = new File(delPath);
		if(file.exists()) {//파일이 존재할 경우
			if(file.delete()) {//삭제 처리 후 성공하면
				success = 1;//1로 변경
			}
			logger.info("파일이 있을 경우 로거");
		}else {
			logger.info("수정 이미 삭제된 상황");
			success = 1;
		}
		logger.info("파일 삭제 후 로거");
		//3. fileList에서 삭제한 파일을 리스트에 넣기
		if(success==1) {
			delfileList.put(delfileName, delfileName);
		}
		
		/*
		if(delfileList.get(delPath) != null) {//파일명이 리스트에 존재하면
			delfileList.remove(delPath);//지운다.
			logger.info("수정 삭제 후 남은 파일 갯수 : "+delfileList.size());
		}
		*/
		//4. session에 fileList넣기
		session.setAttribute("delfileList", delfileList);
		logger.info("delfile : "+delfileList);
		//5. 성공 여부 변경하기
		
		result.put("success", success);
		return result;
	}

	public int countBoard() {
		return dao.countBoard();
	}

}
