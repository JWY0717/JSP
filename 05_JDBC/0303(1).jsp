<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.sql.Statement"%>
<%@ page import="java.sql.Connection"%>
<%@ page import="common.JDBConnect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JDBC</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
</head>
<body>
<h2>도시 구현 테스트(executeUpdate() 사용)</h2>
	
	<%
	// DB에 연결
	JDBConnect jdbc = new JDBConnect();
	//쿼리문 생성
	String sql = "SELECT * FROM city limit 20";
	Statement stmt = jdbc.con.createStatement();
	
	//쿼리 수행
	ResultSet rs = stmt.executeQuery(sql);
	
	%>
	
	<table class="table table-striped">
	 <thead>
	    <tr>
	      <th scope="col">ID</th>
	      <th scope="col">Name</th>
	      <th scope="col">CountryCode</th>
	      <th scope="col">District</th>
	      <th scope="col">Population</th>
	    </tr>
	  </thead>
	
	
	
	<tbody>
	
	
	<%
	//결과 확인(웹 페이지에 출력)
	
	
	while (rs.next()) {
		String ID = rs.getString(1);
		String Name = rs.getString(2);
		String CountryCode = rs.getString(3);
		String District = rs.getString(4);
		String Population = rs.getString(5);
		//out.println(String.format("%s %s %s %s", id,pw,name,regidate) +
		//"<br/>");
	%>

	<tr>
		<td><%= ID  %></td>
		<td><%= Name %></td>
		<td><%= CountryCode %></td>
		<td><%= District %></td>
		<td><%= Population %></td>
	</tr>
	<%
	}
	%>
	
	</tbody>
	</table>
	
	
	<% 
	//연결 닫기 
	jdbc.close(); 
	%>
	
</body>
</html>
