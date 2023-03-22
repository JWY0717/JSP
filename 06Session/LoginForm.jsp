<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Session</title>
</head>
<body>
	<jsp:include page="../Common/Link.jsp" />
	<h2>로그인 페이지</h2>
	<span style="color: red; font-size: 1.2em;"> <%=request.getAttribute("LoginErrMsg") == null ? //클라이언트로 부터 요청이 온 것을 LoginErrMsg값이 null이면 아무것도 나오지 않고 값이 있으면 LoginErrMsg정보 출력
		"" : request.getAttribute("LoginErrMsg")%>
	</span>
	<%
	if (session.getAttribute("UserId") == null) {
	%>
	<script>
		function validateForm(form) {
			if (!form.user_id.value) {
				alert("아이디를 입력하시오.");
				return false;
			}
			if (form.user_pw.value == "") {
				alert("패스워드를 입력하세요.");
				return false;
			}
		}
	</script>
	<form action="LoginProcess.jsp" method="post" name="loginFrm"
		onsubmit="return validateForm(this);">
		아이디 : <input type="text" name="user_id" /><br /> 패스워드 : <input
			type="password" name="user_pw" /><br /> <input type="submit"
			name="로그인하기" />
	</form>
	<%
	//로그인이 완성이 되면 action(LoginProcess.jsp)으로 간다!

	} else { //로그아웃 페이지
		//UserId으 정보를 읽었는데 정보가 있었을 때의 경우
	%>
	<%=session.getAttribute("UserName")%>
	회원님, 로그인하셨습니다.
	<br />
	<a href="Logout.jsp">[로그아웃]</a>
	<%
	}
	%>
</body>
</html>