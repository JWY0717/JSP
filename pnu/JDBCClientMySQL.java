package edu.pnu;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;

public class JDBCClientMySQL {

	public static void main(String[] args) throws Exception {
	
	//Build Path => classPath => Add External Jars => connector-j	
		
	// MYSQL 접속을 위한 JDBC 드라이버 로드
	Class.forName("com.mysql.cj.jdbc.Driver");
	
	// MYSQL Database 연결 객체 생성  // DriverManager가 있고 getConnection static 메소드가 있는 것! 
	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/world", "musthave", "tiger");
	
	//질의를 위한 객체 생성  //connection 객체에 질의를 할 수 있는 객체 생성
	Statement st = con.createStatement();
	
	//SQL 질의  
	ResultSet rs = st.executeQuery("select Name,Continent,Population,HeadOfState from country");
	
	// 질의 결과 Pars //cursor Processing
	while(rs.next()) {   //다음 결과 레코드로 이동!!!!
		
		//1~4번째 값을 찍는다!
		for(int i =1; i <= 4 ; i++) {
			// 2,3,4번째 값에 ,를 찍는다
			if(i !=1) System.out.print(",");
			// 1,2,3,4번째 값을 찍는다 
			System.out.print(rs.getString(i));
			
		}
		System.out.println();
		System.out.println();
	}
	//생성된 객체 연결을 모두 해제
	rs.close();
	st.close();
	con.close();
	
	
	
	}

}
