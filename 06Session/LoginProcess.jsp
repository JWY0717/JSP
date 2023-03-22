<%@page import="membership.MemberDTO"%>
<%@page import="membership.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
<%
//로그인 폼으로부터 받은 아이디와 패스워드
String userId = request.getParameter("user_id");
String userPwd = request.getParameter("user_pw");

//sql musthave에서 가져온 데이터베이스 연결 정보
String driver = application.getInitParameter("MySQLDriver");
String url = application.getInitParameter("MySQLURL");
String id = application.getInitParameter("MySQLId");
String pwd = application.getInitParameter("MySQLPwd");

MemberDAO dao = new MemberDAO(driver,url,id,pwd);
//MemberDAO dao = new MemberDAO();
MemberDTO memberDTO = dao.getMemberDTO(userId, userPwd);
dao.close();

//로그인 성공 실패 관련 조건문 //userId.equals("musthave") && userPwd.equals("1234")
if(memberDTO.getId() != null) {
	session.setAttribute("UserId",memberDTO.getId());
	session.setAttribute("UserName",memberDTO.getName());
	response.sendRedirect("LoginForm.jsp");
}
else {
	request.setAttribute("LoginErrMsg","로그인 오류입니다.");
	request.getRequestDispatcher("LoginForm.jsp").forward(request,response);
}
%>



