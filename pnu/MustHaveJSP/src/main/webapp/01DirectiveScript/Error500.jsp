<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>page 지시어 - errorPage, isErrorPage 속성</title>
</head>
<body>
<%
int myAge = Integer.parseInt(request.getParameter("age")) + 10;  //에러 발생
out.println("10년 후 당신의 나이는 " + myAge + "입니다.");			// 실행되지 않음
%>
<hr>
<% 
String name = request.getParameter("name");//에러 발생
out.println("당신의 이름은  " + name + "입니다.");			// 실행되지 않음
%>
</body>
</html>