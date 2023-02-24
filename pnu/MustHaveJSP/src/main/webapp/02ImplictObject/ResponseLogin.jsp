<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내장 객체 - Response</title>
</head>
<body>
<%
String id = request.getParameter("user_id");
//if (id==null) 
//	id="must";

String pwd = request.getParameter("user_pwd");
//if (pwd==null) 
//	pwd="1234452";

if(id.equalsIgnoreCase("must") && pwd.equalsIgnoreCase("1234")) {
	response.sendRedirect("ResponseWelcome.jsp");
	
}
else {
	request.getRequestDispatcher("ResponseMain.jsp?loginErr=1")
		.forward(request, response);
}

%>
</body>
</html>