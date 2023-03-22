<%@page import="model.board.BoardDTO"%>
<%@page import="model.board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./IsLoggedIn.jsp"%>

<%
request.setCharacterEncoding("UTF-8");
// 품값 받기
String title = request.getParameter("title");
String content = request.getParameter("content");

BoardDTO dto = new BoardDTO();
dto.setTitle(title);
dto.setContent(content);
dto.setId(session.getAttribute("UserId").toString());


BoardDAO dao = new BoardDAO(application);
//int iResult = dao.insertWrite(dto);
int iResult = 0;
for (int i = 1; i <= 100; i++) {
	dto.setTitle(title + "-" + i);
	iResult = dao.insertWrite(dto);
}
dao.close();

//성공 or 실패??
if (iResult == 1) {
	response.sendRedirect("List.jsp");
} else {
	JSFunction.alertBack("글쓰기에 실패하였습니다.", out);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>