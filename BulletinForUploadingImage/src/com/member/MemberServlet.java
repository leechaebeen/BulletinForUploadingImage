/*========================
 	MemberServlet.java
==========================*/
package com.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MemberServlet extends HttpServlet
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
		process(request, response);
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
		
		// 업무 구분 : uri → cp 부터 끝까지...
		String uri = request.getRequestURI();
		
		String cp = request.getContextPath();
		MemberDAO dao = new MemberDAO();
		
		
		if(uri.indexOf("login.action") != -1)
		{
			// 로그인 폼 제공
			String path = "/member/login.jsp";
			forward(request, response, path);
			
		}
		else if(uri.indexOf("login_ok.action")!= -1)
		{
			// 로그인 액션 처리
			HttpSession session = request.getSession(true);
			//-- session 객체를 생성하는 과정에서 true 값을 넘기게 되면
			//   세션을 초기화하여
			//	 다시 생성하게 만든다.
			//	 즉, 로그인을 수행할 때에만 true 값을 부여해야 한다.
			
			String userId = request.getParameter("userId");
			String userPwd = request.getParameter("userPwd");
			
			MemberDTO dto = dao.getReadData(userId);
			if(dto == null || !dto.getUserPwd().equals(userPwd))
			{
				request.setAttribute("msg", "아이디 또는 패스워드가 맞지 않습니다.");
				
				String path = "/member/login.jsp";
				forward(request, response, path);
				return;
			}
			
			// 여기서부터는 로그인 성공한 경우
			
			// 세션에 저장할 객체 구성(사용자 정의 객체 SessionInfo)
			SessionInfo info = new SessionInfo();
			info.setUserId(dto.getUserId());
			info.setUserName(dto.getUserName());
			
			
			// 에러 잡기 위한 테스트...
			// System.out.println(dto.getUserName());
			// → null..
			// rs 에서 안받아옴...
			
			// 세션에 저장
			session.setAttribute("member", info);
			
			// 메인 페이지를 다시 요청할 수 있도록 안내
			response.sendRedirect(cp);
			
		}
		else if(uri.indexOf("logout.action") != -1)
		{
			// 로그아웃 액션 처리
			HttpSession session = request.getSession();
			
			session.removeAttribute("member");
			session.invalidate();
			
			// 로그아웃 이후 다시 요청할 수 있는 페이지 안내
			response.sendRedirect(cp);
		}
		
		
		if(uri.indexOf("created.action") != -1)
		{
			request.setAttribute("mode", "created");
			String path = "/member/member.jsp";
			
			forward(request, response, path);
			
		}
		else if(uri.indexOf("created_ok.jsp") != -1)
		{
			MemberDTO dto = new MemberDTO();
			
			String tel = "";
			tel = request.getParameter("tel1") + "-"
					+ request.getParameter("tel2") + "-"
					+ request.getParameter("tel3");
			
			String email = "";
			email = request.getParameter("email1") + "@"
					 + request.getParameter("email2");
			
			dto.setUserId(request.getParameter("userId"));
			dto.setUserName(request.getParameter("userName"));
			dto.setUserPwd(request.getParameter("userPwd"));
			dto.setTel(tel);
			dto.setEmail(email);

			int result = dao.insertData(dto);
			
			if(result == 0)
			{
				request.setAttribute("message", "회원 가입에 실패하였습니다.");
				request.setAttribute("mode", "created");
				String path = "/member/member.jsp";
				forward(request, response, path);
				return;
			}
			
			response.sendRedirect(cp);
			
		}
		else if (uri.indexOf("update.action") != -1)
		{
			// 회원 수정 폼 구성
			HttpSession session = request.getSession();
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			
			if(info == null)
			{
				// 로그인이 되어있지 않은 상황
				request.setAttribute("msg", "로그인이 필요한 서비스입니다.");
				
				String path = "/join/login.action";
				forward(request, response, path);
				return;
			}
			
			// 로그인이 되어 있는 상황
			
			MemberDTO dto = dao.getReadData(info.getUserId());
			if (dto == null)
			{
				// 넘겨받은 세션 정보로 회원 데이터를 불러올 수 없는 상황
				// (! ex 로그인 한 이후에 서버쪽에서 관리자가 해당 아이디를 지운 경우)
				request.setAttribute("msg", "정상적인 로그인이 필요합니다.");
				String path = "/join/login.action";
				forward(request, response, path);
				return;
			}
			
			// 넘겨받은 세션 정보로 회원 데이터를 정상적으로 불러온 상황
			
			String tel[] = dto.getTel().split("-");
			if(tel != null && tel.length == 3)
			{
				dto.setTel1(tel[0]);
				dto.setTel2(tel[1]);
				dto.setTel3(tel[2]);
			}
			
			String email[] = dto.getEmail().split("@");
			if(email != null && email.length == 2)
			{
				dto.setEmail1(email[0]);
				dto.setEmail2(email[1]);
			}
			
			request.setAttribute("dto", dto);
			request.setAttribute("mode", "update");

			String path = "/member/member.jsp";
			forward(request, response, path);
			
		}
		else if(uri.indexOf("update_ok.action") != -1)
		{
			// 회원 정보 수정 액션 처리
			HttpSession session = request.getSession();
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			if(info == null)
			{
				request.setAttribute("msg", "로그인이 필요한 서비스입니다.");
				String path = "/join/login.action";
				forward(request, response, path);
				return;
			}
			
			MemberDTO dto = new MemberDTO();
			
			String tel = "";
			tel = request.getParameter("tel1") + "-"
					+ request.getParameter("tel2") + "-"
					+ request.getParameter("tel3");
			
			String email = "";
			email = request.getParameter("email1") + "@"
					+ request.getParameter("email2");
			dto.setUserId(request.getParameter("userId"));
			dto.setUserPwd(request.getParameter("userPwd"));
			dto.setTel(tel);
			dto.setEmail(email);
			
			dao.updateData(dto);
			
			response.sendRedirect(cp);
		}
		else if(uri.indexOf("delete.action") != -1)
		{
			// 회원 탈퇴 액션 처리
			HttpSession session = request.getSession();
			SessionInfo info = (SessionInfo)session.getAttribute("member");
			if(info == null)
			{
				request.setAttribute("msg", "로그인이 필요한 서비스입니다.");
				String path = "/join/login.action";
				forward(request, response, path);
				return;
			}
				
			
			// 데이터베이스 삭제 액션 처리
			dao.deleteData(info.getUserId());
			
			session.removeAttribute("member");
			session.invalidate();
			
			response.sendRedirect(cp);
			
		}
				
	}
}
