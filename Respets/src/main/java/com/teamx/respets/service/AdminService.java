package com.teamx.respets.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.teamx.respets.bean.AdminBoard;
import com.teamx.respets.dao.AdminDao;
import com.teamx.respets.userClass.Paging;

@Component
public class AdminService {
	ModelAndView mav;
	@Autowired
	private AdminDao aDao;	
	
	//관리자 로그인
	public ModelAndView adminLogin(String adm_no, String adm_pw, HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		HashMap<String, Object> hmap = new HashMap<>();
		hmap.put("adm_no", adm_no);
		hmap.put("adm_pw", adm_pw);
		hmap = aDao.adminLogin(hmap);
		if (hmap != null) {
			String no = (String) hmap.get("ADM_NO");
			request.getSession().setAttribute("no", no);
			view = "redirect:/unconfirmBusiness";
		} else {
			view = "adminLoginForm";
		}
		mav.setViewName(view);
		return mav;
	} // adminLogin End
	
	// 미인증 기업 목록
	public ModelAndView unconfirmBusiness(HttpSession session) {
		mav = new ModelAndView();
		String view = null;
		StringBuilder sb = new StringBuilder();
		ArrayList<HashMap<String, Object>> bList = new ArrayList<>();
		bList = aDao.getBusinessList();
		for (int i = 0; i < bList.size(); i++) {
			sb.append("<tr><td><a href='./chkLicense?bus_no=" + bList.get(i).get("BUS_NO") + "&bct_code="
					+ bList.get(i).get("BCT_CODE") + "'>" + bList.get(i).get("BUS_NO") + "</a></td>");
			sb.append("<td>" + bList.get(i).get("BCT_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("BUS_EMAIL") + "</td>");
			sb.append("<td>" + bList.get(i).get("BUS_NAME") + "</td>");
			sb.append("<td>" + bList.get(i).get("BUS_TIME") + "</td>");

			mav.addObject("bList", sb);
		}
		view = "unconfirmBusiness";
		mav.setViewName(view);
		return mav;
	} // unconfirmBusiness end

	// 미인증기업정보 확인
	public ModelAndView chkLicense(HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		String bus_no = request.getParameter("bus_no");
		String bct_code = request.getParameter("bct_code");
		HashMap<String, Object> bMap = new HashMap<>();
		bMap.put("bus_no", bus_no);
		bMap.put("bct_code", bct_code);
		if (bus_no != null && bct_code != null) {
			bMap = aDao.getBusinessInfo(bMap);
			System.out.println(bMap);
			String bct_name = bMap.get("BCT_NAME").toString();
			String bus_name = bMap.get("BUS_NAME").toString();
			String bus_ceo = bMap.get("BUS_CEO").toString();
			String bus_phone = bMap.get("BUS_PHONE").toString();
			String bus_email = bMap.get("BUS_EMAIL").toString();
			String bus_addr = bMap.get("BUS_ADDR").toString();
			String bus_addr2 = bMap.get("BUS_ADDR2").toString();
			String bus_lcno = bMap.get("BUS_LCNO").toString();
			String glr_loc = bMap.get("GLR_LOC").toString();
			String glr_file = bMap.get("GLR_FILE").toString();

			mav.addObject("bus_no", bus_no);
			mav.addObject("bct_name", bct_name);
			mav.addObject("bus_name", bus_name);
			mav.addObject("bus_phone", bus_phone);
			mav.addObject("bus_email", bus_email);
			mav.addObject("glr_loc", glr_loc);
			mav.addObject("glr_file", glr_file);
			mav.addObject("bus_ceo", bus_ceo);
			mav.addObject("bus_addr", bus_addr);
			mav.addObject("bus_addr2", bus_addr2);
			mav.addObject("bus_lcno", bus_lcno);
		}
		view = "chkLicense";
		mav.setViewName(view);
		return mav;
	} // chkLicense end

	// 미인증 기업 인증
	public ModelAndView confirmLicense(HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;
		String bus_no = request.getQueryString();
		if (bus_no != null) {
			int result = aDao.confirmLicense(bus_no);
			String alert = "alert('승인이 완료되었습니다.')";
			mav.addObject("alert", alert);
			view = "redirect:unconfirmBusiness";
		} else {
			view = "chkLicense";
		}
		mav.setViewName(view);
		return mav;
	} // confirmLicense end

	/* 게시글 등록 */
	public ModelAndView noticeInsert(HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;//이동할 JSP를 담는 변수
		AdminBoard abo = new AdminBoard();
		//request에 담겨있는 매개변수들을 Bean(abo)에 담는다
		abo.setAbc_no(request.getParameter("abc_no"));
		abo.setAbo_title(request.getParameter("abo_title"));
		abo.setAbo_ctt(request.getParameter("abo_ctt"));
		boolean insertResult = false;//insert결과를 확인할 변수
		//DB에 insert하고 그 결과를 변수 insertResult에 대입
		insertResult = aDao.boardInsert(abo);
		if (insertResult) {//결과값이 true이면
			view = "redirect:noticeList";
		} else {//결과값이 false면
			//ModelAndView에 script태그를 추가하여 안내창을 띄운다		
			mav.addObject("Fail", "<script>alert('게시글 등록에 실패했습니다.')</script>");
			view = "noticeWriteForm";
		}
		mav.setViewName(view);//view변수에 담긴 JSP로 이동
		return mav;
	}

	/* 게시글 내용 */
	public ModelAndView noticeDetail(String abo_no) {
		mav = new ModelAndView();
		AdminBoard abo = new AdminBoard();
		//DB에서 select한 데이터를 Bean(abo)에 대입
		abo = aDao.getBoardDetail(abo_no);
		if(abo!=null) {//데이터값이 존재하면
			//ModelAndView에 데이터값을 추가한다 (JSP에서 EL로 출력)
			mav.addObject("abo", abo);
		} else {//데이터값이 존재하지 않으면
			//ModelAndView에 script태그를 추가하여 안내창을 띄운다		
			mav.addObject("Fail", "<script>alert('게시글 불러오기에 실패했습니다.')</script>");			
		}
		mav.setViewName("noticeDetail");//noticeDetail.jsp로 이동
		return mav;
	}
	
	/* 게시글 삭제 */
	public ModelAndView noticeDelete(String abo_no) {
		mav = new ModelAndView();
		String view = null;//이동할 JSP를 담는 변수
		boolean deleteResult = false;//delete결과를 확인할 변수
		//DB에 delete하고 그 결과를 변수 deleteResult에 대입
		deleteResult = aDao.boardDelete(abo_no);
		if(deleteResult) {//결과값이 true면
			view = "redirect:noticeList";
		} else {//결과값이 false면
			//ModelAndView에 script태그를 추가하여 안내창을 띄운다
			mav.addObject("Fail", "<script>alert('게시글 삭제에 실패했습니다.')</script>");
			view = "noticeList";
		}
		mav.setViewName(view);//view변수에 담긴 JSP로 이동
		return mav;
	}

	/* 게시글 수정 폼 */
	public ModelAndView noticeUpdateForm(String abo_no) {
		mav = new ModelAndView();
		AdminBoard abo = null;
		//DB에서 select한 데이터를 Bean(abo)에 대입
		abo = aDao.getBoardDetail(abo_no);
		if(abo!=null) {//데이터값이 존재하면
			//ModelAndView에 데이터값을 추가한다 (JSP에서 EL로 출력)
			mav.addObject("abo", abo);
		} else {//데이터값이 존재하지 않으면
			//ModelAndView에 script태그를 추가하여 안내창을 띄운다		
			mav.addObject("Fail", "<script>alert('수정 전 내용 불러오기에 실패했습니다.')</script>");
		}
		mav.setViewName("noticeUpdateForm");//noticeUpdateForm.jsp로 이동
		return mav;
	}

	/* 게시글 수정 */
	public ModelAndView noticeUpdate(HttpServletRequest request) {
		mav = new ModelAndView();
		String view = null;//이동할 JSP를 담는 변수
		AdminBoard abo = new AdminBoard();
		//request에 담겨있는 매개변수들을 Bean(abo)에 담는다
		abo.setAbo_no(request.getParameter("abo_no"));
		abo.setAbc_no(request.getParameter("abc_no"));
		abo.setAbo_title(request.getParameter("abo_title"));
		abo.setAbo_ctt(request.getParameter("abo_ctt"));
		boolean updateResult = false;//update결과를 확인할 변수
		//DB에 update하고 그 결과를 변수 updateResult에 대입
		updateResult = aDao.boardUpdate(abo);
		if(updateResult) {//결과값이 true이면
			//DB에서 select한 데이터를 Bean(abo)에 대입
			abo = aDao.getBoardDetail(abo.getAbo_no());
			if(abo!=null) {//데이터값이 존재하면
				//ModelAndView에 데이터값을 추가한다 (JSP에서 EL로 출력)
				mav.addObject("abo", abo);
				view = "noticeDetail";
			} else {//데이터값이 존재하지 않으면
				//ModelAndView에 script태그를 추가하여 안내창을 띄운다		
				mav.addObject("Fail", "<script>alert('수정 전 내용 불러오기에 실패했습니다.')</script>");
				view = "noticeUpdateForm";
			}
		} else {//결과값이 false면
			//ModelAndView에 script태그를 추가하여 안내창을 띄운다		
			mav.addObject("Fail", "<script>alert('게시글 수정에 실패했습니다.')</script>");
			view = "noticeUpdateForm";
		}
		mav.setViewName(view);//view변수에 담긴 JSP로 이동
		return mav;
	}

	/* 공지사항 목록 */
	public ModelAndView noticeList(Integer pageNum) {
		mav = new ModelAndView();
		List<AdminBoard> aboList = null;
		//페이지번호(pageNum)이 null이면 1 대입
		int page_no = (pageNum == null) ? 1 : pageNum;
		//DB에서 select한 데이터를 Bean(aboList)에 대입
		aboList = aDao.getNoticeList(page_no);
		if(aboList != null) {
			//ModelAndView에 데이터값을 추가한다 (JSP에서 EL로 출력)
			mav.addObject("aboList", aboList);//공지사항 목록
			mav.addObject("paging", getPaging(page_no));//페이징
		} else {
			System.out.println("noticeList 실패");
		}
		mav.setViewName("noticeList");//noticeList.jsp로 이동
		return mav;
	}
	
	/* 공지사항 목록의 페이징을 위해 paging class와 연결하는 메소드 */
	private Object getPaging(int pageNum) {
		int maxNum = aDao.getBoardCount(); //전체 글의 개수
		int listCount = 10; //페이지당 글의 수
		int pageCount = 5; //그룹당 페이지 수
		String boardName = "noticeList"; //게시판이 여러개일 때 구분하기 위한 게시판명
		Paging paging = new Paging(maxNum, pageNum, listCount, pageCount, boardName);
		return paging.makeHtmlPaging();
	}
	
	/* 공지사항 검색 목록 */
	public ModelAndView noticeListSearch(Integer pageNum, String abc_name, String search) {	
		mav = new ModelAndView();
		//페이지번호(pageNum)이 null이면 1 대입
		int page_no = (pageNum == null) ? 1 : pageNum;	
		AdminBoard abo = new AdminBoard();
		//매개변수를 Bean(abo)에 담는다
		abo.setPage_no(page_no);//페이지번호
		abo.setAbc_name(abc_name);//카테고리명
		abo.setSearch(search);//검색내용
		List<AdminBoard> aboList = null;		
		//카테고리가 전체이면서 내용이 없으면
		if (abc_name.equals("전체") && search == "") {
			noticeList(pageNum);//일반 공지사항 목록 메소드 호출
		//카테고리가 전체이면
		} else if (abc_name.equals("전체")) {
			aboList = aDao.getNoticeListAllSearch(abo);
		//카테고리가 전체가 아니면(기업 또는 개인)
		} else
			aboList = aDao.getNoticeListCategoriSearch(abo);
		if (aboList != null) {//데이터값이 존재하면
			//ModelAndView에 데이터값을 추가한다 (JSP에서 EL로 출력)
			mav.addObject("searchNotifications",
					"<div class='alert alert-primary' role='alert' "
					+ "style='text-align:center'><strong>"
					+ abc_name + "</strong>에서 <strong>'"
					+ abo.getSearch() + "'</strong>에 대한 검색 결과입니다</div>");			
			mav.addObject("aboList", aboList);
			mav.addObject("abc_name", abc_name);
			mav.addObject("paging", getPagingSearch(abo));
		} else {
			System.out.println("getNoticeListSearch 실패");
		}
		mav.setViewName("noticeList");//noticeList.jsp로 이동
		return mav;
	}

	/* 공지사항 검색 목록의 페이징을 위해 paging class와 연결하는 메소드 */
	private String getPagingSearch(AdminBoard abo) {
		int maxNum = 0;
		if (abo.getAbc_name().equals("전체"))//카테고리가 전체이면
			maxNum = aDao.getBoardCountAllSearch(abo); //전체 글의 개수
		else//카테고리가 전체가 아니면(기업 또는 개인)
			maxNum = aDao.getBoardCountCategoriSearch(abo); //전체 글의 개수
		int listCount = 10; //페이지당 글의 수
		int pageCount = 5; //그룹당 페이지 수
		String boardName = "noticeListSearch"; //게시판이 여러개일 때 구분하기 위한 게시판명
		Paging paging = new Paging(maxNum, abo.getPage_no(), listCount, pageCount, boardName);
		return paging.makeHtmlSearchPaging(abo);
	}
}
