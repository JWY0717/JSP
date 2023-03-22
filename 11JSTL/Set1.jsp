<%@page import="common.Person"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta charset="UTF-8">
<title>STL-set 1</title>
</head>
<body>
	<!-- 변수 선언 -->
	<c:set var ="directVar" value ="100" />
	<c:set var ="elVar" value ="${ directVar mod 5}" />
	<c:set var ="expVar" value ="<%= new Date() %>" />
	<c:set var ="betweenVar">변수값 요렿게 설정</c:set>
	
	<h4>EL을 이용해 변수 출력</h4>
	<ul>
		<li>directVar : ${ pageScope.directVar }</li>
		<li>elVar :  ${ elVar }</li>
		<li>expVar : ${ expVar }</li>
		<li>betweenVar : ${ betweenVar }</li>
	</ul>
	
	<h4>자바빈즈 생성 1 - 생성자 사용</h4>
	<c:set var="personVar1" value='<%= new Person("박문수", 50) %>'scope="request" />
	<ul>
		<li>이름 :  ${ requestScope.personVar1.name }</li>
		<li>나이 :  ${ personVar1.age }</li>
	</ul>
	
	<h4>자바빈즈 생성 2 - target, property 사용</h4>
	<!-- c:set은 prefix="c"에서 온 것이다 -->
	<!-- var1는 변수, value는 변수에 할당된 값, scope는 변수의 생성할 영역을 지정합니다. -->
	<c:set var="personVar2" value="<%= new Person() %>" scope="request" />
	<c:set target="${ personVar2 }" property="name" value="정약용"  />
	<c:set target="${ personVar2 }" property="age" value="60"  />
	<ul>
		<li>이름 : ${ personVar2.name }</li>
		<li>나이 : ${ requestScope.personVar2.age }</li>
	</ul>
</body>
</html>