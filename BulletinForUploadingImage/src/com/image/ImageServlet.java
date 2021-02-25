/*==========================
 	ImageServlet.java
============================*/

package com.image;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.util.FileManager;
import com.util.MyUtil;

public class ImageServlet extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		process(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		process(request,response);
	}
	
	private void forward(HttpServletRequest request, HttpServletResponse response, String path) throws ServletException, IOException
	{
		// 포워딩
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

	protected void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		
		String uri = request.getRequestURI();
		
		MyUtil util = new MyUtil();
		String cp = request.getContextPath();
		ImageDAO dao = new ImageDAO();
		
		if (uri.indexOf("created.action") != -1)
		{
			// 로그인 여부 확인
			HttpSession session = request.getSession();
			
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			if(info == null)
			{
				request.setAttribute("msg", "로그인이 필요한 서비스입니다.");
				
				String path = "/join/login.action";
				forward(request, response, path);
				return;
			
			}
			
			String path = "/imageBoard/created.jsp";
			forward(request, response, path);
			
		} 
		else if(uri.indexOf("created_ok.action") != -1)
		{
			HttpSession session = request.getSession();
			
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			if(info == null)
			{
				request.setAttribute("msg", "로그인이 필요한 서비스입니다.");
				
				String path = "/join/login.action";
				forward(request, response, path);
				return;
			}
			
			String root = session.getServletContext().getRealPath("/");
			String savePath = root + File.separator + "pds" + File.separator + "image"; 
			
			File dir = new File(savePath);
			
			if(!dir.exists())
				dir.mkdirs();
			
			String encType = "UTF-8";
			int maxFileSize = 5*1024*1024;
			
			try
			{
				MultipartRequest req = null;
				req = new MultipartRequest(request, savePath, maxFileSize, encType, new DefaultFileRenamePolicy());
				
				String subject = req.getParameter("subject");
				String saveFileName = req.getFilesystemName("upload"); // ! 주의 multi.getFilesystemName();
				
				ImageDTO dto = new ImageDTO();
				int maxNum = dao.getNumMax();
				
				//테스트
				//System.out.println(saveFileName); -- NULL
				
				dto.setNum(maxNum+1);
				dto.setUserId(info.getUserId());
				dto.setSaveFileName(saveFileName);
				dto.setSubject(subject);
				
				dao.insertData(dto);
				
			} catch (Exception e)
			{
				System.out.println(e.toString());
			}
			
			response.sendRedirect(cp + "/image/list.action");
			
		}
		else if(uri.indexOf("list.action") != -1)
		{
			// 페이지 번호
			String pageNum = request.getParameter("pageNum");
			int currentPage = 1;
			if(pageNum != null && pageNum.length() != 0)
			{
				currentPage = Integer.parseInt(pageNum);
			}
			
			String searchKey = null;
			String searchValue = null;
			
			searchKey = request.getParameter("searchKey");
			searchValue = request.getParameter("searchValue");
			if(searchKey == null)
			{
				searchKey = "subject";
				searchValue = "";
			}
			
			if(request.getMethod().equalsIgnoreCase("GET"))
			{
				searchValue = URLDecoder.decode(searchValue, "UTF-8");
			}
				
			// 전체 데이터 갯수
			int dataCount = dao.getTotalDataCount(searchKey, searchValue);
			
			// 테스트
			//System.out.println("dataCount: " + dataCount); --1 // DAO 구성 이상하게 되어있음..
			
			// 전체 페이지 수 구하기
			int numPerPage = 6;
			int totalPage = util.getPageCount(numPerPage, dataCount);
			
			
			// 전체 페이지 수보다 현재 표시할 페이지가 큰 경우
			if(totalPage < currentPage)
			{
				currentPage = totalPage;
			}
			
			// 테이블에서 가져올 리스트의 시작과 끝 위치
			int start = (currentPage - 1) * numPerPage + 1; 
			int end = currentPage * numPerPage;
			
			System.out.println("start : " + start);
			System.out.println("end : " + end);
			
			// 테이블에서 리스트를 출력할 데이터 가져오기
			List<ImageDTO> lists = dao.getListData(start, end, searchKey, searchValue);
			
			// 테스트
			// System.out.println(lists.size()); -- 0
			
			String params = "";
			if(searchValue != null && searchValue.length() != 0)
			{
				searchValue = URLEncoder.encode(searchKey, "UTF-8");
				params = "searchKey=" + searchKey + "&searchValue=" + searchValue; 
			}		
			
			// 페이징 처리
			String listUrl = cp + "/image/list.action";
			if(params.length() != 0)
			{
				listUrl += "?" + params;
			}
			
			String pageIndexList = util.pageIndexList(currentPage, totalPage, listUrl);
			
			String imagePath = cp +"/pds/image";
			
			// 포워딩할 list.jsp 에 넘겨준다.
			request.setAttribute("lists", lists);
			request.setAttribute("pageIndexList", pageIndexList);
			request.setAttribute("totalPage", totalPage);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("imagePath", imagePath);
			request.setAttribute("dataCount", dataCount);
			
			String path = "/imageBoard/list.jsp";
			forward(request, response, path);
			
		}
		else if(uri.indexOf("delete.action") != -1)
		{
			HttpSession session = request.getSession();
			
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			if(info == null)
			{
				request.setAttribute("msg", "로그인이 필요한 서비스입니다.");
				
				String path = "/join/login.action";
				forward(request, response, path);
				return;
			}
			
			int num = Integer.parseInt(request.getParameter("num"));
			String pageNum = request.getParameter("pageNum");
			
			// 파일 지우기
			String root = session.getServletContext().getRealPath("/");
			String savePath = root + File.separator + "pds" + File.separator +  "image";
			
			ImageDTO dto = dao.getReadData(num);
			if(dto != null)
			{
				FileManager.doFileDelete(dto.getSaveFileName(), savePath); // ! 메소드 확인하기
			}
			
			dao.deleteData(num, info.getUserId());
			
			response.sendRedirect(cp+"/image/list.action?pageNum=" + pageNum);
					
				
					
		}
			
		
	}
}
