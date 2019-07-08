package com.teamx.respets.userClass;

import java.util.HashMap;
import java.util.Map;
import com.teamx.respets.bean.AdminBoard;

public class Paging {

	private int maxNum; // 전체 글의 개수
	private int pageNum; // 현재 페이지 번호
	private int listCount; // 페이지당 나타낼 글의 개수 = 10
	private int pageCount; // 페이지그룹당 페이지 개수 = 5
	private String boardName; // 게시판 종류

	public Paging(int maxNum, int pageNum, int listCount, int pageCount, String boardName) {
		this.maxNum = maxNum;
		this.pageNum = pageNum;
		this.listCount = listCount;
		this.pageCount = pageCount;
		this.boardName = boardName;
	}

	public String makeHtmlPaging() {
		// 전체 페이지 갯수
		int totalPage = (maxNum % listCount > 0) ? maxNum / listCount + 1 : maxNum / listCount;
		// 현재 페이지가 속해 있는 그룹 번호
		int currentGroup = (pageNum % pageCount > 0) ? pageNum / pageCount + 1 : pageNum / pageCount;
		return makeHtml(currentGroup, totalPage, boardName);
	}
	
	private String makeHtml(int currentGroup, int totalPage, String boardName) {
		StringBuffer sb = new StringBuffer();
		// 현재 그룹의 시작 페이지 번호
		int start = (currentGroup * pageCount) - (pageCount - 1);
		// 현재 그룹의 끝 페이지 번호
		int end = (currentGroup * pageCount >= totalPage) ? totalPage : currentGroup * pageCount;
		sb.append("<div class='dataTables_paginate paging_simple_numbers'" + "id='selection-datatable_paginate'>"
				+ "<ul class='pagination pagination-rounded'>");
		if (pageNum <= 1) {//페이지번호가 1 이하이면
			sb.append("<li class='paginate_button page-item previous disabled'"
					+ "id='selection-datatable_previous'><a href='#'"
					+ "aria-controls='selection-datatable' data-dt-idx='0' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-left'></i></a></li>");
		} else {//페이지번호가 1보다 크면
			sb.append("<li class='paginate_button page-item previous'" + "id='selection-datatable_previous'><a href='"
					+ boardName + "?pageNum=" + (pageNum - 1) + "' "
					+ "aria-controls='selection-datatable' data-dt-idx='0' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-left'></i></a></li>");
		}
		//현재 그룹의 시작 페이지부터 끝 페이지까지 반복
		for (int i = start; i <= end; i++) {
			if (pageNum != i) { //현재 페이지가 아닌 경우 링크 처리
				sb.append("<li class='paginate_button page-item'>" + "<a href='" + boardName + "?pageNum=" + i
						+ "' aria-controls='basic-datatable' " + "data-dt-idx='" + i
						+ "' tabindex='0' class='page-link'>" + i + "</a></li>");
			} else { //현재 페이지인 경우 링크 해제
				sb.append("<li class='paginate_button page-item active'>" + "<a href='" + boardName + "?pageNum=" + i
						+ "' aria-controls='basic-datatable' " + "data-dt-idx='" + i
						+ "' tabindex='0' class='page-link'>" + i + "</a></li>");
			} //else End
		} //for End
		if (pageNum == totalPage) {//페이지번호가 전체페이지 개수와 같으면
			sb.append("<li class='paginate_button page-item previous disabled'"
					+ "id='selection-datatable_previous'><a href='#'"
					+ "aria-controls='selection-datatable' data-dt-idx='6' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-right'></i></a></li>");
		} else if (pageNum == end) {//페이지번호가 현재 그룹의 끝 페이지와 같으면
			sb.append("<li class='paginate_button page-item previous'" + "id='selection-datatable_previous'><a href='"
					+ boardName + "?pageNum=" + (end + 1) + "' "
					+ "aria-controls='selection-datatable' data-dt-idx='6' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-right'></i></a></li>");
		} else {
			sb.append("<li class='paginate_button page-item previous'" + "id='selection-datatable_previous'><a href='"
					+ boardName + "?pageNum=" + (pageNum + 1) + "' "
					+ "aria-controls='selection-datatable' data-dt-idx='6' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-right'></i></a></li>");
		}
		sb.append("</ul></div>");
		return sb.toString();
	}// method End

	@SuppressWarnings("unused")
	public String makeHtmlSearchPaging(HashMap<String, Object> hmap) {
		System.out.println("paging makeHtmlSearchPaging hmap=" + hmap);
		// 전체 페이지 갯수
		int totalPage = (maxNum % listCount > 0) ? maxNum / listCount + 1 : maxNum / listCount;
		// 전체 페이지 그룹 갯수
		int totalGroup = (totalPage % pageCount > 0) ? totalPage / pageCount + 1 : totalPage / pageCount;
		// 현재 페이지가 속해 있는 그룹 번호
		int currentGroup = (pageNum % pageCount > 0) ? pageNum / pageCount + 1 : pageNum / pageCount;
		return makeHtmlSearch(currentGroup, totalPage, boardName, hmap);
	} // method End

	private String makeHtmlSearch(int currentGroup, int totalPage, String boardName, HashMap<String, Object> hmap) {
		StringBuffer sb = new StringBuffer();
		System.out.println("paging makeHtmlSearch hmap=" + hmap);
		String select = hmap.get("select").toString();
		String search = hmap.get("search").toString();
		System.out.println("꺼내오니 select? " + select);
		System.out.println("꺼내오니 search? " + search);
		// 현재 그룹의 시작 페이지 번호
		int start = (currentGroup * pageCount) - (pageCount - 1);
		// 현재 그룹의 끝 페이지 번호
		int end = (currentGroup * pageCount >= totalPage) ? totalPage : currentGroup * pageCount;

		sb.append("<div class='dataTables_paginate paging_simple_numbers'" + "id='selection-datatable_paginate'>"
				+ "<ul class='pagination pagination-rounded'>");

		if (pageNum <= 1) {
			sb.append("<li class='paginate_button page-item previous disabled'"
					+ "id='selection-datatable_previous'><a href='#'"
					+ "aria-controls='selection-datatable' data-dt-idx='0' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-left'></i></a></li>");
		} else {
			sb.append("<li class='paginate_button page-item previous'" + "id='selection-datatable_previous'><a href='"
					+ boardName + "?pageNum=" + (pageNum - 1) + "' "
					+ "aria-controls='selection-datatable' data-dt-idx='0' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-left'></i></a></li>");
		}

		for (int i = start; i <= end; i++) {
			if (pageNum != i) { // 현재 페이지가 아닌 경우 링크 처리
				sb.append("<li class='paginate_button page-item'>" + "<a href='" + boardName + "?pageNum=" + i
						+ "' aria-controls='basic-datatable' " + "data-dt-idx='" + i
						+ "' tabindex='0' class='page-link'>" + i + "</a></li>");
			} else { // 현재 페이지인 경우 링크 해제
				sb.append("<li class='paginate_button page-item active'>" + "<a href='" + boardName + "?pageNum=" + i
						+ "' aria-controls='basic-datatable' " + "data-dt-idx='" + i
						+ "' tabindex='0' class='page-link'>" + i + "</a></li>");
			} // else End
		} // for End
		if (pageNum == totalPage) {
			sb.append("<li class='paginate_button page-item previous disabled'"
					+ "id='selection-datatable_previous'><a href='#'"
					+ "aria-controls='selection-datatable' data-dt-idx='6' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-right'></i></a></li>");
		} else if (pageNum == end) {
			sb.append("<li class='paginate_button page-item previous'" + "id='selection-datatable_previous'><a href='"
					+ boardName + "?pageNum=" + (end + 1) + "' "
					+ "aria-controls='selection-datatable' data-dt-idx='6' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-right'></i></a></li>");
		} else {
			sb.append("<li class='paginate_button page-item previous'" + "id='selection-datatable_previous'><a href='"
					+ boardName + "?pageNum=" + (pageNum + 1) + "' "
					+ "aria-controls='selection-datatable' data-dt-idx='6' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-right'></i></a></li>");
		}
		sb.append("</ul></div>");
		
		return sb.toString();
	} // method End

	
	
	//검색 페이징
	public String makeHtmlSearchPaging(AdminBoard abo) {
		//전체 페이지 갯수
		int totalPage = (maxNum % listCount > 0) ? maxNum / listCount + 1 : maxNum / listCount;
		//현재 페이지가 속해 있는 그룹 번호
		int currentGroup = (pageNum % pageCount > 0) ? pageNum / pageCount + 1 : pageNum / pageCount;
		return makeHtmlSearch(currentGroup, totalPage, boardName, abo);
	}

	private String makeHtmlSearch(int currentGroup, int totalPage, String boardName, AdminBoard abo) {
		StringBuffer sb = new StringBuffer();
		//현재 그룹의 시작 페이지 번호
		int start = (currentGroup * pageCount) - (pageCount - 1);
		//현재 그룹의 끝 페이지 번호
		int end = (currentGroup * pageCount >= totalPage) ? totalPage : currentGroup * pageCount;
		sb.append("<div class='dataTables_paginate paging_simple_numbers'" + "id='selection-datatable_paginate'>"
				+ "<ul class='pagination pagination-rounded'>");
		if (pageNum <= 1) {//페이지번호가 1 이하이면
			sb.append("<li class='paginate_button page-item previous disabled'"
					+ "id='selection-datatable_previous'><a href='#'"
					+ "aria-controls='selection-datatable' data-dt-idx='0' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-left'></i></a></li>");
		} else {//페이지번호가 1보다 크면
			sb.append("<li class='paginate_button page-item previous'" + "id='selection-datatable_previous'><a href='"
					+ boardName + "?pageNum=" + (pageNum - 1) + "&abc_name="+ abo.getAbc_name() +""
					+ "&search="+ abo.getSearch() +"' "
					+ "aria-controls='selection-datatable' data-dt-idx='0' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-left'></i></a></li>");
		}
		//현재 그룹의 시작 페이지부터 끝 페이지까지 반복
		for (int i = start; i <= end; i++) {
			if (pageNum != i) { // 현재 페이지가 아닌 경우 링크 처리
				sb.append("<li class='paginate_button page-item'>" + "<a href='" + boardName + "?pageNum=" + i
						+ "&abc_name="+ abo.getAbc_name() +"&search="+ abo.getSearch()
						+ "' aria-controls='basic-datatable' " + "data-dt-idx='" + i
						+ "' tabindex='0' class='page-link'>" + i + "</a></li>");
			} else { // 현재 페이지인 경우 링크 해제
				sb.append("<li class='paginate_button page-item active'>" + "<a href='" + boardName + "?pageNum=" + i
						+ "&abc_name="+ abo.getAbc_name() +"&search="+ abo.getSearch()
						+ "' aria-controls='basic-datatable' " + "data-dt-idx='" + i
						+ "' tabindex='0' class='page-link'>" + i + "</a></li>");
			} // else End
		} // for End
		if (pageNum == totalPage) {//페이지번호가 전체페이지 개수와 같으면
			sb.append("<li class='paginate_button page-item previous disabled'"
					+ "id='selection-datatable_previous'><a href='#'"
					+ "aria-controls='selection-datatable' data-dt-idx='6' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-right'></i></a></li>");
		} else if (pageNum == end) {//페이지번호가 현재 그룹의 끝 페이지와 같으면
			sb.append("<li class='paginate_button page-item previous'" + "id='selection-datatable_previous'><a href='"
					+ boardName + "?pageNum=" + (end + 1) + "&abc_name="+ abo.getAbc_name() +"&search="+ abo.getSearch()
					+ "' aria-controls='selection-datatable' data-dt-idx='6' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-right'></i></a></li>");
		} else {
			sb.append("<li class='paginate_button page-item previous'" + "id='selection-datatable_previous'><a href='"
					+ boardName + "?pageNum=" + (pageNum + 1) + "&abc_name="+ abo.getAbc_name() +"&search="+ abo.getSearch()
					+ "' aria-controls='selection-datatable' data-dt-idx='6' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-right'></i></a></li>");
		}
		sb.append("</ul></div>");
		return sb.toString();
	}
	
///////////////////////////////////////현휘 
	@SuppressWarnings("unused")
	public String businessListPaging(Map<String, Object> map) {
		// 전체 페이지 갯수
		int totalPage = (maxNum % listCount > 0) ? maxNum / listCount + 1 : maxNum / listCount;
		// 전체 페이지 그룹 갯수
		int totalGroup = (totalPage % pageCount > 0) ? totalPage / pageCount + 1 : totalPage / pageCount;
		// 현재 페이지가 속해 있는 그룹 번호
		int currentGroup = (pageNum % pageCount > 0) ? pageNum / pageCount + 1 : pageNum / pageCount;
		return businessList(currentGroup, totalPage, boardName, map);
	} // method End

	private String businessList(int currentGroup, int totalPage, String boardName, Map<String, Object> map) {
		String bus_addr = (String) map.get("bus_addr");
		String bsd_date = (String) map.get("bsd_date");
		String bct_code = (String) map.get("bct_code");
		StringBuffer sb = new StringBuffer();
		// 현재 그룹의 시작 페이지 번호
		int start = (currentGroup * pageCount) - (pageCount - 1);
		// 현재 그룹의 끝 페이지 번호
		int end = (currentGroup * pageCount >= totalPage) ? totalPage : currentGroup * pageCount;
		sb.append("<nav>");
		sb.append("<ul class='pagination'>");
		if (start != 1) {
			sb.append("<li class='page-item'><a class='page-link' href='" + boardName + "?pageNum=" + (start - 1) + "&bus_addr="
					+ bus_addr + "&bsd_date=" + bsd_date + "&bct_code=" + bct_code + "'>");
			sb.append("이전</a></li>");
		} // method End

		for (int i = start; i <= end; i++) {
			if (pageNum != i) { // 현재 페이지가 아닌 경우 링크 처리
				sb.append("<li class='page-item'><a class='page-link' href='" + boardName + "?pageNum=" + i + "&bus_addr=" + bus_addr
						+ "&bsd_date=" + bsd_date + "&bct_code=" + bct_code + "'>");
				sb.append("  " + i + "</a></li>");
			} else { // 현재 페이지인 경우 링크 해제
				sb.append("<li class='page-item active'><a class='page-link' href='#'> " + i + "  </a></li>");
			} // else End
		} // for End

		if (end != totalPage) {
			sb.append("<li class='page-item'><a class='page-link' href='" + boardName + "?pageNum=" + (end + 1) + "&bus_addr=" + bus_addr
					+ "&bsd_date=" + bsd_date + "&bct_code=" + bct_code + "'>");
			sb.append("다음</a></li>");
		} // if End
		sb.append("</ul>");
		sb.append("</nav>");
		return sb.toString();
	} // method End

	/*혜연*/
	public String BookingListButton(String no) {
		int totalPage = (maxNum % listCount > 0) ? maxNum / listCount + 1 : maxNum / listCount;
		int totalGroup = (totalPage % pageCount > 0) ? totalPage / pageCount + 1 : totalPage / pageCount;
		int currentGroup = (pageNum % pageCount > 0) ? pageNum / pageCount + 1 : pageNum / pageCount;
		return BookingListBut(currentGroup, totalPage, boardName, no);
	}

	/*혜연*/
	private String BookingListBut(int currentGroup, int totalPage, String boardName, String no) {
		StringBuffer sb = new StringBuffer();
		// 현재 그룹의 시작 페이지 번호
		int start = (currentGroup * pageCount) - (pageCount - 1);
		// 현재 그룹의 끝 페이지 번호
		int end = (currentGroup * pageCount >= totalPage) ? totalPage : currentGroup * pageCount;
		sb.append("<nav>");
		sb.append("<ul class='pagination'>");
		if (start != 1) {
			sb.append("<li class='page-item'><a class='page-link' href='" + boardName + "?pageNum=" + (start - 1)
					+ "&no=" + no + "'>");
			sb.append("이전</a></li>");
		} // method End

		for (int i = start; i <= end; i++) {
			if (pageNum != i) { // 현재 페이지가 아닌 경우 링크 처리
				sb.append("<li class='page-item'><a class='page-link' href='" + boardName + "?pageNum=" + i
						+ "&no=" + no + "'>");
				sb.append("  " + i + "</a></li>");
			} else { // 현재 페이지인 경우 링크 해제
				sb.append("<li class='page-item active'><a class='page-link' href='#'> " + i + "  </a></li>");
			} // else End
		} // for End

		if (end != totalPage) {
			sb.append("<li class='page-item'><a class='page-link' href='" + boardName + "?pageNum=" + (end + 1)
					+ "&no=" + no + "'>");
			sb.append("다음</a></li>");
		} // if End
		sb.append("</ul>");
		sb.append("</nav>");
		return sb.toString();
	}

	///////////////////////////////////////현휘 
	@SuppressWarnings("unused")
	public String businessListButPaging(String bct_code) {
		// 전체 페이지 갯수
		int totalPage = (maxNum % listCount > 0) ? maxNum / listCount + 1 : maxNum / listCount;
		// 전체 페이지 그룹 갯수
		int totalGroup = (totalPage % pageCount > 0) ? totalPage / pageCount + 1 : totalPage / pageCount;
		// 현재 페이지가 속해 있는 그룹 번호
		int currentGroup = (pageNum % pageCount > 0) ? pageNum / pageCount + 1 : pageNum / pageCount;
		return businessListBut(currentGroup, totalPage, boardName, bct_code);
	} // method End
	
	public String businessListBut(int currentGroup, int totalPage, String boardName, String bct_code) {
		StringBuffer sb = new StringBuffer();
		// 현재 그룹의 시작 페이지 번호
		int start = (currentGroup * pageCount) - (pageCount - 1);
		// 현재 그룹의 끝 페이지 번호
		int end = (currentGroup * pageCount >= totalPage) ? totalPage : currentGroup * pageCount;
		sb.append("<nav>");
		sb.append("<ul class='pagination'>");
		if (start != 1) {
			sb.append("<li class='page-item'><a class='page-link' href='" + boardName + "?pageNum=" + (start - 1)
					+ "&bct_code=" + bct_code + "'>");
			sb.append("이전</a></li>");
		} // method End

		for (int i = start; i <= end; i++) {
			if (pageNum != i) { // 현재 페이지가 아닌 경우 링크 처리
				sb.append("<li class='page-item'><a class='page-link' href='" + boardName + "?pageNum=" + i
						+ "&bct_code=" + bct_code + "'>");
				sb.append("  " + i + "</a></li>");
			} else { // 현재 페이지인 경우 링크 해제
				sb.append("<li class='page-item active'><a class='page-link' href='#'> " + i + "  </a></li>");
			} // else End
		} // for End

		if (end != totalPage) {
			sb.append("<li class='page-item'><a class='page-link' href='" + boardName + "?pageNum=" + (end + 1)
					+ "&bct_code=" + bct_code + "'>");
			sb.append("다음</a></li>");
		} // if End
		sb.append("</ul>");
		sb.append("</nav>");
		return sb.toString();
	}

	@SuppressWarnings("unused")
	public String bctAllPaging(String bct_name) {
		// 전체 페이지 갯수
		int totalPage = (maxNum % listCount > 0) ? maxNum / listCount + 1 : maxNum / listCount;
		// 전체 페이지 그룹 갯수
		int totalGroup = (totalPage % pageCount > 0) ? totalPage / pageCount + 1 : totalPage / pageCount;
		// 현재 페이지가 속해 있는 그룹 번호
		int currentGroup = (pageNum % pageCount > 0) ? pageNum / pageCount + 1 : pageNum / pageCount;
		return bctAllPagingList(currentGroup, totalPage, boardName, bct_name);
	} // method End

	private String bctAllPagingList(int currentGroup, int totalPage, String boardName, String bct_name) {
		StringBuffer sb = new StringBuffer();
		// 현재 그룹의 시작 페이지 번호
		int start = (currentGroup * pageCount) - (pageCount - 1);
		// 현재 그룹의 끝 페이지 번호
		int end = (currentGroup * pageCount >= totalPage) ? totalPage : currentGroup * pageCount;
		sb.append("<div class='dataTables_paginate paging_simple_numbers'" + "id='selection-datatable_paginate'>"
				+ "<ul class='pagination pagination-rounded'>");
		if (start != 1) {
			sb.append("<li class='paginate_button page-item previous disabled' id='selection-datatable_previous'><a href='javascript:void(0)' aria-controls='selection-datatable' data-dt-idx='0' tabindex='0' class='page-link' onclick=\"butListPaging('" + (start - 1) + "','"+bct_name+"','')\">");
			sb.append("<i class='mdi mdi-chevron-left'></i></a></li>");
		} // method End

		for (int i = start; i <= end; i++) {
			if (pageNum != i) { // 현재 페이지가 아닌 경우 링크 처리
				sb.append("<li class='paginate_button page-item'><a href='javascript:void(0)' aria-controls='basic-datatable' data-dt-idx='"+i+"' tabindex='0' class='page-link' onclick=\"butListPaging('" + i + "','"+bct_name+"','')\">");
				sb.append(i + "</a></li>");
			} else { // 현재 페이지인 경우 링크 해제
				sb.append("<li class='paginate_button page-item active'><a class='page-link' href='#' aria-controls='basic-datatable' data-dt-idx='"+i+"' tabindex='0' class='page-link'>" + i + "  </a></li>");
			} // else End
		} // for End

		if (end != totalPage) {
			sb.append("<li class='paginate_button page-item previous' id='selection-datatable_previous'><a href='javascript:void(0)' aria-controls='selection-datatable' data-dt-idx='6' tabindex='0' class='page-link' onclick=\"butListPaging('" + (end + 1) + "','"+bct_name+"','')\">");
			sb.append("<i class='mdi mdi-chevron-right'></i></a></li>");
		} // if End
		sb.append("</ul>");
		sb.append("</nav>");
		return sb.toString();
	}
	
	
	@SuppressWarnings("unused")
	public String AllPaging() {
		// 전체 페이지 갯수
		int totalPage = (maxNum % listCount > 0) ? maxNum / listCount + 1 : maxNum / listCount;
		// 전체 페이지 그룹 갯수
		int totalGroup = (totalPage % pageCount > 0) ? totalPage / pageCount + 1 : totalPage / pageCount;
		// 현재 페이지가 속해 있는 그룹 번호
		int currentGroup = (pageNum % pageCount > 0) ? pageNum / pageCount + 1 : pageNum / pageCount;
		return busAllPaging(currentGroup, totalPage, boardName);
	} // method End

	private String busAllPaging(int currentGroup, int totalPage, String boardName) {
		StringBuffer sb = new StringBuffer();
		// 현재 그룹의 시작 페이지 번호
		int start = (currentGroup * pageCount) - (pageCount - 1);
		// 현재 그룹의 끝 페이지 번호
		int end = (currentGroup * pageCount >= totalPage) ? totalPage : currentGroup * pageCount;
		sb.append("<div class='dataTables_paginate paging_simple_numbers'" + "id='selection-datatable_paginate'>"
				+ "<ul class='pagination pagination-rounded'>");
		if (start != 1) {
			sb.append("<li class='paginate_button page-item previous disabled' id='selection-datatable_previous'><a href='javascript:void(0)' aria-controls='selection-datatable' data-dt-idx='0' tabindex='0' class='page-link' onclick=\"butListPaging('" + (start - 1) + "','','')\">");
			sb.append("<i class='mdi mdi-chevron-left'></i></a></li>");
		} // method End

		for (int i = start; i <= end; i++) {
			if (pageNum != i) { // 현재 페이지가 아닌 경우 링크 처리
				sb.append("<li class='paginate_button page-item'><a href='javascript:void(0)' aria-controls='basic-datatable' data-dt-idx='"+i+"' tabindex='0' class='page-link' onclick=\"butListPaging('" + i + "','','')\">");
				sb.append(i + "</a></li>");
			} else { // 현재 페이지인 경우 링크 해제
				sb.append("<li class='paginate_button page-item active'><a class='page-link' href='#' aria-controls='basic-datatable' data-dt-idx='"+i+"' tabindex='0' class='page-link'> " + i + "  </a></li>");
			} // else End
		} // for End

		if (end != totalPage) {
			sb.append("<li class='paginate_button page-item previous' id='selection-datatable_previous'><a href='javascript:void(0)' aria-controls='selection-datatable' data-dt-idx='6' tabindex='0' class='page-link' onclick=\"butListPaging('" + (end + 1) + "','','')\">");
			sb.append("<i class='mdi mdi-chevron-right'></i></a></li>");
		} // if End
		sb.append("</ul>");
		sb.append("</nav>");
		return sb.toString();
	}
	

////////////////////////////////////////////////////현휘 (주소, 태그 )
	@SuppressWarnings("unused")
	public String businessListTagPaging(Map<String, Object> map) {
		// 전체 페이지 갯수
		int totalPage = (maxNum % listCount > 0) ? maxNum / listCount + 1 : maxNum / listCount;
		// 전체 페이지 그룹 갯수
		int totalGroup = (totalPage % pageCount > 0) ? totalPage / pageCount + 1 : totalPage / pageCount;
		// 현재 페이지가 속해 있는 그룹 번호
		int currentGroup = (pageNum % pageCount > 0) ? pageNum / pageCount + 1 : pageNum / pageCount;
		return businessListTag(currentGroup, totalPage, boardName, map);
	}
	private String businessListTag(int currentGroup, int totalPage, String boardName2, Map<String, Object> map) {
		String bus_addr = (String) map.get("bus_addr");
		String bsd_date = (String) map.get("bsd_date");
		String bct_code = (String) map.get("bct_code");
		String tag_no = (String) map.get("tag_no");
		StringBuffer sb = new StringBuffer();
		// 현재 그룹의 시작 페이지 번호
		int start = (currentGroup * pageCount) - (pageCount - 1);
		// 현재 그룹의 끝 페이지 번호
		int end = (currentGroup * pageCount >= totalPage) ? totalPage : currentGroup * pageCount;
		sb.append("<nav>");
		sb.append("<ul class='pagination'>");
		if (start != 1) {
			sb.append("<li class='page-item'><a class='page-link' href='javascript:void(0)' onclick=\"businessList('" + bct_code + "','"
					+ tag_no + "','" + bsd_date + "','" + bus_addr + "','" + (start - 1) + "')\">");
			sb.append("이전</a></li>");
		} // method End

		for (int i = start; i <= end; i++) {
			if (pageNum != i) { // 현재 페이지가 아닌 경우 링크 처리
				sb.append("<li class='page-item'><a class='page-link' href='javascript:void(0)' onclick=\"businessList(" + bct_code
						+ "','" + tag_no + "','" + bsd_date + "','" + bus_addr + "','" + i + "')\">");
				sb.append("  " + i + "</a></li>");
			} else { // 현재 페이지인 경우 링크
				sb.append("<li class='page-item active'><a class='page-link' href='#'> " + i + "  </a></li>");
			} // else End
		} // for End

		if (end != totalPage) {
			sb.append("<li class='page-item'><a class='page-link' href='javascript:void(0)' onclick=\"businessList(" + bct_code + "','"
					+ tag_no + "','" + bsd_date + "','" + bus_addr + "','" + (end + 1) + "')\">");
			sb.append("다음</a></li>");
		} // if End
		sb.append("</ul>");
		sb.append("</nav>");
		return sb.toString();
	}
	
	////////////////////////////////////////////////////현휘 
	@SuppressWarnings("unused")
	public String butTagSelectListPaging(Map<String, Object> map) {
		// 전체 페이지 갯수
		int totalPage = (maxNum % listCount > 0) ? maxNum / listCount + 1 : maxNum / listCount;
		// 전체 페이지 그룹 갯수
		int totalGroup = (totalPage % pageCount > 0) ? totalPage / pageCount + 1 : totalPage / pageCount;
		// 현재 페이지가 속해 있는 그룹 번호
		int currentGroup = (pageNum % pageCount > 0) ? pageNum / pageCount + 1 : pageNum / pageCount;
		return butTagSelectList(currentGroup, totalPage, boardName, map);
	} // method End

	private String butTagSelectList(int currentGroup, int totalPage, String boardName, Map<String, Object> map) {
		String bct_code = (String) map.get("bct_code");
		String tag_no = (String) map.get("tag_no");
		StringBuffer sb = new StringBuffer();
		// 현재 그룹의 시작 페이지 번호
		int start = (currentGroup * pageCount) - (pageCount - 1);
		// 현재 그룹의 끝 페이지 번호
		int end = (currentGroup * pageCount >= totalPage) ? totalPage : currentGroup * pageCount;
		sb.append("<nav>");
		sb.append("<ul class='pagination'>");
		if (start != 1) {
			sb.append("<li class='page-item'><a class='page-link' href='javascript:void(0)' onclick=\"butTagSelectList('" + bct_code
					+ "','" + tag_no + "','" + (start - 1) + "')\">");
			sb.append("이전</a></li>");
		} // method End

		for (int i = start; i <= end; i++) {
			if (pageNum != i) { // 현재 페이지가 아닌 경우 링크 처리
				sb.append("<li class='page-item'><a class='page-link' href='javascript:void(0)' onclick=\"butTagSelectList('" + bct_code
						+ "','" + tag_no + "','" + i + "')\">");
				sb.append("  " + i + "</a></li>");
			} else { // 현재 페이지인 경우 링크 해제
				sb.append("<li class='page-item active'><a class='page-link' href='#'> " + i + "  </a></li>");
			} // else End
		} // for End

		if (end != totalPage) {
			sb.append("<li class='page-item'><a class='page-link' href='javascript:void(0)' onclick=\"butTagSelectList('" + bct_code
					+ "','" + tag_no + "','" + (end + 1) + "')\">");
			sb.append("다음</a></li>");
		} // if End
		sb.append("</ul>");
		sb.append("</nav>");
		return sb.toString();
	}

	public String BookingListPaging(String no) {
		// 전체 페이지 갯수
		int totalPage = (maxNum % listCount > 0) ? maxNum / listCount + 1 : maxNum / listCount;
		// 전체 페이지 그룹 갯수
		int totalGroup = (totalPage % pageCount > 0) ? totalPage / pageCount + 1 : totalPage / pageCount;
		// 현재 페이지가 속해 있는 그룹 번호
		int currentGroup = (pageNum % pageCount > 0) ? pageNum / pageCount + 1 : pageNum / pageCount;
		return perBookingList(currentGroup, totalPage, boardName, no);
	}

	private String perBookingList(int currentGroup, int totalPage, String boardName, String no) {
		StringBuffer sb = new StringBuffer();
		int start = (currentGroup * pageCount) - (pageCount - 1);
		int end = (currentGroup * pageCount >= totalPage) ? totalPage : currentGroup * pageCount;
		sb.append("<div class='dataTables_paginate paging_simple_numbers'" + "id='selection-datatable_paginate'>"
				+ "<ul class='pagination pagination-rounded'>");
		if (pageNum <= 1) {
			sb.append("<li class='paginate_button page-item previous disabled'"
					+ "id='selection-datatable_previous'><a href='#'"
					+ "aria-controls='selection-datatable' data-dt-idx='0' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-left'></i></a></li>");
		} else {
			sb.append("<li class='paginate_button page-item previous'" + "id='selection-datatable_previous'><a href='" + boardName + "?pageNum=" + (pageNum - 1) + "&no=" + no + "' "
					+ "aria-controls='selection-datatable' data-dt-idx='0' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-left'></i></a></li>");
		}

		
		for (int i = start; i <= end; i++) {
			if (pageNum != i) { // 현재 페이지가 아닌 경우 링크 처리
				sb.append("<li class='paginate_button page-item'>" + "<a href='" + boardName + "?pageNum=" + i + "&no=" + no + "' aria-controls='basic-datatable' " + "data-dt-idx='" + i
						+ "' tabindex='0' class='page-link'>" + i + "</a></li>");
			} else { // 현재 페이지인 경우 링크 해제
				sb.append("<li class='paginate_button page-item active'>" + "<a href='" + boardName + "?pageNum=" + i + "&no=" + no + "' aria-controls='basic-datatable' " + "data-dt-idx='" + i
						+ "' tabindex='0' class='page-link'>" + i + "</a></li>");
			} // else End
		} // for End
		
		if (pageNum == totalPage) {
			sb.append("<li class='paginate_button page-item previous disabled'"
					+ "id='selection-datatable_previous'><a href='#'"
					+ "aria-controls='selection-datatable' data-dt-idx='6' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-right'></i></a></li>");
		} else if (pageNum == end) {
			sb.append("<li class='paginate_button page-item previous'" + "id='selection-datatable_previous'><a href='" + boardName + "?pageNum=" + (end + 1) + "&no=" + no + "' "
					+ "aria-controls='selection-datatable' data-dt-idx='6' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-right'></i></a></li>");
		} else {
			sb.append("<li class='paginate_button page-item previous'" + "id='selection-datatable_previous'><a href='"
					+ boardName + "?pageNum=" + (pageNum + 1) + "&no="+no+"' "
					+ "aria-controls='selection-datatable' data-dt-idx='6' tabindex='0'"
					+ "class='page-link'><i class='mdi mdi-chevron-right'></i></a></li>");
		}

		if (end != totalPage) {
			sb.append("<li class='paginate_button page-item previous' id='selection-datatable_previous'><a href='" + boardName + "?pageNum=" + (end + 1) + "&no=" + no + "' aria-controls='selection-datatable' data-dt-idx='6' tabindex='0' class='page-link'>");
			sb.append("<i class='mdi mdi-chevron-right'></i></a></li>");
		} // if End
		return sb.toString();
	}

	@SuppressWarnings("unused")
	public String searchAllList(String search) {
		// 전체 페이지 갯수
		int totalPage = (maxNum % listCount > 0) ? maxNum / listCount + 1 : maxNum / listCount;
		// 전체 페이지 그룹 갯수
		int totalGroup = (totalPage % pageCount > 0) ? totalPage / pageCount + 1 : totalPage / pageCount;
		// 현재 페이지가 속해 있는 그룹 번호
		int currentGroup = (pageNum % pageCount > 0) ? pageNum / pageCount + 1 : pageNum / pageCount;
		return busAllPaging(currentGroup, totalPage, boardName, search);
	} // method End

	private String busAllPaging(int currentGroup, int totalPage, String boardName, String search) {
		StringBuffer sb = new StringBuffer();
		// 현재 그룹의 시작 페이지 번호
		int start = (currentGroup * pageCount) - (pageCount - 1);
		// 현재 그룹의 끝 페이지 번호
		int end = (currentGroup * pageCount >= totalPage) ? totalPage : currentGroup * pageCount;
		sb.append("<div class='dataTables_paginate paging_simple_numbers'" + "id='selection-datatable_paginate'>"
				+ "<ul class='pagination pagination-rounded'>");
		if (start != 1) {
			sb.append("<li class='paginate_button page-item previous disabled' id='selection-datatable_previous'><a href='javascript:void(0)' aria-controls='selection-datatable' data-dt-idx='0' tabindex='0' class='page-link' onclick=\"butListPaging('" + (start - 1) + "','','"+search+"')\">");
			sb.append("<i class='mdi mdi-chevron-left'></i></a></li>");
		} // method End

		for (int i = start; i <= end; i++) {
			if (pageNum != i) { // 현재 페이지가 아닌 경우 링크 처리
				sb.append("<li class='paginate_button page-item'><a href='javascript:void(0)' ' aria-controls='basic-datatable' data-dt-idx='"+i+"' tabindex='0' class='page-link' onclick=\"butListPaging('" + i + "','','"+search+"')\">");
				sb.append(i + "</a></li>");
			} else { // 현재 페이지인 경우 링크 해제
				sb.append("<li class='paginate_button page-item active'><a href='#' aria-controls='basic-datatable' data-dt-idx='"+i+"' tabindex='0' class='page-link'> " + i + "  </a></li>");
			} // else End
		} // for End

		if (end != totalPage) {
			sb.append("<li class='paginate_button page-item previous' id='selection-datatable_previous'><a href='javascript:void(0)' aria-controls='selection-datatable' data-dt-idx='6' tabindex='0' class='page-link' onclick=\"butListPaging('" + (end + 1) + "','','"+search+"')\">");
			sb.append("<i class='mdi mdi-chevron-right'></i></a></li>");
		} // if End
		sb.append("</ul>");
		sb.append("</div>");
		return sb.toString();
	}
	@SuppressWarnings("unused")
	public String searchBctAllsList(String bct_name, String search) {
		// 전체 페이지 갯수
		int totalPage = (maxNum % listCount > 0) ? maxNum / listCount + 1 : maxNum / listCount;
		// 전체 페이지 그룹 갯수
		int totalGroup = (totalPage % pageCount > 0) ? totalPage / pageCount + 1 : totalPage / pageCount;
		// 현재 페이지가 속해 있는 그룹 번호
		int currentGroup = (pageNum % pageCount > 0) ? pageNum / pageCount + 1 : pageNum / pageCount;
		return searchBctAllsListPaging(currentGroup, totalPage, boardName, search, bct_name);
	} // method End

	private String searchBctAllsListPaging(int currentGroup, int totalPage, String boardName, String search, String bct_name) {
		StringBuffer sb = new StringBuffer();
		// 현재 그룹의 시작 페이지 번호
		int start = (currentGroup * pageCount) - (pageCount - 1);
		// 현재 그룹의 끝 페이지 번호
		int end = (currentGroup * pageCount >= totalPage) ? totalPage : currentGroup * pageCount;
		sb.append("<div class='dataTables_paginate paging_simple_numbers'" + "id='selection-datatable_paginate'>"
				+ "<ul class='pagination pagination-rounded'>");
		if (start != 1) {
			sb.append("<li class='paginate_button page-item previous disabled' id='selection-datatable_previous'><a href='javascript:void(0)' aria-controls='selection-datatable' data-dt-idx='0' tabindex='0' class='page-link' onclick=\"butListPaging('" + (start - 1) + "','"+bct_name+"','"+search+"')\">");
			sb.append("<i class='mdi mdi-chevron-left'></i></a></li>");
		} // method End

		for (int i = start; i <= end; i++) {
			if (pageNum != i) { // 현재 페이지가 아닌 경우 링크 처리
				sb.append("<li class='paginate_button page-item'><a href='javascript:void(0)' aria-controls='basic-datatable' data-dt-idx='"+i+"' tabindex='0' class='page-link'  onclick=\"butListPaging('" + i + "','"+bct_name+"','"+search+"')\">");
				sb.append(i + "</a></li>");
			} else { // 현재 페이지인 경우 링크 해제
				sb.append("<li class='paginate_button page-item active'><a href='#' aria-controls='basic-datatable' data-dt-idx='"+i+"' tabindex='0' class='page-link'> " + i + "  </a></li>");
			} // else End
		} // for End

		if (end != totalPage) {
			sb.append("<li class='paginate_button page-item previous' id='selection-datatable_previous'><a href='javascript:void(0)' aria-controls='selection-datatable' data-dt-idx='6' tabindex='0' class='page-link' onclick=\"butListPaging('" + (end + 1) + "','"+bct_name+"','"+search+"')\">");
			sb.append("<i class='mdi mdi-chevron-right'></i></a></li>");
		} // if End
		sb.append("</ul>");
		sb.append("</nav>");
		return sb.toString();
	}
	
	
} // class End